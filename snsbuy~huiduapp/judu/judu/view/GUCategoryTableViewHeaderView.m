//
//  GUCategoryTableViewHeaderView.m
//  judu
//
//  Created by lurina on 13-8-2.
//  Copyright (c) 2013年 baobao. All rights reserved.
//

#import "GUCategoryTableViewHeaderView.h"
#import "UIColor+Expanded.h"
#import "GUViewUtils.h"

@implementation GUCategoryTableViewHeaderView

@synthesize titleLabel = _titleLabel;

@synthesize imageLabel = _imageLabel;

- (id)initWithFrame:(CGRect)frame
{
    self = [super initWithFrame:frame];
    if (self) {
        // Initialization code
        self.backgroundColor = [UIColor colorWithHexString:@"f8f8f8"];
        [self titleLabel];
        [self imageLabel];
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




-(UILabel*) titleLabel{
    if (_titleLabel == nil) {
        _titleLabel = [[UILabel alloc] initWithFrame:CGRectMake(0, 1, self.frame.size.width, self.frame.size.height)];
        _titleLabel.backgroundColor = [UIColor clearColor];
        _titleLabel.font = [UIFont systemFontOfSize:13];
        _titleLabel.textColor = [UIColor colorWithHexString:@"929292"];
        _titleLabel.text = @"      文章分类";
        _titleLabel.textAlignment = NSTextAlignmentLeft;
        [self addSubview:_titleLabel];
    }
    return _titleLabel;
}

-(UIImageView*) imageLabel{
    if (_imageLabel == nil) {
        _imageLabel = [[UIImageView alloc] initWithFrame:CGRectMake(78, (self.frame.size.height - 15)/2 + 1, 15, 15)];
        _imageLabel.image = [GUViewUtils cell15Image:@"\uf02b"];
        [self addSubview:_imageLabel];
    }
    return _imageLabel;
}



@end
