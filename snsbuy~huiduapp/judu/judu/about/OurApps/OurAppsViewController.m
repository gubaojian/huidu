//
//  OurAppsViewController.m
//  
//
//  Created by 建红 杨 on 12-2-24.
//  Copyright (c) 2012年 __MyCompanyName__. All rights reserved.
//

#import "OurAppsViewController.h"
#import "TableViewAppItemCell.h"
#import "UIView+ActivityIndicator.h"


@interface OurAppsViewController (Private)
- (void)getAppList;
@end


@implementation OurAppsViewController

@synthesize appID = _appID;
@synthesize delegate = _delegate;

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        // Custom initialization
        //
        _biPhoneAppList = YES;
        _bLoadiPadAppList = NO;
        _bSendiPhoneAppList = NO;
        _bReceiveiPhoneAppList = NO;
        _bSendiPadAppList = NO;
        _bReceiveiPadAppList = NO;
        _marrayiPhoneAppItem = [[NSMutableArray alloc] initWithCapacity:5];
        _marrayiPadAppItem = [[NSMutableArray alloc] initWithCapacity:5];
        //产品管家
        _productMan = [[ProductMan alloc] init];
        _productMan.delegate = self;
    }
    return self;
}

- (void)dealloc
{
    //
    [_tableviewAppList release];
    //
    [_marrayiPhoneAppItem release];
    [_marrayiPadAppItem release];
    //
    _productMan.delegate = nil;
    [_productMan release];
    
    [super dealloc];
}

- (void)didReceiveMemoryWarning
{
    // Releases the view if it doesn't have a superview.
    [super didReceiveMemoryWarning];
    
    // Release any cached data, images, etc that aren't in use.
}

#pragma mark - View lifecycle

/*
// Implement loadView to create a view hierarchy programmatically, without using a nib.
- (void)loadView
{
}
*/


// Implement viewDidLoad to do additional setup after loading the view, typically from a nib.
- (void)viewDidLoad
{
    [super viewDidLoad];
    //
    self.navigationItem.title = NSLocalizedString(@"CommonProductsTitle", @"Products List");
    //导航栏左边的按钮用于返回
    if ([self.navigationController.viewControllers objectAtIndex:0] == self) {
        UIBarButtonItem *itemBack = [[UIBarButtonItem alloc] initWithTitle:
                                     NSLocalizedString(@"CommonButtonTextBack", @"Back")
                                                                     style:UIBarButtonItemStyleBordered 
                                                                    target:self 
                                                                    action:@selector(clickBack:)];
        self.navigationItem.leftBarButtonItem = itemBack;
        [itemBack release];
    }
    //导航栏右边的按钮用于刷新
    UIBarButtonItem *itemUpdate = [[UIBarButtonItem alloc] 
                                   initWithBarButtonSystemItem:UIBarButtonSystemItemRefresh 
                                   target:self 
                                   action:@selector(clickUpdate:)];
    self.navigationItem.rightBarButtonItem = itemUpdate;
    [itemUpdate release];
    //显示产品项用的表格
    if (nil == _tableviewAppList) {
        _tableviewAppList = [[UITableView alloc] initWithFrame:self.view.bounds];
        _tableviewAppList.dataSource = self;
        _tableviewAppList.delegate = self;
    }
    [self.view addSubview:_tableviewAppList];
    
    //
    [self getAppList];
    //如果是iPad，也要获取iPad版本
    NSString *strDeviceModel = [UIDevice currentDevice].model;
    NSRange range = [strDeviceModel rangeOfString:@"iPad"];
    if (range.location != NSNotFound) {
        //iPhone、iPad列表切换按钮放在导航栏上
        NSArray *arrayItem = [[NSArray alloc] initWithObjects:@"iPhone", @"iPad", nil];
        UISegmentedControl *segmt = [[UISegmentedControl alloc] initWithItems:arrayItem];
        segmt.frame = CGRectMake(0, 12, 165, 28);
        segmt.segmentedControlStyle = UISegmentedControlStyleBar;
        [segmt addTarget:self action:@selector(clickSwitch:) forControlEvents:UIControlEventValueChanged];
        segmt.selectedSegmentIndex = 0;
        self.navigationItem.titleView = segmt;
        [segmt release];
        [arrayItem release];
    }
    
    UISwipeGestureRecognizer* swipeGesture = [[[UISwipeGestureRecognizer alloc] initWithTarget:self action:@selector(swipeAction:)] autorelease];
    [self.view addGestureRecognizer:swipeGesture];

}


- (void)viewDidUnload
{
    [super viewDidUnload];
    // Release any retained subviews of the main view.
    // e.g. self.myOutlet = nil;
}

- (void)viewWillAppear:(BOOL)animated
{
    [super viewWillAppear:animated];
    //
    _tableviewAppList.frame = self.view.bounds;
}

- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation
{
    // Return YES for supported orientations
    return (interfaceOrientation == UIInterfaceOrientationPortrait);
}


#pragma mark -
#pragma mark ClickEvent

- (void)clickBack:(id)sender
{
    [self.delegate ourAppsVCBack:self];
}

- (void)clickUpdate:(id)sender
{
    [self getAppList];
}

- (void)clickSwitch:(id)sender
{
	if ([sender isMemberOfClass:UISegmentedControl.class]) {
		UISegmentedControl *segmt = (UISegmentedControl*)sender;
		int indexSel = segmt.selectedSegmentIndex;
		//
		switch (indexSel) {
            case 0:
            {
                _biPhoneAppList = YES;
                //发送了iPhone应用列表请求，并且没有收到数据，则显示加载框
                if (_bSendiPhoneAppList && NO == _bReceiveiPhoneAppList) {
                    [self.view showActivityIndicatorWithText:NSLocalizedString(@"Loading...", nil)];
                }
                else {
                    [self.view hideActivityIndicator];
                }
            }
                break;
            case 1:
            {
                _biPhoneAppList = NO;
                //没有加载过iPad应用列表则加载
                //只有没加载过，才能通过切换的方式获取iPad应用列表
                //否则只能通过刷新的方式
                if (NO == _bLoadiPadAppList) {
                    [self getAppList];
                    _bLoadiPadAppList = YES;
                }
                //发送了iPad应用列表请求，并且没有收到数据，则显示加载框
                if (_bSendiPadAppList && NO == _bReceiveiPadAppList) {
                    [self.view showActivityIndicatorWithText:NSLocalizedString(@"Loading...", nil)];
                }
                else {
                    [self.view hideActivityIndicator];
                }
            }
                break;
            default:
                break;
        }
        //
        [_tableviewAppList reloadData];
    }
}


#pragma mark -
#pragma mark UITableViewDataSource

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    if (_biPhoneAppList) {
        return _marrayiPhoneAppItem.count;
    }
    else {
        return _marrayiPadAppItem.count;
    }
}

// Row display. Implementers should *always* try to reuse cells by setting each cell's reuseIdentifier and querying for available reusable cells with dequeueReusableCellWithIdentifier:
// Cell gets various attributes set automatically based on table (separators) and data source (accessory views, editing controls)

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    DataAppItem *dataAppItem = nil;
    if (_biPhoneAppList) {
        dataAppItem = [_marrayiPhoneAppItem objectAtIndex:indexPath.row];
    }
    else {
        dataAppItem = [_marrayiPadAppItem objectAtIndex:indexPath.row];
    }
    
    //
    NSString *strAppItemCell = @"AppItemCell";
    TableViewAppItemCell *cellAppItem = [tableView dequeueReusableCellWithIdentifier:strAppItemCell];
    if (nil == cellAppItem) {
        cellAppItem = [[[TableViewAppItemCell alloc] initWithStyle:UITableViewCellStyleDefault 
                                                   reuseIdentifier:strAppItemCell] autorelease];
    }
    //
    if (nil == dataAppItem.appIcon) {
        //下载应用图标
        [_productMan downloadAppIcon:dataAppItem.appID with:dataAppItem.appIconURL];
    }
    //
    cellAppItem.dataAppItem = dataAppItem;
    
    return cellAppItem;
}


#pragma mark -
#pragma mark UITableViewDelegate

- (CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath
{
    return HEIGHT_APPITEMCELL;
}

- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath
{
    if ([self.delegate respondsToSelector:@selector(ourAppsVC:clickAppItem:)]) {
        //
        NSArray *array = _biPhoneAppList ? _marrayiPhoneAppItem : _marrayiPadAppItem;
        DataAppItem *dataAppItem = [array objectAtIndex:indexPath.row];
        //点击到某应用
        [self.delegate ourAppsVC:self clickAppItem:dataAppItem];
    }
    //
    UITableViewCell *cellSelected = [tableView cellForRowAtIndexPath:indexPath];
    cellSelected.selected = NO;
}


#pragma mark - ProductManDelegate

// 我们的iPhone应用列表获取成功
- (void)productMan:(ProductMan *)productMan iPhoneAppListSuccess:(NSArray *)arrayAppList
{
    _bReceiveiPhoneAppList = YES;
    _bSendiPhoneAppList = NO;
    //
    [_marrayiPhoneAppItem addObjectsFromArray:arrayAppList];
    //iPhone应用列表状态则刷新表格
    if (YES == _biPhoneAppList) {
        [_tableviewAppList reloadData];
    }
    
    [self.view hideActivityIndicator];
}

// 我们的iPhone应用列表获取失败
- (void)productManiPhoneAppListFailure:(ProductMan *)productMan
{
    _bReceiveiPhoneAppList = YES;
    _bSendiPhoneAppList = NO;
    
    [self.view hideActivityIndicator];
}

// 我们的iPad应用列表获取成功
- (void)productMan:(ProductMan *)productMan iPadAppListSuccess:(NSArray *)arrayAppList
{
    _bReceiveiPadAppList = YES;
    _bSendiPadAppList = NO;
    //
    [_marrayiPadAppItem addObjectsFromArray:arrayAppList];
    //iPad应用列表状态则刷新表格
    if (NO == _biPhoneAppList) {
        [_tableviewAppList reloadData];
    }
    
    [self.view hideActivityIndicator];
}

// 我们的iPad应用列表获取失败
- (void)productManiPadAppListFailure:(ProductMan *)productMan
{
    _bReceiveiPadAppList = YES;
    _bSendiPadAppList = NO;
    
    [self.view hideActivityIndicator];
}

// 应用图标下载成功
- (void)productMan:(ProductMan *)productMan downloadAppIconSuccess:(UIImage *)imageAppIcon of:(long long int)appid
{
    //找到相应的应用数据
    NSArray *array = _biPhoneAppList ? _marrayiPhoneAppItem : _marrayiPadAppItem;
    for (DataAppItem *dataAppItem in array) {
        if (appid == dataAppItem.appID) {
            //设置应用图标
            dataAppItem.appIcon = imageAppIcon;
            //刷新表格
            [_tableviewAppList reloadData];
            break;
        }
    }
}

// 应用图标下载失败
- (void)productMan:(ProductMan *)productMan downloadAppIconFailureOf:(long long int)appid
{
    
}


#pragma mark -
#pragma mark Private

- (void)getAppList
{
    //iPhone应用列表状态
    if (_biPhoneAppList) {
        if (NO == _bSendiPhoneAppList) {
            //获取iPhone应用列表
            [_productMan getOuriPhoneAppList:self.appID];
            //
            _bSendiPhoneAppList = YES;
            _bReceiveiPhoneAppList = NO;
            //清空原有数据
            [_marrayiPhoneAppItem removeAllObjects];
            [_tableviewAppList reloadData];
        }
    }
    //iPad应用列表状态
    else {
        if (NO == _bSendiPadAppList) {
            //获取iPad应用列表
            [_productMan getOuriPadAppList:self.appID];
            //
            _bSendiPadAppList = YES;
            _bReceiveiPadAppList = NO;
            //清空原有数据
            [_marrayiPadAppItem removeAllObjects];
            [_tableviewAppList reloadData];
        }
    }
    
    [self.view showActivityIndicatorWithText:NSLocalizedString(@"Loading...", nil)];
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
