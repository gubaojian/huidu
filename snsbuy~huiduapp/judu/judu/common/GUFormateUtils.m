//
//  GUFormateUtils.m
//  judu
//
//  Created by lurina on 13-7-29.
//  Copyright (c) 2013年 baobao. All rights reserved.
//

#import "GUFormateUtils.h"

@implementation GUFormateUtils

+(NSString*) formateDate:(NSDate*) date{
    if (date == nil) {
        return nil;
    }
    NSCalendar *currentCalendar = [NSCalendar currentCalendar];
    NSDate* now = [NSDate dateWithTimeIntervalSinceNow:0];
    NSDateComponents* nowComponents =  [currentCalendar components:(NSYearCalendarUnit|NSMonthCalendarUnit|NSDayCalendarUnit)fromDate:now];
    NSDateComponents* dateComponents =  [currentCalendar components:(NSYearCalendarUnit|NSMonthCalendarUnit|NSDayCalendarUnit)fromDate:date];
    
    
    if ([nowComponents year] == [dateComponents year]) {
        if ([nowComponents month] == [dateComponents month]) {
            int day = [nowComponents day] - [dateComponents day];
            if (day == 0) {
                return [[GUFormateUtils todayFormatter] stringFromDate:date];
            }else if(day == 1){
                return [[GUFormateUtils yesterdayFormatter] stringFromDate:date];
            }else if(day == 2){
                return [[GUFormateUtils beforeYesterdayFormatter] stringFromDate:date];
            }
        }
    }
    return [[GUFormateUtils monthDayFormatter] stringFromDate:date];
}

+(NSDateFormatter*) monthDayFormatter{
    static NSDateFormatter *dateFormatter = nil;
	static dispatch_once_t onceToken;
	dispatch_once(&onceToken, ^{
		dateFormatter = [[NSDateFormatter alloc] init];
        dateFormatter.formatterBehavior =  NSDateFormatterBehaviorDefault;
        dateFormatter.dateStyle = kCFDateFormatterNoStyle;
        dateFormatter.timeStyle = NSDateFormatterShortStyle;
        dateFormatter.dateFormat = @"yyyy-MM-dd HH:mm:ss";
	});
    return dateFormatter;

}


+(NSDateFormatter*) todayFormatter{
    static NSDateFormatter *dateFormatter = nil;
	static dispatch_once_t onceToken;
	dispatch_once(&onceToken, ^{
		dateFormatter = [[NSDateFormatter alloc] init];
        dateFormatter.formatterBehavior =  NSDateFormatterBehaviorDefault;
        dateFormatter.dateStyle = kCFDateFormatterNoStyle;
        dateFormatter.timeStyle = NSDateFormatterShortStyle;
        dateFormatter.dateFormat = @"今天 HH:mm:ss";
	});
    return dateFormatter;
}

+(NSDateFormatter*) yesterdayFormatter{
    static NSDateFormatter *dateFormatter = nil;
	static dispatch_once_t onceToken;
	dispatch_once(&onceToken, ^{
		dateFormatter = [[NSDateFormatter alloc] init];
        dateFormatter.formatterBehavior =  NSDateFormatterBehaviorDefault;
        dateFormatter.dateStyle = kCFDateFormatterNoStyle;
        dateFormatter.timeStyle = NSDateFormatterShortStyle;
        dateFormatter.dateFormat = @"昨天 HH:mm:ss";
	});
    return dateFormatter;
}

+(NSDateFormatter*) beforeYesterdayFormatter{
    static NSDateFormatter *dateFormatter = nil;
	static dispatch_once_t onceToken;
	dispatch_once(&onceToken, ^{
		dateFormatter = [[NSDateFormatter alloc] init];
        dateFormatter.formatterBehavior =  NSDateFormatterBehaviorDefault;
        dateFormatter.dateStyle = kCFDateFormatterNoStyle;
        dateFormatter.timeStyle = NSDateFormatterShortStyle;
        dateFormatter.dateFormat = @"前天 HH:mm:ss";
	});
    return dateFormatter;
}

@end
