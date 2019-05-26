//
//  WaitingView.m
//  
//
//  Created by Jianhong Yang on 12-1-7.
//  Copyright (c) 2012年 __MyCompanyName__. All rights reserved.
//

#import "GUWaitingView.h"
#import <QuartzCore/QuartzCore.h>


#define     WIDTH_ACTIVITYINDICATORBACK     100.0f
#define     HEIGHT_ACTIVITYINDICATORBACK    100.0f
#define     SIZE_ACTIVITYINDICATOR          50.0f


@implementation GUWaitingView

- (id)initWithFrame:(CGRect)frame
{
    self = [super initWithFrame:frame];
    if (self) {
        // Initialization code
        //
        self.backgroundColor = [UIColor clearColor];
        //背景
        CGRect frameBack = CGRectMake((frame.size.width-WIDTH_ACTIVITYINDICATORBACK)/2.0f, 
                                      (frame.size.height-HEIGHT_ACTIVITYINDICATORBACK)/2.0f, 
                                      WIDTH_ACTIVITYINDICATORBACK, 
                                      HEIGHT_ACTIVITYINDICATORBACK);
        _viewBackground = [[UIView alloc] initWithFrame:frameBack];
        _viewBackground.backgroundColor = [UIColor grayColor];
		_viewBackground.layer.masksToBounds = YES;
		_viewBackground.layer.cornerRadius = 10.0f;
        [self addSubview:_viewBackground];
        //活动指示灯
        CGFloat leftIndicator = (WIDTH_ACTIVITYINDICATORBACK-SIZE_ACTIVITYINDICATOR)/2.0f;
        CGRect frameIndicator = CGRectMake(leftIndicator, leftIndicator - 15,
                                           SIZE_ACTIVITYINDICATOR, SIZE_ACTIVITYINDICATOR);
        _viewActivityIndicator = [[UIActivityIndicatorView alloc] initWithFrame:frameIndicator];
        _viewActivityIndicator.activityIndicatorViewStyle = UIActivityIndicatorViewStyleWhiteLarge;
        [_viewBackground addSubview:_viewActivityIndicator];
        //文本
        CGFloat topText = leftIndicator + SIZE_ACTIVITYINDICATOR - 20;
        CGRect frameText = CGRectMake(0, topText, WIDTH_ACTIVITYINDICATORBACK, 
                                      HEIGHT_ACTIVITYINDICATORBACK-topText);
        _labelText = [[UILabel alloc] initWithFrame:frameText];
        _labelText.backgroundColor = [UIColor clearColor];
        _labelText.textColor = [UIColor whiteColor];
        _labelText.baselineAdjustment = UIBaselineAdjustmentAlignCenters;
        _labelText.textAlignment = NSTextAlignmentCenter;
        _labelText.font = [UIFont boldSystemFontOfSize:15.0f];
        [_viewBackground addSubview:_labelText];
        
        [_viewActivityIndicator startAnimating];
    }
    return self;
}

/*
// Only override drawRect: if you perform custom drawing.
// An empty implementation adversely affects performance during animation.
- (void)drawRect:(CGRect)rect
{
    // Drawing code
}
*/

- (void)dealloc
{
    [_viewBackground release];
    [_viewActivityIndicator release];
    [_labelText release];
    
    [super dealloc];
}


- (void)setWaitingText:(NSString *)text
{
    _labelText.text = text;
}

@end
