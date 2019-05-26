//
//  GUSearchBar.m
//  judu
//
//  Created by lurina on 13-7-28.
//  Copyright (c) 2013年 baobao. All rights reserved.
//

#import "GUSearchBar.h"
#import <QuartzCore/QuartzCore.h>

@implementation GUSearchBar
@synthesize searchTextField = _searchTextField;

- (id)initWithFrame:(CGRect)frame
{
    self = [super initWithFrame:frame];
    if (self) {
        // Initialization code
        self.backgroundImage = [UIImage imageNamed:@"f4f4f4"];
        self.placeholder = @"发现你感兴趣的人和技术";
        
        //获取cancel
        /**
        UIBarButtonItem* proxy = [UIBarButtonItem appearanceWhenContainedIn:[self class], nil];
        [proxy setBackgroundImage:[UIImage imageNamed:@"calcel-button-bg"] forState:UIControlStateNormal barMetrics:UIBarMetricsDefault];
        [proxy setBackgroundImage:[UIImage imageNamed:@"calcel-button-bg"] forState:UIControlStateHighlighted barMetrics:UIBarMetricsDefault];
        [proxy setTitle:@" 取消 "];
        [proxy setTitleTextAttributes:[NSDictionary dictionaryWithObjectsAndKeys:[UIColor whiteColor], UITextAttributeTextColor, nil] forState:UIControlStateNormal];
         */
        
        [self searchTextField];
    
    }
    return self;
}

-(UIButton*)searchCancelButton{
    if (_searchCancelButton == nil) {
        for (UIView* subview in [self subviews]) {
            if([subview isKindOfClass:[UIButton class]])
            {
                _searchCancelButton = (UIButton*)subview;
            }
        }
    }
    return _searchCancelButton;
}

-(UITextField*) searchTextField{
    if (_searchTextField == nil) {
        for (UIView* subview in [self subviews]) {
            if([subview isKindOfClass:[UITextField class]])
            {
                _searchTextField = (UITextField*)subview;
            }
        }
    }
    return _searchTextField;
}


/*
// Only override drawRect: if you perform custom drawing.
// An empty implementation adversely affects performance during animation.
- (void)drawRect:(CGRect)rect
{
    // Drawing code
}
*/

@end
