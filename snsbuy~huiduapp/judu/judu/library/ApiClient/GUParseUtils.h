//
//  GUResultUtils.h
//  layer
//
//  Created by world on 13-7-29.
//  Copyright (c) 2013年 world. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "CommonResultCode.h"
#import "GUResult.h"
#import "GUApiRequest.h"

/**
 * 将响应的JSON转化为Domain对象
 */
@interface GUParseUtils : NSObject

/** 
 * 将请求的JSON数据，转化多Domain对象。
 * 系统会自动将数组转化为对象数组。
 */
+(GUResult*) parseResult:(NSDictionary*) json withContext:(GUApiRequest*) request;


/**
 * 转换JSON数据为object对象。
 * 如果json当个对象，则将其转换为单个对象， 
 * 如果json数据是一个数组，则将其转换成对象数组
 */
 +(id) json:(id) datas to:(Class) targetClass error:(NSError**) error;


@end
