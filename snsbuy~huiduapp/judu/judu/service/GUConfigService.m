//
//  GUConfigService.m
//  judu
//
//  Created by lurina on 13-7-30.
//  Copyright (c) 2013年 baobao. All rights reserved.
//

#import "GUConfigService.h"

@implementation GUConfigService

+(BOOL) isShowArticleList{
    NSUserDefaults* defaults = [NSUserDefaults standardUserDefaults];
    NSString* show =  [defaults objectForKey:@"ShowArticleList"];
    if (show == nil) {
        return NO;
    }
    if ([show isEqualToString: @"YES"]) {
        return YES;
    }
    return NO;
}

+(void) setShowArticleList:(BOOL) articleList{
    NSUserDefaults* defaults = [NSUserDefaults standardUserDefaults];
    NSString* ShowArticleList = @"ShowArticleList";
    if (articleList) {
        [defaults setObject:@"YES" forKey:ShowArticleList];
    }else{
        [defaults setObject:@"NO" forKey:ShowArticleList];
    }
}

+(BOOL) isAutoDownloadArticleOn{
    NSUserDefaults* defaults = [NSUserDefaults standardUserDefaults];
    NSString* on = [defaults objectForKey:@"AutoDownloadArticle"];
    if(on == nil){
        return YES;
    }
    if ([on isEqualToString:@"YES"]) {
        return YES;
    }
    return NO;
}

+(void) turnOnAutoDownloadArticle{
    NSUserDefaults* defaults = [NSUserDefaults standardUserDefaults];
    [defaults setObject:@"YES" forKey:@"AutoDownloadArticle"];
}

+(void) turnOffAutoDownloadArticle{
    NSUserDefaults* defaults = [NSUserDefaults standardUserDefaults];
    [defaults setObject:@"NO" forKey:@"AutoDownloadArticle"];
}




@end
