//
//  GUAlertDialog.m
//  powerfee
//
//  Created by lurina on 12-11-26.
//  Copyright (c) 2012年 baobao. All rights reserved.
//

#import "GUAlertDialog.h"

@implementation GUAlertDialog
@synthesize  processAlerIndicatorView;
@synthesize processAlerIndicator;

-(void) showMessage: (NSString *) message{
    UIAlertView *dialog = [[UIAlertView alloc] initWithTitle:
                           NSLocalizedString(@"CommonButtonTextTips", @"LocalizedString")
                                                     message:message delegate:nil
                                           cancelButtonTitle:
                           NSLocalizedString(@"CommonButtonTextOk", @"LocalizedString")
                                           otherButtonTitles:nil] ;
    [dialog show];
}


-(void) showAlterIndicatorWithMessage:(NSString*) message{
    if(processAlerIndicatorView != nil){
        [processAlerIndicatorView setHidden:YES];
        [processAlerIndicatorView dismissWithClickedButtonIndex:0 animated:NO];
        processAlerIndicatorView = nil;
    }
    if(processAlerIndicator != nil){
        [processAlerIndicator stopAnimating];
        processAlerIndicator = nil;
    }
    
    processAlerIndicatorView = [[UIAlertView alloc] initWithTitle:message  message:nil delegate:nil cancelButtonTitle:nil otherButtonTitles:nil, nil];
    
    [processAlerIndicatorView show];
    
    processAlerIndicator = [[UIActivityIndicatorView alloc] initWithActivityIndicatorStyle:UIActivityIndicatorViewStyleWhiteLarge];
    
    CGRect bounds = [processAlerIndicatorView bounds];
    
    processAlerIndicator.center = CGPointMake(bounds.origin.x + bounds.size.width/2,
                                              bounds.origin.y + bounds.size.height*2/3);
    [processAlerIndicator startAnimating];
    //[processAlerIndicator setFrame: CGRectMake(30, 50, 225, 30)];
    
    [processAlerIndicatorView resignFirstResponder]; //关闭键盘  wait_fences: failed to receive reply
    [processAlerIndicatorView addSubview: processAlerIndicator];
    
    
}

-(void) dismissAlterIndicatorWithMessage{
    if(processAlerIndicatorView == nil || processAlerIndicator == nil){
        return;
    }
    [processAlerIndicator stopAnimating];
    [processAlerIndicatorView resignFirstResponder];
    [processAlerIndicatorView   dismissWithClickedButtonIndex:0 animated:YES];  //NO wait_fences: failed to receive reply
    processAlerIndicator = nil;
    processAlerIndicatorView = nil;
    
}


@end
