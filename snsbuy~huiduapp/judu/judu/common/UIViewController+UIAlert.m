//
//  UIViewController+UIAlert.m
//  mapyou
//
//  Created by baobao on 15/11/14.
//  Copyright © 2015年 baobao. All rights reserved.
//

#import "UIViewController+UIAlert.h"

@implementation UIViewController (UIAlert)

-(void)alertTips:(NSString*)tips{
    [self alertTips:tips ok: NSLocalizedString(@"CommonButtonTextOk", @"确定") handler:nil];
}


-(void)alertTips:(NSString*)tips handler:(void (^)(UIAlertAction *action))handler{
 [self alertTips:tips ok: NSLocalizedString(@"CommonButtonTextOk", @"确定") handler:handler];
}


-(void)alertTips:(NSString*)tips title:(NSString*)title handler:(void (^)(UIAlertAction *action))handler{
    UIAlertController* alertController =  [UIAlertController alertControllerWithTitle:title message:tips  preferredStyle:UIAlertControllerStyleAlert];
    [alertController addAction:[UIAlertAction actionWithTitle:NSLocalizedString(@"CommonButtonTextOk", @"确定") style:UIAlertActionStyleDefault handler:handler]];
    [self presentViewController:alertController animated:YES completion:nil];

}

-(void)alertTips:(NSString*)tips ok:(NSString*) ok handler:(void (^)(UIAlertAction *action))handler{
    UIAlertController* alertController =  [UIAlertController alertControllerWithTitle:NSLocalizedString(@"CommonButtonTitleTips", @"提示") message:tips  preferredStyle:UIAlertControllerStyleAlert];
    
    [alertController addAction:[UIAlertAction actionWithTitle:ok style:UIAlertActionStyleDefault handler:handler]];
    [self presentViewController:alertController animated:YES completion:nil];

}


-(void)confirmTips:(NSString*)tips title:(NSString*)title ok:(NSString*)ok handler:(void (^)(UIAlertAction *action))handler cancel:(void (^)(UIAlertAction *action)) cancel{
    if(title == nil){
        title = NSLocalizedString(@"CommonButtonTitleTips", @"提示");
    }
    if (ok == nil) {
        ok = NSLocalizedString(@"CommonButtonTextOk", @"确定");
    }
    UIAlertController* alertController =  [UIAlertController alertControllerWithTitle:title message:tips  preferredStyle:UIAlertControllerStyleAlert];
    [alertController addAction:[UIAlertAction actionWithTitle:NSLocalizedString(@"CommonButtonTextCancel", @"取消") style:UIAlertActionStyleDefault handler:cancel]];
    [alertController addAction:[UIAlertAction actionWithTitle:ok style:UIAlertActionStyleDefault handler:handler]];
    [self presentViewController:alertController animated:YES completion:nil];
}


-(void)confirmTips:(NSString*)tips ok:(NSString*)ok handler:(void (^)(UIAlertAction *action))handler cancel:(void (^)(UIAlertAction *action)) cancel{
    if (ok == nil) {
        ok = NSLocalizedString(@"CommonButtonTextOk", @"确定");
    }
    UIAlertController* alertController =  [UIAlertController alertControllerWithTitle:nil message:tips  preferredStyle:UIAlertControllerStyleAlert];
    [alertController addAction:[UIAlertAction actionWithTitle:NSLocalizedString(@"CommonButtonTextCancel", @"取消") style:UIAlertActionStyleDefault handler:cancel]];
    [alertController addAction:[UIAlertAction actionWithTitle:ok style:UIAlertActionStyleDefault handler:handler]];
    [self presentViewController:alertController animated:YES completion:nil];
}





@end
