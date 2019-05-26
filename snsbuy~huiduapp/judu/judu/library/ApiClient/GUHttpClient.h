//
//  GUApiClient.h
//  layer
//
//  Created by world on 13-7-29.
//  Copyright (c) 2013年 world. All rights reserved.
//

#import "AFHTTPClient.h"
#import "GUResult.h"
#import "GUApiRequest.h"

@interface GUHttpClient : AFHTTPClient{
     @private NSDictionary* _apiMap;
     @private NSMutableDictionary* _serverClients;
}

/**
 * 通过此方法获取全局的共享client
 */
+(GUHttpClient*) shareClient;

/**
 * 返回签名后的的请求对象
 * 对于POST请求，仅签名appId 时间戳这两个参数。其余参数暂不签名
 * 对于GET请求，对所有请求参数、appId、时间戳都进行签名处理。防止请求被修改。
 * 传入时，请确保Api存在，此接口不对Api是否存在进行检查。
 * 执行请求请使用GUApiClient, GUApiClient会对Api进行校验及检查。
 */
- (NSMutableURLRequest *)requestWithApiRequest:(GUApiRequest*) apiRequest;

/**
 * api映射列表，key为api的名字，value为url
 */
-(NSDictionary*) apiMap;

/**
 * app标识Id
 */
-(NSString*) appId;

/**
 * app的秘钥
 */
-(NSString*) appSecret;



/**
 * app标识Id
 */
-(NSString*) noneSignTimeAppId;

/**
 * app的秘钥
 */
-(NSString*) noneSignTimeAppSecret;


-(NSString*) baseUrl;


@end
