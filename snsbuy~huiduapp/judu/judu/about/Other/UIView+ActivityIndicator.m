//
//  UIView+ActivityIndicator.m
//  
//
//  Created by Jianhong Yang on 12-1-7.
//  Copyright (c) 2012年 __MyCompanyName__. All rights reserved.
//

#import "UIView+ActivityIndicator.h"
#import "GUWaitingView.h"


@implementation UIView (ActivityIndicator)

// 显示带文本的活动指示灯
- (void)showActivityIndicatorWithText:(NSString *)strText
{
    //
    [self hideActivityIndicator];
    //
    GUWaitingView *viewWaiting = [[GUWaitingView alloc] initWithFrame:self.bounds];
    [viewWaiting setWaitingText:strText];
    [self addSubview:viewWaiting];
    [viewWaiting release];
}

// 设置活动指示灯的文本
- (void)setTextForActivityIndicator:(NSString *)strText
{
    for (UIView *subview in self.subviews) {
        if ([subview isMemberOfClass:GUWaitingView.class]) {
            GUWaitingView *viewWaiting = (GUWaitingView *)subview;
            [viewWaiting setTextForActivityIndicator:strText];
            break;
        }
    }
}

// 隐藏（与显示相对应）活动指示灯
- (void)hideActivityIndicator
{
    for (UIView *subview in self.subviews) {
        if ([subview isMemberOfClass:GUWaitingView.class]) {
            [subview removeFromSuperview];
            break;
        }
    }
}

// 填充活动指示灯
- (void)fillWithActivityIndicatorySize:(CGSize)size 
                              andStyle:(UIActivityIndicatorViewStyle)activityIndicatorViewStyle
{
    //
    [self removeActivityIndicatory];
    //
    CGRect frame = CGRectMake((self.bounds.size.width-size.width)/2, 
                              (self.bounds.size.height-size.height)/2, 
                              size.width, size.height);
    UIActivityIndicatorView *viewActivityIndicator = [[UIActivityIndicatorView alloc] initWithFrame:frame];
    viewActivityIndicator.activityIndicatorViewStyle = activityIndicatorViewStyle;
    [self addSubview:viewActivityIndicator];
    [viewActivityIndicator startAnimating];
    [viewActivityIndicator release];
}

// 填充活动指示灯
- (void)fillWithActivityIndicatoryStyle:(UIActivityIndicatorViewStyle)activityIndicatorViewStyle
{
    [self fillWithActivityIndicatorySize:self.bounds.size andStyle:activityIndicatorViewStyle];
}

// 移除活动指示灯
- (void)removeActivityIndicatory
{
    for (UIView *subview in self.subviews) {
        if ([subview isMemberOfClass:UIActivityIndicatorView.class]) {
            [subview removeFromSuperview];
            break;
        }
    }
}

@end
