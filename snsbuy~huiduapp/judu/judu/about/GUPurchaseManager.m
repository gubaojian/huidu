//
//  GUPurchaseManager.m
//  powerfee
//
//  Created by lurina on 12-11-27.
//  Copyright (c) 2012年 baobao. All rights reserved.
//

#import "GUPurchaseManager.h"



@implementation GUPurchaseManager

#ifndef PURCHASED_PRODUCT_ID_KEY
#define PURCHASED_PRODUCT_ID_KEY  @"PURCHASED_PRODUCT_ID_KEY"
#endif

#ifndef PURCHASED_TRANSACTION_ID_KEY
#define PURCHASED_TRANSACTION_ID_KEY  @"PURCHASED_TRANSACTION_ID_KEY"
#endif

#ifndef PURCHASED_VERIFY_RECEIPT_KEY
#define PURCHASED_VERIFY_RECEIPT_KEY    @"PURCHASED_VERIFY_RECEIPT_KEY"
#endif

/**用户如果已经购买，则存放此变量*/
#ifndef USER_PURCHASED_CERTIFICATE
#define USER_PURCHASED_CERTIFICATE  @"USER_PURCHASED_CERTIFICATE"
#endif


+(NSString *) getAppPurchaseIdentify{
    return APP_PURCHASE_IDENTIFY;
}

+(BOOL) isPurchased{
    NSString* certificate  = [[NSUserDefaults standardUserDefaults] valueForKey:APP_PURCHASE_IDENTIFY];
    GULog(LOG_LEVEL_DEBUG, @"certificate : %@", certificate);
    if (certificate != nil && [USER_PURCHASED_CERTIFICATE  isEqualToString: certificate]) { //用户已经购买，则不在显示广告
            return YES;
    }
    return NO;
}

+(void) storePurchase:(SKPaymentTransaction *)transaction{
     NSString*  identify = transaction.payment.productIdentifier;
     if (![identify isEqualToString:APP_PURCHASE_IDENTIFY]) {
        GULog(LOG_LEVEL_DEBUG, @"app purchase identify not match: %@   %@",  identify, APP_PURCHASE_IDENTIFY);
        return;
     }
     NSUserDefaults* defaults =  [NSUserDefaults standardUserDefaults];
     [defaults setValue:USER_PURCHASED_CERTIFICATE forKey:APP_PURCHASE_IDENTIFY];
    
    
     NSString* base64 = [self encodeDataBase64:(uint8_t *) transaction.transactionReceipt.bytes  length:transaction.transactionReceipt.length];
    
     //存储信息
     [defaults setValue: base64 forKey:PURCHASED_VERIFY_RECEIPT_KEY];
     [defaults setValue: identify forKey:PURCHASED_PRODUCT_ID_KEY];
     [defaults setValue: transaction.transactionIdentifier  forKey:PURCHASED_TRANSACTION_ID_KEY];
    
     GULog(LOG_LEVEL_DEBUG, @"store purchased identify %@  ",  identify);
     GULog(LOG_LEVEL_DEBUG, @"receipt %@  ",  base64);
     GULog(LOG_LEVEL_DEBUG, @"transactionIdentifier %@  ",  transaction.transactionIdentifier);
    
}

+(void) inValidPurchase{
    NSUserDefaults* defaults =  [NSUserDefaults standardUserDefaults];
    [defaults setValue: nil forKey:APP_PURCHASE_IDENTIFY];
    [defaults setValue:nil forKey:PURCHASED_PRODUCT_ID_KEY];
    [defaults setValue:nil forKey:PURCHASED_TRANSACTION_ID_KEY];
    [defaults setValue:nil forKey:PURCHASED_VERIFY_RECEIPT_KEY];
    
    GULog(LOG_LEVEL_DEBUG, @"cleared centificate : %@", [defaults objectForKey:APP_PURCHASE_IDENTIFY]);
}


+(void)  verifyPurchase{
     GULog(LOG_LEVEL_DEBUG,  @"Verify URL: %@", ITMS_PROD_VERIFY_RECEIPT_URL);
     NSUserDefaults* defaults =  [NSUserDefaults standardUserDefaults];
     NSString*  receipt  = [defaults valueForKey:PURCHASED_VERIFY_RECEIPT_KEY];
    if (receipt == nil) {
         GULog(LOG_LEVEL_DEBUG, @"receipt is null, invalid purchase");
        [GUPurchaseManager inValidPurchase];
        return;
    }
    
    GULog(LOG_LEVEL_DEBUG, @"stored receipt : %@ "  , receipt);
     GULog(LOG_LEVEL_DEBUG, @"shared password : %@ "  , APP_VERIFY_SECRET);
    // Create the POST request payload.
    NSString *payload = [NSString stringWithFormat:@"{\"receipt-data\" : \"%@\", \"password\" : \"%@\"}", receipt, APP_VERIFY_SECRET];
    NSData *payloadData = [payload dataUsingEncoding:NSUTF8StringEncoding];
    NSString *serverURL = ITMS_PROD_VERIFY_RECEIPT_URL;
    
    
    // Create the POST request to the server.
    NSMutableURLRequest *request = [NSMutableURLRequest requestWithURL:[NSURL URLWithString:serverURL]];
    [request setHTTPMethod:@"POST"];
    [request setHTTPBody:payloadData];
    
    NSURLResponse __autoreleasing *response = nil;
    NSError __autoreleasing *error = nil;
    GULog(LOG_LEVEL_DEBUG, @"connection to servlet : %@", ITMS_PROD_VERIFY_RECEIPT_URL);
    NSData* data  = [NSURLConnection sendSynchronousRequest:request returningResponse: &response error: &error];
    if (error !=  nil) {
         GULog(LOG_LEVEL_ERROR, @"create connection  error: %@", [error description]);
        return;
    }
    GULog(LOG_LEVEL_DEBUG, @"server response length %d", [data length]);
    NSString *responseString = [[NSString alloc] initWithData:data encoding:NSUTF8StringEncoding];
    GULog(LOG_LEVEL_DEBUG, @"server response json content: %@",  responseString);
    
    NSError* jsonError;
    NSDictionary *jsonObject =[NSJSONSerialization JSONObjectWithData:[responseString dataUsingEncoding:NSUTF8StringEncoding] options:NSJSONReadingMutableContainers error:& jsonError];
    if (jsonError) {
         GULog(LOG_LEVEL_DEBUG, @"invalid purchase json data");
        return;
    }
    
    BOOL buyed = [GUPurchaseManager  verifyResponseFromServer: jsonObject];
    if (!buyed) {
        GULog(LOG_LEVEL_DEBUG, @"user not buy app, invalid purchase");
       // [GUPurchaseManager inValidPurchase];
    }else{
        GULog(LOG_LEVEL_DEBUG, @"user buyed the app purchase");
    }
      
}


+ (NSString *)encodeDataBase64:(const uint8_t *)input length:(NSInteger)length {
    static char table[] = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=";
    
    NSMutableData *data = [NSMutableData dataWithLength:((length + 2) / 3) * 4];
    uint8_t *output = (uint8_t *)data.mutableBytes;
    
    for (NSInteger i = 0; i < length; i += 3) {
        NSInteger value = 0;
        for (NSInteger j = i; j < (i + 3); j++) {
            value <<= 8;
            
            if (j < length) {
                value |= (0xFF & input[j]);
            }
        }
        
        NSInteger index = (i / 3) * 4;
        output[index + 0] =                    table[(value >> 18) & 0x3F];
        output[index + 1] =                    table[(value >> 12) & 0x3F];
        output[index + 2] = (i + 1) < length ? table[(value >> 6)  & 0x3F] : '=';
        output[index + 3] = (i + 2) < length ? table[(value >> 0)  & 0x3F] : '=';
    }
    
    return [[NSString alloc] initWithData:data encoding:NSASCIIStringEncoding];
}
+ (BOOL)verifyResponseFromServer: (NSDictionary *)verifiedReceiptDictionary{
    // Check the status of the verifyReceipt call
    id status = [verifiedReceiptDictionary objectForKey:@"status"];
    GULog(LOG_LEVEL_DEBUG, @"response json status:  %@", status);
    
    if (!status)
    {
        return NO;
    }
    int verifyReceiptStatus = [status integerValue];
    // 21006 = This receipt is valid but the subscription has expired. && 21006 != verifyReceiptStatus
    if (0 != verifyReceiptStatus)
    {
        return NO;
    }
    
    // The receipt is valid, so checked the receipt specifics now.
    
    NSDictionary *verifiedReceipt  = [verifiedReceiptDictionary objectForKey:@"receipt"];
    
    NSUserDefaults* defaults = [NSUserDefaults standardUserDefaults];
    
    // Verify all the receipt specifics to ensure everything matches up as expected
    if (![[verifiedReceipt objectForKey:@"product_id"] isEqualToString:APP_PURCHASE_IDENTIFY])
    {
        GULog(LOG_LEVEL_DEBUG, @"product ID Not Match: %@   %@", [verifiedReceipt objectForKey:@"product_id"], APP_PURCHASE_IDENTIFY);
        return NO;
    }
    
    if (![[verifiedReceipt objectForKey:@"transaction_id"] isEqualToString:[defaults valueForKey: PURCHASED_TRANSACTION_ID_KEY]])
    {
        GULog(LOG_LEVEL_DEBUG, @"transacton id  Not Match: %@   %@", [verifiedReceipt objectForKey:@"app_item_id"],[defaults valueForKey:PURCHASED_TRANSACTION_ID_KEY]);
        return NO;
    }
    
    return YES;
}


@end
