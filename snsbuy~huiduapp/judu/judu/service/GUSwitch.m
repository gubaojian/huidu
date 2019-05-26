//
//  GUCDNSwitch.m
//  huidu
//
//  Created by lurina on 14-5-14.
//  Copyright (c) 2014年 baobao. All rights reserved.
//

#import "GUSwitch.h"

@implementation GUSwitch

+(BOOL) isUseCDN{
    NSString*  cdn =  [[NSUserDefaults standardUserDefaults] objectForKey:@"article_service_cdn"];
    if (cdn == nil) {
        return YES;
    }
    return [@"YES" isEqualToString:cdn];
}


+(void) switchUseCDN{
    [[NSUserDefaults standardUserDefaults] setObject:@"YES" forKey:@"article_service_cdn"];
}

+(void) switchUseServer{
    [[NSUserDefaults standardUserDefaults] setObject:@"NO" forKey:@"article_service_cdn"];
}



@end
