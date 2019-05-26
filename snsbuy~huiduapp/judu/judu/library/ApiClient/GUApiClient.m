//
//  GUApiProxy.m
//  layer
//
//  Created by world on 13-7-29.
//  Copyright (c) 2013年 world. All rights reserved.
//

#import "GUApiClient.h"
#import "GUHttpClient.h"
#import "AFHTTPClient.h"
#import "GUConfig.h"

/**
 1、TODO sha及签名防止请
 2、缓存测试
 3、单元测试
 4、整体集成测试
 5、发布到pods库
 */

@implementation GUApiClient

+(AFJSONRequestOperation*) execute:(GUApiRequest*) apiRequest withCallback:(Callback)callback{
    GUHttpClient* client = [GUHttpClient shareClient];
    
    //检查API请求是否存在，若不存在，返回失败结果
    NSString* path = [[client apiMap] objectForKey:[apiRequest api]];
    if (path == nil) {
        NSLog(@"api request execute error。url not found for api: %@", [apiRequest api]);
        GUResult* result = [[GUResult alloc] init];
        [result setSuccess:NO];
        [result setResultCode:RESULT_CODE_API_NOT_EXIST_ERROR];
        callback(result);
        return [[AFJSONRequestOperation alloc] init]; // 返回非空的结果，减少crash
    }

    //创建Api请求
    NSMutableURLRequest * request = [client requestWithApiRequest:apiRequest];
    if (request == nil) {
        NSLog(@"create http request error");
        GUResult* result = [[GUResult alloc] init];
        [result setSuccess:NO];
        [result setResultCode:RESULT_CODE_CREATE_HTTP_REQUEST_ERROR];
        callback(result);
        return [[AFJSONRequestOperation alloc] init]; // 返回非空的结果，减少crash
    }
    
    
    //创建请求的操作，并将其加入操作队列
     [UIApplication sharedApplication].networkActivityIndicatorVisible = YES;
    AFJSONRequestOperation* operation = [AFJSONRequestOperation JSONRequestOperationWithRequest:request success:^(NSURLRequest *request, NSHTTPURLResponse *response, id JSON) {
         NSLog(@"request for api %@ success \n %@", [apiRequest api], JSON);
        GUResult*  result = [GUParseUtils parseResult:JSON withContext:apiRequest];
        callback(result);
        [UIApplication sharedApplication].networkActivityIndicatorVisible = NO;
    } failure:^(NSURLRequest *request, NSHTTPURLResponse *response, NSError *error, id JSON) {
        NSLog(@"request for api %@ fail %@ \n %@", [apiRequest api], error, JSON);
        GUResult*  result = [GUParseUtils parseResult:JSON withContext:apiRequest];
        callback(result);
        [UIApplication sharedApplication].networkActivityIndicatorVisible = NO;
    }];
    if (apiRequest.syncQueue != NULL) {
        // operation.queuePriority = NSOperationQueuePriorityVeryLow;
        // operation.failureCallbackQueue = apiRequest.syncQueue;
         //operation.successCallbackQueue = apiRequest.syncQueue;
    }
    [[client operationQueue] addOperation:operation];
    if (apiRequest.syncQueue != NULL) {
       // [operation waitUntilFinished];
    }
    return operation;
}





@end
