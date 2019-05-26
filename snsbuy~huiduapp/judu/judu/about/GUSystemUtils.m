//
//  GUSystemUtils.m
//  powerfee
//
//  Created by lurina on 12-11-26.
//  Copyright (c) 2012年 baobao. All rights reserved.
//

#import "GUSystemUtils.h"
#import <sys/utsname.h>

@implementation GUSystemUtils


+(BOOL) isZhHans{
    NSString* zhHans = @"zh-hans";
    NSString* preferLanguage = [[[NSLocale preferredLanguages] objectAtIndex:0] lowercaseString];
    return [zhHans isEqualToString: preferLanguage];
}

+(BOOL) isIphone{
    return UI_USER_INTERFACE_IDIOM() == UIUserInterfaceIdiomPhone;
}


+(BOOL) isIphone4inch{
   return [GUSystemUtils isIphone] && ([[UIScreen mainScreen] bounds].size.height - 568  == 0);
}



+(BOOL) isIpad{
    return  UI_USER_INTERFACE_IDIOM() == UIUserInterfaceIdiomPad;
}


+(BOOL) isIos5{
    NSArray *versionCompatibility = [[UIDevice currentDevice].systemVersion componentsSeparatedByString:@"."];
    if ([[versionCompatibility objectAtIndex:0] integerValue] == 5) {
        return YES;
    }
    return  NO;
}

+(BOOL) isIos6{
    return  [[[UIDevice currentDevice] systemVersion] floatValue] >= 6.0;
}

+(BOOL) isIos7{
   return  [[[UIDevice currentDevice] systemVersion] floatValue] >= 7.0;
}

+(NSString* ) localIp{
    NSString *address = @"error";
    struct ifaddrs *interfaces = NULL;
    struct ifaddrs *temp_addr = NULL;
    int success = 0;
    // retrieve the current interfaces - returns 0 on success
    success = getifaddrs(&interfaces);
    if (success == 0) {
        // Loop through linked list of interfaces
        temp_addr = interfaces;
        while(temp_addr != NULL) {
            if(temp_addr->ifa_addr->sa_family == AF_INET) {
                // Check if interface is en0 which is the wifi connection on the iPhone
                if([[NSString stringWithUTF8String:temp_addr->ifa_name] isEqualToString:@"en0"]) {
                    // Get NSString from C String
                    address = [NSString stringWithUTF8String:inet_ntoa(((struct sockaddr_in *)temp_addr->ifa_addr)->sin_addr)];
                }
            }
            temp_addr = temp_addr->ifa_next;
        }
    }
    // Free memory
    freeifaddrs(interfaces);
    return address;
}

@end
