//
//  GUBaseViewController.h
//  judu
//
//  Created by lurina on 13-7-28.
//  Copyright (c) 2013年 baobao. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "GUTitleBar.h"
#import "GUStatusView.h"
#import "GUSystemUtils.h"
#import "GUViewUtils.h"
#import "FontAwesomeKit.h"

@interface GUBaseViewController : UIViewController{
     @private GUStatusView* _statusView;
}

- (id)initWithQuery:(NSDictionary*) query;

-(GUStatusView*) statusView;

-(void)reloadForError;

-(void) backAction;

/**子类需要重写字方法清理资源，并调用[super clean]*/
-(void) clean;



@end
