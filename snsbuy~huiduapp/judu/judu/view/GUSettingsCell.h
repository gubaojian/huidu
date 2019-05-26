//
//  GUSettingsCell.h
//  huidu
//
//  Created by lurina on 14-4-8.
//  Copyright (c) 2014年 baobao. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface GUSettingsCell : UITableViewCell{
     @private UIImageView* _leftImageView;
     @private UILabel*  _leftTitleView;
}



@property(strong, nonatomic, readonly) UILabel* leftTitleView;


@property(strong, nonatomic, readonly) UIImageView* leftImageView;

@end
