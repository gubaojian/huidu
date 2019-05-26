//
//  GUProcessView.m
//  judu
//
//  Created by lurina on 13-7-29.
//  Copyright (c) 2013年 baobao. All rights reserved.
//
#import <QuartzCore/QuartzCore.h>
#import "GUStatusView.h"
#import "UIColor+Expanded.h"


@implementation GUStatusView

@synthesize reloadBlock = _reloadBlock;

- (id)initWithFrame:(CGRect)frame
{
    self = [super initWithFrame:frame];
    if (self) {
        self.backgroundColor = [UIColor clearColor];
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


-(void) showActivityInView:(UIView*) view forFrame:(CGRect)frame{
    [self removeViewInfo];
    [self setFrame:frame];
    [[self processHud] show:YES];
    [view addSubview:self];
    //[view bringSubviewToFront:self];
}

-(void) showErrorInView:(UIView*) view forFrame:(CGRect)frame{
    [self removeViewInfo];
    [self setFrame:frame];
    [self errorTips];
    [self reloadButton];
    [view addSubview:self];
}

-(void) removeViewInfo{
    if (_processHud != nil) {
        [_processHud setHidden:YES];
        _processHud = nil;
    }
    if (_reloadButton != nil) {
        [_reloadButton removeFromSuperview];
        _reloadButton = nil;
    }
    if (_errorTips != nil) {
        [_errorTips removeFromSuperview];
        _errorTips = nil;
    }
    [self removeFromSuperview];
}


-(UILabel*)errorTips{
    if (_errorTips == nil) {
        CGFloat height = self.frame.size.height/2 - 50;
        _errorTips = [[UILabel alloc] initWithFrame:CGRectMake((self.frame.size.width - 300)/2, height, 300, 40)];
        _errorTips.backgroundColor = [UIColor clearColor];
        _errorTips.textColor = [UIColor colorWithHexString:@"565656"];
        _errorTips.textAlignment = NSTextAlignmentCenter;
        _errorTips.text = @"加载出错啦,请检查网络是否正常!";
        _errorTips.font = [UIFont systemFontOfSize:14];
        [self addSubview:_errorTips];
    }
    return _errorTips;
}

-(UIButton*)reloadButton{
    if (_reloadButton == nil) {
         CGFloat height = self.frame.size.height/2;
        _reloadButton  = [UIButton buttonWithType:UIButtonTypeRoundedRect];
        [_reloadButton setFrame:CGRectMake((self.frame.size.width - 100)/2, height, 100, 40)];
        [_reloadButton setTitle:@"重新加载" forState:UIControlStateNormal];
        [_reloadButton setTitleColor:[UIColor colorWithHexString:@"565656"] forState:UIControlStateNormal];
        [_reloadButton setBackgroundImage:[UIImage imageNamed:@"f4f4f4"] forState:UIControlStateNormal];
        _reloadButton.layer.cornerRadius = 8;
        _reloadButton.clipsToBounds = YES;
        [_reloadButton addTarget:self action:@selector(executeReloadBlock) forControlEvents:UIControlEventTouchUpInside];
        [self addSubview:_reloadButton];
    }
    return _reloadButton;
}

-(MBProgressHUD*) processHud{
    if (_processHud == nil) {
        _processHud = [[MBProgressHUD alloc] initWithView:self];
        [_processHud setMode:MBProgressHUDModeIndeterminate];
        _processHud.animationType = MBProgressHUDAnimationFade;
        _processHud.labelText = NSLocalizedString(@"Loading...", nil);
        _processHud.detailsLabelText = NSLocalizedString(@"HuiduSlogan", nil);
        _processHud.color = [UIColor colorWithHexString:@"414141"];
        [self addSubview:_processHud];
    }
    return _processHud;
}


-(void) executeReloadBlock{
    if (_reloadBlock) {
         [self removeViewInfo];
        _reloadBlock();
    }
}



@end
