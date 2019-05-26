//
//  UIViewController+UIShare.h
//  mapyou
//
//  Created by baobao on 15/11/18.
//  Copyright © 2015年 baobao. All rights reserved.
//

#import <UIKit/UIKit.h>
#import <MessageUI/MessageUI.h>
#import "UIViewController+UIAlert.h"



@interface UIViewController (UIShare)<MFMessageComposeViewControllerDelegate,MFMailComposeViewControllerDelegate>



-(void) mail:(NSString*) title content:(NSString*)content to:(NSString*) address;


-(void) sendSms:(NSString*) content;

-(void) share:(NSString*) message sourceView:(UIView*)sourceView complete:(UIActivityViewControllerCompletionWithItemsHandler)completionWithItemsHandler;

-(void) shareNoSms:(NSString*) message sourceView:(UIView*)sourceView complete:(UIActivityViewControllerCompletionWithItemsHandler)completionWithItemsHandler;


@end
