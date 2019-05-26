//
//  GUTipsView.m
//  judu
//
//  Created by lurina on 13-8-15.
//  Copyright (c) 2013年 baobao. All rights reserved.
//

#import "GUTipsView.h"

@implementation GUTipsView

@synthesize tips = _tips;

@synthesize actionButton = _actionButton;

- (id)initWithFrame:(CGRect)frame
{
    self = [super initWithFrame:frame];
    if (self) {
        // Initialization code
        self.backgroundColor = [UIColor clearColor];
        [self errorTips];
        [self reloadButton];
    }
    return self;
}


-(void) showInView:(UIView*) view;{
    [self removeFromView]; 
    [view addSubview:self];
}


-(void) removeFromView{
    [self removeFromSuperview];
}


-(UILabel*)errorTips{
    if (_tips == nil) {
        CGFloat height = self.frame.size.height/2 - 50;
        _tips = [[UILabel alloc] initWithFrame:CGRectMake((self.frame.size.width - 300)/2, height, 300, 40)];
        _tips.backgroundColor = [UIColor clearColor];
        _tips.textColor = [UIColor colorWithHexString:@"565656"];
        _tips.textAlignment = NSTextAlignmentCenter;
        _tips.font = [UIFont systemFontOfSize:14];
        [self addSubview:_tips];
    }
    return _tips;
}

-(UIButton*)reloadButton{
    if (_actionButton == nil) {
        CGFloat height = self.frame.size.height/2;
        _actionButton  = [UIButton buttonWithType:UIButtonTypeRoundedRect];
        [_actionButton setFrame:CGRectMake((self.frame.size.width - 120)/2, height, 120, 40)];
        [_actionButton setTitleColor:[UIColor colorWithHexString:@"565656"] forState:UIControlStateNormal];
        [_actionButton setBackgroundImage:[UIImage imageNamed:@"f4f4f4"] forState:UIControlStateNormal];
        _actionButton.layer.cornerRadius = 8;
        _actionButton.clipsToBounds = YES;
        [self addSubview:_actionButton];
    }
    return _actionButton;
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
