//
//  GUAppDelegate.m
//  judu
//
//  Created by lurina on 13-7-28.
//  Copyright (c) 2013年 baobao. All rights reserved.
//
#import <QuartzCore/QuartzCore.h>
#import "GUAppDelegate.h"
#import "GUMainViewController.h"
#import "MobClick.h"
#import "SDURLCache.h"
#import "GUSignUtils.h"
#import "GUSwitch.h"

@implementation GUAppDelegate

@dynamic window;


- (BOOL)application:(UIApplication *)application didFinishLaunchingWithOptions:(NSDictionary *)launchOptions
{
    [self configAppearance];
    [self configCache];
    [GUSwitch switchUseCDN];
    [super application:application didFinishLaunchingWithOptions:launchOptions];
    self.window = [[UIWindow alloc] initWithFrame:[[UIScreen mainScreen] bounds]];
    self.viewController = [[GUMainViewController alloc] init];
    self.window.rootViewController = self.viewController;
    [self.window makeKeyAndVisible];
    
    
   
    
    
    @try{
        [MobClick startWithAppkey:UM_APP_KEY];
        [MobClick  beginEvent:@"UseJudu"]; //使用聚读
    }@catch(NSException* e){
        NSLog(@"%@", e);
    }
  
    NSLog(@"bounds %@", NSStringFromCGRect([[UIScreen mainScreen] bounds]));

    
    
    return YES;
}

- (void)applicationWillResignActive:(UIApplication *)application
{
    // Sent when the application is about to move from active to inactive state. This can occur for certain types of temporary interruptions (such as an incoming phone call or SMS message) or when the user quits the application and it begins the transition to the background state.
    // Use this method to pause ongoing tasks, disable timers, and throttle down OpenGL ES frame rates. Games should use this method to pause the game.
    [super applicationWillResignActive:application];
    [MobClick endEvent:@"UseJudu"]; //使用聚读
}

- (void)applicationDidEnterBackground:(UIApplication *)application
{
    // Use this method to release shared resources, save user data, invalidate timers, and store enough application state information to restore your application to its current state in case it is terminated later. 
    // If your application supports background execution, this method is called instead of applicationWillTerminate: when the user quits.
    [super applicationDidEnterBackground:application];
}

- (void)applicationWillEnterForeground:(UIApplication *)application
{
    // Called as part of the transition from the background to the inactive state; here you can undo many of the changes made on entering the background.
    [super applicationWillEnterForeground:application];
}

- (void)applicationDidBecomeActive:(UIApplication *)application
{
    // Restart any tasks that were paused (or not yet started) while the application was inactive. If the application was previously in the background, optionally refresh the user interface.
    [super applicationDidBecomeActive:application];
    [MobClick  beginEvent:@"UseJudu"]; //使用聚读
}


-(NSUInteger) application:(UIApplication *)application supportedInterfaceOrientationsForWindow:(UIWindow *)window{
   return (UIInterfaceOrientationMaskPortrait | UIInterfaceOrientationMaskPortraitUpsideDown);
}



- (void)applicationWillTerminate:(UIApplication *)application
{
    // Called when the application is about to terminate. Save data if appropriate. See also applicationDidEnterBackground:.
    [super applicationWillTerminate:application];
    [MobClick endEvent:@"UseJudu"]; //使用聚读
}


-(void)  configCache{
    //分配4mb内存   12MB存储空间
    SDURLCache* shareCache = [[SDURLCache alloc] initWithMemoryCapacity:4*1024*1024 diskCapacity:12*1024*1024 diskPath:[SDURLCache defaultCachePath]];
    [NSURLCache setSharedURLCache:shareCache];
}

-(void)  configAppearance{
    UINavigationBar* navigationBar = [UINavigationBar appearance];
    [navigationBar setBackgroundImage:[UIImage imageNamed:@"navigation_bar_bg"] forBarMetrics:UIBarMetricsDefault];
    [navigationBar  setTitleTextAttributes:[NSDictionary dictionaryWithObjectsAndKeys:
                                            [UIColor colorWithHexString:@"565656"], NSForegroundColorAttributeName,
                                            [NSValue valueWithUIOffset:UIOffsetMake(0, 0)], UITextAttributeTextShadowOffset,
                                            [UIFont boldSystemFontOfSize:20], NSFontAttributeName,
                                            nil]];
    
    UIToolbar* toolBar = [UIToolbar appearance];
    [toolBar setBackgroundImage:[UIImage imageNamed:@"navigation_bar_bg"] forToolbarPosition:UIToolbarPositionAny barMetrics:UIBarMetricsDefault];
    
    
    if (![GUSystemUtils isIos7]) {  //iOS7下采用系统默认
    UIBarButtonItem* proxy = [UIBarButtonItem appearance];
    UIImage* resizeableBackgrounImage = [[UIImage imageNamed:@"bar_button_item_bg"] resizableImageWithCapInsets:UIEdgeInsetsMake(10, 10, 10, 10)];
    [proxy setBackgroundImage:resizeableBackgrounImage forState:UIControlStateNormal barMetrics:UIBarMetricsDefault];
    [proxy setTitleTextAttributes:[NSDictionary dictionaryWithObjectsAndKeys:[UIColor whiteColor], NSForegroundColorAttributeName, nil] forState:UIControlStateNormal];
    
    
 
        UIImage* backButtonBackgroundImage = [[UIImage imageNamed:@"back_bg"] resizableImageWithCapInsets:UIEdgeInsetsMake(5, 12, 5, 5)];
        [proxy setBackButtonBackgroundImage:backButtonBackgroundImage forState:UIControlStateNormal barMetrics:UIBarMetricsDefault];
        [proxy setBackButtonTitlePositionAdjustment:UIOffsetMake(0, 1) forBarMetrics:UIBarMetricsDefault];
    }
   
    [[UIApplication sharedApplication] setStatusBarStyle:UIStatusBarStyleLightContent];
}








@end
