//
//  GUgggg.m
//  asm
//
//  Created by lurina on 13-1-19.
//  Copyright (c) 2013年 baobao. All rights reserved.
//

#import "GUNSStringExtension.h"

@implementation  NSString (GUNSStringExtension)

-(int) countOfSubstring:(NSString *) substring{
    int count = 0;
    NSRange range=  [self rangeOfString:substring];
    while (range.location != NSNotFound) {
        count++;
        range.location = range.location + range.length;
        range =  [self rangeOfString:substring options:NSCaseInsensitiveSearch range:range];
    }
    return count;
}

@end
