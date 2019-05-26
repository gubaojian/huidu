//
//  GUAlertDialog.h
//  powerfee
//
//  Created by lurina on 12-11-26.
//  Copyright (c) 2012年 baobao. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface GUAlertDialog : NSObject

@property(strong, nonatomic)  UIAlertView* processAlerIndicatorView;
@property(strong, nonatomic)  UIActivityIndicatorView* processAlerIndicator;



/**显示提示信息*/
-(void) showMessage: (NSString *) message;


/**显示精度条提示信息*/
-(void) showAlterIndicatorWithMessage:(NSString*) message;

/**隐藏提示信息*/
-(void) dismissAlterIndicatorWithMessage;

@end
