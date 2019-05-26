//
//  GUResultDB.h
//  huidu
//
//  Created by lurina on 13-10-31.
//  Copyright (c) 2013年 baobao. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "GUResult.h"
#import "GUApiRequest.h"


@interface GUResultDB : NSObject

+(void)setResult:(GUResult*)value forRequest:(GUApiRequest*) request;
+(GUResult*)resultForKey:(GUApiRequest*) request;
+(void)removeResultForKey:(GUApiRequest*) request;


@end
