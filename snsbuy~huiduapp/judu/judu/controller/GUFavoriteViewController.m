//
//  GUFavoriteViewController.m
//  judu
//
//  Created by lurina on 13-7-29.
//  Copyright (c) 2013年 baobao. All rights reserved.
//

#import "GUFavoriteViewController.h"
#import "GUStatusView.h"
#import "GUConfigService.h"
#import "GULoadMoreCell.h"
#import "SSPullToRefresh.h"
#import "GUArticleViewCell.h"
#import "GUCategoryViewCell.h"
#import "MobClick.h"
#import "GUReaderViewController.h"
#import "GUCategoryArticleViewController.h"
#import "GUFavoriteService.h"
#import "GUArticleService.h"
#import "GUConfig.h"
#import "GUTipsView.h"

@interface GUFavoriteViewController ()<UITableViewDelegate, UITableViewDataSource, SSPullToRefreshViewDelegate>{
   @private GUTitleBar* _titleBar;
   @private UITableView* _dataTableView;
   @private GULoadMoreCell* _articleListLoadMoreCell;
   @private SSPullToRefreshView* _pullToRefreshView;

    @private NSMutableArray* _articleList;
    @private int _articleListPageNum;
    @private GUArticleService* _articleService;
    @private GUTipsView*  _tipsView;

}

@end

@implementation GUFavoriteViewController




-(void) loadView{
    self.navigationController.navigationBarHidden = NO;
    [super loadView];
    [self titleBar];
    if ([GUConfigService isShowArticleList]) {
        [MobClick event:@"JuduMyFavoriteIsArticleList"];
        [self loadFavoriteArticleListForPage:GU_PAGE_START_NUM];
    }else{
        [MobClick event:@"JuduMyFavoriteIsCategoryList"];
        [self dataTableView];
        [self pullToRefreshView];
    }
}


-(void) viewWillAppear:(BOOL)animated{
    [super viewWillAppear:animated];
    [self refreshDataOrShowEmptyTips];
    [self.navigationController setNavigationBarHidden:NO];
}

-(void) viewDidAppear:(BOOL)animated{
    [super viewDidAppear:animated];
    [MobClick beginEvent:@"JuduMyFavorite"];
}

-(void) viewDidDisappear:(BOOL)animated{
    [super viewDidDisappear:animated];
    [MobClick endEvent:@"JuduMyFavorite"];
   
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
 

-(void) clean{
    if (_articleService != nil) {
        [_articleService cancelRequestOperation];
        _articleService = nil;
    }
    if (_articleList != nil) {
        _articleList = nil;
    }
    
    if (_titleBar != nil) {
        _titleBar = nil;
    }
    
    if (_dataTableView != nil) {
        _dataTableView = nil;
    }
    
    if (_articleListLoadMoreCell != nil) {
        _articleListLoadMoreCell = nil;
    }
    
    if (_pullToRefreshView != nil) {
        _pullToRefreshView = nil;
    }
    
    if (_tipsView) {
        _tipsView = nil;
    }
    [super clean];
}



/////////////////////////////////////////////////////////////////////////////////////////////////
#pragma init method

-(GUTitleBar*)titleBar{
    if (_titleBar == nil) {
        _titleBar = [[GUTitleBar alloc] initWithFrame:CGRectMake(0, 0, self.view.frame.size.width, 44)];
        _titleBar.titleLabel.text = NSLocalizedString(@"MyFavoriteTitle", @"我的关注");
        [_titleBar.rightButton addTarget:self action:@selector(switchButton:) forControlEvents:UIControlEventTouchUpInside];
        self.navigationItem.titleView = _titleBar;
        self.navigationItem.rightBarButtonItem = [[UIBarButtonItem alloc] initWithCustomView:[_titleBar rightButton]];
        [self configTitleBarRightButton];
        
    }
    return _titleBar;
}

-(void) configTitleBarRightButton{
    [self dataTableView];
    if ([GUConfigService isShowArticleList]) {
        [_titleBar.rightButton setImage:[GUViewUtils cell22Image:@"\uf03a"] forState:UIControlStateNormal];
        _dataTableView.tableHeaderView = [[UIView alloc] initWithFrame:CGRectMake(0, 0, self.view.frame.size.width, 3 )];
        _dataTableView.tableHeaderView.backgroundColor = [UIColor clearColor];
        _dataTableView.tableFooterView = [[UIView alloc] initWithFrame:CGRectMake(0, 0, self.view.frame.size.width, 3)];
       _dataTableView.tableFooterView.backgroundColor = [UIColor clearColor];
    }else{
        [_titleBar.rightButton setImage:[GUViewUtils cell22Image:@"\uf022"] forState:UIControlStateNormal];
        _dataTableView.tableHeaderView = [[UIView alloc] initWithFrame:CGRectMake(0, 0, self.view.frame.size.width, 0.5)];
        _dataTableView.tableHeaderView.backgroundColor = [UIColor clearColor];
        _dataTableView.tableFooterView = [[UIView alloc] initWithFrame:CGRectMake(0, 0, self.view.frame.size.width, 0.5)];
        _dataTableView.tableFooterView.backgroundColor = [UIColor clearColor];
    }
}

-(UITableView*) dataTableView{
    if (_dataTableView == nil) {
        if ([GUSystemUtils isIos7]) {
            _dataTableView = [[UITableView alloc] initWithFrame:CGRectMake(0, 0, self.view.bounds.size.width,  self.view.bounds.size.height)];
       }else{
            _dataTableView = [[UITableView alloc] initWithFrame:CGRectMake(0, 0, self.view.bounds.size.width,  self.view.bounds.size.height - self.tabBarController.tabBar.frame.size.height)];
        }
        _dataTableView.separatorStyle =  UITableViewCellSeparatorStyleNone;
        _dataTableView.backgroundColor = self.view.backgroundColor;
        _dataTableView.tableHeaderView = [[UIView alloc] initWithFrame:CGRectZero];
        _dataTableView.tableFooterView = [[UIView alloc] initWithFrame:CGRectZero];
        _dataTableView.autoresizingMask = UIViewAutoresizingFlexibleHeight;
        _dataTableView.delegate = self;
        _dataTableView.dataSource = self ;
        [self.view addSubview:_dataTableView];
    }
    return _dataTableView;
}


-(GULoadMoreCell*) articleListLoadMoreCell{
    if (_articleListLoadMoreCell == nil) {
        static NSString* loadMoreCellIdentify = @"LoadMoreCellIdentify";
        _articleListLoadMoreCell = [[GULoadMoreCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:loadMoreCellIdentify];
        __weak GUFavoriteViewController* weakSelf = self;
        _articleListLoadMoreCell.loadMoreBlock = ^(void){
            if ([GUConfigService isShowArticleList]) {
                if ([[weakSelf articleListLoadMoreCell] isLoadSuccess]) {
                       [weakSelf loadFavoriteArticleListForPage:[weakSelf articleListPageNum] + 1];
                }else{
                  [weakSelf loadFavoriteArticleListForPage:[weakSelf articleListPageNum]];
                }
            }
        };
    }
    return _articleListLoadMoreCell;
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
        _tipsView.tips.text = @"您还未关注感兴趣的人和技术，赶紧关注吧。";
        [_tipsView.actionButton setTitle:@"发现兴趣" forState:UIControlStateNormal];
        [_tipsView.actionButton addTarget:self action:@selector(showSearchCategoryController) forControlEvents:UIControlEventTouchUpInside];
    }
    return _tipsView;
}


-(NSMutableArray*)articleList{
    if (_articleList == nil) {
        _articleList = [[NSMutableArray alloc] initWithCapacity:8];
    }
    return _articleList;
}


-(int) articleListPageNum{
    return _articleListPageNum;
}

-(void) setArticleListPageNum:(int)page{
    _articleListPageNum = page;
}

-(GUArticleService*)articleService{
    if (_articleService == nil) {
        _articleService = [[GUArticleService alloc] init];
    }
    return _articleService;
}

-(CGRect) activityFrame{
    return CGRectMake(0, 44,
                      self.view.frame.size.width,
                      self.view.frame.size.height - 44 -  self.tabBarController.tabBar.frame.size.height);
}

/////////////////////////////////////////////////////////////////////////////////////////////////
#pragma data load action
-(void) loadFavoriteArticleListForPage:(int)page{
    if ([[GUFavoriteService favoriteCategoryIds] count] == 0) {
        [[self articleList] removeAllObjects];
        [[self dataTableView] reloadData];
        return;
    }
    
    if (page <= GU_PAGE_START_NUM) {
        [[self articleList] removeAllObjects];
        [[self dataTableView] reloadData];
        [[self statusView] showActivityInView:self.view forFrame:[self activityFrame]];
    }
   
    
    
    [self setArticleListPageNum:page];
    __weak GUFavoriteViewController* weakSelf = self;
    [[self articleService] getFavoriteArticleList:[GUFavoriteService favoriteCategoryIds] forPage:page withCallback:^(GUResult *result) {
        if ([result isSuccess] && [result result]) {
            [[weakSelf  statusView] removeViewInfo];
            [[weakSelf articleListLoadMoreCell] setLoadSuccess:YES];
            [[weakSelf articleListLoadMoreCell] setHasMore:[result hasMore]];
            [[weakSelf articleList] addObjectsFromArray:[result result]];
            [[weakSelf dataTableView] reloadData];
            [weakSelf pullToRefreshView];
        
        }else{
            if ([weakSelf articleListPageNum] <= GU_PAGE_START_NUM) {
                [[weakSelf statusView] showErrorInView:[weakSelf view] forFrame:[weakSelf activityFrame]];
            }else{
                [[weakSelf articleListLoadMoreCell] setLoadSuccess:NO];
                [[weakSelf dataTableView] reloadData];
            }
        }
        [[weakSelf articleListLoadMoreCell] loadMoreEnd];
    }];
}

-(void) checkFavoriteArticleListIfUpdate{
    __weak GUFavoriteViewController* weakSelf = self;
    [[self articleService] getFavoriteArticleList:[GUFavoriteService favoriteCategoryIds] forPage:[self articleListPageNum] withCallback:^(GUResult *result) {
        if (weakSelf) {
            if ([result isSuccess]) {
                if ([result result] && [[result result] count] > 0) {
                    NSNumber* newFirst = [[[result result] objectAtIndex:0] id];
                    NSNumber* oldFirst = nil;
                    if ([[weakSelf articleList] count] > 0) {
                        oldFirst = [[[weakSelf articleList] objectAtIndex:0] id];
                    }
                    if (![newFirst isEqualToNumber:oldFirst]) {
                        [[weakSelf articleList] removeAllObjects];
                        [[weakSelf articleList] addObjectsFromArray:[result result]];
                        [[weakSelf articleListLoadMoreCell] setLoadSuccess:YES];
                        [[weakSelf articleListLoadMoreCell] setHasMore:YES];
                        [weakSelf setArticleListPageNum:GU_PAGE_START_NUM];
                        [[weakSelf dataTableView] reloadData];
                    }
                }else{
                    [[weakSelf articleListLoadMoreCell] setLoadSuccess:YES];
                    [[weakSelf articleListLoadMoreCell] setHasMore:NO];
                    [[weakSelf articleList] removeAllObjects];
                    [[weakSelf dataTableView] reloadData];
                }
            }
            [weakSelf finishLoading];
        }
    }];
}

-(void) reloadForError{
    [self loadFavoriteArticleListForPage:GU_PAGE_START_NUM];
}

/////////////////////////////////////////////////////////////////////////////////////////////////
#pragma view category action
-(void) showSearchCategoryController{
    [self.tabBarController setSelectedIndex:1];  //search view controller;
    [self.navigationController popToRootViewControllerAnimated:NO];
}

-(void) refreshDataOrShowEmptyTips{
    if ([[GUFavoriteService favoriteCategoryIds] count] == 0) {
        [[self titleBar].rightButton setHidden:YES];
        [[self dataTableView] setHidden:YES];
        [[self statusView] setHidden:YES];
        [[self tipsView] showInView:self.view];
    }else{
        [[self tipsView] removeFromView];
        [[self titleBar].rightButton setHidden:NO];
        [[self dataTableView] setHidden:NO];
        [[self statusView] setHidden:NO];
        if ([GUConfigService isShowArticleList]) {
            if (_articleList == nil || [_articleList count] == 0) {
                [self loadFavoriteArticleListForPage:GU_PAGE_START_NUM];
            }
        }else{
            [[self statusView] removeViewInfo];
            [[self dataTableView] reloadData];
        }
    }
}


/////////////////////////////////////////////////////////////////////////////////////////////////
#pragma uibutton action
-(void) switchButton:(UIButton*) button{
    if ([GUConfigService isShowArticleList]) {
        [GUConfigService setShowArticleList:NO];
    }else{
        [GUConfigService setShowArticleList:YES];
    }
    
    [self configTitleBarRightButton];
    if ([GUConfigService isShowArticleList]) {
        [self loadFavoriteArticleListForPage:GU_PAGE_START_NUM];
    }else{
       [[self statusView] removeViewInfo];
       [[self dataTableView] reloadData];
    }
}



///////////////////////////////////////////////////////////////////////////////////////////////
#pragma SSPullToRefreshViewDelegate
- (void)pullToRefreshViewDidStartLoading:(SSPullToRefreshView *)view{
    if ([GUConfigService isShowArticleList]) {
        [self checkFavoriteArticleListIfUpdate];
    }else{
        [self finishLoading];
        [[self dataTableView] reloadData];
    }
}


-(void) finishLoading{
    [[self pullToRefreshView] finishLoading];
}

///////////////////////////////////////////////////////////////////////////////////////////////
#pragma UITableViewDelegate

- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath{
    [tableView deselectRowAtIndexPath:indexPath animated:YES];
    if ([GUConfigService isShowArticleList]) {
        int row = [indexPath row];
        if (row >= [_articleList count]) {
            return;
        }
        GUArticle* article = [_articleList objectAtIndex:row];
        if (article != nil) {
            NSDictionary*  query = [[NSDictionary alloc] initWithObjectsAndKeys:article, @"article", nil];
            GUReaderViewController* readerDetail = [[GUReaderViewController alloc] initWithQuery:query];
            [self.navigationController pushViewController:readerDetail animated:YES];
        }
    }else{
        int index = [[GUFavoriteService favoriteCategoryIds] count] - [indexPath row] - 1;
        if (index < 0) {
            index = 0;
        }
        NSNumber* categoryId = [[GUFavoriteService favoriteCategoryIds] objectAtIndex:index];
        GUCategory*  category = [GUFavoriteService getCategoryById:categoryId];
        if (category != nil) {
            NSDictionary* query = [NSDictionary dictionaryWithObjectsAndKeys:category, @"category", nil];
            GUCategoryArticleViewController* categoryArticleController = [[GUCategoryArticleViewController alloc] initWithQuery:query];
            [self.navigationController pushViewController:categoryArticleController animated:YES];
        }
    }
}

- (CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath{
    int row = [indexPath row];
    if ([GUConfigService isShowArticleList]) {
        if (row == [_articleList count]) {
            return 54;
        }
        return 87;
    }else{
        if (row == [[GUFavoriteService favoriteCategoryIds] count]) {
            return 54;
        }
        return 59;
    }
    
    return 0;
}

- (void)tableView:(UITableView *)tableView willDisplayCell:(UITableViewCell *)cell forRowAtIndexPath:(NSIndexPath *)indexPath{
    if ([GUConfigService isShowArticleList]) {
        int row = [indexPath row];
        if (row == [_articleList count]) {
            [[self articleListLoadMoreCell] startLoadMore];
        }
    }
}



///////////////////////////////////////////////////////////////////////////////////////////////
#pragma UITableViewDataSource

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section{
    if ([GUConfigService isShowArticleList]) {
        if ([_articleList count] == 0) {
            return 0;
        }
        if ([[self articleListLoadMoreCell] showLoadingCell]) {
            return [_articleList count] + 1;
        }
        return [_articleList count];
    }else{
        return [[GUFavoriteService favoriteCategoryIds] count];
    }
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath{
    int row = [indexPath row];
    if ([GUConfigService isShowArticleList]) {
        if (row == [_articleList count]) {
            return [self articleListLoadMoreCell];
        }
        static NSString* cellIdentify = @"DataTableViewArticleCellIdentify";
        GUArticleViewCell* cell = [tableView dequeueReusableCellWithIdentifier:cellIdentify];
        if (cell == nil) {
            cell = [[GUArticleViewCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:cellIdentify];
        }
        
        GUArticle* article = [_articleList objectAtIndex:row];
        cell.titleLabel.text = [article title];
        cell.authorLabel.text = [article author];
        cell.timeLabel.text =  [GUFormateUtils formateDate:[article publishDate]];
        cell.shortDescLabel.text = [article shortDesc];
        return cell;
    }else{
        static NSString* cellIdentify = @"DataTableViewFavoriteCellIdentify";
        GUCategoryViewCell* cell = [tableView dequeueReusableCellWithIdentifier:cellIdentify];
        if (cell == nil) {
            cell = [[ GUCategoryViewCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:cellIdentify];
        }
        //倒序展示
        int index = [[GUFavoriteService favoriteCategoryIds] count] - row - 1;
        if (index < 0) {
            index = 0;
        }
        NSNumber* categoryId = [[GUFavoriteService favoriteCategoryIds] objectAtIndex:index];
        GUCategory*  category = [GUFavoriteService getCategoryById:categoryId];
        cell.categoryTitleLabel.text  =  [category name];
        [cell setFavoriteImage:NO];
        [cell.favoriteButton addTarget:self action:@selector(cancelFavoriteButtonAction:forEvent:) forControlEvents:UIControlEventTouchUpInside];
        return cell;
    }
}


////////////////////////////////////////////////////////////////////////////////////
#pragma favoriteAction
-(void) cancelFavoriteButtonAction:(UIButton*) button forEvent:(UIEvent *) event{
    UITouch* touch =  [[event touchesForView:button] anyObject];
    CGPoint point = [touch locationInView:_dataTableView];
    NSIndexPath* indexPath = [_dataTableView indexPathForRowAtPoint:point];
    int row = [indexPath row];
    int index = [[GUFavoriteService favoriteCategoryIds] count] - row - 1;
    if (index < 0) {
        index = 0;
    }
    NSNumber* categoryId = [[GUFavoriteService favoriteCategoryIds] objectAtIndex:index];
    [GUFavoriteService removeFavoriteCategory:categoryId];
    [self refreshDataOrShowEmptyTips];
}



@end
