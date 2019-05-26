//
//  GUTemplateUtils.h
//  asm
//
//  Created by lurina on 13-1-20.
//  Copyright (c) 2013年 baobao. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "GUConfig.h"
#import "GULog.h"
#import "GRMustache.h"
#import "GUArticle.h"
#import "GUFormateUtils.h"
#import "GUSystemUtils.h"

#define FONT_SIZE_SMALL      0
#define FONT_SIZE_NORMAL     1
#define FONT_SIZE_LARGE      2
#define FONT_SIZE_LARGEST    3

@interface GUTemplateUtils : NSObject


+(NSString *) renderHtmlFile:(NSString*) fileName withContext:(id) context;


+(NSString*) renderArticle:(GUArticle*) article;



+(void) setFontSize:(int)size;

+(int)fontSize;



@end
