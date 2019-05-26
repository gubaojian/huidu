//
//  GUMainViewController.m
//  judu
//
//  Created by lurina on 13-7-28.
//  Copyright (c) 2013年 baobao. All rights reserved.
//
//
#import <QuartzCore/QuartzCore.h>
#import "GUMainViewController.h"
#import "UIColor+Expanded.h"
#import "GUHomeViewController.h"
#import "GUSearchViewController.h"
#import "GUFavoriteViewController.h"
#import "GUAbout.h"
#import "GUNavigationViewController.h"
#import "GUMyHuiduViewController.h"


@interface GUMainViewController (){
    UIViewController* _homeViewController;
    UIViewController* _searchViewController;
    UIViewController* _favoriteViewController;
    GUAbout*  _aboutViewController;
    UIViewController* _myHuiduController;
}

@end

@implementation GUMainViewController

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        // Custom initialization
        UITabBar*  tabBar =  self.tabBar;
        
        [tabBar setSelectedImageTintColor:[UIColor colorWithHexString:@"414141"]];
        [tabBar setSelectionIndicatorImage:[UIImage imageNamed:@"414141"]];
        [tabBar setBackgroundImage:[UIImage imageNamed:@"414141"]];
        [tabBar setTintColor:[UIColor whiteColor]];
        if ([tabBar respondsToSelector:@selector(setBarTintColor:)]) {
            [tabBar setBarTintColor:[UIColor whiteColor]];
        }
        if ([self.tabBar respondsToSelector:@selector(setTranslucent:)]) {
            self.tabBar.translucent = NO;
        }
        
        NSArray* items = @[ [self homeViewController],
                            [self searchViewController] ,
                            [self myHuiduController]
                            ];
        self.viewControllers = items;
        
        
        
       // [[UITabBarItem appearance] setTitleTextAttributes:@{ UITextAttributeTextColor : [UIColor whiteColor] }
         //                                        forState:UIControlStateNormal];
       // [[UITabBarItem appearance] setTitleTextAttributes:@{ UITextAttributeTextColor : [UIColor blueColor] }
      //                                           forState:UIControlStateHighlighted];
        
    }
    return self;
}

-(void) loadView{
    [super loadView];
    self.view.backgroundColor = [UIColor blackColor];
}


- (BOOL) shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation
{
    return UIInterfaceOrientationIsPortrait(interfaceOrientation);
}

- (NSUInteger)supportedInterfaceOrientations
{
    return (UIInterfaceOrientationMaskPortrait | UIInterfaceOrientationMaskPortraitUpsideDown);
}


- (void)viewDidLoad
{
    [super viewDidLoad];
	// Do any additional setup after loading the view.
}



- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

-(void) dealloc{
    [self clean];
}

-(void) clean{
    _homeViewController = nil;
    _searchViewController = nil;
    _favoriteViewController = nil;
    _aboutViewController = nil;
    _myHuiduController = nil;
}

/////////////////////////////////////////////////////////////////////////////////////////////////
#pragma init method

-(UIViewController* ) homeViewController{
   
    if (_homeViewController == nil) {
         _homeViewController = [[GUNavigationViewController alloc] initWithRootViewController:[[GUHomeViewController alloc] init]];
     }
    return _homeViewController;
}



-(UIViewController* ) searchViewController{
    if (_searchViewController == nil) {
        _searchViewController = [[GUNavigationViewController alloc] initWithRootViewController:[[GUSearchViewController alloc] init]];
        _searchViewController.view.autoresizesSubviews = NO;
    }
    return _searchViewController;
}


-(UIViewController*) myHuiduController{
    if (_myHuiduController == nil) {
        _myHuiduController = [[GUNavigationViewController alloc] initWithRootViewController:[[GUMyHuiduViewController alloc] init]];
    }
    return _myHuiduController;
}



@end
