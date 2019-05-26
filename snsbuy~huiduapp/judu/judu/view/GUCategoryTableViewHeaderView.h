//
//  GUCategoryTableViewHeaderView.h
//  judu
//
//  Created by lurina on 13-8-2.
//  Copyright (c) 2013年 baobao. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface GUCategoryTableViewHeaderView : UIView{
     @private UILabel*  _titleLabel;
     @private UIImageView* _imageLabel;
}


@property(strong, nonatomic, readonly)UILabel*  titleLabel;

@property(strong, nonatomic, readonly)UIImageView* imageLabel;

@end
