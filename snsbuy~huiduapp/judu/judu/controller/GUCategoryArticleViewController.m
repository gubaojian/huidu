//
//  GUCategoryArticleViewController.m
//  judu
//
//  Created by lurina on 13-8-3.
//  Copyright (c) 2013年 baobao. All rights reserved.
//

#import "GUCategoryArticleViewController.h"
#import "UIColor+Expanded.h"
#import "GUReaderViewController.h"
#import "GULoadMoreCell.h"
#import "SSPullToRefresh.h"
#import "GUArticleViewCell.h"
#import "GUSystemUtils.h"
#import "GUArticleService.h"
#import "GUCategory.h"
#import "GUConfig.h"
#import "GUFavoriteService.h"

@interface GUCategoryArticleViewController()<UITableViewDelegate, UITableViewDataSource, SSPullToRefreshViewDelegate>{
   @private  UIToolbar *_bottomToolBar;
   @private  UILabel* _categoryTitleLabel;
   @private  UITableView*  _articleTableView;
   @private  GULoadMoreCell* _loadMoreCell;
   @private  SSPullToRefreshView* _pullToRefreshView;
    
    
   @private GUCategory* _category;
   @private GUArticleService* _articleService;
    
   @private NSMutableArray* _articleList;
   @private int _currentPage;
    
    
   @private UIButton* _favoriteButton;

}
@end

@implementation GUCategoryArticleViewController

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
             _category = [query objectForKey:@"category"];
        }
    }
    return self;
}


-(void)loadView{
    self.navigationController.navigationBarHidden = YES;
    [super loadView];
    [self loadCategoryArticleList:GU_PAGE_START_NUM];
    [self bottomToolBar];
}


- (void)viewDidLoad
{
    [super viewDidLoad];
	// Do any additional setup after loading the view.
}

-(void)viewWillAppear:(BOOL)animated{
    self.navigationController.navigationBarHidden = YES;
    [super viewWillAppear:animated];
    if (_category != nil) {
        NSNumber* categoryId = [_category id];
        if([GUFavoriteService isFavoriteCategory:categoryId]){
            [_favoriteButton setImage:[UIImage imageNamed:@"cancel_favorite"] forState:UIControlStateNormal];
        }else{
            [_favoriteButton setImage:[UIImage imageNamed:@"add_favorite"] forState:UIControlStateNormal];
        }
    }

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
    if (_articleService != nil) {
        [_articleService cancelRequestOperation];
        _articleService = nil;
    }
    
    if (_articleList != nil) {
        _articleList = nil;
    }
    
    if (_bottomToolBar != nil) {
        _bottomToolBar = nil;
    }
    
    if (_categoryTitleLabel != nil) {
        _categoryTitleLabel = nil;
    }
    
    if (_articleTableView != nil) {
        _articleTableView = nil;
    }
    
    if (_loadMoreCell != nil) {
        _loadMoreCell = nil;
    }
    
    if (_pullToRefreshView != nil) {
        _pullToRefreshView = nil;
    }
}


///////////////////////////////////////////////////////////////////////////////////////////////
#pragma init method

-(UITableView*)articleTableView{
    if (_articleTableView == nil) {
        _articleTableView = [[UITableView alloc] initWithFrame:CGRectMake(0, 0, self.view.frame.size.width,
                                                                          self.view.frame.size.height  - 44)];
        _articleTableView.separatorStyle =  UITableViewCellSeparatorStyleNone;
        _articleTableView.backgroundColor = self.view.backgroundColor;
        _articleTableView.delegate = self;
        _articleTableView.dataSource = self ;
        if ([GUSystemUtils isIpad]) {
             _articleTableView.tableHeaderView = [[UIView alloc] initWithFrame:CGRectMake(0, 0, self.view.frame.size.width, 15)];
        }else{
             _articleTableView.tableHeaderView = [[UIView alloc] initWithFrame:CGRectMake(0, 0, self.view.frame.size.width, 5)];
        }
       
        _articleTableView.tableHeaderView.backgroundColor = [UIColor clearColor];
        _articleTableView.tableFooterView = [[UIView alloc] initWithFrame:CGRectMake(0, 0, self.view.frame.size.width, 3)];
        _articleTableView.tableFooterView.backgroundColor = [UIColor clearColor];
        [self.view addSubview:_articleTableView];
        [self.view bringSubviewToFront:[self bottomToolBar]];
    }
    return _articleTableView;
}

-(GULoadMoreCell*) loadMoreCell{
    if (_loadMoreCell == nil) {
        static NSString* loadMoreCellIdentify = @"LoadMoreCellIdentify";
        _loadMoreCell = [[GULoadMoreCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:loadMoreCellIdentify];
        __weak GUCategoryArticleViewController* weakSelf = self;
        _loadMoreCell.loadMoreBlock = ^(void){
            if ([[weakSelf loadMoreCell] isLoadSuccess]) {
               [weakSelf loadCategoryArticleList:[weakSelf currentPage] + 1];
            }else{
               [weakSelf loadCategoryArticleList:[weakSelf currentPage] + 1];
            }
        };
    }
    return _loadMoreCell;
}


-(UIToolbar*)bottomToolBar{
    if (_bottomToolBar == nil) {
        _bottomToolBar = [[UIToolbar alloc] initWithFrame:CGRectMake(0, CGRectGetHeight([self.view frame]) - 44, self.view.frame.size.width, 44)];
        
        UIBarButtonItem* leftSpace = [[UIBarButtonItem alloc] initWithBarButtonSystemItem:UIBarButtonSystemItemFixedSpace target:nil action:nil];
        leftSpace.width = 0;
        
        UIButton* backButton = [UIButton buttonWithType:UIButtonTypeCustom];
        [backButton setFrame:CGRectMake(0, 0, 64, 44)];
        [backButton setImage:[GUViewUtils cell22Image:@"\uf060"] forState:UIControlStateNormal];
        [backButton setImageEdgeInsets:UIEdgeInsetsMake(10, 20, 10, 20)];
        [backButton addTarget:self action:@selector(backAction) forControlEvents:UIControlEventTouchUpInside];
        UIBarButtonItem* back = [[UIBarButtonItem alloc] initWithCustomView:backButton];
        UIBarButtonItem* leftFlexSpace = [[UIBarButtonItem alloc] initWithBarButtonSystemItem:UIBarButtonSystemItemFlexibleSpace target:nil action:nil];
        
        UIBarButtonItem* titleBarButtonItem = [[UIBarButtonItem alloc] initWithCustomView:[self categoryTitleLabel]];
        
        UIBarButtonItem* rightFlexSpace = [[UIBarButtonItem alloc] initWithBarButtonSystemItem:UIBarButtonSystemItemFlexibleSpace target:nil action:nil];
        UIBarButtonItem* titleRightFixedSpace = [[UIBarButtonItem alloc] initWithBarButtonSystemItem:UIBarButtonSystemItemFixedSpace target:nil action:nil];
        titleRightFixedSpace.width = 2.5;
        
        UIButton* favoriteButton = [UIButton buttonWithType:UIButtonTypeCustom];
        [favoriteButton setFrame:CGRectMake(0, 0, 60, 25)];
        if ([GUFavoriteService isFavoriteCategory:[_category id]]) {
            [favoriteButton setImage:[UIImage imageNamed:@"cancel_favorite"] forState:UIControlStateNormal];
            [favoriteButton addTarget:self action:@selector(favoriteButtonAction:) forControlEvents:UIControlEventTouchUpInside];
        }else{
            [favoriteButton setImage:[UIImage imageNamed:@"add_favorite"] forState:UIControlStateNormal];
            [favoriteButton addTarget:self action:@selector(favoriteButtonAction:) forControlEvents:UIControlEventTouchUpInside];
        }
        _favoriteButton = favoriteButton;
        UIBarButtonItem* favorite = [[UIBarButtonItem alloc] initWithCustomView:favoriteButton];
        
        UIBarButtonItem* rightSpace = [[UIBarButtonItem alloc] initWithBarButtonSystemItem:UIBarButtonSystemItemFixedSpace target:nil action:nil];
        rightSpace.width = -2.5;
        
        
        NSArray* items = [NSArray arrayWithObjects:leftSpace, back, leftFlexSpace,
                          titleBarButtonItem, rightFlexSpace, titleRightFixedSpace, favorite, rightSpace, nil];
        [_bottomToolBar setItems:items];
        
        [self.view addSubview:_bottomToolBar];
    }
    return _bottomToolBar;
}





-(UILabel*)categoryTitleLabel{
    if (_categoryTitleLabel == nil) {
        if ([GUSystemUtils isIpad]) {
            _categoryTitleLabel = [[UILabel alloc] initWithFrame:CGRectMake(0, 0, 360, 20)];
        }else{
           _categoryTitleLabel = [[UILabel alloc] initWithFrame:CGRectMake(0, 0, 180, 20)];
        }
        _categoryTitleLabel.font = [UIFont boldSystemFontOfSize:16];
        _categoryTitleLabel.backgroundColor = [UIColor clearColor];
        _categoryTitleLabel.textAlignment = NSTextAlignmentCenter;
        _categoryTitleLabel.textColor = [UIColor colorWithHexString:@"565656"];
        _categoryTitleLabel.lineBreakMode = NSLineBreakByClipping;
        _categoryTitleLabel.text = [_category name];
    }
    return _categoryTitleLabel;
}

-(GUArticleService* )articleService{
    if (_articleService == nil) {
        _articleService = [[GUArticleService alloc] init];
    }
    return _articleService;
}

-(NSMutableArray*) articleList{
    if (_articleList == nil) {
        _articleList = [[NSMutableArray alloc] initWithCapacity:8];
    }
    return _articleList;
}
-(int)currentPage{
    return _currentPage;
}

-(void) setCurrentPage:(int)page{
    _currentPage = page;
}




-(CGRect) activityFrame{
    return CGRectMake(0, 0,
                      self.view.frame.size.width,
                      self.view.frame.size.height - 44);
}

///////////////////////////////////////////////////////////////////////////////////////////////
#pragma data load method
-(void) loadCategoryArticleList:(int)page{
    if (_category == nil) {
        return;
    }
    if (page <= GU_PAGE_START_NUM) {
        [[self statusView] showActivityInView:[self view] forFrame:[self activityFrame]];
        [[self articleList] removeAllObjects];
    }
    [self setCurrentPage:page];
    __weak GUCategoryArticleViewController* weakSelf = self;
    [[self articleService] getCategoryArticleList:[_category id] forPage:[self currentPage] withCallback:^(GUResult *result) {
        if ([result isSuccess] && [result result]) {
            [[weakSelf statusView] removeViewInfo];
            [[weakSelf loadMoreCell] setLoadSuccess:YES];
            [[weakSelf loadMoreCell] setHasMore:[result hasMore]];
            [[weakSelf articleList] addObjectsFromArray:[result result]];
            [[weakSelf articleTableView] reloadData];
        }else{
            if ([weakSelf currentPage] == GU_PAGE_START_NUM) {
                [[weakSelf statusView] showErrorInView:[weakSelf view] forFrame:[weakSelf activityFrame]];
            }else{
                [[weakSelf loadMoreCell] setLoadSuccess:NO];
            }
        }
        [[weakSelf loadMoreCell] loadMoreEnd];
    }];
}


-(void)reloadForError{
    [self loadCategoryArticleList:GU_PAGE_START_NUM];
}



///////////////////////////////////////////////////////////////////////////////////////////////
#pragma UIButton action
-(void)favoriteButtonAction:(UIButton*) button{
    if (_category == nil) {
        return;
    }
    
    NSNumber* categoryId = [_category id];
   if([GUFavoriteService isFavoriteCategory:categoryId]){
       [GUFavoriteService removeFavoriteCategory:categoryId];
       [button setImage:[UIImage imageNamed:@"add_favorite"] forState:UIControlStateNormal];
   }else{
       [GUFavoriteService addFavoriteCategory:_category];
       [button setImage:[UIImage imageNamed:@"cancel_favorite"] forState:UIControlStateNormal];
   }
}




-(SSPullToRefreshView*) pullToRefreshView{
    if (_pullToRefreshView == nil) {
        _pullToRefreshView = [[SSPullToRefreshView alloc] initWithScrollView:[self articleTableView] delegate:self];
    }
    return _pullToRefreshView;
}

///////////////////////////////////////////////////////////////////////////////////////////////
#pragma SSPullToRefreshViewDelegate

- (void)pullToRefreshViewDidStartLoading:(SSPullToRefreshView *)view{
    NSLog(@"start loading");
    [self performSelector:@selector(finishLoading) withObject:self afterDelay:2];
    
}


-(void) finishLoading{
    [[self pullToRefreshView] finishLoading];
    [[self articleTableView] reloadData];
}

///////////////////////////////////////////////////////////////////////////////////////////////
#pragma UITableViewDelegate
- (CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath{
    int row = [indexPath row];
    if (row >= [_articleList count]) {
         return 54;
    }
    return 87;
}

- (void)tableView:(UITableView *)tableView willDisplayCell:(UITableViewCell *)cell forRowAtIndexPath:(NSIndexPath *)indexPath{
    int row = [indexPath row];
    if (row == [_articleList count]){
        [[self loadMoreCell] startLoadMore];
    }
}

- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath{
    [tableView deselectRowAtIndexPath:indexPath animated:YES];
     int row = [indexPath row];
     if (row == [_articleList count]){
         return;
     }
    
     GUArticle* article = [_articleList objectAtIndex:row];
     if (article != nil) {
        NSDictionary* query = [NSDictionary dictionaryWithObjectsAndKeys:article, @"article", nil];
        GUReaderViewController* readerDetail = [[GUReaderViewController alloc] initWithQuery:query];
        [self.navigationController pushViewController:readerDetail animated:YES];
     }
}

///////////////////////////////////////////////////////////////////////////////////////////////
#pragma UITableViewDataSource

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section{
    if ([[self loadMoreCell] showLoadingCell]) {
        return [_articleList count] + 1;
    }
    return [_articleList count];
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath{
    int row = [indexPath row];
    if (row == [_articleList count]){
        return [self loadMoreCell];
    }

    static NSString* cellIdentify = @"ArticleTableViewCellIdentify";
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
}




@end
