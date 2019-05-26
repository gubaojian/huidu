//
//  GUFavoriteViewCell.h
//  judu
//
//  Created by lurina on 13-7-28.
//  Copyright (c) 2013年 baobao. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface GUCategoryViewCell : UITableViewCell{
     @private UILabel* _categoryTitleLabel;
     @private UIButton* _favoriteButton;
}

@property(strong, nonatomic)UILabel* categoryTitleLabel;

@property(strong, nonatomic)UIButton* favoriteButton;


-(void) setFavoriteImage:(BOOL)favorite;

@end
