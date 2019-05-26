//
//  WaitingView.h
//  
//
//  Created by Jianhong Yang on 12-1-7.
//  Copyright (c) 2012年 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "GUConfig.h"


@interface GUWaitingView : UIView {
@private
    //
    UIView *_viewBackground;
    UIActivityIndicatorView *_viewActivityIndicator;
    UILabel *_labelText;
}

- (void)setWaitingText:(NSString *)text;

@end
