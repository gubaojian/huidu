//
//  GULog.h
//  mapyou
//
//  Created by lurina on 12-10-21.
//  Copyright (c) 2012年 baobao. All rights reserved.
//

#import "GUConfig.h"

//日志级别定义
#ifndef LOG_LEVEL_ALL
#define LOG_LEVEL_ALL  0
#endif

#ifndef LOG_LEVEL_TRACE
#define LOG_LEVEL_TRACE 3
#endif

#ifndef LOG_LEVEL_DEBUG
#define LOG_LEVEL_DEBUG 5
#endif

#ifndef LOG_LEVEL_INFO
#define LOG_LEVEL_INFO  10
#endif

#ifndef LOG_LEVEL_WARN
#define LOG_LEVEL_WARN  15
#endif

#ifndef LOG_LEVEL_ERROR
#define LOG_LEVEL_ERROR  20
#endif

#ifndef LOG_LEVEL_FATAL
#define LOG_LEVEL_FATAL  25
#endif

#ifndef LOG_LEVEL_OFF
#define LOG_LEVEL_OFF  30
#endif



FOUNDATION_EXPORT void GULog(int level, NSString* format, ...);






