//
//  GUNavigationViewController.m
//  judu
//
//  Created by lurina on 13-7-31.
//  Copyright (c) 2013年 baobao. All rights reserved.
//

#import "GUNavigationViewController.h"
#import "UIColor+Expanded.h"

@interface GUNavigationViewController ()

@end

@implementation GUNavigationViewController

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        // Custom initialization
    }
    return self;
}


-(void) loadView{
    [super loadView];
    self.navigationBarHidden = YES;
    self.view.backgroundColor =[UIColor colorWithHexString:@"e7e8e9"];
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


-(void) pushViewController:(UIViewController *)viewController animated:(BOOL)animated{
    if ([self topViewController] != nil) {
        viewController.hidesBottomBarWhenPushed = YES;
    }
    [super pushViewController:viewController animated:animated];
}


@end
