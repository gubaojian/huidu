//
//  GULoadMoreCell.m
//  judu
//
//  Created by lurina on 13-7-29.
//  Copyright (c) 2013年 baobao. All rights reserved.
//

#import "GULoadMoreCell.h"
#import "GUSystemUtils.h"

@implementation GULoadMoreCell

@synthesize loadMoreBlock = _loadMoreBlock;

- (id)initWithStyle:(UITableViewCellStyle)style reuseIdentifier:(NSString *)reuseIdentifier
{
    self = [super initWithStyle:style reuseIdentifier:reuseIdentifier];
    if (self) {
        self.backgroundColor = [UIColor clearColor];
        if ([GUSystemUtils isIpad]) {
            [self setFrame:CGRectMake(self.frame.origin.x, self.frame.origin.y,  768, self.frame.size.height)];
        }
        // Initialization code
        [self setSelectionStyle:UITableViewCellSelectionStyleNone];
        [self activityIndicator];
        [self tipsLabel];
        [self addGestureRecognizer:[[UITapGestureRecognizer alloc] initWithTarget:self action:@selector(tapToReload)]];
        _hasMore = NO;
        _loadSuccess = NO;
    }
    return self;
}

- (void)setSelected:(BOOL)selected animated:(BOOL)animated
{
    [super setSelected:selected animated:animated];

    // Configure the view for the selected state
}


/**开始加载更多*/
-(void) startLoadMore{
    if (!_loadSuccess) {
        return;
    }
    [self loadMoreAction];
}
/**加载更多的操作结果*/
-(void)loadMoreEndWithLoadSuccess:(BOOL)loadSuccess hasMore:(BOOL)hasMore{
    _loadSuccess = loadSuccess;
    _hasMore = hasMore;
    [self loadMoreEnd];
}

/**加载结束*/
-(void)loadMoreEnd{
    [[self activityIndicator] stopAnimating];
    if (_loadSuccess) {
        [self tipsLabel].text = @"加载成功";
    }else{
        [self tipsLabel].text = @"加载失败，点击重新加载";
    }
}

/**根据状况是否显示加载更多的按钮*/
-(BOOL) showLoadingCell{
    if (_loadSuccess) {
        return _hasMore;
    }
    return YES;
}


-(void) tapToReload{
    if (_loadSuccess) {
        return;
    }
    [self loadMoreAction];
}


-(void)loadMoreAction{
    [[self activityIndicator] startAnimating];
    _tipsLabel.text = @"正在加载...";
    if (_loadMoreBlock) {
        _loadMoreBlock();
    }
}


-(BOOL)hasMore{
    return _hasMore;
}

-(void) setHasMore:(BOOL) hasMore{
    _hasMore = hasMore;
}

-(BOOL)isLoadSuccess{
    return _loadSuccess;
}

-(void) setLoadSuccess:(BOOL)success{
    _loadSuccess = success;
}


-(UIActivityIndicatorView*)  activityIndicator{
    if (_activityIndicator == nil) {
        _activityIndicator = [[UIActivityIndicatorView alloc] initWithActivityIndicatorStyle:UIActivityIndicatorViewStyleGray];
        CGRect frame =   _activityIndicator.frame;
        _activityIndicator.frame = CGRectMake((self.frame.size.width - frame.size.width)/2, (self.frame.size.height- frame.size.height)/3, frame.size.width, frame.size.height);
        [self addSubview:_activityIndicator];
    }
    return _activityIndicator;
}

-( UILabel*)tipsLabel{
    if (_tipsLabel == nil) {
        CGFloat width = 180;
        _tipsLabel = [[UILabel alloc] initWithFrame:CGRectMake((self.frame.size.width - width)/2 + 4, 32, width, 12)];
        _tipsLabel.backgroundColor = [UIColor  clearColor];
        _tipsLabel.textColor = [UIColor  grayColor];
        _tipsLabel.textAlignment = NSTextAlignmentCenter;
        _tipsLabel.font = [UIFont systemFontOfSize:12];
        _tipsLabel.text = @"正在加载...";
        [self addSubview:_tipsLabel];
    }
    return _tipsLabel;
}



@end
