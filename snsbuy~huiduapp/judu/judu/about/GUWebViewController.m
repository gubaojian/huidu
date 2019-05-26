//
//  GUWebViewController.m
//  powerfee
//
//  Created by lurina on 12-11-25.
//  Copyright (c) 2012年 baobao. All rights reserved.
//

#import "GUWebViewController.h"
#import "GUConfig.h"

@implementation GUWebViewController
@synthesize webView = _webView;

@synthesize htmlResourceName = _htmlResourceName;



- (void)loadView{
    [super loadView];
    [self webView];
}



- (void)viewDidLoad
{
    [super viewDidLoad];
    
    if (_htmlResourceName == nil) {
        return;
    }
    NSError __autoreleasing * error = nil;
    NSBundle * bundle = [NSBundle mainBundle];
    NSString* path = [bundle pathForResource:_htmlResourceName ofType:@"html"];
    NSString* html = [NSString stringWithContentsOfFile:path encoding:NSUTF8StringEncoding error:&error];
    if (html == nil) {
        GULog(LOG_LEVEL_ERROR, @"load bundleName: %@ error", _htmlResourceName);
        return;
    }
    [_webView loadHTMLString:html baseURL:[NSURL fileURLWithPath:[[NSBundle mainBundle] resourcePath]]];
    UISwipeGestureRecognizer* swipeGesture = [[UISwipeGestureRecognizer alloc] initWithTarget:self action:@selector(swipeAction:)];
    [self.view addGestureRecognizer:swipeGesture];
   
}


- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

- (void)viewDidUnload {
    [self setWebView:nil];
    [super viewDidUnload];
}



- (BOOL)webView:(UIWebView *)webView shouldStartLoadWithRequest:(NSURLRequest *)request navigationType:(UIWebViewNavigationType)navigationType{
    
    if (navigationType == UIWebViewNavigationTypeOther) {
        return YES;
    }
    
    return NO;
}


- (void)webView:(UIWebView *)webView didFailLoadWithError:(NSError *)error{
    NSLog(@"error load");
}

-(UIWebView*)webView{
    if (_webView == nil) {
        _webView = [[UIWebView alloc] initWithFrame:CGRectMake(0, 0, self.view.frame.size.width, self.view.frame.size.height)];
        _webView.autoresizingMask =UIViewAutoresizingFlexibleHeight | UIViewAutoresizingFlexibleTopMargin |  UIViewAutoresizingFlexibleBottomMargin;
        _webView.delegate = self;
        [_webView setUserInteractionEnabled:NO];
        [self.view addSubview:_webView];

    }
    return  _webView;
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


@end
