//
//  GUBaseAppDelegate.m
//  hscode
//
//  Created by lurina on 12-12-16.
//  Copyright (c) 2012年 baobao. All rights reserved.
//

#import "GUBaseAppDelegate.h"

@implementation GUBaseAppDelegate

@synthesize window;

static  __strong FMDatabase* database;

/**管理广告对象*/
static NSMutableDictionary*  admobManager;


/**UIApplicationDelegate  方法开始 */
- (BOOL)application:(UIApplication *)application didFinishLaunchingWithOptions:(NSDictionary *)launchOptions
{
    // Override point for customization after application launch.
    [[SKPaymentQueue defaultQueue] addTransactionObserver:self];
    [iTellAFriend  sharedInstance].appStoreID = APP_STORE_ID;
    if (COPY_FROM_BUNDLE_TO_DOCUMENT) {
         [self copyFilesFromBundleToDocuments];
    }
    return YES;
}

- (void)applicationWillResignActive:(UIApplication *)application
{
    // Sent when the application is about to move from active to inactive state. This can occur for certain types of temporary interruptions (such as an incoming phone call or SMS message) or when the user quits the application and it begins the transition to the background state.
    // Use this method to pause ongoing tasks, disable timers, and throttle down OpenGL ES frame rates. Games should use this method to pause the game.
}

- (void)applicationDidEnterBackground:(UIApplication *)application
{
    // Use this method to release shared resources, save user data, invalidate timers, and store enough application state information to restore your application to its current state in case it is terminated later.
    // If your application supports background execution, this method is called instead of applicationWillTerminate: when the user quits.
}

- (void)applicationWillEnterForeground:(UIApplication *)application
{
    // Called as part of the transition from the background to the inactive state; here you can undo many of the changes made on entering the background.
}

- (void)applicationDidBecomeActive:(UIApplication *)application
{
    // Restart any tasks that were paused (or not yet started) while the application was inactive. If the application was previously in the background, optionally refresh the user interface.
    //[GUBaseAppDelegate removeAllAdmobBanner];
}

- (void)applicationWillTerminate:(UIApplication *)application{
    if (database != nil) {
        if([database close]){
            GULog(LOG_LEVEL_DEBUG, @"Close Opened database success");
        }else{
            GULog(LOG_LEVEL_DEBUG, @"Close Opened database failed");
        }
    }
}

/**UIApplicationDelegate  方法结束 */


/**admob 相关的方法开始
+(GADBannerView *) addAdmobOnViewButtom:(UIViewController* ) rootController  identifyKey:(NSString*) key{
    GULog(LOG_LEVEL_DEBUG, @"load admob banner for %@", key);;
    GADBannerView *banner;
    if (admobManager != nil) {
        banner =  [admobManager objectForKey:key];
        if (banner != nil) {
            banner.rootViewController = rootController;
            [rootController.view addSubview:banner];
            return banner;
        }
    }
   
    banner =  [GUAmobUtils addAdmobOnViewButtom:rootController];
    if (admobManager == nil) {
        admobManager = [[NSMutableDictionary alloc] initWithCapacity:4];
    }
    GULog(LOG_LEVEL_DEBUG, @"Register admon banner objects");
    [admobManager setObject:banner forKey:key];
    return banner;
}

+(void) removeAllAdmobBanner{
    if (admobManager != nil) {
         NSArray *keys =  [admobManager allKeys];   //不要访问其它无关的参数， 有可能已经释放了。
         GULog(LOG_LEVEL_DEBUG, @"notifycation key  count : %d", [keys count]);
        for (int i=0; i < [keys count];  i++) {
            [[NSNotificationCenter defaultCenter] postNotificationName:[keys objectAtIndex:i] object:self];
            GULog(LOG_LEVEL_DEBUG, @"notifycation key : %@", [keys objectAtIndex:i]);
        }
        //[admobManager removeAllObjects];
       // admobManager = nil;
    }
}

+(BOOL)  hasAmobBanner:(NSString*) key{
    if (key == nil) {
        return NO;
    }
    if (admobManager == nil) {
        return NO;
    }
    return [admobManager objectForKey:key] != nil;
}
 */
/**admob 相关的方法结束 */


/** app内置购买开始  */
- (void)paymentQueue:(SKPaymentQueue *)queue updatedTransactions:(NSArray *)transactions
{
    
    for (SKPaymentTransaction *transaction in transactions)
    {
        switch (transaction.transactionState)
        {
            case SKPaymentTransactionStatePurchased:
                [self completeTransaction:transaction];
                break;
            case SKPaymentTransactionStateRestored:
                [self restoreTransaction:transaction];
                break;
            case SKPaymentTransactionStateFailed:
                [self failedTransaction:transaction];
                break;
            default:
                break;
        }
    }
}

-(void) completeTransaction:(SKPaymentTransaction *)transaction{
    NSString*  identify = transaction.payment.productIdentifier;
    GULog(LOG_LEVEL_DEBUG, @"buy success : %@", identify);
    [GUPurchaseManager storePurchase:transaction];
    [self refreshView];
    [[SKPaymentQueue defaultQueue] finishTransaction: transaction];
}



-(void)restoreTransaction:(SKPaymentTransaction *)transaction{
    NSString*  identify = transaction.originalTransaction.payment.productIdentifier;
    GULog(LOG_LEVEL_DEBUG, @"restore buy process : %@", identify);
    GULog(LOG_LEVEL_DEBUG, @"restore buy process : %d", [transaction.transactionReceipt length]);
    GULog(LOG_LEVEL_DEBUG, @"restore buy process : %d", [transaction.originalTransaction.transactionReceipt length]);
    GULog(LOG_LEVEL_DEBUG, @"restore buy process : %a", [transaction transactionIdentifier]);
    
    [GUPurchaseManager storePurchase:transaction];
    [self refreshView];
    [[SKPaymentQueue defaultQueue] finishTransaction: transaction];
    
}

-(void) failedTransaction:(SKPaymentTransaction *)transaction{
    if (transaction.error.code != SKErrorPaymentCancelled) {
        UIAlertView* alert = [[UIAlertView alloc] initWithTitle:   NSLocalizedString(@"CommonButtonTextTips", @"LocalizedString")
                                                        message: [transaction.error description]
                                                       delegate:nil
                                              cancelButtonTitle:NSLocalizedString(@"CommonButtonTextOk", @"LocalizedString")
                                              otherButtonTitles:nil, nil];
        [alert show];
    }
    
    if (transaction.error.code  == SKErrorPaymentNotAllowed) {
        GULog(LOG_LEVEL_DEBUG, @"state %@", @"SKErrorPaymentNotAllowed");
    }else if(transaction.error.code  == SKErrorClientInvalid){
        GULog(LOG_LEVEL_DEBUG, @"state %@", @"SKErrorClientInvalid");
    }
    
    GULog(LOG_LEVEL_DEBUG, @"buy failed: %d", transaction.error.code);
    GULog(LOG_LEVEL_DEBUG, @"buy failed: %@", [transaction.error description]);
    [[SKPaymentQueue defaultQueue] finishTransaction: transaction];
}

-(void)paymentQueueRestoreCompletedTransactionsFinished:(SKPaymentQueue *)queue{
    GULog(LOG_LEVEL_DEBUG, @"ALL COMPLETED TRANSACTION RESTORED");
}


-(void) refreshView{
    dispatch_async(dispatch_get_main_queue(), ^(void){
        [GUBaseAppDelegate removeAllAdmobBanner];
    });
    
}

/** app内置购买结束  */

+(FMDatabase *) applicationDatabase{
    GULog(LOG_LEVEL_DEBUG, @"load application database");
    if (database == nil) {
        GULog(LOG_LEVEL_DEBUG, @"init application database from database file");
        NSString * path =  [[NSBundle mainBundle] pathForResource:DATABASE_FILE_NAME ofType:@"sqlite"];
        GULog(LOG_LEVEL_DEBUG, @"database file path: %@", path);
        NSString* documentsPath = [NSSearchPathForDirectoriesInDomains(NSDocumentDirectory, NSUserDomainMask, YES) objectAtIndex:0];
        
        //区分中英文版本
        NSString* fileName = nil;
        if ([GU_ZH_HANS isEqualToString:[[NSLocale preferredLanguages] objectAtIndex:0]]) {
            fileName = [DATABASE_FILE_NAME stringByAppendingString:@"_zh-Hans"];
        }else{
            fileName = [DATABASE_FILE_NAME stringByAppendingString:@"_en"];
        }
        NSString* writeFile = [documentsPath stringByAppendingPathComponent:[fileName stringByAppendingPathExtension:@"sqlite"]];
        if (![[NSFileManager defaultManager] isReadableFileAtPath:writeFile]) {
            [[NSFileManager defaultManager] copyItemAtPath:path toPath:writeFile error:nil];
            GULog(LOG_LEVEL_DEBUG, @"Copy File Succed: %@", writeFile);
        }
        database = [FMDatabase databaseWithPath:writeFile];
    }
    GULog(LOG_LEVEL_DEBUG, @"application database: %@" , database);
    if (![database open]) {
        GULog(LOG_LEVEL_ERROR, @"open application database failed: %@" , [database lastErrorMessage]);
    }
    return database;
}

-(void) copyFilesFromBundleToDocuments{
    dispatch_async(dispatch_get_global_queue(DISPATCH_QUEUE_PRIORITY_DEFAULT, 0), ^(void){
        NSUserDefaults* defaults = [NSUserDefaults standardUserDefaults];
        NSString* key = @"Group_copyFilesFromBundleToDocuments";
        if ([defaults objectForKey:key] != nil) {
            return;
        }
             
       NSString* language =   [[NSLocale preferredLanguages] objectAtIndex:0];
       GULog(LOG_LEVEL_DEBUG, @"Default Key: %@ language: %@", key, language);
        NSArray* bundleFiles = nil;
        if ([@"zh-Hans" isEqualToString:language]) {
            bundleFiles = [[NSArray alloc] initWithObjects:
                           @"邮件地址示例.csv",
                           @"邮件地址示例.txt",
                           @"手机号码示例.csv",
                           @"手机号码示例.txt",
                           @"APP使用说明.txt",
                           @"导入短信内容示例.txt",
                           @"导入邮件内容示例.txt",
                           nil];
        }else{
            bundleFiles = [[NSArray alloc] initWithObjects:
                           @"MailAddress-Example.txt",
                           @"PhoneNum-Example.txt",
                           @"Import-SMS-Content.txt",
                           @"Import-Mail-Content.txt",
                           @"App Use Introduction.txt",
                           @"MailAddress-Example.csv",
                           @"PhoneNum-Example.csv",
                           @"Party-Phone-List.txt",
                           @"Customer-Mail-List.txt",
                           nil];
        }
        
        
        NSString* documentsPath = [NSSearchPathForDirectoriesInDomains(NSDocumentDirectory, NSUserDomainMask, YES) objectAtIndex:0];
        NSFileManager* fileManager = [NSFileManager defaultManager];
        for (NSString* bundleFile in bundleFiles) {
            NSString* fileBundleName = [bundleFile stringByDeletingPathExtension];
            NSString* fromFile = [[NSBundle mainBundle] pathForResource:fileBundleName ofType:[bundleFile pathExtension]];
            if (fromFile == nil) {
                GULog(LOG_LEVEL_DEBUG, @"File %@ not exits", bundleFile);
                continue;
            }
            
            NSString* toFile = [documentsPath stringByAppendingPathComponent:bundleFile];
            if ([fileManager isReadableFileAtPath:toFile]) {
                 GULog(LOG_LEVEL_DEBUG, @"File %@ already exits", bundleFile);
                continue;
            }
            NSError* error;
            BOOL result = [fileManager copyItemAtPath:fromFile toPath:toFile error:&error];
            GULog(LOG_LEVEL_DEBUG, @"Copy File %@  %d  %@", bundleFile, result, error);
        }
        [defaults setObject:key forKey:key];
    });
}



@end
