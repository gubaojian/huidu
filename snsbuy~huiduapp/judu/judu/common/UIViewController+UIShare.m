//
//  UIViewController+UIShare.m
//  mapyou
//
//  Created by baobao on 15/11/18.
//  Copyright © 2015年 baobao. All rights reserved.
//

#import "UIViewController+UIShare.h"
#include <objc/runtime.h>

@implementation UIViewController (UIShare)


/**发送短信消息  @content  短信内容 */
-(void) mail:(NSString*) title content:(NSString*)content to:(NSString*) address{
    Class mailClass = (NSClassFromString(@"MFMailComposeViewController"));
    if (mailClass != nil) {
        if ([mailClass canSendMail]) {
            MFMailComposeViewController* mailComposeViewPicker = [[MFMailComposeViewController alloc] init];
            mailComposeViewPicker.mailComposeDelegate = self;
            [mailComposeViewPicker setSubject:title];
            if (content != nil) {
                [mailComposeViewPicker setMessageBody:content isHTML:NO];
            };
            [mailComposeViewPicker setToRecipients:@[address]];
            [self presentViewController:mailComposeViewPicker animated:YES completion:nil];
        }
        else {
            NSString* message = @"Device not configured to send mail.";
           [self  alertTips: message];
        }
    }
    else {
        NSString* message =@"Device not supported to send mail.";
        [self  alertTips: message];
    }
}

- (void)mailComposeController:(MFMailComposeViewController*)controller
          didFinishWithResult:(MFMailComposeResult)result error:(NSError*)error{
    [controller  dismissViewControllerAnimated:YES completion:nil];
}


/**发送短信消息  @content  短信内容 */
-(void) sendSms:(NSString*) content{
    Class messageClass = (NSClassFromString(@"MFMessageComposeViewController"));
    if (messageClass != nil) {
        // Check whether the current device is configured for sending SMS messages
        if ([messageClass canSendText]) {
            //[self displaySMSComposerSheet];
           MFMessageComposeViewController* messageComposeViewPicker = [[MFMessageComposeViewController alloc] init];
            messageComposeViewPicker.body = content;
            messageComposeViewPicker.messageComposeDelegate = self;
            [self presentViewController:messageComposeViewPicker animated:YES completion:nil];
        }else {
            [self  alertTips:NSLocalizedString(@"CommonMessageDeviceNotSupportSms", @"LocalizedString")];
        }
    }
    else {
        [self  alertTips: NSLocalizedString(@"CommonMessageDeviceNotSupportSms", @"LocalizedString")];
    }
    
    
}

/**   处理发送短信的委托对象实现  */
- (void)messageComposeViewController:(MFMessageComposeViewController *)controller didFinishWithResult:(MessageComposeResult)result{
    __weak UIViewController* weakSelf = self;
    [controller dismissViewControllerAnimated:YES completion:^{
        if (result == MessageComposeResultSent) {
            if ([weakSelf respondsToSelector:@selector(sendSmsSuccess)]) {
                [weakSelf performSelector:@selector(sendSmsSuccess)];
            }
        }else{
            if ([weakSelf respondsToSelector:@selector(sendSmsFailed)]) {
                [weakSelf performSelector:@selector(sendSmsFailed)];
            }
        }
    }];
}


-(void) shareNoSms:(NSString*) message sourceView:(UIView*)sourceView complete:(UIActivityViewControllerCompletionWithItemsHandler)completionWithItemsHandler{
    if (sourceView == nil) {
        [NSException raise:@"sourceView cann't be null " format:@"sourceView cann't be null ", nil];
    }
    NSMutableArray* items = [[NSMutableArray alloc] init];
    [items addObject:message];
    UIActivityViewController* activityViewController = [[UIActivityViewController alloc] initWithActivityItems:items applicationActivities:nil];
    __weak UIActivityViewController* weakShareController = activityViewController;
    activityViewController.excludedActivityTypes = @[UIActivityTypeMessage, UIActivityTypePrint, UIActivityTypeAssignToContact,UIActivityTypeSaveToCameraRoll];
    weakShareController.completionWithItemsHandler = ^(NSString * activityType, BOOL completed, NSArray *returnedItems, NSError *activityError){
        if (completionWithItemsHandler != nil) {
            completionWithItemsHandler(activityType, completed, returnedItems, activityError);
        }
        if (weakShareController) {
            weakShareController.completionWithItemsHandler = nil;
        }
    };
    if (UI_USER_INTERFACE_IDIOM() == UIUserInterfaceIdiomPhone) {
        [self presentViewController:activityViewController animated:YES completion:nil];
    }else{
        activityViewController.modalPresentationStyle = UIModalPresentationPopover;
        UIPopoverPresentationController *presentationController = activityViewController.popoverPresentationController;
        presentationController.sourceView = sourceView;
        presentationController.sourceRect = [sourceView bounds];
        presentationController.permittedArrowDirections = UIPopoverArrowDirectionUp |UIPopoverArrowDirectionDown;
        [self presentViewController:activityViewController  animated:YES completion:nil];
        
    }
}


-(void) share:(NSString*) message sourceView:(UIView*)sourceView  complete:(UIActivityViewControllerCompletionWithItemsHandler)completionWithItemsHandler;{
    if (sourceView == nil) {
        [NSException raise:@"sourceView cann't be null " format:@"sourceView cann't be null ", nil];
    }
    NSMutableArray* items = [[NSMutableArray alloc] init];
    [items addObject:message];
    UIActivityViewController* activityViewController = [[UIActivityViewController alloc] initWithActivityItems:items applicationActivities:nil];
   __weak UIActivityViewController* weakShareController = activityViewController;
   activityViewController.excludedActivityTypes = @[UIActivityTypePrint, UIActivityTypeAssignToContact,UIActivityTypeSaveToCameraRoll];
    weakShareController.completionWithItemsHandler = ^(NSString * activityType, BOOL completed, NSArray *returnedItems, NSError *activityError){
        if (completionWithItemsHandler != nil) {
            completionWithItemsHandler(activityType, completed, returnedItems, activityError);
        }
        if (weakShareController) {
           weakShareController.completionWithItemsHandler = nil;
        }
    };
    if (UI_USER_INTERFACE_IDIOM() == UIUserInterfaceIdiomPhone) {
        [self presentViewController:activityViewController animated:YES completion:nil];
    }else{
        activityViewController.modalPresentationStyle = UIModalPresentationPopover;
        UIPopoverPresentationController *presentationController = activityViewController.popoverPresentationController;
        presentationController.sourceView = sourceView;
        presentationController.sourceRect = [sourceView bounds];
        presentationController.permittedArrowDirections = UIPopoverArrowDirectionUp |UIPopoverArrowDirectionDown;
        [self presentViewController:activityViewController  animated:YES completion:nil];
      
    }
}


@end
