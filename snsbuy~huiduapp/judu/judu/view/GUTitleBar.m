//
//  GUTitleBar.m
//  judu
//
//  Created by lurina on 13-7-28.
//  Copyright (c) 2013年 baobao. All rights reserved.
//

#import "GUTitleBar.h"
#import "UIColor+Expanded.h"

@implementation GUTitleBar

@synthesize titleLabel = _titleLabel;

@synthesize rightButton = _rightButton;

- (id)initWithFrame:(CGRect)frame
{
    self = [super initWithFrame:frame];
    if (self) {
        self.backgroundColor = [UIColor colorWithHexString:@"f4f4f4"];
    }
    return self;
}

-(void) layoutSubviews{
    if (_titleLabel.text != nil) {
        CGSize size = [[_titleLabel text] sizeWithFont:[_titleLabel font]];
        CGFloat x = (self.frame.size.width -  size.width)/2;
        CGFloat y = (self.frame.size.height - size.height)/2;
        CGRect frame = CGRectMake(x, y, size.width, size.height);
        [_titleLabel setFrame:frame];
    }
    [super layoutSubviews];
}




-(UILabel*) titleLabel{
    if (_titleLabel == nil) {
        _titleLabel = [[UILabel alloc] init];
        _titleLabel.font = [UIFont boldSystemFontOfSize:20];
        _titleLabel.backgroundColor = [UIColor clearColor];
        _titleLabel.textColor = [UIColor colorWithHexString:@"565656"];
        [self addSubview:_titleLabel];
    }
    return _titleLabel;
}


-(UIButton*) rightButton{
    if (_rightButton == nil) {
        _rightButton = [UIButton buttonWithType:UIButtonTypeCustom];
        [_rightButton setFrame:CGRectMake(self.frame.size.width - 67 , 0, 44, 44)];
        [_rightButton setImageEdgeInsets:UIEdgeInsetsMake(11, 11,11, 11)];
        _rightButton.tintColor = [UIColor blackColor];
        //[self addSubview:_rightButton];
    }
    return _rightButton;
}

-(UIButton*) rightButtonWithImage:(UIImage*)image{
    if (_rightButton != nil) {
        [_rightButton removeFromSuperview];
        _rightButton = nil;
    }
    if (_rightButton == nil) {
        _rightButton = [UIButton buttonWithType:UIButtonTypeCustom];
        [_rightButton setFrame:CGRectMake(0, 0, 44, 44)];
        [_rightButton setImageEdgeInsets:UIEdgeInsetsMake(11, 11,11, 11)];
        [_rightButton setImage:image forState:UIControlStateNormal];
        _rightButton.tintColor = [UIColor blackColor];
        //[self addSubview:_rightButton];
    }
    return _rightButton;
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
