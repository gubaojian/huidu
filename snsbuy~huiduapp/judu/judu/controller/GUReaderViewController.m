//
//  GUReaderViewController.m
//  judu
//
//  Created by lurina on 13-7-31.
//  Copyright (c) 2013年 baobao. All rights reserved.
//


#import "GUReaderViewController.h"
#import "GUSiteViewController.h"
#import "MobClick.h"
#import "GUConfig.h"
#import "UIColor+Expanded.h"
#import "GUCategoryViewCell.h"
#import "GUCategoryTableViewHeaderView.h"
#import "GUCategoryArticleViewController.h"
#import "GUSystemUtils.h"
#import "GUArticleService.h"
#import "GUTemplateUtils.h"
#import "GUFavoriteService.h"
#import "GUHttpClient.h"
#import "UIView+Toast.h"
#import "GUArticleCollectService.h"
#import "UIViewController+UIShare.h"



@interface GUReaderViewController ()<UIWebViewDelegate, UITableViewDataSource, UITableViewDelegate,UIActionSheetDelegate, UIScrollViewDelegate>{
    UIWebView* _articleWebView;
    UIToolbar* _bottomBar;
    UIButton* _seeArticleSourceButton;
    UITableView* _categoryTableView;
    
    GUArticleService* _articleService;
    GUArticle* _article;
    NSArray* _categoryList;
    NSString* _articleHuiduUrl;
    UIButton* _addCollectButton;
    UIButton* _goHome;
    
    
}

@end

@implementation GUReaderViewController

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
           _article = [query objectForKey:@"article"];
            if (_article == nil) {
                NSNumber* articleId = [query objectForKey:@"articleId"];
                _article = [[GUArticle alloc] init];
                _article.id = articleId;
             }
        }
       
    }
    return self;
}

-(void) loadView{
    [super loadView];
    self.navigationController.navigationBarHidden = YES;
    self.view.backgroundColor = [UIColor whiteColor];
    [self articleWebView];
    [self bottomBar];
    [self quickHomeButton];

    [self loadArticleDetail];
}


- (void)viewDidLoad
{
    [super viewDidLoad];
    
    [MobClick event:@"JuduReaderDetail"];
	// Do any additional setup after loading the view.
}

-(void) viewWillAppear:(BOOL)animated{
    [super viewWillAppear:animated];
    if (_categoryList != nil && [_categoryList count] > 0 && _categoryTableView != nil) {
        [[self categoryTableViewForWebView] reloadData];
    }
    self.navigationController.navigationBarHidden = YES;
}

-(void) viewDidAppear:(BOOL)animated{
    [super viewDidAppear:animated];
}

-(void) viewDidDisappear:(BOOL)animated{
    [super viewDidDisappear:animated];
   
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


-(void)dealloc{
    [self clean];
}

-(void) clean{
    if (_articleWebView) {
        [_articleWebView loadHTMLString:@"" baseURL:nil];
        [_articleWebView stopLoading];
        [_articleWebView setDelegate:nil];
        [_articleWebView removeFromSuperview];
        _articleWebView = nil;
    }
    
    if (_categoryTableView != nil) {
        [_categoryTableView removeFromSuperview];
        _categoryTableView = nil;
    }
    
    if (_seeArticleSourceButton != nil) {
        [_seeArticleSourceButton removeFromSuperview];
        _seeArticleSourceButton = nil;
    }
    if (_adBannerView != nil) {
        [_adBannerView removeFromSuperview];
        _adBannerView = nil;
    }
    
    
    if (_bottomBar) {
        _bottomBar = nil;
    }
    
    if (_articleService) {
        [_articleService cancelRequestOperation];
        _articleService = nil;
    }
    
    if (_categoryList) {
        _categoryList = nil;
    }
    
    if (_article) {
        [_article setUrl:nil];
        [_article setContent:nil];
    }
    
    if (_articleHuiduUrl) {
        _articleHuiduUrl = nil;
    }
    
    [super clean];
}





////////////////////////////////////////////////////////////////////////////////////
#pragma init method
-(UIWebView*) articleWebView{
    if (_articleWebView == nil) {
        _articleWebView = [[UIWebView alloc] initWithFrame:CGRectMake(0, 0, self.view.frame.size.width, CGRectGetHeight([self.view frame]) - 44)];
        _articleWebView.backgroundColor = [UIColor whiteColor];
        [[_articleWebView scrollView] setShowsHorizontalScrollIndicator:YES];
        if ([[_articleWebView subviews] count] > 0) {
            for (UIView *view in [[[_articleWebView subviews] objectAtIndex:0] subviews]) {
                if ([view isKindOfClass:[UIImageView class]]) view.hidden = YES;
            }
        }
        [_articleWebView setAllowsInlineMediaPlayback:YES];
    
        _articleWebView.dataDetectorTypes = UIDataDetectorTypeNone;
        _articleWebView.delegate = self;
        _articleWebView.scrollView.decelerationRate = UIScrollViewDecelerationRateFast*1.5;
        _articleWebView.scrollView.showsVerticalScrollIndicator = YES;
        _articleWebView.scrollView.showsHorizontalScrollIndicator = NO;
        _articleWebView.scrollView.scrollIndicatorInsets = UIEdgeInsetsMake(0, 0, 0, 0);
        _articleWebView.scrollView.delegate = self;
        [_articleWebView setScalesPageToFit:YES];
 
        [self.view addSubview:_articleWebView];
    }
    return _articleWebView;
}

-(UIToolbar*)bottomBar{
    if (_bottomBar == nil) {
        _bottomBar = [[UIToolbar alloc] initWithFrame:CGRectMake(0, CGRectGetHeight([self.view frame]) - 44, self.view.frame.size.width, 44)];
        
        UIBarButtonItem* leftSpace = [[UIBarButtonItem alloc] initWithBarButtonSystemItem:UIBarButtonSystemItemFixedSpace target:nil action:nil];
        leftSpace.width = 0;
        
        UIButton* backButton = [UIButton buttonWithType:UIButtonTypeCustom];
        [backButton setFrame:CGRectMake(0, 0, 64, 44)];
        [backButton setImage:[GUViewUtils cell22Image:@"\uf060"] forState:UIControlStateNormal];
        [backButton setImageEdgeInsets:UIEdgeInsetsMake(10, 20, 10, 20)];
        [backButton addTarget:self action:@selector(backAction) forControlEvents:UIControlEventTouchUpInside];
        UIBarButtonItem* back = [[UIBarButtonItem alloc] initWithCustomView:backButton];

        UIBarButtonItem* flexSpaceOne = [[UIBarButtonItem alloc] initWithBarButtonSystemItem:UIBarButtonSystemItemFlexibleSpace target:nil action:nil];
        
        UIButton* fontButton = [UIButton buttonWithType:UIButtonTypeCustom];
        [fontButton setFrame:CGRectMake(0, 0, 54, 44)];
        
        FAKIcon* bigFont = [FAKFontAwesome fontIconWithSize:18];
        [bigFont addAttribute:NSForegroundColorAttributeName value:[UIColor colorWithRed:102/255.0f green:102/255.0f blue:102/255.0f alpha:1.0f]];
        bigFont.drawingPositionAdjustment = UIOffsetMake(0, 1);
        FAKIcon* smallFont = [FAKFontAwesome fontIconWithSize:12];
        [smallFont addAttribute:NSForegroundColorAttributeName value:[UIColor colorWithRed:102/255.0f green:102/255.0f blue:102/255.0f alpha:1.0f]];
        smallFont.drawingPositionAdjustment = UIOffsetMake(-5, 3.5f);
        NSArray* icons = @[smallFont,bigFont];
        UIImage* font = [UIImage imageWithStackedIcons:icons imageSize:CGSizeMake(22, 22)];
        
        
        [fontButton setImage:font forState:UIControlStateNormal];
        [fontButton setImageEdgeInsets:UIEdgeInsetsMake(10, 15, 10, 15)];
        [fontButton addTarget:self action:@selector(chooseFontSize) forControlEvents:UIControlEventTouchUpInside];
        UIBarButtonItem* fontSetting = [[UIBarButtonItem alloc] initWithCustomView:fontButton];
        
        UIBarButtonItem* flexSpacTwo = [[UIBarButtonItem alloc] initWithBarButtonSystemItem:UIBarButtonSystemItemFlexibleSpace target:nil action:nil];
        
        UIBarButtonItem* favorite = [[UIBarButtonItem alloc] initWithCustomView:[self addCollectButton]];
        
        UIBarButtonItem* flexSpacThree = [[UIBarButtonItem alloc] initWithBarButtonSystemItem:UIBarButtonSystemItemFlexibleSpace target:nil action:nil];
        
        UIButton* shareButton = [UIButton buttonWithType:UIButtonTypeCustom];
        [shareButton setFrame:CGRectMake(0, 0, 22 + 20 + 20, 22 + 10 + 10)];
        [shareButton setImage:[GUViewUtils cell22Image:@"\uf045"] forState:UIControlStateNormal];
        [shareButton setImageEdgeInsets:UIEdgeInsetsMake(10, 20, 10, 20)];
        [shareButton addTarget:self action:@selector(shareArticleToSns) forControlEvents:UIControlEventTouchUpInside];
        UIBarButtonItem* share = [[UIBarButtonItem alloc] initWithCustomView:shareButton];
        
        UIBarButtonItem* rightSpace = [[UIBarButtonItem alloc] initWithBarButtonSystemItem:UIBarButtonSystemItemFixedSpace target:nil action:nil];
        rightSpace.width = 0;
        
        
        NSArray* items = [NSArray arrayWithObjects:leftSpace, back, flexSpaceOne,fontSetting, flexSpacTwo, favorite, flexSpacThree, share, rightSpace, nil];
        [_bottomBar setItems:items];
        [self.view addSubview:_bottomBar];
    }
    return _bottomBar;
}




-(UIButton*)quickHomeButton{
    /**
    if (_goHome == nil) {
        if ([GUSystemUtils isIpad]) {
            if ([self.navigationController.viewControllers count] >= 2) {
                _goHome = [UIButton buttonWithType:UIButtonTypeCustom];
                [_goHome setFrame:CGRectMake(self.view.frame.size.width - 64, 20, 64, 44)];
                [_goHome setImageEdgeInsets:UIEdgeInsetsMake(9.5f, 19.5f,  9.5f, 19.5f)];
                [_goHome setImage:[GUViewUtils cell25Image:@"\uf015"] forState:UIControlStateNormal];
                [_goHome addTarget:self action:@selector(goHomeAction:) forControlEvents:UIControlEventTouchUpInside];
                [self.view addSubview:_goHome];
            }
        }
    }
    return _goHome;
     */
    return nil;
}


-(void) goHomeAction:(UIButton*) button{
    [self.navigationController popToRootViewControllerAnimated:NO];
    [self.tabBarController setSelectedIndex:0];
}


-(UIButton*)addCollectButton{
    if (_addCollectButton == nil) {
        _addCollectButton= [UIButton buttonWithType:UIButtonTypeCustom];
        [_addCollectButton setFrame:CGRectMake(0, 0, 54, 44)];
        [_addCollectButton setImageEdgeInsets:UIEdgeInsetsMake(10, 15, 10, 15)];
        [_addCollectButton addTarget:self action:@selector(articleCollectAction) forControlEvents:UIControlEventTouchUpInside];
        [self refreshCollectButton];
    }
    return _addCollectButton;
}

-(void)refreshCollectButton{
    if ([GUArticleCollectService isCollectArticle:[_article id]]) {
        [_addCollectButton setImage:[GUViewUtils cell22Image:@"\uf004"] forState:UIControlStateNormal];
    }else{
        [_addCollectButton setImage:[GUViewUtils cell22Image:@"\uf08a"] forState:UIControlStateNormal];
    }
}



-(UIButton*) seeArticleSourceButtonForWebView{
    if (_seeArticleSourceButton == nil) {
        
        CGFloat buttonHeight = 40;
        CGFloat y = [self articleWebView].scrollView.contentSize.height;
        y += (80 - buttonHeight)/2 ; //80 for button space
        y += kAdViewSizeDefaultHeight + 50;
       
        
        _seeArticleSourceButton = [UIButton buttonWithType:UIButtonTypeCustom];
        _seeArticleSourceButton.frame = CGRectMake(20, y, CGRectGetWidth([self.view frame]) - 40, buttonHeight);
        [_seeArticleSourceButton setBackgroundImage:[UIImage imageNamed:@"f8f8f8"] forState:UIControlStateNormal];
        [_seeArticleSourceButton setTitle:@"查看原文" forState:UIControlStateNormal];
        [_seeArticleSourceButton setTitleColor:[UIColor colorWithHexString:@"929292"] forState:UIControlStateNormal];
        [_seeArticleSourceButton setTitleColor:[UIColor whiteColor] forState:UIControlStateHighlighted];
        _seeArticleSourceButton.titleLabel.font = [UIFont systemFontOfSize:14];
        _seeArticleSourceButton.tintColor = [UIColor whiteColor];
        [_seeArticleSourceButton addTarget:self action:@selector(seeArticleSourceAction:) forControlEvents:UIControlEventTouchUpInside];
    }
    return _seeArticleSourceButton;
}

-(UITableView*) categoryTableViewForWebView{
    if (_categoryTableView == nil) {
        CGFloat y = _articleWebView.scrollView.contentSize.height;
        y += 80;
        y += kAdViewSizeDefaultHeight + 50;
        CGFloat height = 59*[self categoryListCount] + 26 + 0.5;
        _categoryTableView = [[UITableView alloc] initWithFrame:CGRectMake(0, y, CGRectGetWidth([self.view frame]), height) style:UITableViewStylePlain];
        _categoryTableView.tableHeaderView = [[GUCategoryTableViewHeaderView alloc] initWithFrame:CGRectMake(0, 0, CGRectGetWidth([self.view frame]), 26)];
        UIView* footer = [[UIView alloc] initWithFrame:CGRectMake(0, 0, CGRectGetWidth([self.view frame]), 0.5)];
        footer.backgroundColor = [UIColor colorWithHexString:@"e7e8e9"];
        _categoryTableView.tableFooterView = footer;
        _categoryTableView.backgroundColor = [UIColor colorWithHexString:@"e7e8e9"];
        _categoryTableView.separatorStyle = UITableViewCellSeparatorStyleNone;
        _categoryTableView.scrollEnabled = NO;
        _categoryTableView.delegate = self;
        _categoryTableView.dataSource = self;
    }
    return _categoryTableView;
}



-(GUArticleService*)articleService{
    if (_articleService == nil) {
        _articleService = [[GUArticleService alloc] init];
    }
    return _articleService;
}

-(void) setCategoryList:(NSArray*) categoryList{
    _categoryList = categoryList;
}

-(GUArticle*)article{
    return _article;
}

-(NSUInteger)categoryListCount{
    if(_categoryList == nil){
        return 0;
    }
    return [_categoryList count];
}

////////////////////////////////////////////////////////////////////////////////////
#pragma data load method
-(void) loadArticleDetail{
    [[self statusView] showActivityInView:self.view forFrame:CGRectMake(0, 0, self.view.frame.size.width, CGRectGetHeight([self.view frame]) - 44)];
    if (_article) {
         __weak GUReaderViewController* weakSelf = self;
         [[self articleService] getArticleDetail:[_article id] withCallback:^(GUResult *result) {
             if ([result isSuccess] && [result result]) {
                 [weakSelf setCategoryList:[[result models] objectForKey:@"categoryList"]];
                 [weakSelf renderForArticleDetail:[result result]];
             }else{
               [[weakSelf statusView] showErrorInView:weakSelf.view forFrame:CGRectMake(0, 0, weakSelf.view.frame.size.width, CGRectGetHeight([weakSelf.view frame]) - 44)];
             }
         }];
    }else{
        [[self statusView] showErrorInView:self.view forFrame:CGRectMake(0, 0, self.view.frame.size.width, CGRectGetHeight([self.view frame]) - 44)];
    }
}

-(void) renderForArticleDetail:(GUArticle*) article{
    if (_categoryTableView != nil) {
        [_categoryTableView removeFromSuperview];
        _categoryTableView = nil;
    }
    
    if (_seeArticleSourceButton != nil) {
        [_seeArticleSourceButton removeFromSuperview];
        _seeArticleSourceButton = nil;
    }
    if (_adBannerView != nil) {
        [_adBannerView removeFromSuperview];
        _adBannerView = nil;
    }
    
    _article = article;
    GUReaderViewController* weakSelf = self;
    dispatch_async(dispatch_get_global_queue(DISPATCH_QUEUE_PRIORITY_DEFAULT, 0), ^{
        __block NSString* html = [GUTemplateUtils renderArticle:[weakSelf article]];
        GUReaderViewController* strongSelf = weakSelf;
        dispatch_async(dispatch_get_main_queue(), ^{
            [[strongSelf articleWebView] loadHTMLString:html baseURL:[[NSBundle mainBundle] bundleURL]];
        });
    });
   
    
    
}

-(void)reloadForError{
    [self loadArticleDetail];
}

-(NSString*) articleHuiduUrl{
    if (_articleHuiduUrl == nil) {
       _articleHuiduUrl = [NSString stringWithFormat:@"http://article.lanxijun.com/articleDetail.html?id=%@",  [[_article id] stringValue]];
    }
    return _articleHuiduUrl;
}


///////////////////////////////////////////////////////////////////////////////////////////////
#pragma UITableViewDelegate
- (CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath{
    return 59;
}

- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath{
    [tableView deselectRowAtIndexPath:indexPath animated:YES];
    int row = [indexPath row];
    if (row >= [_categoryList count]) {
        return;
    }
    GUCategory* category = [_categoryList objectAtIndex:row];
    if (category  != nil) {
        NSDictionary* query = [NSDictionary dictionaryWithObjectsAndKeys:category, @"category", nil];
        GUCategoryArticleViewController* categoryArticleController = [[GUCategoryArticleViewController alloc] initWithQuery:query];
        [self.navigationController pushViewController:categoryArticleController animated:YES];
    }
}

///////////////////////////////////////////////////////////////////////////////////////////////
#pragma UITableViewDataSource

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section{
    return [_categoryList count];
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath{
    static NSString* cellIdentify = @" GUFavoriteViewCellIdentify";
    GUCategoryViewCell* cell = [tableView dequeueReusableCellWithIdentifier:cellIdentify];
    if (cell == nil) {
        cell = [[GUCategoryViewCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:cellIdentify];
    }

    int row = [indexPath row];
    GUCategory* category = [_categoryList objectAtIndex:row];
    cell.categoryTitleLabel.text  = [category name];
    [cell.favoriteButton addTarget:self action:@selector(favoriteButtonAction:forEvent:) forControlEvents:UIControlEventTouchUpInside];
    if ([GUFavoriteService isFavoriteCategory:[category id]]) {
          [cell setFavoriteImage:NO];
    }else{
        [cell setFavoriteImage:YES];
       
    }
    return cell;
}

////////////////////////////////////////////////////////////////////////////////////
#pragma favoriteAction
-(void) favoriteButtonAction:(UIButton*) button forEvent:(UIEvent *) event{
    UITouch* touch =  [[event touchesForView:button] anyObject];
    CGPoint point = [touch locationInView:_categoryTableView];
    NSIndexPath* indexPath = [_categoryTableView indexPathForRowAtPoint:point];
    int row = [indexPath row];
    GUCategory* category = [_categoryList objectAtIndex:row];
    if ([GUFavoriteService isFavoriteCategory:[category id]]) {
        [GUFavoriteService removeFavoriteCategory:[category id]];
        [button setBackgroundImage:[UIImage imageNamed:@"add_favorite"] forState:UIControlStateNormal];
    }else{
        [GUFavoriteService addFavoriteCategory:category];
        [button setBackgroundImage:[UIImage imageNamed:@"cancel_favorite"] forState:UIControlStateNormal];
    }
}

////////////////////////////////////////////////////////////////////////////////////
#pragma seeArticleSourceAction
-(void) seeArticleSourceAction:(UIButton*) button{
    if ([_article url] != nil) {
        NSDictionary* query = [NSDictionary dictionaryWithObjectsAndKeys:[_article url], @"siteUrl", nil];
        GUSiteViewController* siteViewController = [[GUSiteViewController alloc] initWithQuery:query];
        [self.navigationController presentViewController:siteViewController animated:YES completion:nil];
    } 
}


////////////////////////////////////////////////////////////////////////////////////
#pragma UIButtonAction

-(void) chooseFontSize{
    UIActionSheet* actionSheel = [[UIActionSheet alloc] initWithTitle:NSLocalizedString(@"SettingFontTips", @"LocalizedString") delegate:self cancelButtonTitle:NSLocalizedString(@"Cancel", @"LocalizedString") destructiveButtonTitle:nil otherButtonTitles:  NSLocalizedString(@"FontSizeSmall", @"LocalizedString"),
                                  NSLocalizedString(@"FontSizeNormal", @"LocalizedString"),
                                  NSLocalizedString(@"FontSizeLarge", @"LocalizedString"),
                                  NSLocalizedString(@"FontSizeLargest", @"LocalizedString"), nil];

    const int fontSize = [GUTemplateUtils fontSize];
    int index = 1;
    if (fontSize == FONT_SIZE_SMALL) {
        index = 0;
    }else if (FONT_SIZE_LARGE == fontSize){
        index = 2;
    }else if (FONT_SIZE_LARGEST == fontSize){
        index = 3;
    }
    actionSheel.destructiveButtonIndex = index;
    [actionSheel showFromToolbar:_bottomBar];
}

- (void)actionSheet:(UIActionSheet *)actionSheet clickedButtonAtIndex:(NSInteger)buttonIndex{
    int fontSize = FONT_SIZE_NORMAL;
    if (buttonIndex == 0) {
        fontSize = FONT_SIZE_SMALL;
    }else if (buttonIndex == 1){
        fontSize = FONT_SIZE_NORMAL;
    }else if (buttonIndex == 2){
        fontSize = FONT_SIZE_LARGE;
    }else if (buttonIndex == 3){
        fontSize = FONT_SIZE_LARGEST;
    }
    if ([GUTemplateUtils fontSize] != fontSize) {
        [GUTemplateUtils setFontSize:fontSize];
        [self renderForArticleDetail:_article];
    }
    actionSheet = nil;
}


-(void) articleCollectAction{
    if ([GUArticleCollectService isCollectArticle:[_article id]]) {
        [GUArticleCollectService removeCollectArticle:_article];
        [self.view makeToast:NSLocalizedString(@"RemoveArticleFromCollect", @"取消收藏成功")];
    }else{
         [GUArticleCollectService addCollectArticle:_article];
         [self.view makeToast:NSLocalizedString(@"AddArticleToCollect", @"文章收藏成功")];
    }
    [self refreshCollectButton];
}

-(void) shareArticleToSns{
    NSMutableString*  shareText = [[NSMutableString alloc] init];
    [shareText appendString:[_article title]];
    [shareText appendString:@"    "];
    [shareText appendString:[self articleHuiduUrl]];
    
    
    [self share:shareText sourceView:[self bottomBar] complete:^(UIActivityType  _Nullable activityType, BOOL completed, NSArray * _Nullable returnedItems, NSError * _Nullable activityError) {
        
    }];
}



////////////////////////////////////////////////////////////////////////////////////
#pragma UMSocialUIDelegate
/***
-(void)didSelectSocialPlatform:(NSString *)platformName withSocialData:(UMSocialData *)socialData{
    NSString* url = [self articleHuiduUrl];//生成文章的预览地址。
    socialData.extConfig.title = [NSString stringWithFormat:@"%@  ", [_article title]]; //用于指定微信分享标题，qzone分享的标题和邮件分享的标题。
    if ([platformName isEqualToString: UMShareToWechatSession]){
        socialData.extConfig.wechatSessionData.url = url;
        socialData.extConfig.wechatSessionData.title = [_article title];
        socialData.extConfig.wxMessageType = UMSocialWXMessageTypeOther; //设置为网页、音乐等其他类型
        WXWebpageObject *webObject = [WXWebpageObject object];    //初始化微信网页对象
        webObject.webpageUrl = url; //设置网页的url
        socialData.extConfig.wxMediaObject = webObject; //设置网页对象
        return;
    }
    if([platformName isEqualToString:UMShareToWechatTimeline]) {
        socialData.extConfig.wechatTimelineData.url = url;
        socialData.extConfig.wechatTimelineData.title = [_article title];
        socialData.extConfig.wxMessageType = UMSocialWXMessageTypeOther; //设置为网页、音乐等其他类型
        WXWebpageObject *webObject = [WXWebpageObject object];    //初始化微信网页对象
        webObject.webpageUrl = url; //设置网页的url
        socialData.extConfig.wxMediaObject = webObject; //设置网页对象
    }
    
    if ([platformName isEqualToString:UMShareToEmail]) {
        NSString* mailMessage = [NSString stringWithFormat:@"<a href='%@'/>%@<a>  %@ ", url, [_article title], url];
        socialData.extConfig.emailData.title = socialData.extConfig.title;
        socialData.extConfig.emailData.shareText = mailMessage;
    }
}
*/
////////////////////////////////////////////////////////////////////////////////////
#pragma UIWebViewDelegate

- (BOOL)webView:(UIWebView *)webView shouldStartLoadWithRequest:(NSURLRequest *)request navigationType:(UIWebViewNavigationType)navigationType{
    NSLog(@"url %@", request.URL );
    if ([[[request URL] description] hasPrefix:@"huiduaction://loadsuccess"]) {
        [self performSelector:@selector(addNativeButtonAndCategoryViewToWebView) withObject:self afterDelay:0.2];
        return NO;
    }
    return YES;
}
- (void)webViewDidStartLoad:(UIWebView *)webView{
    [[self statusView]  removeViewInfo];
    [webView setHidden:NO];
}
- (void)webViewDidFinishLoad:(UIWebView *)webView{
    //增加按钮
   // [self performSelector:@selector(addNativeButtonAndCategoryViewToWebView) withObject:self afterDelay:0.5];
}


- (void)webView:(UIWebView *)webView didFailLoadWithError:(NSError *)error{
     [[self statusView]  removeViewInfo];
}



- (void)scrollViewDidScroll:(UIScrollView *)scrollView{
   // [self articleWebView].scrollView.delegate = nil;
    //[self addNativeButtonAndCategoryViewToWebView];
}


-(void) addNativeButtonAndCategoryViewToWebView{
    [self addNativeButtonAndCategoryView];
}




-(void) addNativeButtonAndCategoryView{
    UIWebView* webView = [self articleWebView];
    if (_seeArticleSourceButton == nil) {
        CGSize size = webView.scrollView.contentSize;
        [webView.scrollView addSubview:[self baiduMobAdView]];
        [webView.scrollView addSubview:[self seeArticleSourceButtonForWebView]];
        if ([_categoryList count] > 0) {
            [webView.scrollView addSubview:[self categoryTableViewForWebView]];
            size.height += (80 + [self categoryTableViewForWebView].frame.size.height) + kAdViewSizeDefaultHeight + 40;;
        }else{
            size.height += (80 + 20)  +  kAdViewSizeDefaultHeight + 40;;
        }
        size.width = webView.frame.size.width; //防止水平滑动
        
        webView.scrollView.contentSize = size;
        
        [webView.scrollView setNeedsLayout];
        [webView.scrollView layoutIfNeeded];
    }
}


/**
 *  应用的APPID
 */
- (NSString *)publisherId
{
    return  @"b936a1f1"; //@"your_own_app_id";
}


-(NSArray*) keywords{
    NSMutableArray* keywords = [[NSMutableArray alloc] initWithObjects:[self article].title, nil];
    return  keywords;
}


/**
 *  - 用户最高教育学历
 *  - 学历输入数字，范围为0-6
 *  - 0代表小学，1代表初中，2代表中专/高中，3代表专科
 *  - 4代表本科，5代表硕士，6代表博士
 
-(NSInteger) userEducation{
int e = 4 +rand()%(6 - 4 + 1);
    NSLog(@" userEducation  %d", e);
 return e;
}*/




-(GADBannerView*)baiduMobAdView{
    if(_adBannerView == nil){
        CGFloat y = [self articleWebView].scrollView.contentSize.height;
        if ([GUSystemUtils isIpad]) {
            _adBannerView = [[GADBannerView alloc] initWithFrame:CGRectMake(60, y, CGRectGetWidth([self.view frame]) - 120, kAdViewSizeDefaultHeight)];
        }else{
            _adBannerView = [[GADBannerView alloc] initWithFrame:CGRectMake(20, y, CGRectGetWidth([self.view frame]) - 40, kAdViewSizeDefaultHeight - 8)];
        }
        _adBannerView.adUnitID = @"ca-app-pub-5638649287709716/3380117336";
        _adBannerView.rootViewController = self.navigationController;
        GADRequest *request = [GADRequest request];
        request.keywords = [self keywords];
        
        request.testDevices = @[ kGADSimulatorID ];
        // Requests test ads on devices you specify. Your test device ID is printed to the console when
        // an ad request is made. GADBannerView automatically returns test ads when running on a
        // simulator.
        //把在mssp.baidu.com上创建后获得的广告位id写到这里
       
         [_adBannerView loadRequest:request];
    }
    return _adBannerView;

}





@end
