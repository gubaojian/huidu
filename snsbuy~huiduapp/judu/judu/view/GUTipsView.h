//
//  GUTipsView.h
//  judu
//
//  Created by lurina on 13-8-15.
//  Copyright (c) 2013年 baobao. All rights reserved.
//

#import <UIKit/UIKit.h>
#import <QuartzCore/QuartzCore.h>
#import "UIColor+Expanded.h"

@interface GUTipsView : UIView{

   @private UILabel* _tips;
   @private UIButton* _actionButton;
}

@property(strong, nonatomic) UILabel* tips;

@property(strong, nonatomic) UIButton* actionButton;

-(void) showInView:(UIView*) view;

-(void) removeFromView;

@end
