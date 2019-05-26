//
//  GUSiteViewController.m
//  judu
//
//  Created by lurina on 13-8-2.
//  Copyright (c) 2013年 baobao. All rights reserved.
//

#import "GUSiteViewController.h"

enum actionSheetButtonIndex {
    kCopyUrlButtonIndex,
	kSafariButtonIndex,
	kChromeButtonIndex,
};

@interface GUSiteViewController ()<UIWebViewDelegate, UIActionSheetDelegate,UIToolbarDelegate>{
      @private UIToolbar *_toolBar;
      @private UIActivityIndicatorView* _activityIndicator;
      @private UIWebView *_siteWebView;
      @private UIBarButtonItem *_buttonGoBack;
      @private UIBarButtonItem *_buttonGoForward;
    
      @private NSURL* _siteUrl;
 
}
@end

@implementation GUSiteViewController

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
        if (query) {
           _siteUrl = [query objectForKey:@"siteUrl"];
        }
    }
    return self;
}


-(void) loadView{
    [super loadView];
    [self toolBar];
   [self siteWebView];

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

-(void)viewDidUnload{
    [self clean];
    [super viewDidUnload];
}

-(void) dealloc{
    [self clean];
}

-(void)clean{
    if (_siteWebView != nil) {
        [_siteWebView stopLoading];
        [_siteWebView setDelegate:nil];
        _siteWebView = nil;
    }
    if (_toolBar != nil) {
        _toolBar = nil;
    }
    if (_activityIndicator != nil) {
        [_activityIndicator stopAnimating];
        _activityIndicator = nil;
    }
    
    if (_buttonGoBack != nil) {
        _buttonGoBack = nil;
    }
    
    if (_buttonGoForward != nil) {
        _buttonGoForward = nil;
    }
    [super clean];
}

///////////////////////////////////////////////////////////////////////////////////////////////
#pragma init method
-(UIToolbar*)toolBar{
    if (_toolBar == nil) {
         _toolBar = [[UIToolbar alloc] initWithFrame:CGRectMake(0, [GUViewUtils viewStartOffset], self.view.frame.size.width, 44)];
        _toolBar.delegate = self;
      
        
        
        UIButton* refreshButton = [UIButton buttonWithType:UIButtonTypeCustom];
        [refreshButton setImage:[GUViewUtils cell20Image:@"\uf01e"] forState:UIControlStateNormal];
        [refreshButton addTarget:self action:@selector(refreshButtonTouchUp:) forControlEvents:UIControlEventTouchUpInside];
        [refreshButton setFrame:CGRectMake(0, 0, 40, 20)];
        UIBarButtonItem *refreshItem = [[UIBarButtonItem alloc] initWithCustomView:refreshButton];
        

        UIButton* moreButton = [UIButton buttonWithType:UIButtonTypeCustom];
        [moreButton setImage:[GUViewUtils cell20Image:@"\uf141"] forState:UIControlStateNormal];
        [moreButton addTarget:self action:@selector(buttonActionTouchUp:) forControlEvents:UIControlEventTouchUpInside];
        [moreButton setFrame:CGRectMake(0, 0, 40, 20)];
        UIBarButtonItem *moreItem = [[UIBarButtonItem alloc] initWithCustomView:moreButton];
        
        // Activity indicator is a bit special
        UIView *containerView = [[UIView alloc] initWithFrame:CGRectMake(0, 0, 43, 33)];
        [containerView addSubview:[self activityIndicator]];
        UIBarButtonItem *activityItem = [[UIBarButtonItem alloc] initWithCustomView:containerView];
        
        UIButton* closeButton = [UIButton buttonWithType:UIButtonTypeCustom];
        [closeButton setImage:[GUViewUtils cell20Image:@"\uf00d"] forState:UIControlStateNormal];
        [closeButton addTarget:self action:@selector(closeButtonActionTouchUp:) forControlEvents:UIControlEventTouchUpInside];
        [closeButton setFrame:CGRectMake(0, 0, 40, 20)];
        UIBarButtonItem* closeItem = [[UIBarButtonItem alloc] initWithCustomView:closeButton];
        
        
        // Add butons to an array
        NSMutableArray *toolBarButtons = [[NSMutableArray alloc] init];
        [toolBarButtons addObject:[self fixSpaceWithWidth:10]];
        [toolBarButtons addObject:[self buttonGoBack]];
        [toolBarButtons addObject:[self fixSpaceWithWidth:20]];
        [toolBarButtons addObject:[self buttonGoForward]];
        [toolBarButtons addObject:[self flexibleSpace]];
        [toolBarButtons addObject:activityItem];
        [toolBarButtons addObject:refreshItem];
        [toolBarButtons addObject:moreItem];
        [toolBarButtons addObject:closeItem];
        

        // Set buttons to tool bar
        [_toolBar setItems:toolBarButtons animated:YES];
        [self.view addSubview:_toolBar];
        
        // Tint toolBar
        //[toolBar setTintColor:barTintColor];
    }
    return _toolBar;
}


- (UIBarPosition)positionForBar:(id <UIBarPositioning>)bar{
    return UIBarPositionTopAttached;
}

/**
 ButtonWithImage(xxx)
 ButtonWithImageNamed(xxx)
 UIButton* reploadButton = [UIButton buttonWithType:UIButtonTypeCustom];
 [reploadButton setImage:[UIImage imageNamed:@"reload_icon"] forState:UIControlStateNormal];
 [reploadButton addTarget:self action:@selector(reloadButtonTouchUp:) forControlEvents:UIControlEventTouchUpInside];
 [reploadButton setFrame:CGRectMake(0, 0, 40, 20)];
 UIBarButtonItem *buttonReload = [[UIBarButtonItem alloc] initWithCustomView:reploadButton];
 */

-(UIBarButtonItem *) flexibleSpace{
    UIBarButtonItem *flexibleSpace = [[UIBarButtonItem alloc] initWithBarButtonSystemItem:UIBarButtonSystemItemFlexibleSpace target:nil action:nil];
    return flexibleSpace;
}

-(UIBarButtonItem *) fixSpaceWithWidth:(CGFloat) width{
    UIBarButtonItem *fixedSpace = [[UIBarButtonItem alloc] initWithBarButtonSystemItem:UIBarButtonSystemItemFixedSpace target:nil action:nil];
    fixedSpace.width = width;;
    return fixedSpace;
}

-(UIBarButtonItem*)buttonGoBack{
    if (_buttonGoBack == nil) {
        UIButton* button = [UIButton buttonWithType:UIButtonTypeCustom];
        [button setImage:[GUViewUtils cell20Image:@"\uf053"] forState:UIControlStateNormal];
        [button setFrame:CGRectMake(0, 0, 20, 20)];
        [button addTarget:self action:@selector(backButtonTouchUp:) forControlEvents:UIControlEventTouchUpInside];
        _buttonGoBack = [[UIBarButtonItem alloc] initWithCustomView:button];
    }
    return _buttonGoBack;
}

-(UIBarButtonItem*)buttonGoForward{
    if (_buttonGoForward == nil) {
        UIButton* button = [UIButton buttonWithType:UIButtonTypeCustom];
        [button setImage:[GUViewUtils cell20Image:@"\uf054"] forState:UIControlStateNormal];
        [button setFrame:CGRectMake(0, 0, 20, 20)];
        [button addTarget:self action:@selector(forwardButtonTouchUp:) forControlEvents:UIControlEventTouchUpInside];
        _buttonGoForward = [[UIBarButtonItem alloc] initWithCustomView:button];
    }
    return _buttonGoForward;
}


-(UIActivityIndicatorView*)activityIndicator{
    if (_activityIndicator == nil) {
        _activityIndicator = [[UIActivityIndicatorView alloc] initWithActivityIndicatorStyle:UIActivityIndicatorViewStyleGray];
        _activityIndicator.frame = CGRectMake(11, 7, 20, 20);
    }
    return _activityIndicator;
}

-(UIWebView *)siteWebView{
    if (_siteWebView == nil) {
        _siteWebView = [[UIWebView alloc] initWithFrame:CGRectMake(0, 44 + [GUViewUtils viewStartOffset],  CGRectGetWidth([self.view frame]), CGRectGetHeight([self.view frame]) - 44 - [GUViewUtils viewStartOffset])];
        _siteWebView.scalesPageToFit = YES;
        _siteWebView.delegate = self;
        _siteWebView.autoresizingMask = UIViewAutoresizingFlexibleHeight;
        NSURLRequest *request = [NSURLRequest requestWithURL:_siteUrl];
        [_siteWebView loadRequest:request];
        [self.view addSubview:_siteWebView];
        [self.view bringSubviewToFront:[self toolBar]];
    }
    return _siteWebView;
}



#pragma mark - Actions

- (void)backButtonTouchUp:(id)sender {
    [_siteWebView goBack];
    
    [self toggleBackForwardButtons];
}

- (void)forwardButtonTouchUp:(id)sender {
    [_siteWebView goForward];
    
    [self toggleBackForwardButtons];
}

- (void)refreshButtonTouchUp:(id)sender {
    [_siteWebView reload];
    
    [self toggleBackForwardButtons];
}

- (void)buttonActionTouchUp:(id)sender {
    [self showActionSheet];
}

- (void)closeButtonActionTouchUp:(id)sender {
    [[self siteWebView] stopLoading];
    if (self.navigationController) {
        [self.navigationController popViewControllerAnimated:YES];
    }else{
        [self dismissViewControllerAnimated:YES completion:nil];
    }
  
}


-(void) toggleBackForwardButtons {
    //[[[self  buttonGoBack] customView] setUserInteractionEnabled:_siteWebView.canGoBack];
    [self buttonGoBack].enabled = _siteWebView.canGoBack;
    //[[[self  buttonGoForward] customView] setHidden:!(_siteWebView.canGoForward)];
    [self buttonGoForward].enabled = _siteWebView.canGoForward;
}

-(void)showActivityIndicators {
    [[self activityIndicator ] setHidden:NO];
    [[self activityIndicator] startAnimating];
    [UIApplication sharedApplication].networkActivityIndicatorVisible = YES;
}

-(void)hideActivityIndicators {
    [[self activityIndicator] setHidden:YES];
    [[self activityIndicator] stopAnimating];
    [UIApplication sharedApplication].networkActivityIndicatorVisible = NO;
}

- (void)showActionSheet {
    UIActionSheet *actionSheet = [[UIActionSheet alloc] init];
    actionSheet.delegate = self;
    [actionSheet addButtonWithTitle: NSLocalizedString(@"CopyUrlTitle", @"复制链接") ];
    [actionSheet addButtonWithTitle: NSLocalizedString(@"Open in Safari", nil)];
    
    /**
    if ([[UIApplication sharedApplication] canOpenURL:[NSURL URLWithString:@"googlechrome://"]]) {
        // Chrome is installed, add the option to open in chrome.
        [actionSheet addButtonWithTitle:NSLocalizedString(@"Open in Chrome", nil)];
    }*/
    actionSheet.cancelButtonIndex = [actionSheet addButtonWithTitle:NSLocalizedString(@"Cancel", nil)];
	actionSheet.actionSheetStyle = UIActionSheetStyleBlackTranslucent;
    [actionSheet showInView:self.view];
}


///////////////////////////////////////////////////////////////////////////////////////////////
#pragma UIActionSheetDelegate
- (void)actionSheet:(UIActionSheet *)actionSheet clickedButtonAtIndex:(NSInteger)buttonIndex {
    if (buttonIndex == [actionSheet cancelButtonIndex]) return;
    
    NSURL *url = _siteUrl;
    if (url == nil || [url isEqual:[NSURL URLWithString:@""]]) {
        return;
    }
    
    if (buttonIndex == kCopyUrlButtonIndex) {
        UIPasteboard *pasteboard = [UIPasteboard generalPasteboard];
        pasteboard.string = [url absoluteString];
    }else if (buttonIndex == kSafariButtonIndex) {
        [[UIApplication sharedApplication] openURL:url];
    }
    else if (buttonIndex == kChromeButtonIndex) {
        NSString *scheme = url.scheme;
        
        // Replace the URL Scheme with the Chrome equivalent.
        NSString *chromeScheme = nil;
        if ([scheme isEqualToString:@"http"]) {
            chromeScheme = @"googlechrome";
        } else if ([scheme isEqualToString:@"https"]) {
            chromeScheme = @"googlechromes";
        }
        // Proceed only if a valid Google Chrome URI Scheme is available.
        if (chromeScheme) {
            NSString *absoluteString = [url absoluteString];
            NSRange rangeForScheme = [absoluteString rangeOfString:@":"];
            NSString *urlNoScheme = [absoluteString substringFromIndex:rangeForScheme.location];
            NSString *chromeURLString = [chromeScheme stringByAppendingString:urlNoScheme];
            NSURL *chromeURL = [NSURL URLWithString:chromeURLString];
            // Open the URL with Chrome.
            [[UIApplication sharedApplication] openURL:chromeURL];
        }
    }
}

///////////////////////////////////////////////////////////////////////////////////////////////
#pragma UIWebViewDelegate
- (BOOL)webView:(UIWebView *)webView shouldStartLoadWithRequest:(NSURLRequest *)request navigationType:(UIWebViewNavigationType)navigationType {
    if ([[request.URL absoluteString] hasPrefix:@"sms:"]) {
        [[UIApplication sharedApplication] openURL:request.URL];
        return NO;
    }
    return YES;
}

- (void)webViewDidStartLoad:(UIWebView *)webView {
    [self toggleBackForwardButtons];
    [self showActivityIndicators];
}

- (void)webViewDidFinishLoad:(UIWebView *)_webView {
    // Show page title on title bar?
    [self hideActivityIndicators];
    [self toggleBackForwardButtons];
}


- (void)webView:(UIWebView *)webView didFailLoadWithError:(NSError *)error {
    [self hideActivityIndicators];
    if ([error code] == NSURLErrorCancelled) {
        return;
    }
}





@end
