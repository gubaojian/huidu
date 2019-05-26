//
//  GUStringUtils.m
//  hscode
//
//  Created by lurina on 12-12-25.
//  Copyright (c) 2012年 baobao. All rights reserved.
//

#import "GUStringUtils.h"

@implementation GUStringUtils


+ (BOOL)isEmpty:(NSString *)string;{
    // Note that [string length] == 0 can be false when [string isEqualToString:@""] is true, because these are Unicode strings.
    
    if (((NSNull *) string == [NSNull null]) || (string == nil) ) {
        return YES;
    }
    string = [string stringByTrimmingCharactersInSet: [NSCharacterSet whitespaceAndNewlineCharacterSet]];
    
    if ([string isEqualToString:@""]) {
        return YES;
    }
    
    return NO;
}

@end
