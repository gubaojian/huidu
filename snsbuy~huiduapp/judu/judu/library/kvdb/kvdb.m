#import "KVDB.h"
#import "KVDB_Private.h"

#import "KVDBFunctions.h"

#if !__has_feature(objc_arc)
#error KVDB must be built with ARC.
#endif

typedef void(^KVBlock)(void);
typedef void(^KVDictBlock)(NSDictionary *dict);

static int kvdbQueryCallback(void *resultBlock, int argc, char **argv, char **column);

#define kKVDBTableName @"kvdb"

@implementation KVDB

#define kDefaultSQLFile @"kvdb.sqlite3"

static NSMutableDictionary *_kvdbMap = nil;

- (id)init {
    NSString *reason = [NSString stringWithFormat:@"-[%@ init] must not be called directly. Use designated initializers instead: -[%@ initWithSQLFile:] or -[%@ initWithSQLFile:inDirectory:].", [self class], [self class], [self class]];
    @throw [NSException exceptionWithName:NSGenericException reason:reason userInfo:nil];
}

- (void)dealloc {
    self.file = nil;
    self.isolationQueue = nil;
}

- (void)setIsolationQueue:(dispatch_queue_t)isolationQueue {
#if !OS_OBJECT_USE_OBJC
    if (_isolationQueue) dispatch_release(_isolationQueue);

    if (isolationQueue) {
        dispatch_retain(isolationQueue);
    }
#endif

    _isolationQueue = isolationQueue;
}

#pragma mark - Public API: Initialization

- (instancetype)initWithSQLFile:(NSString *)sqliteFile {
    self = [self initWithSQLFile:sqliteFile inDirectory:KVDocumentsDirectory()];

    if (self == nil) return nil;

    return self;
}

- (instancetype)initWithSQLFile:(NSString *)sqliteFile inDirectory:(NSString *)directory {
    self = [super init];

    if (self == nil) return nil;

    self.file = [directory stringByAppendingPathComponent:sqliteFile];
    self.isolationQueue = dispatch_queue_create("com.queue.kvdb", DISPATCH_QUEUE_SERIAL);
    self.isAccessToDatabaseIsolated = NO;

    NSLog(@"Initializing Shared DB with file: %@", self.file);
    [self createDatabase];

    return self;
}

+ (instancetype)sharedDB {
    return [KVDB sharedDBUsingFile:kDefaultSQLFile];
}

+ (instancetype)sharedDBUsingFile:(NSString *)file {
       return [KVDB sharedDBUsingFile:file inDirectory:KVDocumentsDirectory()];
}

+ (instancetype)sharedDBUsingFile:(NSString *)file inDirectory:(NSString *)directory {
    NSString* key = [NSString stringWithFormat:@"%@_%@", file, directory];
    KVDB* kvdb = [[KVDB kvdbMap] objectForKey:key];
    if (kvdb == nil) {
        @synchronized(self) {
            kvdb = [[KVDB kvdbMap] objectForKey:key];
            if (kvdb == nil) {
                kvdb = [[self alloc] initWithSQLFile:file inDirectory:directory];
                [[KVDB kvdbMap] setObject:kvdb forKey:key];
            }
        }
    }
    return kvdb;
}

+ (void)resetDB {
    if (_kvdbMap != nil) {
        [_kvdbMap removeAllObjects];
        _kvdbMap = nil;
    }
}

+ (NSMutableDictionary*)kvdbMap{
    if (_kvdbMap == nil) {
        _kvdbMap = [[NSMutableDictionary alloc] initWithCapacity:4];
    }
    return _kvdbMap;
}

#pragma mark - Public API: Creating and dropping database

- (void)createDatabase {
    sqlite3 *db = [self _openDatabase];

    [self _createKVDBTableIfNotExistsInDB:db];
    [self _ensureKVDBTableExistsInDB:db];
    [self _cleanOutOfDateDataInDB:db secondsOffset:-(60*60*24*10)];
    [self _closeDatabase:db];
}

- (void)dropDatabase {
    NSFileManager *fileManager = [NSFileManager defaultManager];
    NSError *error;

    if ([fileManager fileExistsAtPath:self.file]) {
        [fileManager removeItemAtPath:self.file error:&error];
        if (error) @throw KVDBExceptionWrite();
    }
}

#pragma mark - Public API: Isolated access to database

- (void)performBlock:(void(^)(id DB))block {
    dispatch_async(self.isolationQueue, ^{
        self.isolatedAccessDatabase = [self _openDatabase];
        self.isAccessToDatabaseIsolated = YES;

        block(self);

        self.isAccessToDatabaseIsolated = NO;
        [self _closeDatabase:self.isolatedAccessDatabase];
        self.isolatedAccessDatabase = nil;
    });
}

- (void)performBlockAndWait:(void(^)(id DB))block {
    dispatch_sync(self.isolationQueue, ^{
        self.isolatedAccessDatabase = [self _openDatabase];
        self.isAccessToDatabaseIsolated = YES;

        block(self);

        self.isAccessToDatabaseIsolated = NO;
        [self _closeDatabase:self.isolatedAccessDatabase];
        self.isolatedAccessDatabase = nil;
    });
}

#pragma mark - Public API: NSKeyValueCoding

- (void)setValue:(id)value forKey:(NSString *)key {
    if (value == nil) {
        NSString *reasonString = [NSString stringWithFormat:@"%s : value cannot be nil - use NSNull instead!", __PRETTY_FUNCTION__];
        @throw [NSException exceptionWithName:NSInternalInconsistencyException reason:reasonString userInfo:nil];
    }

    [self _performAccessToDatabaseWithBlock:^(sqlite3 *database) {
        [self _queryDatabase:database
                   statement:[self _upsertQueryWithKey:key]
                        data:[self archiveObject:value]
                      result:^(BOOL success, NSDictionary *result) {
                          if (!success) {
                             NSLog(@"Sqlite Insert Error %@", result);
                          }
                      }];
    }];
}

- (id)valueForKey:(NSString *)key {
    __block NSDictionary *value;

    [self _performAccessToDatabaseWithBlock:^(sqlite3 *database) {
        NSArray *values = [self _queryDatabase:database statement:[self _selectQueryForKey:key]];

        if (values) value = [values objectAtIndex:0];
        if (value) value = [value objectForKey:@"value"];
    }];

    return value;
}

- (void)removeValueForKey:(NSString *)key {
    [self _performAccessToDatabaseWithBlock:^(sqlite3 *database) {
        [self _queryDatabase:database statement:[self _deleteQueryForKey:key]];
    }];
}

- (void)setObject:(id)object forKey:(NSString *)key {
    [self setValue:object forKey:key];
}

- (void)removeObjectForKey:(NSString *)key {
    [self removeValueForKey:key];
}

- (id)objectForKey:(NSString *)key {
    return [self valueForKey:key];
}

- (NSArray *)allObjects {
    __block id value;

    [self _performAccessToDatabaseWithBlock:^(sqlite3 *database) {
        value = [self _queryDatabase:database statement:[NSString stringWithFormat:@"SELECT key, value FROM %@", kKVDBTableName]];
    }];

    return value;
}

- (NSUInteger)count {
    __block NSInteger count = 0;

    [self _performAccessToDatabaseWithBlock:^(sqlite3 *database) {
        NSArray *records;

        records = [self _queryDatabase:database statement:[NSString stringWithFormat:@"Select count(*) as value from %@", kKVDBTableName]];

        if (records) {
            // TODO

            count = [[[records objectAtIndex:0] objectForKey:@"key"] intValue];
        }
    }];

    return count;
}

-(BOOL)isExist:(NSString*)key{
        __block NSInteger count = 0;
        
        [self _performAccessToDatabaseWithBlock:^(sqlite3 *database) {
            NSArray *records;
            
            records = [self _queryDatabase:database statement:[NSString stringWithFormat:@"Select count(*) as value from %@ where key= '%@' ", kKVDBTableName, key]];
            
            if (records) {
                // TODO
                count = [[[records objectAtIndex:0] objectForKey:@"key"] intValue];
            }
        }];
        
        return count > 0;
}

#pragma mark - Private API

- (void)_performAccessToDatabaseWithBlock:(void(^)(sqlite3 *database))databaseAccessBlock {
    sqlite3 *DB;

    if (self.isAccessToDatabaseIsolated) {
        DB = self.isolatedAccessDatabase;
    } else {
        DB = [self _openDatabase];
    }

    databaseAccessBlock(DB);

    if (self.isAccessToDatabaseIsolated == NO) {
        [self _closeDatabase:DB];
    }
}

#pragma mark - Private API: SQLITE methods

- (sqlite3 *)_openDatabase {
    sqlite3 *db = NULL;

    const char *dbpath = [self.file UTF8String];
    if (sqlite3_open(dbpath, &db) != SQLITE_OK) {
        @throw KVDBExceptionDBOpen();
    }

    return db;
}

- (void)_closeDatabase:(sqlite3 *)db {
    sqlite3_close(db);
}

/* Returns an array of rows */
- (NSArray *)_queryDatabase:(sqlite3 *)db statement:(NSString *)statement {
    const char *sql = [statement UTF8String];
    const char *tail;
    sqlite3_stmt *stmt;
    int result = sqlite3_prepare_v2(db, sql, -1, &stmt, &tail);
    if (result != SQLITE_OK) {
        return nil; /* No data found. */
    }

    NSMutableArray *array = nil;
    while (sqlite3_step(stmt) == SQLITE_ROW) {
        if (array == nil) array = [NSMutableArray array];

        const char *keyText = (const char *)sqlite3_column_text(stmt, 0);
        if (keyText == nil) continue;

        NSString *key = [NSString stringWithCString:keyText encoding:NSUTF8StringEncoding];
        NSData *blob = [NSData dataWithBytes:sqlite3_column_blob(stmt, 1) length:sqlite3_column_bytes(stmt, 1)];

        NSMutableDictionary *rowDict = [NSMutableDictionary dictionaryWithObject:key forKey:@"key"];

        if ([blob length]) {
            id value = [self unarchiveData:blob];
            [rowDict setObject:value forKey:@"value"];
        }

        [array addObject:rowDict];
    }

    sqlite3_finalize(stmt);

    return array;
}

/* Doesn't use blobs, so simply queries. */
- (void)_queryDatabase:(sqlite3 *)db statement:(NSString *)statement result:(void (^)(NSDictionary *))resultBlock {
    char *errMsg;

    int result = sqlite3_exec(db, [statement UTF8String], kvdbQueryCallback, (__bridge void *)(resultBlock), &errMsg);

    if (result != SQLITE_OK) {
        NSString *errorMsg = [[NSString alloc] initWithUTF8String:errMsg];

        sqlite3_free(errMsg);
        resultBlock([NSDictionary dictionaryWithObject:errorMsg forKey:@"error"]);
    }
}

/* Writes blobs, so it uses transactions */
- (void)_queryDatabase:(sqlite3 *)db statement:(NSString *)statement data:(NSData *)data result:(void (^)(BOOL success, NSDictionary *))resultBlock {

    // @todo this is totally inflexible to argument count
    const char *sql = [statement UTF8String];
    sqlite3_stmt *stmt;

    if ((sqlite3_prepare_v2(db, sql, -1, &stmt, NULL) == SQLITE_OK)) {
        sqlite3_bind_blob(stmt, 1, [data bytes], [data length], SQLITE_STATIC);
    }

    int status = sqlite3_step(stmt);
    if (status != SQLITE_DONE) {
        const char *errMsg = sqlite3_errmsg(db);

        NSString *errorMsg = [[NSString alloc] initWithUTF8String:errMsg];

        resultBlock(NO, [NSDictionary dictionaryWithObject:errorMsg forKey:@"error"]);
    }

    sqlite3_finalize(stmt);

    resultBlock(YES, [NSDictionary dictionaryWithObjectsAndKeys:
                      [NSNumber numberWithLongLong:sqlite3_last_insert_rowid(db)], @"lastRowID",
                      [NSNumber numberWithInt:sqlite3_changes(db)], @"rowsChanged"
                      , nil]);
}

- (void)_createKVDBTableIfNotExistsInDB:(sqlite3 *)db {
    [self _queryDatabase:db statement:[NSString stringWithFormat:@"CREATE TABLE IF NOT EXISTS %@ (key TEXT PRIMARY KEY, value BLOB, gmt_create INTEGER);", kKVDBTableName]];
    [self _queryDatabase:db statement:[NSString stringWithFormat:@"CREATE INDEX IF NOT EXISTS \"gmt_create_index\"  ON %@ (\"gmt_create\" ASC);", kKVDBTableName]];
}
    
-(void)_cleanOutOfDateDataInDB:(sqlite3*)db secondsOffset:(NSInteger) secondsOffset{
    [self _queryDatabase:db statement:[NSString stringWithFormat:@"DELETE FROM %@ where gmt_create <= %d;", kKVDBTableName, [self _secondsFromReferenceDate] + secondsOffset]];
}

-(NSInteger)_secondsFromReferenceDate{
    return  (NSInteger)[NSDate timeIntervalSinceReferenceDate];
}
    
- (void)_ensureKVDBTableExistsInDB:(sqlite3 *)db {
    NSString *statement = @"SELECT name FROM sqlite_master WHERE type='table' ORDER BY name;";

    [self _queryDatabase:db statement:statement result:^(NSDictionary *result) {
        if ([[result objectForKey:@"name"] isEqualToString:kKVDBTableName] == NO) {
            @throw [NSException exceptionWithName:@"SQLITEError" reason:[NSString stringWithFormat:@"There should have been a table called %@.", kKVDBTableName] userInfo:nil];
        }
    }];
}

#pragma mark - Data/query methods

/* Upsert via SO contributor Eric B;
 Updates or inserts safely.
 http://stackoverflow.com/questions/418898/sqlite-upsert-not-insert-or-replace/4253806#4253806
 */
- (NSString *)_upsertQueryWithKey:(NSString *)key {
    return [NSString stringWithFormat:@"INSERT OR REPLACE INTO `%@` (`key`,`value`, 'gmt_create')" // table
            "VALUES ( '%@', ?, %d); COMMIT;",
            kKVDBTableName, key, [self _secondsFromReferenceDate]];
}

- (NSString *)_selectQueryForKey:(NSString *)key {
    return [NSString stringWithFormat:@"SELECT key, value FROM %@ WHERE key='%@'", kKVDBTableName, key];
}

- (NSString *)_deleteQueryForKey:(NSString *)key {
    return [NSString stringWithFormat:@"DELETE FROM %@ WHERE key='%@'", kKVDBTableName, key];
}
    


/* Call this function with a sqlite3_blob* initialized to NULL. */
- (void)_writeObject:(id)objC inDatabase:(sqlite3*)DB toBlob:(sqlite3_blob**)blob {

    if (*blob != NULL) {
        @throw [NSException exceptionWithName:@"SQLITEError" reason:@"Can only write to NULL blobs." userInfo:nil];
    }

    // Opening the blob with no data
    sqlite3_blob_open(DB, NULL, NULL, NULL, 0, 1 /* Open for writing */, blob);

    // Objects must conform to NSCoding since we are using NSKeyedArchiver for data serialization.
    if (![objC conformsToProtocol:@protocol(NSCoding)]) {
        @throw KVDBExceptionNoCoding(objC);
    }

    NSData *data = [self archiveObject:objC];
    int byteCt = [data length];
    Byte *byteData = (Byte*)malloc(byteCt);
    memcpy(byteData, [data bytes], byteCt);
    sqlite3_blob_write(*blob, byteData, byteCt, 0);
    free(byteData);
}

/* Call this function with a sqlite3_blob * initialized to NULL. */
- (NSData *)_readBlobFromDatabaseNamed:(NSString *)dbName tableName:(NSString *)tableName columnName:(NSString *)columnName rowID:(sqlite3_int64)rowID blob:(sqlite3_blob **)blob {
    if (*blob != NULL) {
        @throw [NSException exceptionWithName:@"SQLITEError" reason:@"Can only read to NULL blobs." userInfo:nil];
    }

    int status = sqlite3_blob_open([self _openDatabase], [dbName UTF8String], [tableName UTF8String], [columnName UTF8String], rowID, 0 /* Open for reading */, blob);

    if (status != SQLITE_OK) {
        @throw KVDBExceptionDBRead();
    }

    int byteCt = sqlite3_blob_bytes(*blob);
    Byte byteBuff[byteCt];

    return [NSData dataWithBytes:byteBuff length:byteCt];
}

- (NSData *)archiveObject:(id)object {
    return [NSKeyedArchiver archivedDataWithRootObject:object];
}

- (id)unarchiveData:(NSData *)data {
    if (data == nil) return nil;

    return [NSKeyedUnarchiver unarchiveObjectWithData:data];
}

@end

#pragma mark - Sqlite3 callback
int kvdbQueryCallback(void *resultBlock, int argc, char **argv, char **column) {
    // converts row to an nsdictionary

    NSMutableDictionary *row = [NSMutableDictionary dictionary];

    for (int i=0; i< argc; i++) {
        NSString *columnName = [NSString stringWithCString:column[i] encoding:NSUTF8StringEncoding];

        id value = nil;

        if ([columnName isEqualToString:@"value"] == NO) {
            value = [NSString stringWithCString:argv[i] encoding:NSUTF8StringEncoding];
        } else {
            sqlite3_int64 rowID = 0;
            sqlite3_blob *blob = NULL;

            NSData *data = [[KVDB sharedDB] _readBlobFromDatabaseNamed:@"main"
                                                             tableName:kKVDBTableName
                                                            columnName:@"value"
                                                                 rowID:rowID
                                                                  blob:&blob];

            // Revive object from NSKeyedArchiver
            if (data != nil)
                value = [NSKeyedUnarchiver unarchiveObjectWithData:data];
        }
        
        if (value != nil) [row setObject:value forKey:columnName];
    }
    
    KVDictBlock objcBlk = (__bridge KVDictBlock)(resultBlock);
    
    objcBlk(row);
    
    return 0;
}
