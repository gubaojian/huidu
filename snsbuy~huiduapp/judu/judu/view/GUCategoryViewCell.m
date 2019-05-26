//
//  GUFavoriteViewCell.m
//  judu
//
//  Created by lurina on 13-7-28.
//  Copyright (c) 2013年 baobao. All rights reserved.
//

#import "GUCategoryViewCell.h"
#import "UIColor+Expanded.h"
#import "GUSystemUtils.h"
#import "GUViewUtils.h"

@implementation GUCategoryViewCell

@synthesize favoriteButton = _favoriteButton;

@synthesize categoryTitleLabel = _categoryTitleLabel;

- (id)initWithStyle:(UITableViewCellStyle)style reuseIdentifier:(NSString *)reuseIdentifier
{
    self = [super initWithStyle:style reuseIdentifier:reuseIdentifier];
    if (self) {
        
        if ([GUSystemUtils isIpad]) {
            [self setFrame:CGRectMake(self.frame.origin.x, self.frame.origin.y,  [GUViewUtils screenWidth], self.frame.size.height)];
        }
        // Initialization code
        UIImageView* cellBackgrounView = [[UIImageView alloc] initWithFrame:CGRectMake(0, 0, self.frame.size.width, self.frame.size.height)];
        cellBackgrounView.image = [[UIImage imageNamed:@"category_cell_bg"] stretchableImageWithLeftCapWidth:24 topCapHeight:12];
        [self setBackgroundView:cellBackgrounView];
        [self setBackgroundColor:[UIColor  clearColor]];
        [self setSelectionStyle:UITableViewCellSelectionStyleNone];
        
        
        [self categoryTitleLabel];
        
        [self favoriteButton];
    }
    return self;
}

- (void)setSelected:(BOOL)selected animated:(BOOL)animated
{
    [super setSelected:selected animated:animated];

    // Configure the view for the selected state
}


-(UILabel*) categoryTitleLabel{
    if (_categoryTitleLabel == nil) {
        if ([GUSystemUtils isIpad]) {
            _categoryTitleLabel = [[UILabel alloc] initWithFrame:CGRectMake(20, 19.5, 500, 19)];
        }else{
            _categoryTitleLabel = [[UILabel alloc] initWithFrame:CGRectMake(20, 19.5, 200, 19)];
        }
        _categoryTitleLabel.backgroundColor = [UIColor clearColor];
        _categoryTitleLabel.textColor = [UIColor colorWithHexString:@"535353"];
        _categoryTitleLabel.font = [UIFont boldSystemFontOfSize:16];
        _categoryTitleLabel.lineBreakMode = NSLineBreakByClipping;
        [self.contentView addSubview:_categoryTitleLabel];
    }
    return _categoryTitleLabel;
}


-(UIButton*) favoriteButton{
    if (_favoriteButton == nil) {
        _favoriteButton = [UIButton buttonWithType:UIButtonTypeCustom];
       if ([GUSystemUtils isIpad]) {
            [_favoriteButton setFrame:CGRectMake([GUViewUtils screenWidth] - 20 - 66.6, 16, 66.6, 27.5)];
       }else{
          [_favoriteButton setFrame:CGRectMake([GUViewUtils screenWidth] - 20 - 66.6, 16, 66.6, 27.5)];
       }
        
        [self.contentView addSubview:_favoriteButton];
    }
    return _favoriteButton;
}

-(void) setFavoriteImage:(BOOL)favorite{
    if (favorite) {
        [_favoriteButton setBackgroundImage:[UIImage imageNamed:@"add_favorite"] forState:UIControlStateNormal];
    }else{
       [_favoriteButton setBackgroundImage:[UIImage imageNamed:@"cancel_favorite"] forState:UIControlStateNormal];
    }
}




@end
