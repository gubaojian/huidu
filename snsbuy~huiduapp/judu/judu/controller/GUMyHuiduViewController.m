//
//  GUMyHuiduControllerViewController.m
//  huidu
//
//  Created by lurina on 14-4-7.
//  Copyright (c) 2014年 baobao. All rights reserved.
//

#import "GUMyHuiduViewController.h"
#import "GUSettingsCell.h"
#import "GUAbout.h"
#import "GUFavoriteViewController.h"
#import "GuCollectViewController.h"
#import "MobClick.h"

@interface GUMyHuiduViewController()<UITableViewDelegate, UITableViewDataSource, MFMailComposeViewControllerDelegate>{
      @private UITableView* _tableView;
      @private NSDictionary* _cellDatas;
      @private GUTitleBar* _titleBar;
}

@end

@implementation GUMyHuiduViewController

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        // Custom initialization
        self.tabBarItem.title = NSLocalizedString(@"MyHuidu", @"我的汇读");
        [self.tabBarItem setFinishedSelectedImage:[GUViewUtils tabbarSelectImage:@"\uf007"] withFinishedUnselectedImage:[GUViewUtils tabbarNormalImage:@"\uf007"]];
    }
    return self;
}

-(void) loadView{
    self.navigationController.navigationBarHidden = NO;
    [super loadView];
    [self titleBar];
    [self tableView];

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
    _titleBar = nil;
    _tableView  = nil;
    _cellDatas = nil;
    [super clean];
}

/*
#pragma mark - Navigation

// In a storyboard-based application, you will often want to do a little preparation before navigation
- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender
{
    // Get the new view controller using [segue destinationViewController].
    // Pass the selected object to the new view controller.
}
*/


-(GUTitleBar*)titleBar{
    if (_titleBar == nil) {
        _titleBar = [[GUTitleBar alloc] initWithFrame:CGRectMake(0, 0, self.view.frame.size.width, 44)];
        _titleBar.titleLabel.text = NSLocalizedString(@"AppName", @"汇读");
        self.navigationItem.titleView = _titleBar;
    }
    return _titleBar;
}

-(UITableView*)tableView{
    if (_tableView == nil) {
        _tableView = [[UITableView alloc] initWithFrame:CGRectMake(0, 0, self.view.frame.size.width,  self.view.frame.size.height) style:UITableViewStylePlain];
        _tableView.autoresizingMask =  UIViewAutoresizingFlexibleHeight;
        _tableView.backgroundColor = self.view.backgroundColor;
        _tableView.tableHeaderView = [[UIView alloc] initWithFrame:CGRectZero];
        _tableView.tableFooterView = [[UIView alloc] initWithFrame:CGRectZero];
        _tableView.separatorColor = self.view.backgroundColor;
        _tableView.delegate = self;
        _tableView.dataSource = self;
        
        [self.view addSubview:_tableView];
    }
    return _tableView;
}

-(NSDictionary*) cellDatas{
    if (_cellDatas == nil) {
        NSMutableDictionary* datas = [[NSMutableDictionary alloc] initWithCapacity:4];
        
        NSArray* sectionOne = @[@[@"我的关注",@"\uf005"], @[@"我的收藏", @"\uf004"]];
        [datas setObject:sectionOne forKey:@"0"];
        
        
        NSArray* sectionTwo = @[@[@"添加我喜欢的博客",@"\uf143"]];
        [datas setObject:sectionTwo forKey:@"1"];
        
        
        NSArray* sectionThree = @[@[@"设置",@"\uf013"]];
        [datas setObject:sectionThree  forKey:@"2"];
        
        
        _cellDatas = datas;
    }
    return _cellDatas;
}

///////////////////////////////////////////////////////////////////////////////////////////////
#pragma UITableViewDataSource
- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section{
    NSArray*  datas = [[self cellDatas] objectForKey:[NSString stringWithFormat:@"%d", section]];
    return [datas count];

}


- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView{
    return [[self cellDatas] count];
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath{
    static NSString* cellIdentify = @"MyHuiduTableViewCellIdentify";
    GUSettingsCell* cell = [tableView dequeueReusableCellWithIdentifier:cellIdentify];
    if (cell == nil) {
        cell = [[GUSettingsCell alloc] initWithStyle: UITableViewCellStyleDefault reuseIdentifier:cellIdentify];
    }
    NSArray*  datas = [[self cellDatas] objectForKey:[NSString stringWithFormat:@"%d", [indexPath section]]];
    NSArray*   data = [datas objectAtIndex:[indexPath row]];
    NSString*  img = [data objectAtIndex:1];
    
    cell.leftTitleView.text = [data objectAtIndex:0];
    if (img) {
        cell.leftImageView.image = [GUViewUtils cell22Image:img];
    }else{
        cell.leftImageView.image = nil;
    }
    
    return cell;
}



///////////////////////////////////////////////////////////////////////////////////////////////
#pragma UITableViewDelegate
- (CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath{
    return 44;
}
- (CGFloat)tableView:(UITableView *)tableView heightForHeaderInSection:(NSInteger)section{
    return 12;
}

- (UIView *)tableView:(UITableView *)tableView viewForHeaderInSection:(NSInteger)section{
    UIView* view = nil;
    if ([GUSystemUtils isIos6]) {
        static NSString* headerIdentify = @"settingsHeaderIdentify";
        view = [tableView dequeueReusableHeaderFooterViewWithIdentifier:headerIdentify];
    }
    if (view  == nil) {
        view = [[UIView alloc] initWithFrame:CGRectMake(0, 0, self.view.frame.size.width, 32)];
    }
    view.backgroundColor = self.view.backgroundColor;
    return view;
}


- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath{
       [tableView deselectRowAtIndexPath:indexPath animated:YES];
       NSInteger section = [indexPath section];
       NSInteger row = [indexPath row];
     if (section == 0) {
         if (row == 0) {
             GUFavoriteViewController* favoriteViewController = [[GUFavoriteViewController alloc] init];
             [self.navigationController pushViewController:favoriteViewController animated:YES];
         }else if (row == 1){
             GuCollectViewController* collectViewController = [[GuCollectViewController alloc] init];
             [self.navigationController pushViewController:collectViewController animated:YES];
         }
         return;
     }
    
    if (section == 1) {
        if (row == 0) {
            [self sendFavoriteBlog];
        }
        return;
    }
    
     if (section == 2) {
         if (row == 0) {
             GUAbout* about = [[GUAbout alloc] initWithStyle:UITableViewStyleGrouped];
             [self.navigationController pushViewController:about animated:YES];
         }
        return;
     }
}



-(void) sendFavoriteBlog{
    [MobClick event:@"JuduMyFavoriteSendFavoriteBlog"];
    Class mailClass = (NSClassFromString(@"MFMailComposeViewController"));
    if (mailClass != nil) {
        if ([mailClass canSendMail]) {
            MFMailComposeViewController*  mailComposeViewPicker = [[MFMailComposeViewController alloc] init];
            mailComposeViewPicker.mailComposeDelegate = self;
            [mailComposeViewPicker setSubject: @"添加我喜欢的博客"];
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


/**邮件发送的消息处理*/
- (void)mailComposeController:(MFMailComposeViewController*)controller
          didFinishWithResult:(MFMailComposeResult)result error:(NSError*)error{
    [controller  dismissViewControllerAnimated:YES completion:nil];
}




@end
