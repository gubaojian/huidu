//
//  GUSettingsCell.m
//  huidu
//
//  Created by lurina on 14-4-8.
//  Copyright (c) 2014年 baobao. All rights reserved.
//

#import "GUSettingsCell.h"
#import "UIColor+Expanded.h"

@implementation GUSettingsCell

@synthesize leftImageView = _leftImageView;
@synthesize leftTitleView = _leftTitleView;

- (id)initWithStyle:(UITableViewCellStyle)style reuseIdentifier:(NSString *)reuseIdentifier
{
    self = [super initWithStyle:style reuseIdentifier:reuseIdentifier];
    if (self) {
        // Initialization code
        UIView* backgroundView = [[UIView alloc] initWithFrame:CGRectMake(0, 0, self.frame.size.width,  self.frame.size.height)];
        backgroundView.backgroundColor = [UIColor whiteColor];
        [self setBackgroundView:backgroundView];
        [self setBackgroundColor:[UIColor clearColor]];
        [self setSelectionStyle: UITableViewCellSelectionStyleNone];
        
        UIImageView* accessoryView = [[UIImageView alloc] initWithFrame:CGRectMake(0, 0, 22, 22)];
        accessoryView.image = [UIImage imageNamed:@"small_arrow_right"];
        [self setAccessoryView:accessoryView];
         
    }
    return self;
}

- (void)awakeFromNib
{
    // Initialization code
}

- (void)setSelected:(BOOL)selected animated:(BOOL)animated
{
    [super setSelected:selected animated:animated];

    // Configure the view for the selected state
}



-(UILabel*) leftTitleView{
    if (_leftTitleView == nil) {
        _leftTitleView = [[UILabel alloc] initWithFrame:CGRectMake(44, 14, 280, 16)];
        _leftTitleView.autoresizingMask = UIViewAutoresizingFlexibleTopMargin | UIViewAutoresizingFlexibleBottomMargin;
        _leftTitleView.font = [UIFont systemFontOfSize:16];
        _leftTitleView.textColor = [UIColor colorWithHexString:@"535353"];
        [self.contentView addSubview:_leftTitleView];
    }
    return _leftTitleView;
}

-(UIImageView*)leftImageView{
    if (_leftImageView == nil) {
        _leftImageView = [[UIImageView alloc] initWithFrame:CGRectMake(16, 11, 22, 22)];
        [self.contentView addSubview:_leftImageView];
    }
    return _leftImageView;
}




@end
