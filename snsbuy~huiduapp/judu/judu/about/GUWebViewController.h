//
//  GUWebViewController.h
//  powerfee
//
//  Created by lurina on 12-11-25.
//  Copyright (c) 2012年 baobao. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "GULog.h"

@interface GUWebViewController : UIViewController<UIWebViewDelegate>{
          @private UIWebView*  _webView;
           @private NSString*  _htmlResourceName;
   
}
@property (strong, nonatomic) UIWebView *webView;


@property(strong, nonatomic) NSString* htmlResourceName;

@end
