//
//  GUViewUtils.h
//  huidu
//
//  Created by lurina on 13-10-30.
//  Copyright (c) 2013年 baobao. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "FontAwesomeKit.h"

@interface GUViewUtils : NSObject


+(CGFloat)viewStartOffset;

+(UIImage*) tabbarNormalImage:(NSString*)code;

+(UIImage*) tabbarSelectImage:(NSString*)code;

+(UIImage*) cell25Image:(NSString*)code;

+(UIImage*) cell22Image:(NSString*)code;

+(UIImage*) cell15Image:(NSString*)code;

+(UIImage*) cell20Image:(NSString*)code;


+(float) screenWidth;


@end
