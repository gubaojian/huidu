//
//  GUApiProxy.h
//  layer
//
//  Created by world on 13-7-29.
//  Copyright (c) 2013年 world. All rights reserved.
//

/**
 在xcode other link flag 中加-ObjC 
 
 */

#import <Foundation/Foundation.h>
#import "GUApiRequest.h"
#import "AFNetworking.h"
#import "GUResult.h"
#import "GUParseUtils.h"

typedef void(^Callback)(GUResult* result);

@interface GUApiClient : NSObject

/**
 * api请求执行接口，请求执行完成后在主线程调用回调。
 * 所有api的请求均应通过此接口进行执行调用。
 * 提示：
 * controller被释放时，注意取消AFJSONRequestOperation,防止回调时controller已经被销毁产生crash。
 * 传参数self要用weakSelf的形式，防止循环引用
 */
+(AFJSONRequestOperation*) execute:(GUApiRequest*) apiRequest withCallback:(Callback) callback;

@end
