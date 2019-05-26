//
//  GULog.m
//  mapyou
//
//  Created by lurina on 12-11-9.
//  Copyright (c) 2012年 baobao. All rights reserved.
//

#import "GULog.h"


void GULog(int level, NSString* format, ...){
    if(level >= LOG_LEVEL){
        va_list args;
        va_start(args,format);
        NSLogv(format, args);
        va_end(args);
    }
}
