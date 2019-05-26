//
//  GUViewController.m
//  judu
//
//  Created by lurina on 13-7-28.
//  Copyright (c) 2013年 baobao. All rights reserved.
//

#import "GUHomeViewController.h"
#import "GUArticleViewCell.h"
#import "GULoadMoreCell.h"
#import "SSPullToRefreshView.h"
#import "GUReaderViewController.h"
#import "MobClick.h"
#import "GUSystemUtils.h"
#import "GUArticleService.h"
#import "GUFormateUtils.h"
#import "GUConfig.h"
#import "GUAutoDownloadService.h"
#import "GUFavoriteViewController.h"


@interface GUHomeViewController ()<UITableViewDelegate, UITableViewDataSource, SSPullToRefreshViewDelegate>{
    GUTitleBar* _titleBar;
    UITableView*  _articleTableView;
    GULoadMoreCell* _loadMoreCell;
    SSPullToRefreshView* _pullToRefreshView;

    GUArticleService*  _articleService;
    int _currentPageNum;
    NSMutableArray* _articleList;
     GUAutoDownloadService* _autoDownloadService;
}

@end

@implementation GUHomeViewController


-(id) initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        // Custom initialization
        self.tabBarItem.title = NSLocalizedString(@"Home", @"首页");
        
        
        [self.tabBarItem setFinishedSelectedImage:
         [GUViewUtils tabbarSelectImage:@"\uf015"]
                      withFinishedUnselectedImage:[GUViewUtils tabbarNormalImage:@"\uf015"]];
        
        NSLog(@"self %@", self.navigationController );
    }
    return self;

}

-(void) loadView{
    self.navigationController.navigationBarHidden = NO;
    [super loadView];
    [self titleBar];
    [self loadArticleForPage:GU_PAGE_START_NUM];
    //[self articleTableView];
    
    NSLog(@"self %@  %@", self.navigationController, self.tabBarController );
    //[self pullToRefreshView];
}

- (void)viewDidLoad
{
    [super viewDidLoad];
	// Do any additional setup after loading the view, typically from a nib.
}

-(void) viewWillAppear:(BOOL)animated{
    [super viewWillAppear:animated];
     self.navigationController.navigationBarHidden = NO;
}
-(void) viewDidAppear:(BOOL)animated{
    [super viewDidAppear:animated];
    [MobClick beginEvent:@"JuduMainTab"];
}

-(void) viewDidDisappear:(BOOL)animated{
    [super viewDidDisappear:animated];
    [MobClick endEvent:@"JuduMainTab"];
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
    if (_articleService != nil) {
        [_articleService cancelRequestOperation];
        _articleService = nil;
    }
    
    if (_titleBar != nil) {
        _titleBar = nil;
    }
    
    if (_loadMoreCell != nil) {
        _loadMoreCell = nil;
    }
    if (_pullToRefreshView != nil) {
        _pullToRefreshView = nil;
    }
    
    if (_articleTableView != nil) {
        _articleTableView = nil;
    }
    if (_articleList != nil) {
        _articleList = nil;
    }
    [super clean];
}


/////////////////////////////////////////////////////////////////////////////////////////////////
#pragma init method

-(GUTitleBar*)titleBar{
    if (_titleBar == nil) {
        _titleBar = [[GUTitleBar alloc] initWithFrame:CGRectMake(0, 0, self.view.frame.size.width, 44)];
        _titleBar.titleLabel.text = NSLocalizedString(@"AppName", @"汇读");
        ;
        self.navigationItem.titleView = _titleBar;
        self.navigationItem.leftBarButtonItem = [[UIBarButtonItem alloc] initWithCustomView:[[UIView alloc] initWithFrame:CGRectMake(0, 0, 44, 44)]];
        self.navigationItem.rightBarButtonItem = [[UIBarButtonItem alloc] initWithCustomView:[_titleBar rightButtonWithImage:[GUViewUtils cell22Image:@"\uf006"]]];
        [_titleBar.rightButton addTarget:self action:@selector(goFavorites:) forControlEvents:UIControlEventTouchUpInside];
         
    }
    return _titleBar;
}

-(void) goFavorites:(UIButton*) button{
    GUFavoriteViewController* favoriteViewController = [[GUFavoriteViewController alloc] init];
    [self.navigationController pushViewController:favoriteViewController animated:YES];
}



-(UITableView*)articleTableView{
    if (_articleTableView == nil) {
        _articleTableView = [[UITableView alloc] initWithFrame:CGRectMake(0, 0, self.view.frame.size.width,
                                                                              self.view.frame.size.height                                                                    ) style:UITableViewStylePlain];
        _articleTableView.separatorStyle =  UITableViewCellSeparatorStyleNone;
        _articleTableView.backgroundColor = self.view.backgroundColor;
        _articleTableView.delegate = self;
        _articleTableView.dataSource = self ;
        _articleTableView.autoresizingMask = UIViewAutoresizingFlexibleHeight;
        _articleTableView.tableHeaderView = [[UIView alloc] initWithFrame:CGRectMake(0, 0, self.view.frame.size.width, 3 )];
        _articleTableView.tableHeaderView.backgroundColor = [UIColor clearColor];
        _articleTableView.tableFooterView = [[UIView alloc] initWithFrame:CGRectMake(0, 0, self.view.frame.size.width, 3)];
        _articleTableView.tableFooterView.backgroundColor = [UIColor clearColor];
 
        [self.view addSubview:_articleTableView];
    }
    return _articleTableView;
}


-(GULoadMoreCell*) loadMoreCell{
    if (_loadMoreCell == nil) {
        static NSString* loadMoreCellIdentify = @"LoadMoreCellIdentifyForCategory";
        _loadMoreCell = [[GULoadMoreCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:loadMoreCellIdentify];
        __weak GUHomeViewController* weakSelf = self;
        _loadMoreCell.loadMoreBlock = ^(void){
            if ([[weakSelf loadMoreCell] isLoadSuccess]) {
                [weakSelf loadArticleForPage:[weakSelf currentPageNum] + 1];
            }else{
                [weakSelf loadArticleForPage:[weakSelf currentPageNum]];
            }
        };
    }
    return _loadMoreCell;
}

-(SSPullToRefreshView*) pullToRefreshView{
    if (_pullToRefreshView == nil) {
        _pullToRefreshView = [[SSPullToRefreshView alloc] initWithScrollView:[self articleTableView] delegate:self];   
   }
    return _pullToRefreshView;
}

-(GUArticleService*)  articleService{
    if (_articleService == nil) {
        _articleService = [[GUArticleService alloc] init];
    }
    return _articleService;
}

-(GUAutoDownloadService*)autoDownloadService{
    if (_autoDownloadService == nil) {
        _autoDownloadService = [[GUAutoDownloadService alloc] init];
    }
    return _autoDownloadService;
}

-(NSMutableArray*) articleList{
    if (_articleList == nil) {
        _articleList = [[NSMutableArray alloc] init];
    }
    return _articleList;
}

-(int)currentPageNum{
    return _currentPageNum;
}

-(void) setCurrentPageNum:(int)currentPageNum{
    _currentPageNum = currentPageNum;
}


-(CGRect) activityFrame{
    return CGRectMake(0, 0,
                      self.view.frame.size.width,
                      self.view.frame.size.height -  self.tabBarController.tabBar.frame.size.height);
}

/////////////////////////////////////////////////////////////////////////////////////////////////
#pragma load data method
-(void) loadArticleForPage:(int) page{
    _currentPageNum = page;
    if (page <= GU_PAGE_START_NUM) {  //第一次加载，显示加载页面，并清空数据
        [[self statusView] showActivityInView:self.view forFrame:[self activityFrame]];
        [[self articleList] removeAllObjects];
    }
    __weak GUHomeViewController* weakSelf = self;
    [[self articleService] getArticleList:_currentPageNum withCallback:^(GUResult *result) {
        if ([result isSuccess] && [result result]) {
            [[weakSelf loadMoreCell] setLoadSuccess:YES];
            [[weakSelf loadMoreCell] setHasMore: [result hasMore]];
            [[weakSelf  statusView] removeViewInfo];
            [[weakSelf  articleList] addObjectsFromArray:[result result]];
            [[weakSelf  articleTableView] reloadData];
            [weakSelf   pullToRefreshView];
            
            if ([weakSelf currentPageNum] <= GU_PAGE_START_NUM) {
                [[weakSelf autoDownloadService] autoDownloadHomeArticles:[result result]];
            }
            
        }else{
            if ([weakSelf currentPageNum] <= GU_PAGE_START_NUM) {
                 [[weakSelf statusView] showErrorInView:[weakSelf view] forFrame:[weakSelf activityFrame]];
            }else{
                 [[weakSelf loadMoreCell] setLoadSuccess:NO];
            }
        }
        [[weakSelf loadMoreCell] loadMoreEnd];
    }];
}

-(void)reloadForError{
    [self loadArticleForPage:GU_PAGE_START_NUM];
}

-(void) reloadArticleIfUpdate{
    __weak GUHomeViewController* weakSelf = self;
    [[self articleService] getArticleList:GU_PAGE_START_NUM withCallback:^(GUResult *result) {
        if ([result isSuccess] && [result result]) {
            if ([[result result] count] > 0) {
                NSNumber* newFirst = [[[result result] objectAtIndex:0] id];
                NSNumber* oldFirst = nil;
                if ([[weakSelf articleList] count] > 0) {
                    oldFirst = [[[weakSelf articleList] objectAtIndex:0] id];
                }
                if (![newFirst isEqualToNumber:oldFirst]) {
                    [[weakSelf articleList] removeAllObjects];
                    [[weakSelf articleList] addObjectsFromArray:[result result]];
                    [[weakSelf loadMoreCell] setHasMore:YES];
                    [[weakSelf loadMoreCell] setLoadSuccess:YES];
                    [weakSelf setCurrentPageNum:GU_PAGE_START_NUM];
                    [[weakSelf articleTableView] reloadData];
                }
            }
        }
        [weakSelf finishLoading];
    }];
}



///////////////////////////////////////////////////////////////////////////////////////////////
#pragma SSPullToRefreshViewDelegate

- (void)pullToRefreshViewDidStartLoading:(SSPullToRefreshView *)view{
    [self reloadArticleIfUpdate];
}


-(void) finishLoading{
    [[self articleTableView] reloadData];
    [[self pullToRefreshView] finishLoading];
}

///////////////////////////////////////////////////////////////////////////////////////////////
#pragma UITableViewDelegate
- (CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath{
    int row = [indexPath row];
    if (row == [[self articleList]  count]) {
        return 54;
    }
    return 87;
}

- (void)tableView:(UITableView *)tableView willDisplayCell:(UITableViewCell *)cell forRowAtIndexPath:(NSIndexPath *)indexPath{
    int row = [indexPath row];
    if (row == [[self articleList] count]) {
        [[self loadMoreCell] startLoadMore];
    }
}

- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath{
    [tableView deselectRowAtIndexPath:indexPath animated:YES];
    int row = [indexPath row];
    if (row >= [_articleList count]) {
        return;
    }
    GUArticle* article = [_articleList objectAtIndex:row];
    if (article != nil) {
        NSDictionary* query = [[NSDictionary alloc] initWithObjectsAndKeys:article, @"article", nil];
        GUReaderViewController* readerDetail = [[GUReaderViewController alloc] initWithQuery:query];
        
        [self.navigationController pushViewController:readerDetail animated:YES];
    }
}

///////////////////////////////////////////////////////////////////////////////////////////////
#pragma UITableViewDataSource

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section{
    if ([[self loadMoreCell] showLoadingCell]) {
        return [[self articleList] count] + 1;
    }
    return [[self articleList] count];
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath{
    int row = [indexPath row];
    static NSString* cellIdentify = @"ArticleTableViewCellIdentify";
 
    if (row == [[self articleList] count]) {
        return [self loadMoreCell];
    }
    
    GUArticleViewCell* cell = [tableView dequeueReusableCellWithIdentifier:cellIdentify];
    if (cell == nil) {
        cell = [[GUArticleViewCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:cellIdentify];
    }
    
    GUArticle* article = [[self articleList] objectAtIndex:row];
    cell.titleLabel.text = [article title];
    cell.authorLabel.text = [article author];
    cell.timeLabel.text =  [GUFormateUtils formateDate:[article publishDate]];
    cell.shortDescLabel.text = [article shortDesc];
    
    return cell;
}









@end
