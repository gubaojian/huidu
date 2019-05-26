 //
//  GUArticleViewCell.m
//  judu
//
//  Created by lurina on 13-7-28.
//  Copyright (c) 2013年 baobao. All rights reserved.
//

#import "GUArticleViewCell.h"
#import "UIColor+Expanded.h"
#import "GUSystemUtils.h"
#import "GUViewUtils.h"

@implementation GUArticleViewCell
@synthesize titleLabel = _titleLabel;
@synthesize timeLabel = _timeLabel;
@synthesize authorLabel = _authorLabel;
@synthesize shortDescLabel = _shortDescLabel;

- (id)initWithStyle:(UITableViewCellStyle)style reuseIdentifier:(NSString *)reuseIdentifier
{
    self = [super initWithStyle:style reuseIdentifier:reuseIdentifier];
    if (self) {
        
        if ([GUSystemUtils isIpad]) {
            [self setFrame:CGRectMake(self.frame.origin.x, self.frame.origin.y,  [GUViewUtils screenWidth], self.frame.size.height)];
        }
        
        // Initialization code
        UIImageView* backgroundView = [[UIImageView alloc] initWithFrame:CGRectMake(0, 0, self.frame.size.width,  self.frame.size.height)];
        
        backgroundView.image = [[UIImage imageNamed:@"article_cell_bg"] stretchableImageWithLeftCapWidth:24 topCapHeight:24];
        [self setBackgroundView:backgroundView];
        [self setBackgroundColor:[UIColor clearColor]];
       // UIView* selectBackgrounView = [[UIView alloc] initWithFrame:CGRectMake(0, 0, self.frame.size.width,  self.frame.size.height)];
       // selectBackgrounView.backgroundColor = [UIColor colorWithHexString:@"8c8c8c"];
       // [self setSelectedBackgroundView:selectBackgrounView];
        [self setSelectionStyle: UITableViewCellSelectionStyleNone];
   
        
        
        
        [self titleLabel];
        [self authorLabel];
        [self timeLabel];
        [self shortDescLabel];
    }
    return self;
}

- (void)setSelected:(BOOL)selected animated:(BOOL)animated
{
    [super setSelected:selected animated:animated];
}


-(UILabel*)titleLabel{
    if (_titleLabel == nil) {
        if ([GUSystemUtils isIpad]) {
            _titleLabel = [[UILabel alloc] initWithFrame:CGRectMake(27, 12.5, [GUViewUtils screenWidth] - 27 * 2, 19)];
        }else{
           _titleLabel = [[UILabel alloc] initWithFrame:CGRectMake(17, 12.5,
                                                                   [GUViewUtils screenWidth] - 17 * 2,
                                                                   19)];
        }
        
        _titleLabel.backgroundColor = [UIColor clearColor];
        _titleLabel.textColor =  [UIColor colorWithHexString:@"535353"];
        _titleLabel.font = [UIFont boldSystemFontOfSize:16];
        _titleLabel.lineBreakMode = NSLineBreakByTruncatingTail;
        [self.contentView addSubview:_titleLabel];
    }
    return _titleLabel;
}



-(UILabel*)authorLabel{
    if (_authorLabel == nil) {
        if ([GUSystemUtils isIpad]) {
            _authorLabel = [[UILabel alloc] initWithFrame:CGRectMake(27, 38, 80, 15)];
        }else{
             _authorLabel = [[UILabel alloc] initWithFrame:CGRectMake(17, 38, 80, 15)];
        }
        _authorLabel.backgroundColor = [UIColor clearColor];
        _authorLabel.textColor = [UIColor colorWithHexString:@"949494"];
        _authorLabel.font = [UIFont systemFontOfSize:13];
        [self.contentView addSubview:_authorLabel];
    }
    return _authorLabel;
}

-(UILabel*)timeLabel{
    if (_timeLabel == nil) {
        if ([GUSystemUtils isIpad]) {
            _timeLabel = [[UILabel alloc] initWithFrame:CGRectMake(124, 38, 160, 15)];
        }else{
            _timeLabel = [[UILabel alloc] initWithFrame:CGRectMake(114, 38, 160, 15)];
        }
        _timeLabel.backgroundColor = [UIColor clearColor];
        _timeLabel.textColor = [UIColor colorWithHexString:@"949494"];
        _timeLabel.font = [UIFont systemFontOfSize:13];
        [self.contentView addSubview:_timeLabel];
    }
    return _timeLabel;
}


-(UILabel*) shortDescLabel{
    if (_shortDescLabel == nil) {
        if ([GUSystemUtils isIpad]) {
        _shortDescLabel = [[UILabel alloc] initWithFrame:CGRectMake(27, 60,
                                                                  [GUViewUtils screenWidth] - 27 * 2,
                                                                    13)];
        }else{
            _shortDescLabel = [[UILabel alloc] initWithFrame:CGRectMake(17, 60,  [GUViewUtils screenWidth] - 17 * 2 - 1, 13)];
        }
        _shortDescLabel.backgroundColor = [UIColor clearColor];
        _shortDescLabel.textColor = [UIColor colorWithHexString:@"949494"];
        _shortDescLabel.font = [UIFont systemFontOfSize:13];
        _shortDescLabel.lineBreakMode = NSLineBreakByClipping;
        [self.contentView addSubview:_shortDescLabel];
    }
    return _shortDescLabel;
}




@end
