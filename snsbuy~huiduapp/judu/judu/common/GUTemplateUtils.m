//
//  GUTemplateUtils.m
//  asm
//
//  Created by lurina on 13-1-20.
//  Copyright (c) 2013年 baobao. All rights reserved.
//

#import "GUTemplateUtils.h"
#import "GUConfig.h"

@implementation GUTemplateUtils
static __strong  NSMutableDictionary* templateCache;


+(NSString *) renderHtmlFile:(NSString*) fileName withContext:(id) context{
    if (templateCache == nil) {
        templateCache = [[NSMutableDictionary alloc] initWithCapacity:4];
         GULog(LOG_LEVEL_DEBUG, @"TemplateCache inited");
    }
    
    GRMustacheTemplate* template = [templateCache objectForKey:fileName];
    
    if (template == nil) {
        NSURL* url = [[NSBundle mainBundle] URLForResource:fileName withExtension:@"html"];
        GULog(LOG_LEVEL_DEBUG, @"load template resource url : %@", [url description]);
        NSError* error = nil;
        NSString* content = [NSString stringWithContentsOfURL:url encoding:NSUTF8StringEncoding error: &error];
        if (content == nil) {
            GULog(LOG_LEVEL_DEBUG, @"load template content error: %@", [error description]);
        }else{
            GULog(LOG_LEVEL_DEBUG, @"load template content: %@", content);
            template = [GRMustacheTemplate templateFromString:content error:&error];
            if (template == nil) {
                GULog(LOG_LEVEL_DEBUG, @"GRMustacheTemplate build template error: %@", [error description]);
            }
        }
        
        if (template != nil) {
            [templateCache setObject:template forKey:fileName];
        }
    }
    
    if (template != nil) {
        NSError* error = nil;
        NSString* renderedHtml = [template renderObject:context error:&error];
        if (renderedHtml == nil) {
            GULog(LOG_LEVEL_ERROR, @"Render html template error: %@", [error description]);
        }else{
            GULog(LOG_LEVEL_ERROR, @"Rendered html content %@", renderedHtml);
        }
        return renderedHtml;
    }
    
    return nil;
}


+(NSString*) renderArticle:(GUArticle*) article{
    if (article == nil) {
        return nil;
    }
   
    NSMutableDictionary* context = nil;
    if ([GUSystemUtils isIpad]) {
        context = [GUTemplateUtils readerConfigForIpad];
    }else{
        context = [GUTemplateUtils readerConfigForIphone];
    }
    [context setObject:article forKey:@"article"];
    int color = [[article id] intValue]%7;
    switch (color) {
        case 0:
            [context setObject:@"#1989e0" forKey:@"borderBottomColor"];
            break;
        case 1:
            [context setObject:@"#e06827" forKey:@"borderBottomColor"];
            break;
        case 2:
            [context setObject:@"#d7b319" forKey:@"borderBottomColor"];
            break;
        case 3:
            [context setObject:@"#dedfde" forKey:@"borderBottomColor"];
            break;
        case 4:
            [context setObject:@"#339933" forKey:@"borderBottomColor"];
            break;
        case 5:
            [context setObject:@"#F09609" forKey:@"borderBottomColor"];
            break;
        case 6:
            [context setObject:@"#8CBF26" forKey:@"borderBottomColor"];
            break;
        default:
            [context setObject:@"#00ABA9" forKey:@"borderBottomColor"];
            break;
    }
    
    int fontSize = [GUTemplateUtils fontSize];
    if (fontSize == FONT_SIZE_SMALL) {
        [context setObject:@"14px" forKey:@"contentFontSize"];
    }else if (fontSize == FONT_SIZE_LARGE){
        [context setObject:@"18px" forKey:@"contentFontSize"];
    }else if (fontSize == FONT_SIZE_LARGEST){
        [context setObject:@"20px" forKey:@"contentFontSize"];
    }else{
        [context setObject:@"16px" forKey:@"contentFontSize"];
    }
    
    
    
    
    
    
    
    NSError* error = NULL;
    return [[GUTemplateUtils shareTemplate] renderObject:context error:&error];
}





+(NSMutableDictionary*) readerConfigForIpad{
    NSMutableDictionary* params = [[NSMutableDictionary alloc] initWithCapacity:4];
    [params setObject:@"200px" forKey:@"minWidth"];
    if (((int)[[UIScreen mainScreen] bounds].size.width) == 1024
        && ((int)[[UIScreen mainScreen] bounds].size.height) == 1366) { // ipad pro
        [params setObject:@"900px" forKey:@"maxWidth"];
    }else{
        [params setObject:@"640px" forKey:@"maxWidth"];
    }
    [params setObject:@"50px" forKey:@"paddingTop"];
    [params setObject:@"#e06827" forKey:@"borderBottomColor"];
    [params setObject:@"22px" forKey:@"titleFontSize"];
    [params setObject:@"16px" forKey:@"contentFontSize"];
    return params;
}

+(NSMutableDictionary*) readerConfigForIphone{
    NSMutableDictionary* params = [[NSMutableDictionary alloc] initWithCapacity:4];
    [params setObject:@"200px" forKey:@"minWidth"];
    [params setObject:@"640px" forKey:@"maxWidth"];
    [params setObject:@"10px" forKey:@"paddingTop"];
    [params setObject:@"#e06827" forKey:@"borderBottomColor"];
    [params setObject:@"22px" forKey:@"titleFontSize"];
    
    
    [params setObject:@"16px" forKey:@"contentFontSize"];
    return params;
}



+(void) setFontSize:(int)size{
    NSUserDefaults* defaults = [NSUserDefaults standardUserDefaults];
    [defaults setObject:[NSNumber numberWithInt:size] forKey:@"fontSize"];
}

+(int)fontSize{
    NSUserDefaults* defaults = [NSUserDefaults standardUserDefaults];
    NSNumber* numbers = [defaults objectForKey:@"fontSize"];
    if (numbers == nil) {
        return FONT_SIZE_NORMAL;
    }
    return [numbers intValue];
}




+(GRMustacheTemplate*) shareTemplate{
    static dispatch_once_t once;
    static GRMustacheTemplate* sharedTemplate;
    dispatch_once(&once, ^{
        NSURL* url = [[NSBundle mainBundle] URLForResource:@"readerDetail" withExtension:@"html"];
        NSError* error = nil;
        sharedTemplate =  [GRMustacheTemplate templateFromContentsOfURL:url error:&error];
        if (error) {
             NSLog(@" shareTemplate error :%@", error);
        }
    });
    return sharedTemplate;
}

@end
