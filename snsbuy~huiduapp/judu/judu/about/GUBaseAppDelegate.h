//
//  GUBaseAppDelegate.h
//  hscode
//
//  Created by lurina on 12-12-16.
//  Copyright (c) 2012年 baobao. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <StoreKit/StoreKit.h>
#import "GUConfig.h"
#import "GUPurchaseManager.h"
#import "GUAmobUtils.h"
#import "iTellAFriend.h"
#import "FMDatabase.h"

@interface GUBaseAppDelegate : UIResponder <UIApplicationDelegate, SKPaymentTransactionObserver>{
}

/**数据库*/
+(FMDatabase *) applicationDatabase;

/** 添加广告到view底部，返回添加的对象, 并注册到全局管理器中 */
//+(GADBannerView *) addAdmobOnViewButtom:(UIViewController* ) rootController identifyKey:(NSString*) key;

+(BOOL)  hasAmobBanner:(NSString*) key;

/**移除全局注册管理器中的广告**/
+(void) removeAllAdmobBanner;

@property (strong, nonatomic) UIWindow *window;

@end
