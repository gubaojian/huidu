//
//  GUAmobUtils.m
//  hscode
//
//  Created by lurina on 12-12-16.
//  Copyright (c) 2012年 baobao. All rights reserved.
//

#import "GUAmobUtils.h"

@implementation GUAmobUtils


/** 添加广告, 如果用户已经购买，不显示广告, 返回NO
+(GADBannerView *) addAdmobOnViewButtom:(UIViewController* ) controller{
    GADBannerView *adBannerView;
    GULog(LOG_LEVEL_DEBUG, @"Table Bar Items: %@", controller.tabBarController.tabBar);
    
    //获取主控制器
    UIView* rootView = nil;
    int offsetHeight = 0;
    if (controller.navigationController ) {
        rootView = controller.navigationController.view;
        offsetHeight = 0;
    }else if(controller.tabBarController){
        rootView = controller.view;
        UITabBar* tabBar = controller.tabBarController.tabBar;
        offsetHeight = tabBar.frame.size.height;
    }else{
        rootView = controller.view;
        offsetHeight = 0;
    }
    if ([GUSystemUtils isIpad]) {
        adBannerView = [[GADBannerView alloc] initWithFrame:CGRectMake(0.0, rootView.frame.size.height - 90 - offsetHeight, 768,90)];
        adBannerView.adUnitID = GU_ADMOB_IPAD_UNIT_ID;
        
    }else{
        adBannerView = [[GADBannerView alloc] initWithFrame:CGRectMake(0.0,  rootView.frame.size.height  - GAD_SIZE_320x50.height - offsetHeight , GAD_SIZE_320x50.width, GAD_SIZE_320x50.height)];
        adBannerView.adUnitID = GU_ADMOB_IPONE_UNIT_ID;
    }

    adBannerView.rootViewController = controller; // assign to root controller
    [rootView addSubview:adBannerView];
    
    
    GADRequest *request = [GADRequest request];
    
#ifdef  APP_ADMOB_TEST_VERSION
    request.testing = YES;
#ifdef  APP_ADMOB_TEST_SIMULATOR_VERSION
    request.testDevices = [NSArray arrayWithObjects:
                           GAD_SIMULATOR_ID,  nil];  // 模拟器
    GULog(LOG_LEVEL_DEBUG, @"admob on simulator");
#else
    request.testDevices = [NSArray arrayWithObjects:
                           @"TEST_DEVICE_ID",   nil];// 测试 iOS 设备
    GULog(LOG_LEVEL_DEBUG, @"admob on test device");
#endif
    
#endif
#ifndef  APP_ADMOB_TEST_VERSION
       GULog(LOG_LEVEL_DEBUG, @"admob on test mode");
#else
    GULog(LOG_LEVEL_DEBUG, @"admob on product mode");
#endif
    [adBannerView loadRequest:request];
    return  adBannerView;
}

 */
@end
