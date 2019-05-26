//
//  GUSystemUtils.h
//  powerfee
//
//  Created by lurina on 12-11-26.
//  Copyright (c) 2012年 baobao. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <ifaddrs.h>
#import <arpa/inet.h>

@interface GUSystemUtils : NSObject

/**是否是中文*/
+(BOOL) isZhHans;

/**是否是 iphone */
+(BOOL) isIphone;

//+(BOOL) isIphone35inch;

/** 四寸的 iphone */
+(BOOL) isIphone4inch;

/**是否是 ios 5 */
+(BOOL) isIos5;

+(BOOL) isIos6;

+(BOOL) isIos7;



/**是否是 ipad */
+(BOOL) isIpad;

+(NSString* ) localIp;


@end
