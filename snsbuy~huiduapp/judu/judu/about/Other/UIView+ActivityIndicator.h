//
//  UIView+ActivityIndicator.h
//  
//
//  Created by Jianhong Yang on 12-1-7.
//  Copyright (c) 2012年 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "GUConfig.h"


@interface UIView (ActivityIndicator)

// 显示带文本的活动指示灯
- (void)showActivityIndicatorWithText:(NSString *)strText;

// 设置活动指示灯的文本
- (void)setTextForActivityIndicator:(NSString *)strText;

// 隐藏（与显示相对应）活动指示灯
- (void)hideActivityIndicator;

// 填充活动指示灯
- (void)fillWithActivityIndicatorySize:(CGSize)size 
                              andStyle:(UIActivityIndicatorViewStyle)activityIndicatorViewStyle;

// 填充活动指示灯
- (void)fillWithActivityIndicatoryStyle:(UIActivityIndicatorViewStyle)activityIndicatorViewStyle;

// 移除活动指示灯
- (void)removeActivityIndicatory;

@end
