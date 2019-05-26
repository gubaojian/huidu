//
//  GUSearchViewController.m
//  judu
//
//  Created by lurina on 13-7-28.
//  Copyright (c) 2013年 baobao. All rights reserved.
//

#import "GUSearchViewController.h"
#import "GUSearchBar.h"
#import "GUCategoryViewCell.h"
#import "GUSearchDisplayView.h"
#import "GUStatusView.h"
#import "GULoadMoreCell.h"
#import "SSPullToRefreshView.h"
#import "MobClick.h"
#import "GUCategoryArticleViewController.h"
#import "GUCategoryService.h"
#import "GUConfig.h"
#import "GUFavoriteService.h"

#define LOAD_ERROR_CAREGORY_LIST  0 

#define LOAD_ERROR_SEARCH_CAREGORY_LIST  1

@interface GUSearchViewController ()<UISearchBarDelegate,  UISearchDisplayDelegate, UITableViewDataSource, UITableViewDelegate,SSPullToRefreshViewDelegate>{
    UITableView*  _categoryListTableView;
    GULoadMoreCell* _categoryListLoadMoreCell;
    SSPullToRefreshView* _categoryListPullToRefreshView;
    
    GUSearchBar* _searchBar;
    GUSearchDisplayView* _searchDisplayView;
    GULoadMoreCell* _searchCategoryLoadMoreCell;
    
    GUCategoryService* _categoryService;
    NSMutableArray* _categoryList;
    int _currentPage;
    
    NSMutableArray* _searchCategoryList;
    int _searchCategoryCurrentPage;
    
    int _loadError; //0 代表正常列表， 1 代表搜索列表 
    
}

@end

@implementation GUSearchViewController

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        // Custom initialization
        self.tabBarItem.title = NSLocalizedString(@"Favorite", @"发现");
        [self.tabBarItem setFinishedSelectedImage:[GUViewUtils tabbarSelectImage:@"\uf002"] withFinishedUnselectedImage:[GUViewUtils tabbarNormalImage:@"\uf002"]];
    }
    return self;
}

-(void) loadView{
    self.navigationController.navigationBarHidden = NO;
    [super loadView];
    [self searchBar];
    [self loadCategoryList:GU_PAGE_START_NUM];
   // [self favoriteCategoryTableView];
    //[self pullToRefreshView];
}


- (void)viewDidLoad
{
    [super viewDidLoad];
	// Do any additional setup after loading the view.
}

-(void)viewWillAppear:(BOOL)animated{
    [super viewWillAppear:animated];
    self.navigationController.navigationBarHidden = NO;
}


-(void) viewDidAppear:(BOOL)animated{
    [super viewDidAppear:animated];
    [MobClick beginEvent:@"JuduSearchTab"];
    if (_searchDisplayView != nil && [_searchDisplayView searchState] == DISPLAY_RESULTS) {
        [[_searchDisplayView searchResultsTableView] reloadData];
    }else{
        if (_categoryListTableView != nil) {
            [_categoryListTableView reloadData];
        }
    }
}

-(void) viewDidDisappear:(BOOL)animated{
    [super viewDidDisappear:animated];
    [MobClick endEvent:@"JuduSearchTab"];
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
    if (_categoryService != nil) {
        [_categoryService cancelRequestOperation];
        _categoryService = nil;
    }
    
    if (_searchCategoryList != nil) {
        _searchCategoryList = nil;
    }

    if (_searchBar != nil) {
        _searchBar = nil;
    }    
    
    if (_categoryListTableView) {
        _categoryListTableView = nil;
    }
    
    if (_categoryListLoadMoreCell) {
        _categoryListLoadMoreCell = nil;
    }
    
    if (_categoryListPullToRefreshView) {
        _categoryListPullToRefreshView = nil;
    }
    
    if (_searchDisplayView) {
        _searchDisplayView = nil;
    }
    
    if (_searchCategoryLoadMoreCell) {
        _searchCategoryLoadMoreCell = nil;
    }
    
    if (_categoryList) {
        _categoryList = nil;
    }
   
    [super clean];
}





///////////////////////////////////////////////////////////////////////////////////////////////////
#pragma init method
-(GUSearchBar*) searchBar{
    if(_searchBar == nil){
        _searchBar = [[GUSearchBar alloc] initWithFrame:CGRectMake(0, 0, self.view.frame.size.width, 44)];
        _searchBar.delegate = self;
        self.navigationItem.titleView = _searchBar;
    }
    return _searchBar;
}


-(GUSearchDisplayView*) searchDisplayView{
    if (_searchDisplayView == nil) {
        _searchDisplayView = [[GUSearchDisplayView alloc] initWithFrame:CGRectMake(0, 0, self.view.frame.size.width, self.view.frame.size.height)];
        _searchDisplayView.searchResultsTableView.dataSource = self;
        _searchDisplayView.searchResultsTableView.delegate = self;
        _searchDisplayView.searchResultsTableView.autoresizingMask = UIViewAutoresizingFlexibleHeight;
        [_searchDisplayView setSearchBar:[self searchBar]];
        [self.view addSubview:_searchDisplayView];
    }
    return _searchDisplayView;
}

-(UITableView*) categoryListTableView{
    if (_categoryListTableView == nil) {
        if ([GUSystemUtils isIos7]) {
            _categoryListTableView = [[UITableView alloc] initWithFrame:CGRectMake(0, 0, self.view.frame.size.width, self.view.frame.size.height) style:UITableViewStylePlain];
        }else{
          _categoryListTableView = [[UITableView alloc] initWithFrame:CGRectMake(0, 0, self.view.frame.size.width, self.view.frame.size.height -self.tabBarController.tabBar.frame.size.height) style:UITableViewStylePlain];
         }
        _categoryListTableView.dataSource = self;
        _categoryListTableView.delegate = self;
        _categoryListTableView.backgroundColor = self.view.backgroundColor;
        _categoryListTableView.separatorStyle = UITableViewCellSeparatorStyleNone;
        _categoryListTableView.autoresizingMask = UIViewAutoresizingFlexibleHeight;
        [self.view addSubview:_categoryListTableView];    
    }
    return _categoryListTableView;
}

-(GULoadMoreCell*) categoryListLoadMoreCell{
    if (_categoryListLoadMoreCell == nil) {
        static NSString* loadMoreCellIdentify = @"LoadMoreCategoryCellIdentify";
        _categoryListLoadMoreCell = [[GULoadMoreCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:loadMoreCellIdentify];
        __weak GUSearchViewController* weakSelf = self;
        _categoryListLoadMoreCell.loadMoreBlock = ^(void){
            if ([[weakSelf categoryListLoadMoreCell] isLoadSuccess]) {
                  [weakSelf loadCategoryList:[weakSelf currentPage] + 1];
            }else{
                [weakSelf loadCategoryList:[weakSelf currentPage]];
            }
        };
    }
    return _categoryListLoadMoreCell;
}

-(GULoadMoreCell*)searchCategoryLoadMoreCell{
    if (_searchCategoryLoadMoreCell == nil) {
        static NSString* loadMoreCellIdentify = @"LoadMoreSearchCategoryCellIdentify";
        _searchCategoryLoadMoreCell = [[GULoadMoreCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:loadMoreCellIdentify];
        [_searchCategoryLoadMoreCell setHasMore:NO];
        __weak GUSearchViewController* weakSelf = self;
        _searchCategoryLoadMoreCell.loadMoreBlock = ^(void){
            if ([[weakSelf searchCategoryLoadMoreCell] isLoadSuccess]) {
                [weakSelf loadSearchCategoryList:[[weakSelf searchBar] text] forPage:[weakSelf searchCategoryCurrentPage] + 1];
            }else{
                [weakSelf loadSearchCategoryList:[[weakSelf searchBar] text] forPage:[weakSelf searchCategoryCurrentPage]];
            }
        };
    }
    return _searchCategoryLoadMoreCell;
}
-(SSPullToRefreshView*) categoryListPullToRefreshView{
    if (_categoryListPullToRefreshView == nil) {
        _categoryListPullToRefreshView = [[SSPullToRefreshView alloc] initWithScrollView:[self categoryListTableView] delegate:self];
    }
    return _categoryListPullToRefreshView;
}

-(GUCategoryService* )categoryService{
    if (_categoryService == nil) {
        _categoryService = [[GUCategoryService alloc] init];
    }
    return _categoryService;
}

-(NSMutableArray*)categoryList{
    if (_categoryList == nil) {
        _categoryList = [[NSMutableArray alloc] initWithCapacity:16];
    }
    return _categoryList;
}


-(int) currentPage{
    return _currentPage;
}

-(void) setCurrentPage:(int)page{
    _currentPage = page;
}

-(NSMutableArray*)searchCategoryList{
    if (_searchCategoryList == nil) {
        _searchCategoryList = [[NSMutableArray alloc] initWithCapacity:16];
    }
    return _searchCategoryList;
}


-(int)searchCategoryCurrentPage{
    return _searchCategoryCurrentPage;
}

-(void)setSearchCategoryCurrentPage:(int) page{
    _searchCategoryCurrentPage = page;
}

-(int)loadError{
    return _loadError;
}

-(void) setLoadError:(int) loadError{
    _loadError = loadError;
}

-(CGRect) activityFrame{
    return CGRectMake(0, 44,
               self.view.frame.size.width,
                      self.view.frame.size.height - 44 -  self.tabBarController.tabBar.frame.size.height);
}

-(CGRect) searchActivityFrame{
    return CGRectMake(0, 0, [self searchDisplayView].frame.size.width, [self searchDisplayView].frame.size.height);
}

///////////////////////////////////////////////////////////////////////////////////////////////
#pragma data load method
-(void) loadCategoryList:(int) pageNum{
    [self setCurrentPage:pageNum];
    if ([self currentPage] <= GU_PAGE_START_NUM) {
        [[self statusView] showActivityInView:self.view forFrame:[self activityFrame]];
        [[self categoryList] removeAllObjects];
    }
    __weak GUSearchViewController* weakSelf = self;
    [[self categoryService] getCategoryList:pageNum withCallback:^(GUResult *result) {
        if ([result isSuccess] && [result result]) {
            [[weakSelf statusView] removeViewInfo];
            [[weakSelf categoryListLoadMoreCell] setLoadSuccess:YES];
            [[weakSelf categoryListLoadMoreCell] setHasMore: [result hasMore]];
            [[weakSelf categoryList] addObjectsFromArray:[result result]];
            [[weakSelf categoryListTableView] reloadData];
            [weakSelf categoryListPullToRefreshView];
        }else{
            if ([weakSelf currentPage] <= GU_PAGE_START_NUM) {
                [[weakSelf statusView] showErrorInView:[weakSelf view] forFrame:[weakSelf activityFrame]];
                [weakSelf setLoadError:LOAD_ERROR_CAREGORY_LIST];
            }else{
                [[weakSelf categoryListLoadMoreCell] setLoadSuccess:NO];
                [[weakSelf categoryListTableView] reloadData];
            }
        }
        [[weakSelf categoryListLoadMoreCell] loadMoreEnd];
    }];
}


-(void) reloadCategoryListIfUpdate{
    __weak GUSearchViewController* weakSelf = self;
    [[self categoryService] getCategoryList:GU_PAGE_START_NUM withCallback:^(GUResult *result) {
        if ([result isSuccess] && [result result]) {
            if ([[result result] count] > 0) {
                NSNumber* newFirst = [[[result result] objectAtIndex:0] id];
                NSNumber* oldFirst = nil;
                if ([[weakSelf categoryList] count] > 0) {
                    oldFirst = [[[weakSelf categoryList] objectAtIndex:0] id];
                }
                if (![newFirst isEqualToNumber:oldFirst]) {
                    [[weakSelf categoryList] removeAllObjects];
                    [[weakSelf categoryList] addObjectsFromArray:[result result]];
                    [weakSelf setCurrentPage:GU_PAGE_START_NUM];
                    [[weakSelf categoryListLoadMoreCell] setHasMore:YES];
                    [[weakSelf categoryListLoadMoreCell] setLoadSuccess:YES];
                    [[weakSelf categoryListTableView] reloadData];
                }
            }
        }
        [weakSelf finishLoading];
    }];
}


-(void) loadSearchCategoryList:(NSString*)keyword forPage:(int) pageNum{
    [[self searchCategoryLoadMoreCell] setHasMore:YES];
    [self setSearchCategoryCurrentPage:pageNum];
    if ([self searchCategoryCurrentPage] <= GU_PAGE_START_NUM) {
        [[self statusView] showActivityInView:[self searchDisplayView] forFrame:[self searchActivityFrame]];
        [[self searchCategoryList] removeAllObjects];
        [[self searchDisplayView] setActiveSearchForState:SEARCHING];
    }
    __weak GUSearchViewController* weakSelf = self;
    [[self categoryService] searchCategory:keyword forPage:pageNum withCallback:^(GUResult *result) {
        if ([result isSuccess] && [result result]) {
            [[weakSelf statusView] removeViewInfo];
             [[self searchCategoryLoadMoreCell] setLoadSuccess:YES];
             [[self searchCategoryLoadMoreCell] setHasMore:[result hasMore]];
            [[weakSelf searchCategoryList] addObjectsFromArray:[result result]];
            [[weakSelf searchDisplayView] setActiveSearchForState:DISPLAY_RESULTS];
        }else{
            if ([weakSelf searchCategoryCurrentPage] <= GU_PAGE_START_NUM) {
                [[weakSelf statusView] showErrorInView:[weakSelf searchDisplayView] forFrame:[weakSelf searchActivityFrame]];
                [weakSelf setLoadError:LOAD_ERROR_SEARCH_CAREGORY_LIST];
            }else{
                [[[weakSelf searchDisplayView] searchResultsTableView] reloadData];
                 [[self searchCategoryLoadMoreCell] setLoadSuccess:NO];
            }
        }
        [[self searchCategoryLoadMoreCell] loadMoreEnd];
    }];
}


-(void)reloadForError{
    if ([self loadError] == LOAD_ERROR_SEARCH_CAREGORY_LIST) {
        [self loadSearchCategoryList:[[self searchBar] text] forPage:GU_PAGE_START_NUM];
    }else{
       [self loadCategoryList:GU_PAGE_START_NUM];
    }
}

///////////////////////////////////////////////////////////////////////////////////////////////
#pragma SSPullToRefreshViewDelegate

- (void)pullToRefreshViewDidStartLoading:(SSPullToRefreshView *)view{
    [self reloadCategoryListIfUpdate];
}

-(void) finishLoading{
    [[self categoryListPullToRefreshView] finishLoading];
}

///////////////////////////////////////////////////////////////////////////////////////////////////
#pragma UISearchBarDelegate
- (BOOL)searchBarShouldBeginEditing:(UISearchBar *)searchBar{
    if ([[self statusView] superview] == [self searchDisplayView]) {
        [[self statusView] removeViewInfo];
    }
    

    if ([GUSystemUtils isIos7]) {
        searchBar.showsCancelButton = NO;
        self.navigationItem.rightBarButtonItem = [[UIBarButtonItem alloc] initWithTitle:NSLocalizedString(@"Cancel", nil) style:UIBarButtonItemStylePlain target:self action:@selector(didClickCancelButton:)];

    }else{
        [searchBar setShowsCancelButton:YES];
    }
    
    
    [[self searchDisplayView] setActiveSearchForState:SEARCHING];
    return YES;
}

-(void) didClickCancelButton:(UIBarButtonItem*) item{
    [self cancelAction];
}


- (void)searchBarSearchButtonClicked:(UISearchBar *)searchBar{
    [searchBar resignFirstResponder];
    [searchBar setShowsCancelButton:NO];
    [self loadSearchCategoryList:[searchBar text] forPage:GU_PAGE_START_NUM];
}

- (void)searchBarBookmarkButtonClicked:(UISearchBar *)searchBar{
    [searchBar resignFirstResponder];
}

- (void)searchBarCancelButtonClicked:(UISearchBar *) searchBar{
    [self cancelAction];
}

-(void) cancelAction{
    [_searchBar setText:@""];
    [_searchBar resignFirstResponder];
    [_searchBar setShowsCancelButton:NO];
    if (self.navigationItem.rightBarButtonItem) {
        self.navigationItem.rightBarButtonItem = nil;
    }
    
    [[self searchDisplayView] setActiveSearchForState:END];
    _searchCategoryList = nil;
    [[self searchCategoryLoadMoreCell] setHasMore:NO];
    if (_categoryList == nil || [_categoryList count] == 0) {
        [self setLoadError:LOAD_ERROR_CAREGORY_LIST];
        [[self statusView] showErrorInView:[self view] forFrame:[self activityFrame]];
    }else{
        [_categoryListTableView reloadData];
    }


}


///////////////////////////////////////////////////////////////////////////////////////////////
#pragma UITableViewDelegate
- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath{
    [tableView deselectRowAtIndexPath:indexPath animated:YES];
    
    int row = [indexPath row];
    GUCategory* category = nil;
    if (tableView == _categoryListTableView) {
        if (row >= [_categoryList count]) {
            return;
        }
        category = [_categoryList objectAtIndex:row];
    }else if (tableView == _searchDisplayView.searchResultsTableView){
        if (row >= [_searchCategoryList count]) {
            return;
        }
        category = [_searchCategoryList objectAtIndex:row];
    }
    
    if (category != nil) {
        NSDictionary* query = [NSDictionary dictionaryWithObjectsAndKeys:category, @"category", nil];
        GUCategoryArticleViewController* categoryArticleController = [[GUCategoryArticleViewController alloc] initWithQuery:query];
        [self.navigationController pushViewController:categoryArticleController animated:YES];
    }
}

- (void)tableView:(UITableView *)tableView willDisplayCell:(UITableViewCell *)cell forRowAtIndexPath:(NSIndexPath *)indexPath{
    int row = [indexPath row];
    if (tableView == _categoryListTableView) {
        if (row == [_categoryList count]) {
            [[self categoryListLoadMoreCell] startLoadMore];
        }
    }else if (tableView == [_searchDisplayView searchResultsTableView]){
        if (row == [_searchCategoryList count]) {
            [[self searchCategoryLoadMoreCell] startLoadMore];
        }
    }
}

- (CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath{
    int row = [indexPath row];
    if (tableView == _categoryListTableView) {
        if (row == [_categoryList count]) {
            return 54;
        }
        return 59;
    }else if (tableView == [_searchDisplayView searchResultsTableView]){
        if (row == [_searchCategoryList count]) {
            return 54;
        }
        return 59;
    }
    return 59;
}

///////////////////////////////////////////////////////////////////////////////////////////////
#pragma UITableViewDataSource

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section{
    if (tableView == [self categoryListTableView]) {
        if ([_categoryList count] <= 0) {
            return 0;
        }
        if([[self categoryListLoadMoreCell] showLoadingCell]) {
            return [_categoryList count] + 1;
        }
        return [_categoryList count];
    }else if (tableView == [[self searchDisplayView] searchResultsTableView]){
        if ([_searchCategoryList count] <= 0) {
            return 0;
        }
        if ([[self searchCategoryLoadMoreCell] showLoadingCell]) {
            return [_searchCategoryList count] + 1;
        }
        return [_searchCategoryList count];
    }
    return 0;
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath{
    static NSString* cellIdentify = @" GUFavoriteViewCellIdentify";
    GUCategoryViewCell* cell = [tableView dequeueReusableCellWithIdentifier:cellIdentify];
    if (cell == nil) {
        cell = [[GUCategoryViewCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:cellIdentify];
    }
    
    int row = [indexPath row];
    GUCategory* category = nil;
    if (tableView == _categoryListTableView) {
        if (row >= [_categoryList count]) {
            return [self categoryListLoadMoreCell];
        }
        category = [_categoryList objectAtIndex:row];
    }else if (tableView == [_searchDisplayView searchResultsTableView]){
        if (row >= [_searchCategoryList count]) {
            return [self searchCategoryLoadMoreCell];
        }
        category = [_searchCategoryList objectAtIndex:row];
    }
    
    if (category != nil) {
        cell.categoryTitleLabel.text = [category name];
        if ([GUFavoriteService isFavoriteCategory:[category id]]) {
            [cell setFavoriteImage:NO];
        }else{
            [cell setFavoriteImage:YES];
        }
        [cell.favoriteButton addTarget:self action:@selector(favoriteButtonAction:forEvent:) forControlEvents:UIControlEventTouchUpInside];
        return cell;
    }
    return cell;
}


////////////////////////////////////////////////////////////////////////////////////
#pragma favoriteAction
-(void) favoriteButtonAction:(UIButton*) button forEvent:(UIEvent *) event{
    UITouch* touch =  [[event touchesForView:button] anyObject];
    GUCategory* category = nil;
    if (_searchDisplayView != nil && [[self searchDisplayView] searchState] != END) {
        CGPoint point = [touch locationInView:[[self searchDisplayView] searchResultsTableView]];
        NSIndexPath* indexPath = [[[self searchDisplayView] searchResultsTableView] indexPathForRowAtPoint:point];
        int row = [indexPath row];
        category = [_searchCategoryList objectAtIndex:row];
    }else{
        CGPoint point = [touch locationInView:[self categoryListTableView]];
        NSIndexPath* indexPath = [[self categoryListTableView] indexPathForRowAtPoint:point];
        int row = [indexPath row];
        category = [_categoryList objectAtIndex:row];
    }
    
    if ([GUFavoriteService isFavoriteCategory:[category id]]) {
        [GUFavoriteService removeFavoriteCategory:[category id]];
        [button setBackgroundImage:[UIImage imageNamed:@"add_favorite"] forState:UIControlStateNormal];
    }else{
        [GUFavoriteService addFavoriteCategory:category];
        [button setBackgroundImage:[UIImage imageNamed:@"cancel_favorite"] forState:UIControlStateNormal];
    }
}




@end
