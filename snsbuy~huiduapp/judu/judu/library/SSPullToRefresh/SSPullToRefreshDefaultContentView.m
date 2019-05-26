//
//  SSPullToRefreshDefaultContentView
//  SSPullToRefresh
//
//  Created by Sam Soffes on 4/9/12.
//  Copyright (c) 2012 Sam Soffes. All rights reserved.
//

#import "SSPullToRefreshDefaultContentView.h"
#import "GUViewUtils.h"


@implementation SSPullToRefreshDefaultContentView

@synthesize statusLabel = _statusLabel;
@synthesize lastUpdatedAtLabel = _lastUpdatedAtLabel;
@synthesize activityIndicatorView = _activityIndicatorView;

#pragma mark - UIView

- (id)initWithFrame:(CGRect)frame {
	if ((self = [super initWithFrame:frame])) {
		CGFloat width = self.bounds.size.width;
		
		_statusLabel = [[UILabel alloc] initWithFrame:CGRectMake(0.0f, 14.0f, width, 20.0f)];
		_statusLabel.autoresizingMask = UIViewAutoresizingFlexibleWidth;
		_statusLabel.font = [UIFont boldSystemFontOfSize:14.0f];
		_statusLabel.textColor = [UIColor grayColor];
		_statusLabel.backgroundColor = [UIColor clearColor];
		_statusLabel.textAlignment = NSTextAlignmentCenter;
		[self addSubview:_statusLabel];
		
		_lastUpdatedAtLabel = [[UILabel alloc] initWithFrame:CGRectMake(0.0f, 34.0f, width, 20.0f)];
		_lastUpdatedAtLabel.autoresizingMask = UIViewAutoresizingFlexibleWidth;
		_lastUpdatedAtLabel.font = [UIFont systemFontOfSize:12.0f];
		_lastUpdatedAtLabel.textColor = [UIColor lightGrayColor];
		_lastUpdatedAtLabel.backgroundColor = [UIColor clearColor];
		_lastUpdatedAtLabel.textAlignment = NSTextAlignmentCenter;
		[self addSubview:_lastUpdatedAtLabel];
		
        _activityIndicatorView = [[UIActivityIndicatorView alloc] initWithActivityIndicatorStyle:UIActivityIndicatorViewStyleGray];
        if (UI_USER_INTERFACE_IDIOM() == UIUserInterfaceIdiomPad) {
            _activityIndicatorView.frame = CGRectMake(270.0f, 25.0f, 20.0f, 20.0f);
        }else{
            _activityIndicatorView.frame = CGRectMake(30.0f, 25.0f, 20.0f, 20.0f);
        }
	
		[self addSubview:_activityIndicatorView];
        
        
        if (UI_USER_INTERFACE_IDIOM() == UIUserInterfaceIdiomPad) {
            _arrowView = [[UIImageView alloc] initWithFrame:CGRectMake(270.0f, 25.0f, 20.0f, 20.0f)];
        }else{
             _arrowView = [[UIImageView alloc] initWithFrame:CGRectMake(30.0f, 25.0f, 20.0f, 20.0f)];
        }
        [_arrowView setHidden:YES];
        [self addSubview:_arrowView];
        
	}
	return self;
}


#pragma mark - SSPullToRefreshContentView

- (void)setState:(SSPullToRefreshViewState)state withPullToRefreshView:(SSPullToRefreshView *)view {	
	switch (state) {
		case SSPullToRefreshViewStateReady: {
			_statusLabel.text = @"释放立即刷新";
			[_activityIndicatorView stopAnimating];
            [_activityIndicatorView setHidden:YES];
            [_arrowView setHidden:NO];
            [_arrowView setImage:[GUViewUtils cell22Image:@"\uf062"]];
			break;
		}
			
		case SSPullToRefreshViewStateNormal: {
			_statusLabel.text = @"下拉刷新";
			[_activityIndicatorView stopAnimating];
            [_activityIndicatorView setHidden:YES];
            [_arrowView setHidden:NO];
            [_arrowView setImage:[GUViewUtils cell22Image:@"\uf063"]];
			break;
		}
		
		case SSPullToRefreshViewStateLoading:
		case SSPullToRefreshViewStateClosing: {
			_statusLabel.text = @"正在获取最新内容";
			[_activityIndicatorView startAnimating];
            [_activityIndicatorView setHidden:NO];
            [_arrowView setHidden:YES];
			break;
		}
	}
}


- (void)setLastUpdatedAt:(NSDate *)date withPullToRefreshView:(SSPullToRefreshView *)view {

    NSString* dateStr = [GUFormateUtils formateDate:date];
	_lastUpdatedAtLabel.text = [NSString stringWithFormat:@"上次刷新时间: %@", dateStr];
}

@end
