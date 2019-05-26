//
//  GUAbout.m
//  powerfee
//
//  Created by lurina on 12-11-25.
//  Copyright (c) 2012年 baobao. All rights reserved.
//

#import "GUAbout.h"
#import "MobClick.h"
#import "GUConfigService.h"
#import "GUTemplateUtils.h"

@implementation GUAbout

@synthesize sectionOneData;

@synthesize sectionTwoData;

@synthesize sectionThreeData;

@synthesize sectionFourData;

@synthesize alertDialog;

//@synthesize adBannerView;


static const int SECTION_NUM = 4;

#define ABOUT_VIEW_ADMOB_MANAGER_IDENTIFY @"aboutView"

- (id)initWithStyle:(UITableViewStyle)style
{
    self = [super initWithStyle:style];
    if (self) {
        // Custom initialization
        
    }
    return self;
}

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        // Custom initialization
    }
    return self;
}

- (void)awakeFromNib{
    [super awakeFromNib];

    if (self.navigationController) {
            self.navigationController.tabBarItem.title = NSLocalizedString(@"About", @"关于");
            [self.navigationController.tabBarItem setFinishedSelectedImage:[UIImage imageNamed:@"about_selected"] withFinishedUnselectedImage:[UIImage imageNamed:@"about"]];
    }
  
    //self.tabBarItem.title = NSLocalizedString(@"About", @"关于");
    //[self.tabBarItem setFinishedSelectedImage:[UIImage imageNamed:@"about_selected"] withFinishedUnselectedImage:[UIImage imageNamed:@"about"]];
    GULog(LOG_LEVEL_DEBUG, @"GUAboutTableView awakeFromNib");
}

- (void)loadView{
    [self.navigationController.navigationBar setTranslucent:NO];
    [super loadView];
     GULog(LOG_LEVEL_DEBUG, @"GUAboutTableView loadView");
    
    self.view.backgroundColor = GLOBAL_BACKGROUND_COLOR;
    self.tableView.backgroundColor = GLOBAL_BACKGROUND_COLOR;
    UIView* backgroundView = [[UIView alloc] init];
    backgroundView.backgroundColor = GLOBAL_BACKGROUND_COLOR;
    self.tableView.backgroundView = backgroundView;
    [self.tableView setScrollEnabled:YES];
}

- (void)viewDidLoad
{
    [super viewDidLoad];
    // Uncomment the following line to preserve selection between presentations.
    // self.clearsSelectionOnViewWillAppear = NO;
    sectionOneData = [[NSArray alloc] initWithObjects:
                       NSLocalizedString(@"ArticleFontSize", @"Font Size"),
                      NSLocalizedString(@"AutoDownloadOfflineTips", @"AutoDownload"),
                       NSLocalizedString(@"AbountModuleMyApps", @"My Other Apps"),
                       NSLocalizedString(@"AbountModuleListShareWithFriends", @"LocalizedString"),
                      NSLocalizedString(@"AbountModuleListGiveGood",  @"LocalizedString"), nil];
    
    sectionTwoData = [[NSArray alloc] initWithObjects:
                      @"推荐我喜欢的博客",
                      NSLocalizedString(@"AbountModuleListBugReports", @"LocalizedString"),
                      NSLocalizedString(@"AbountModuleListContactMe", @"LocalizedString"), nil];
    
    sectionThreeData = [[NSArray alloc] initWithObjects:
                        NSLocalizedString(@"AbountModuleListAboutEverTrack", @"LocalizedString"),
                       // NSLocalizedString(@"CommonMessagePrivacyPolicy", @"LocalizedString"),
                        //NSLocalizedString(@"CommonMessageCopyright", @"LocalizedString"),
                       // NSLocalizedString(@"CommonMessageVersion", @"LocalizedString"),
                        nil];
    if (SHOW_ADMOB) {
        sectionFourData = [[NSArray alloc] initWithObjects:
                           NSLocalizedString(@"CommonMessageRemoveAd", @"LocalizedString"), nil];
        [self addAdmob];
    }
    
    // Uncomment the following line to display an Edit button in the navigation bar for this view controller.
    
    
 
 
    if (self.navigationController.tabBarController == nil) {
        UIBarButtonItem *done =  [[UIBarButtonItem alloc] initWithTitle:
                                  NSLocalizedString(@"CommonButtonTextBack", @"LocalizedString")
                                                                  style: UIBarButtonItemStylePlain target:self action:@selector(doneButtonTapped:)];
         self.navigationItem.leftBarButtonItem = done;
    }
    
    self.navigationItem.title =  NSLocalizedString(@"AbountModuleListAboutEverTrackNavigationTitle", @"LocalizedString");
    
    UISwipeGestureRecognizer* swipeGesture = [[UISwipeGestureRecognizer alloc] initWithTarget:self action:@selector(swipeAction:)];
    [self.view addGestureRecognizer:swipeGesture];
    
    GULog(LOG_LEVEL_DEBUG, @"AboutTabView init from board");
}

-(void) viewDidAppear:(BOOL)animated{
    [super viewDidAppear:animated];
    [MobClick beginEvent:@"JuduAboutTab"];
    self.navigationItem.title =  NSLocalizedString(@"AbountModuleListAboutEverTrackNavigationTitle", @"LocalizedString");
}

-(void) viewDidDisappear:(BOOL)animated{
    [super viewDidDisappear:animated];
    [MobClick endEvent:@"JuduAboutTab"];
}





- (void)viewDidUnload
{
    [self setSectionOneData:nil];
    [self setSectionTwoData:nil];
    [self setSectionThreeData:nil];
   // [self setAdBannerView:nil];
    [super viewDidUnload];
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

#pragma mark - Table view data source

- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView
{
    // Return the number of sections.
    if (SHOW_ADMOB) {
         return SECTION_NUM;
    }else{
        return SECTION_NUM - 1;
    }
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    if(section == 0){
        return [sectionOneData count];  //分享及评价功能等上线后再加入
    }else if(section == 1){
        return [sectionTwoData count];
    }else if(section == 2){
        return  [sectionThreeData count];
    }else if(section == 3){
        return  [sectionFourData count];
    }
    
    return 0;
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    NSString *CellIdentifier = @"Cell";
    UITableViewCell *cell = nil;
    if ([indexPath section] == 0 && [indexPath row] == 0) {
        CellIdentifier  = @"SegmentControlCell";
        cell = [tableView dequeueReusableCellWithIdentifier:CellIdentifier];
        if(cell == nil) {
            cell = [[UITableViewCell alloc] initWithStyle: UITableViewCellStyleSubtitle reuseIdentifier:CellIdentifier];
            cell.backgroundColor = [UIColor whiteColor];
            cell.accessoryType = UITableViewCellAccessoryNone;
            cell.selectionStyle = UITableViewCellSelectionStyleNone;
            cell.textLabel.textColor = [UIColor colorWithHexString:@"535353"];
            cell.textLabel.font = [UIFont systemFontOfSize:16];
            cell.selectionStyle = UITableViewCellSelectionStyleNone;
            
            
            UISegmentedControl* segmentControl = [[UISegmentedControl alloc] initWithItems:@[
                                                NSLocalizedString(@"FontSizeSmall", @"LocalizedString"),
                                                NSLocalizedString(@"FontSizeNormal", @"LocalizedString"),
                                                NSLocalizedString(@"FontSizeLarge", @"LocalizedString"),
                                                NSLocalizedString(@"FontSizeLargest", @"LocalizedString")]];
            UIFont *font = [UIFont boldSystemFontOfSize:12.0f];
            NSDictionary *attributes = [NSDictionary dictionaryWithObject:font
                                                                   forKey:UITextAttributeFont];
            [segmentControl setTitleTextAttributes:attributes
                                          forState:UIControlStateNormal];
            if ([GUSystemUtils isIos7]) {
                if ([GUSystemUtils isIpad]) {
                   segmentControl.frame = CGRectMake(768 - 182,  8, 168, 28);
                }else{
                   segmentControl.frame = CGRectMake(cell.frame.size.width - 182, 8, 168, 28);
                }
            }else{
                if ([GUSystemUtils isIpad]) {
                    segmentControl.frame = CGRectMake(768 - 224, 8, 168, 28);
                }else{
                    segmentControl.frame = CGRectMake( cell.frame.size.width - 192,  8, 168, 28);
                }
            }
            int fontSize = [GUTemplateUtils fontSize];
            if (fontSize == FONT_SIZE_SMALL) {
                [segmentControl setSelectedSegmentIndex:0];
            }else if (fontSize == FONT_SIZE_LARGE){
                [segmentControl setSelectedSegmentIndex:2];
            }else if (fontSize == FONT_SIZE_LARGEST){
                [segmentControl setSelectedSegmentIndex:3];
            }else{
                [segmentControl setSelectedSegmentIndex:1];
            }
            [segmentControl addTarget:self action:@selector(changeReaderFont:) forControlEvents:UIControlEventValueChanged];
    
            [cell addSubview:segmentControl];
        }
        //return cell;
    }else if ([indexPath section] == 0 && [indexPath row] == 1) {
        CellIdentifier  = @"SwtichCell";
        cell = [tableView dequeueReusableCellWithIdentifier:CellIdentifier];
        if(cell == nil) {
            cell = [[UITableViewCell alloc] initWithStyle: UITableViewCellStyleSubtitle reuseIdentifier:CellIdentifier];
            cell.backgroundColor = [UIColor whiteColor];
            cell.accessoryType = UITableViewCellAccessoryNone;
            cell.selectionStyle = UITableViewCellSelectionStyleNone;
            cell.textLabel.textColor = [UIColor colorWithHexString:@"535353"];
            cell.textLabel.font = [UIFont systemFontOfSize:16];
            cell.selectionStyle = UITableViewCellSelectionStyleNone;
            UISwitch* switchButton = nil;
            if ([GUSystemUtils isIos7]) {
                if ([GUSystemUtils isIpad]) {
                    switchButton =  [[UISwitch alloc] initWithFrame:CGRectMake(768 - 64, 9, 80, 26)];
                }else{
                    switchButton =  [[UISwitch alloc] initWithFrame:CGRectMake( cell.frame.size.width - 64, 9, 80, 26)];
                }
            }else{
                if ([GUSystemUtils isIpad]) {
                    switchButton = [[UISwitch alloc] initWithFrame:CGRectMake(768 - 132, 9, 80, 26)];
                }else{
                    switchButton = [[UISwitch alloc] initWithFrame:CGRectMake( cell.frame.size.width - 100, 9, 80, 26)];
                }
            }
            [switchButton setOn:[GUConfigService isAutoDownloadArticleOn]];
            [switchButton addTarget:self action:@selector(switchButtonChanged:) forControlEvents:UIControlEventValueChanged];
            [cell addSubview:switchButton];
        }
    }else{
        CellIdentifier = @"Cell";
        cell = [tableView dequeueReusableCellWithIdentifier:CellIdentifier];
        if(cell == nil) {
            cell = [[UITableViewCell alloc] initWithStyle: UITableViewCellStyleSubtitle reuseIdentifier:CellIdentifier];
            cell.backgroundColor = [UIColor whiteColor];
            cell.accessoryType = UITableViewCellAccessoryDisclosureIndicator;
            cell.selectionStyle = UITableViewCellSelectionStyleNone;
            cell.textLabel.textColor = [UIColor colorWithHexString:@"535353"];
            cell.textLabel.font = [UIFont systemFontOfSize:16];
            cell.selectionStyle = UITableViewCellSelectionStyleNone;
        }
    }
    
   
    
    NSArray* data  = nil;
    if([indexPath section] == 0){
        data = sectionOneData;
    }else if([indexPath section] == 1){
        data = sectionTwoData;
    }else if([indexPath section] == 2){
        data = sectionThreeData;
    }else if([indexPath section] == 3){
        data = sectionFourData;
    }
    
    cell.textLabel.text = [data objectAtIndex:[indexPath row]];

    
    return cell;
}


-(void)changeReaderFont:(UISegmentedControl*)control{
   int index = [control selectedSegmentIndex];
    int fontSize = FONT_SIZE_NORMAL;
    if(index == 0){
        fontSize = FONT_SIZE_SMALL;
    }else if(index == 1){
        fontSize = FONT_SIZE_NORMAL;
    }else if(index == 2){
        fontSize = FONT_SIZE_LARGE;
    }else if(index == 3){
       fontSize = FONT_SIZE_LARGEST;
    }
    [GUTemplateUtils setFontSize:fontSize];
}

-(void) switchButtonChanged:(UISwitch*)button{
    if ([button isOn]) {
        [GUConfigService turnOnAutoDownloadArticle];
    }else{
        [GUConfigService turnOffAutoDownloadArticle];
    }
}

#pragma mark - Table view delegate

- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath
{
    GULog(LOG_LEVEL_DEBUG, @"Clicked Section %d", [indexPath section]);
    if([indexPath section] == 0){ //分享评价
        if([indexPath row] == 2){
            [self showMyDevelopedApps];
        }else if([indexPath row] == 3){  //分享给朋友
            [self shareAppWithFriends];
        }else if([indexPath row] == 4){ //给个好评
            [self rateAppAppleInStore];
        }
    }else if([indexPath section] == 1){
        if([indexPath row] == 0){
            [self sendFavoriteBlog];
        }else if([indexPath row] == 1){        //反馈建议
            [self sendEmailWithSubject: NSLocalizedString(@"AbountModuleListBugReportsSubject", @"LocalizedString")];
        }else if([indexPath row] == 2){  //联系作者
            [self sendEmailWithSubject: NSLocalizedString(@"AbountModuleListContactMeSubject", @"LocalizedString")];
        }
    }else if([indexPath section] == 2){
        NSString* title = nil;
        NSString* htmlResourceName = nil;
        if([indexPath row] == 0){  //关于我
            title =  NSLocalizedString(@"AbountModuleListAboutEverTrack", @"LocalizedString");
            htmlResourceName = @"about";
            GULog(LOG_LEVEL_DEBUG, @"About Me Html Boundle Name: %@", htmlResourceName);
        }
        
        if(htmlResourceName  != nil){
            GUWebViewController* webView = [[GUWebViewController alloc] init];
            [webView setHtmlResourceName:htmlResourceName];
            webView.navigationItem.title =  NSLocalizedString(@"AbountModuleListAboutWebTitle", @"LocalizedString");
            if (self.navigationController) {
                 [self.navigationController pushViewController:webView animated:YES];
            }else{
                [self presentViewController:webView animated:YES completion:nil];
            }
        }
    }else if([indexPath section] == 3){
        [self requestForPuchase];
    }else{ //其它情况，返回主界面
        [self dismissViewControllerAnimated:YES completion:nil];
        
    }

    

}

-(void) requestForPuchase{
    if (![SKPaymentQueue canMakePayments]) {
        UIAlertView* alert = [[UIAlertView alloc] initWithTitle:NSLocalizedString(@"CommonMessageIppPurchaseBeClosed", @"LocalizedString")
                        message:NSLocalizedString(@"CommonMessageIppPurchaseBeClosed", @"LocalizedString")
                        delegate:nil
                        cancelButtonTitle:NSLocalizedString(@"CommonButtonTextOk", @"LocalizedString")
                        otherButtonTitles:nil, nil];
        [alert show];
        return;
    }
    
    if([GUPurchaseManager isPurchased]){
            [[[GUAlertDialog alloc] init] showMessage: NSLocalizedString(@"CommonMessageAlreadyBuyedService", @"LocalizedString")];
            return;
    }
     
    
    if (alertDialog == nil) {
        alertDialog = [[GUAlertDialog alloc] init];
    }
    
    [alertDialog showAlterIndicatorWithMessage:NSLocalizedString(@"CommonMessageProcessing", @"LocalizedString")];
    
    NSSet* identifies = [NSSet setWithObject:[GUPurchaseManager getAppPurchaseIdentify]];
    
    GULog(LOG_LEVEL_DEBUG, @"query for products with identify: %@", [GUPurchaseManager getAppPurchaseIdentify]);
    
    SKProductsRequest*  request = [[SKProductsRequest alloc] initWithProductIdentifiers:identifies];
    request.delegate = self;
    [request start];
}

- (void)productsRequest:(SKProductsRequest *)request didReceiveResponse:(SKProductsResponse *)response{
    NSArray *products = response.products;
    if(products == nil || [products count] <= 0){
        GULog(LOG_LEVEL_DEBUG, @"no products in response");
        if (alertDialog != nil) {
            [alertDialog dismissAlterIndicatorWithMessage];
            alertDialog = nil;
        }
        [self noProductTips];
        return;
    }
    SKProduct * product = nil;
    for (int i=0; i<[products count]; i++) {
        product = [products objectAtIndex:0];
        if ([[product productIdentifier] isEqual: [GUPurchaseManager getAppPurchaseIdentify]]){
            break;
        }
    }
    if (product == nil) {
        GULog(LOG_LEVEL_ERROR, @"cann't find product which identify is %@", [GUPurchaseManager getAppPurchaseIdentify]);
        if (alertDialog != nil) {
            [alertDialog dismissAlterIndicatorWithMessage];
            alertDialog = nil;
        }
        [self noProductTips];
        return;
    }
    
    GULog(LOG_LEVEL_DEBUG, @"product response : %@  %@  %@",  [product price],  [product localizedTitle]
          , [product productIdentifier]);
    SKPayment *payment = [SKPayment paymentWithProduct:product];
    [[SKPaymentQueue defaultQueue] addPayment:payment];
    if (alertDialog != nil) {
        [alertDialog dismissAlterIndicatorWithMessage];
        alertDialog = nil;
    }
    GULog(LOG_LEVEL_DEBUG, @"add product to payment queue.");
}


-(void) noProductTips{
    UIAlertView* alert = [[UIAlertView alloc] initWithTitle:@"Tips" message:@"No In App Purchase Product!" delegate:nil cancelButtonTitle:   NSLocalizedString(@"CommonButtonTextOk", @"LocalizedString") otherButtonTitles:nil, nil];
    [alert show];
}


-(void) sendFavoriteBlog{
    Class mailClass = (NSClassFromString(@"MFMailComposeViewController"));
	if (mailClass != nil) {
		if ([mailClass canSendMail]) {
			MFMailComposeViewController*  mailComposeViewPicker = [[MFMailComposeViewController alloc] init];
            mailComposeViewPicker.mailComposeDelegate = self;
            [mailComposeViewPicker setSubject: @"推荐我喜欢的博客"];
            [mailComposeViewPicker setMessageBody:@"\n\n        欢迎将您喜欢的博客网址发给我们，有了您的推荐，汇读才能让更多人收益。" isHTML:NO];
            [mailComposeViewPicker setToRecipients:@[@"loveaworld@qq.com"]];
            [self presentViewController:mailComposeViewPicker animated:YES completion:nil];
            
		}
		else {
            NSString* message = @"Device not configured to send mail.";
            UIAlertView *dialog = [[UIAlertView alloc] initWithTitle:
                                   NSLocalizedString(@"CommonButtonTextTips", @"LocalizedString")
                                                             message:message delegate:nil
                                                   cancelButtonTitle:
                                   NSLocalizedString(@"CommonButtonTextOk", @"LocalizedString")
                                                   otherButtonTitles:nil] ;
            [dialog show];
		}
	}
	else {
        NSString* message =@"Device not supported to send mail.";
        UIAlertView *dialog = [[UIAlertView alloc] initWithTitle:
                               NSLocalizedString(@"CommonButtonTextTips", @"LocalizedString")
                                                         message:message delegate:nil
                                               cancelButtonTitle:
                               NSLocalizedString(@"CommonButtonTextOk", @"LocalizedString")
                                               otherButtonTitles:nil] ;
        [dialog show];
	}
}


/**发送email  @content  email内容 */
-(void) sendEmailWithSubject:(NSString* ) subject{
    Class mailClass = (NSClassFromString(@"MFMailComposeViewController"));
	if (mailClass != nil) {
		if ([mailClass canSendMail]) {
			MFMailComposeViewController*  mailComposeViewPicker = [[MFMailComposeViewController alloc] init];
            mailComposeViewPicker.mailComposeDelegate = self;
            [mailComposeViewPicker setSubject: subject];
            [mailComposeViewPicker setToRecipients:@[@"loveaworld@qq.com"]];
            [self presentViewController:mailComposeViewPicker animated:YES completion:nil];

		}
		else {
            NSString* message = @"Device not configured to send mail.";
            UIAlertView *dialog = [[UIAlertView alloc] initWithTitle:
                                   NSLocalizedString(@"CommonButtonTextTips", @"LocalizedString")
                                                             message:message delegate:nil
                                                   cancelButtonTitle:
                                   NSLocalizedString(@"CommonButtonTextOk", @"LocalizedString")
                                                   otherButtonTitles:nil] ;
            [dialog show];
		}
	}
	else {
        NSString* message =@"Device not supported to send mail.";
        UIAlertView *dialog = [[UIAlertView alloc] initWithTitle:
                               NSLocalizedString(@"CommonButtonTextTips", @"LocalizedString")
                                                         message:message delegate:nil
                                               cancelButtonTitle:
                               NSLocalizedString(@"CommonButtonTextOk", @"LocalizedString")
                                               otherButtonTitles:nil] ;
        [dialog show];
	}
}


/**邮件发送的消息处理*/
- (void)mailComposeController:(MFMailComposeViewController*)controller
          didFinishWithResult:(MFMailComposeResult)result error:(NSError*)error{
    [controller  dismissViewControllerAnimated:YES completion:nil];
}



- (void) shareAppWithFriends{
    if ([[iTellAFriend sharedInstance] canTellAFriend]) {
            UINavigationController* tellAFriendController = [[iTellAFriend sharedInstance] tellAFriendController];
        [self presentViewController:tellAFriendController animated:YES completion:nil];
    }
}

/**我开发的App*/
-(void)showMyDevelopedApps{
    OurAppsViewController* developedApps = [[OurAppsViewController alloc] init];
    developedApps.appID = DEVELOPER_APP_INFO_ID;
    developedApps.delegate = self;
    [self.navigationController pushViewController:developedApps animated:YES];
}
// 应用项被点击
- (void)ourAppsVC:(OurAppsViewController *)ourAppsVC clickAppItem:(DataAppItem *)dataAppItem
{
    [[UIApplication sharedApplication] openURL:[NSURL URLWithString:dataAppItem.appURL]];
}


- (void) rateAppAppleInStore{
    [[iTellAFriend sharedInstance] rateThisApp];
}

- (void)doneButtonTapped:(id)sender {
    [self.navigationController dismissViewControllerAnimated:YES completion:nil];
    
}

/** 添加广告, 如果用户已经购买，不显示广告, 返回NO */
-(BOOL) addAdmob{

    if ([GUPurchaseManager isPurchased]) { //用户已经购买，则不在显示广告
         [self removeAdmobBannerAd];
         return NO;
    }
    /**
    //添加广告到底部
    adBannerView  = [GUAppDelegate addAdmobOnViewButtom: self  identifyKey:ABOUT_VIEW_ADMOB_MANAGER_IDENTIFY];
    [[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(removeAdmobBannerAd) name: ABOUT_VIEW_ADMOB_MANAGER_IDENTIFY
                                             object:nil];
     */  
    return  YES;
}

/** 移除广告 */
-(void) removeAdmobBannerAd{
    /**
    if (adBannerView != nil) {
         GULog(LOG_LEVEL_DEBUG, @"Before remove ad GUAbout adBanner View: %@", adBannerView);
        [adBannerView removeFromSuperview];
        adBannerView = nil;
        [self.view setNeedsDisplay];
        GULog(LOG_LEVEL_DEBUG, @"After remove ad GUAbout adBanner View: %@", adBannerView);
    }*/
   
}

/** 检查购买，若购买则移除广告 */
-(void) checkRefreshAdmobBannerAd{
    if ([GUPurchaseManager isPurchased]) {
        [self removeAdmobBannerAd];
    }else{
        if (![GUAppDelegate hasAmobBanner:ABOUT_VIEW_ADMOB_MANAGER_IDENTIFY]) {
            [self removeAdmobBannerAd];
        }
    }
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
