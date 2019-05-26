//
//  GUMapperUtils.m
//  asm
//
//  Created by lurina on 13-1-19.
//  Copyright (c) 2013年 baobao. All rights reserved.
//

#import "GUMapperUtils.h"

@implementation GUMapperUtils


+(id) mappObject:(id)target withResultSet: (FMResultSet *)result{
    int count = [result columnCount];
    for (int i=0; i <count;  i++) {
        NSString* key = [result columnNameForIndex:i];
        id data = [result objectForColumnIndex:i];
        if (data == nil || data == [NSNull null]) {
            continue;
        }
        [target setValue:data forKey:key];
    }
    return target;
}

@end
