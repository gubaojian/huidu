//
//  GUFMDBExtension.m
//  asm
//
//  Created by lurina on 13-1-19.
//  Copyright (c) 2013年 baobao. All rights reserved.
//

#import "GUFMDatabaseExtension.h"

@interface FMDatabase (PrivateStuff)
- (FMResultSet *)executeQuery:(NSString *)sql withArgumentsInArray:(NSArray*)arrayArgs orDictionary:(NSDictionary *)dictionaryArgs orVAList:(va_list)args;
@end

@implementation  FMDatabase (FMDatabaseAdditions)

- (NSArray* ) arrayForQuery:(NSString*)query, ... {
    va_list args;
    va_start(args, query);
    FMResultSet* result = [self executeQuery:query withArgumentsInArray:nil orDictionary:nil orVAList:args];
    va_end(args);
    
    //array result
    NSMutableArray* datas = [[NSMutableArray alloc] initWithCapacity:8];
    while ([result next]) {
        [datas addObject:[result objectForColumnIndex:0]];
    }
    
    [result close];
    return datas;
}

- (int)intForGUQuery:(GUDynQuery*)query{
    FMResultSet* result = [self executeGUQuery:query];
    
    int count = 0;
    if ([result next]) {
        count= [result intForColumnIndex:0];
    }
   
    [result close];
    return count;
}

- (FMResultSet *)executeGUQuery:(GUDynQuery*) query{
    GULog(LOG_LEVEL_TRACE, @"sql: %@ \n params: %@", [query query], [query params]);
    return [self executeQuery:[query query] withArgumentsInArray:[query params]];
}

- (NSArray* ) arrayForGUQuery:(GUDynQuery*)query{
    FMResultSet* result = [self executeGUQuery:query];
    NSMutableArray* datas = [[NSMutableArray alloc] initWithCapacity:8];
    while ([result next]) {
        [datas addObject:[result objectForColumnIndex:0]];
    }
    [result close];
    return datas;
}

@end
