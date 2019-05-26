//
//  GUAbout.h
//  powerfee
//
//  Created by lurina on 12-11-25.
//  Copyright (c) 2012年 baobao. All rights reserved.
//
#import <UIKit/UIKit.h>
#import <Foundation/Foundation.h>
#import <MessageUI/MessageUI.h>
#import <StoreKit/StoreKit.h>
#import "GUConfig.h"
#import "GUWebViewController.h"
#import "GUAppDelegate.h"
#import "GUAlertDialog.h"
#import "GUPurchaseManager.h"
//#import "GADBannerView.h"
#import "GUSystemUtils.h"
#import "GUAmobUtils.h"
#import "iTellAFriend.h"
#import "OurApps/OurAppsViewController.h"
#import "UIColor+Expanded.h"


@interface GUAbout  : UITableViewController<MFMailComposeViewControllerDelegate,SKProductsRequestDelegate,OurAppsVCDelegate>

@property (nonatomic, strong) NSArray*  sectionOneData;

@property (nonatomic, strong) NSArray*  sectionTwoData;

@property (nonatomic, strong) NSArray*  sectionThreeData;

@property (nonatomic, strong) NSArray*  sectionFourData;

@property (nonatomic, strong) NSArray*  sectionFiveData;

@property(nonatomic, strong) GUAlertDialog* alertDialog;

//@property(strong, nonatomic)  GADBannerView *adBannerView;



@end
