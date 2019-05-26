//
//  GUBaseViewController.m
//  judu
//
//  Created by lurina on 13-7-28.
//  Copyright (c) 2013年 baobao. All rights reserved.
//
#import <QuartzCore/QuartzCore.h>
#import "GUBaseViewController.h"
#import "UIColor+Expanded.h"
#import "GUConfig.h"


@interface GUBaseViewController (){

   
}

@end

@implementation GUBaseViewController

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        // Custom initialization
    }
    return self;
}

- (id)initWithQuery:(NSDictionary*) query{
    self = [super init];
    if (self) {
 
    }
    return self;
}

-(void) loadView{
    [super loadView];
    
    if ([GUSystemUtils isIos7]) {
        [self adapterForIos7];
    }
    
    self.view.backgroundColor = GLOBAL_BACKGROUND_COLOR;
    
    UISwipeGestureRecognizer* swipeGesture = [[UISwipeGestureRecognizer alloc] initWithTarget:self action:@selector(swipeAction:)];
    [self.view addGestureRecognizer:swipeGesture];
}



- (void)viewDidLoad
{
    [super viewDidLoad];
	// Do any additional setup after loading the view.
}

-(void) adapterForIos7{
    self.edgesForExtendedLayout = UIRectEdgeLeft | UIRectEdgeRight | UIRectEdgeBottom;
    self.automaticallyAdjustsScrollViewInsets = NO;
    [self.navigationController.navigationBar setTranslucent:NO];
}



- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    /**ios6.0如果view未被清理，则促发清理*/
    if ([self isViewLoaded]) {
        if (self.view.window == nil) {
            if ([self respondsToSelector:@selector(viewDidUnload)]) {
                self.view = nil;
                [self viewDidUnload];
            }
        }
    }
    
    // Dispose of any resources that can be recreated.
}

-(void) viewDidUnload{
    [self clean];
    [super viewDidUnload];
}

-(void) dealloc{
    [self clean];
}

-(void) clean{
    if (_statusView != nil) {
        [_statusView removeViewInfo];
        _statusView = nil;
    }
}



-(GUStatusView*) statusView{
    if (_statusView == nil) {
        _statusView = [[GUStatusView alloc] init];
        __weak GUBaseViewController* weakSelf = self;
        _statusView.reloadBlock = ^(void){
            [weakSelf reloadForError];
        };
    }
    return _statusView;
}

-(void)reloadForError{
}

////////////////////////////////////////////////////////////////////////////////////
#pragma UISwipeGestureRecognizer
-(void) swipeAction:(UISwipeGestureRecognizer*) gesture{
    if ([gesture direction] ==  UISwipeGestureRecognizerDirectionRight) { //回退
        if (self.navigationController && [[self.navigationController viewControllers] count] > 1) {
            [self.navigationController popViewControllerAnimated:YES];
            return;
        }
    }
    [gesture setCancelsTouchesInView:YES];
}

///////////////////////////////////////////////////////////////////////////////////////////////
#pragma UIButton action
-(void) backAction{
    if (self.navigationController) {
            [self.navigationController popViewControllerAnimated:YES];
    }else{
        if (self.presentingViewController) {
              [self dismissViewControllerAnimated:YES completion:nil];
        }
    }
}




@end
