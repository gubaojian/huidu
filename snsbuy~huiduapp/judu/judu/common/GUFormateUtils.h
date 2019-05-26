//
//  GUFormateUtils.h
//  judu
//
//  Created by lurina on 13-7-29.
//  Copyright (c) 2013年 baobao. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface GUFormateUtils : NSObject


/**
 http://www.istar.name/blog/ios-formatter-date
 */

+(NSString*) formateDate:(NSDate*) date;

+(NSDateFormatter*) monthDayFormatter;

+(NSDateFormatter*) todayFormatter;

+(NSDateFormatter*) yesterdayFormatter;

+(NSDateFormatter*) beforeYesterdayFormatter;



@end
