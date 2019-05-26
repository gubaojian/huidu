//
//  GUPurchaseManager.h
//  powerfee
//
//  Created by lurina on 12-11-27.
//  Copyright (c) 2012年 baobao. All rights reserved.
//




#import <Foundation/Foundation.h>
#import <StoreKit/StoreKit.h>
#import "GULog.h"
#import "GUConfig.h"






@interface GUPurchaseManager : NSObject

/**返回软件购买的标示符 */
+(NSString *) getAppPurchaseIdentify;

/** 用户是否购买 */
+(BOOL) isPurchased;

/** 存储购买信息 */
+(void) storePurchase:(SKPaymentTransaction *)transaction;

/** 废弃购买信息 */
+(void) inValidPurchase;

/**校验购买信息  */
+(void) verifyPurchase;





@end
