//
//  GuCollectViewController.m
//  huidu
//
//  Created by lurina on 14-4-16.
//  Copyright (c) 2014年 baobao. All rights reserved.
//

#import "GuCollectViewController.h"
#import "GUTitleBar.h"
#import "GUTipsView.h"
#import "GUConfig.h"
#import "GUSystemUtils.h"
#import "SSPullToRefresh.h"
#import "GUArticleCollectService.h"
#import "GUReaderViewController.h"
#import "GUArticleViewCell.h"

@interface GuCollectViewController ()<UITableViewDelegate, UITableViewDataSource, SSPullToRefreshViewDelegate>{
      @private UITableView* _dataTableView;
      @private SSPullToRefreshView* _pullToRefreshView;
      @private GUTipsView*  _tipsView;
}



@end

@implementation GuCollectViewController

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        // Custom initialization
    }
    return self;
}


-(void) loadView{
    self.navigationController.navigationBarHidden = NO;
    [super loadView];
    [self dataTableView];
    [self pullToRefreshView];
}



- (void)viewDidLoad
{
    [super viewDidLoad];
    self.navigationItem.title = @"我的收藏";
}

-(void) viewWillAppear:(BOOL)animated{
    [super viewWillAppear:animated];
    [self refreshDataOrShowEmptyTips];
    [self.navigationController setNavigationBarHidden:NO];
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
    _dataTableView = nil;
    _pullToRefreshView  = nil;
    _tipsView = nil;
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




-(UITableView*) dataTableView{
    if (_dataTableView == nil) {
        if ([GUSystemUtils isIos7]) {
            _dataTableView = [[UITableView alloc] initWithFrame:CGRectMake(0, 0, self.view.bounds.size.width,  self.view.bounds.size.height)];
        }else{
            _dataTableView = [[UITableView alloc] initWithFrame:CGRectMake(0, 0, self.view.bounds.size.width,  self.view.bounds.size.height - self.tabBarController.tabBar.frame.size.height)];
        }
        _dataTableView.separatorStyle =  UITableViewCellSeparatorStyleNone;
        _dataTableView.backgroundColor = self.view.backgroundColor;
        _dataTableView.tableHeaderView = [[UIView alloc] initWithFrame:CGRectMake(0, 0, self.view.frame.size.width, 3 )];
        _dataTableView.tableHeaderView.backgroundColor = [UIColor clearColor];
        _dataTableView.tableFooterView = [[UIView alloc] initWithFrame:CGRectMake(0, 0, self.view.frame.size.width, 3)];
        _dataTableView.tableFooterView.backgroundColor = [UIColor clearColor];
        _dataTableView.autoresizingMask = UIViewAutoresizingFlexibleHeight;
        _dataTableView.delegate = self;
        _dataTableView.dataSource = self ;
        
        [self.view addSubview:_dataTableView];
    }
    return _dataTableView;
}



-(SSPullToRefreshView*) pullToRefreshView{
    if (_pullToRefreshView == nil) {
        _pullToRefreshView = [[SSPullToRefreshView alloc] initWithScrollView:[self dataTableView] delegate:self];
    }
    return _pullToRefreshView;
}


-(GUTipsView*) tipsView{
    if (_tipsView == nil) {
        _tipsView = [[GUTipsView alloc] initWithFrame:CGRectMake(0, 44,
                                                                 self.view.frame.size.width,
                                                                 self.view.frame.size.height - 44 -  self.tabBarController.tabBar.frame.size.height)];
        _tipsView.tips.text = @"您没有收藏感兴趣的文章，赶紧收藏吧。";
        [_tipsView.actionButton setTitle:@"浏览文章" forState:UIControlStateNormal];
        [_tipsView.actionButton addTarget:self action:@selector(exploreArticle) forControlEvents:UIControlEventTouchUpInside];
    }
    return _tipsView;
}

-(void) exploreArticle{
    [self.tabBarController setSelectedIndex:0];
    [self.navigationController popToRootViewControllerAnimated:NO];
}


-(void) refreshDataOrShowEmptyTips{
    if ([GUArticleCollectService articleCount] == 0) {
        [[self dataTableView] setHidden:YES];
        [[self tipsView] showInView:self.view];
    }else{
        [[self tipsView] removeFromView];
        [[self dataTableView] setHidden:NO];
        [[self dataTableView] reloadData];
    }
}



///////////////////////////////////////////////////////////////////////////////////////////////
#pragma SSPullToRefreshViewDelegate
- (void)pullToRefreshViewDidStartLoading:(SSPullToRefreshView *)view{
    [self finishLoading];
    [[self dataTableView] reloadData];
}


-(void) finishLoading{
    [[self pullToRefreshView] finishLoading];
}

///////////////////////////////////////////////////////////////////////////////////////////////
#pragma UITableViewDelegate

- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath{
    [tableView deselectRowAtIndexPath:indexPath animated:YES];
    int row = [indexPath row];
    GUArticle* article = [GUArticleCollectService articleAtIndex:row];
    if (article != nil) {
        NSDictionary*  query = [[NSDictionary alloc] initWithObjectsAndKeys:article, @"article", nil];
        GUReaderViewController* readerDetail = [[GUReaderViewController alloc] initWithQuery:query];
        [self.navigationController pushViewController:readerDetail animated:YES];
    }
}

- (CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath{
    return 87;
}


///////////////////////////////////////////////////////////////////////////////////////////////
#pragma UITableViewDataSource


-(UITableViewCellEditingStyle)tableView:(UITableView *)tableView editingStyleForRowAtIndexPath:(NSIndexPath *)indexPath{
    UITableViewCellEditingStyle result = UITableViewCellEditingStyleNone;
    if ([GUSystemUtils isIos7]) {
        if ([tableView isEqual:[self dataTableView]]) {
            result = UITableViewCellEditingStyleDelete;
        }

    }
    return result;
}



-(void)tableView:(UITableView *)tableView commitEditingStyle:(UITableViewCellEditingStyle)editingStyle forRowAtIndexPath:(NSIndexPath *)indexPath{
    if (editingStyle ==UITableViewCellEditingStyleDelete) {
        GUArticle* article = [GUArticleCollectService articleAtIndex:[indexPath row]];
        [GUArticleCollectService removeCollectArticle:article position:[indexPath row]];
        [tableView deleteRowsAtIndexPaths:[NSArray arrayWithObject:indexPath] withRowAnimation:UITableViewRowAnimationFade];
        [self refreshDataOrShowEmptyTips];
    }
}



- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section{
    return [GUArticleCollectService articleCount];
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath{
    int row = [indexPath row];
    static NSString* cellIdentify = @"DataTableViewArticleCellIdentify";
    GUArticleViewCell* cell = [tableView dequeueReusableCellWithIdentifier:cellIdentify];
    if (cell == nil) {
         cell = [[GUArticleViewCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:cellIdentify];
     }
     GUArticle* article = [GUArticleCollectService articleAtIndex:row];
     cell.titleLabel.text = [article title];
     cell.authorLabel.text = [article author];
     cell.timeLabel.text =  [GUFormateUtils formateDate:[article publishDate]];
     cell.shortDescLabel.text = [article shortDesc];
     return cell;
}





@end
