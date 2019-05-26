//
//  GUProcessView.h
//  judu
//
//  Created by lurina on 13-7-29.
//  Copyright (c) 2013年 baobao. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "MBProgressHUD.h"

// https://github.com/samsoffes/sspulltorefresh

@interface GUStatusView : UIView{
   @private MBProgressHUD* _processHud;
   
   @private UILabel* _errorTips;
    
   @private UIButton* _reloadButton;
    
    
   @private void(^_reloadBlock) (void) ;
    

}


-(void) showActivityInView:(UIView*) view forFrame:(CGRect)frame;

-(void) showErrorInView:(UIView*) view forFrame:(CGRect)frame;


-(void) removeViewInfo;

@property(nonatomic, strong) void(^reloadBlock) (void);




//-(void) showErrorInView:(UIView*) view forFrame:(CGRect)frame;
//-(void) removeErrorFromView:(UIView*) view;



@end
