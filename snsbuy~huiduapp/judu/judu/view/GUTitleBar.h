//
//  GUTitleBar.h
//  judu
//
//  Created by lurina on 13-7-28.
//  Copyright (c) 2013年 baobao. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface GUTitleBar : UIView{
    @private UILabel* _titleLabel;
    @private UIButton* _rightButton;

}


@property(strong, nonatomic, readonly) UILabel* titleLabel;


@property(strong, nonatomic, readonly) UIButton* rightButton;

-(UIButton*) rightButtonWithImage:(UIImage*)image;

@end
