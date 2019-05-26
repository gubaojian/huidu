//
//  GULoadMoreCell.h
//  judu
//
//  Created by lurina on 13-7-29.
//  Copyright (c) 2013年 baobao. All rights reserved.
//

#import <UIKit/UIKit.h>

typedef void(^LoadMoreBlock)(void);

@interface GULoadMoreCell : UITableViewCell{
   @private  UIActivityIndicatorView*  _activityIndicator;
   @private  UILabel* _tipsLabel;
   @private  LoadMoreBlock _loadMoreBlock;
   @private  BOOL  _loadSuccess;
   @private  BOOL _hasMore;
}


/**根据状况是否显示加载更多的按钮*/
-(BOOL) showLoadingCell;

/**开始加载更多*/
-(void) startLoadMore;
/**加载更多的操作结果， 如果本次加载不成功，则设置加载更多为YES*/
-(void)loadMoreEndWithLoadSuccess:(BOOL)loadSuccess hasMore:(BOOL)hasMore;
/**加载接受*/
-(void)loadMoreEnd;
/**是否还有更多加载*/
-(BOOL)hasMore;
-(void) setHasMore:(BOOL) hasMore;
/**本次加载是否成功*/
-(BOOL) isLoadSuccess;
-(void) setLoadSuccess:(BOOL)success;

/**
-(void) stopAnimation;

-(void) setHasMore:(BOOL) hasMore;
-(BOOL) isLoadFailed;
-(void) setLoadFailed:(BOOL)failed;
*/


@property (strong, nonatomic)LoadMoreBlock loadMoreBlock;

@end
