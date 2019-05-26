//
//  UIViewController+UIAlert.h
//  mapyou
//
//  Created by baobao on 15/11/14.
//  Copyright © 2015年 baobao. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface UIViewController (UIAlert)

-(void)alertTips:(NSString*)tips;

-(void)alertTips:(NSString*)tips handler:(void (^)(UIAlertAction *action))handler;

-(void)alertTips:(NSString*)tips ok:(NSString*)ok handler:(void (^)(UIAlertAction *action))handler;


-(void)alertTips:(NSString*)tips title:(NSString*)title handler:(void (^)(UIAlertAction *action))handler;

-(void)confirmTips:(NSString*)tips title:(NSString*)title ok:(NSString*)ok handler:(void (^)(UIAlertAction *action))handler cancel:(void (^)(UIAlertAction *action)) cancel;


-(void)confirmTips:(NSString*)tips ok:(NSString*)ok handler:(void (^)(UIAlertAction *action))handler cancel:(void (^)(UIAlertAction *action)) cancel;



@end
