//
//  GUApiClient.m
//  layer
//
//  Created by world on 13-7-29.
//  Copyright (c) 2013年 world. All rights reserved.
//

#import "GUHttpClient.h"
#import "GUSignUtils.h"
#import "GUConfig.h"

@implementation GUHttpClient

+(GUHttpClient*) shareClient{
    static GUHttpClient *_sharedClient = nil;
    static dispatch_once_t onceToken;
    dispatch_once(&onceToken, ^{
        NSString* apiPath = [[NSBundle mainBundle] pathForResource:@"Api" ofType:@"plist"];
        NSDictionary* dic = [NSDictionary dictionaryWithContentsOfFile:apiPath];
        if (dic == nil) {
             [NSException raise:@"Api.plist file not found on main bundle" format:@""];
        }
        NSString* baseUrl = [dic objectForKey:@"baseUrl"];
        if ([dic count] == 0 || baseUrl == nil) {
            [NSException raise:@"baseUrl not found in Api.plist" format:@""];
        }
        
        if ([dic objectForKey:@"appId"] == nil) {
            [NSException raise:@"appId not found in Api.plist" format:@""];
        }
        if ([dic objectForKey:@"appSecret"] == nil) {
            [NSException raise:@"appSecret not found in Api.plist" format:@""];
        }
                
        _sharedClient = [[GUHttpClient alloc] initWithBaseURL:[NSURL URLWithString:baseUrl]];
        [_sharedClient setApiMap:dic];
        [_sharedClient configShareClient];
    });
    
    return _sharedClient;
}

/////////////////////////////////////////////////////////////////////////////
#pragma init method

-(void) configShareClient{
     _serverClients = [[NSMutableDictionary alloc] initWithCapacity:4];
}

-(void) setApiMap:(NSDictionary*) apiMap{
    _apiMap = apiMap;
}


-(NSMutableDictionary*) servetClients{
    return _serverClients;
}



-(NSDictionary*) apiMap{
    return _apiMap;
}

-(NSString*) appId{
    return [_apiMap objectForKey:@"appId"];
}

-(NSString*) appSecret{   //不使用api文件里面的，防止泄露
    return @"WRitdgAvmGBe6h8LOLmrPS03mAWfxQIQ";
}


/**
 * app标识Id
 */
-(NSString*) noneSignTimeAppId{
    return @"hQCxinnGahes5iXQ";
}

/**
 * app的秘钥
 */
-(NSString*) noneSignTimeAppSecret{
    return @"SkT3EGqVmjBih1LDL8T16dFywoaLHEKh";
}


-(NSString*) baseUrl{
   return [_apiMap objectForKey:@"baseUrl"];
}

/////////////////////////////////////////////////////////////////////////////
#pragma method for create http request from api request
- (NSMutableURLRequest *)requestWithApiRequest:(GUApiRequest*) apiRequest{
    NSString* path = [[self apiMap] objectForKey:[apiRequest api]];
    if (path == nil) {
        NSLog(@"api request execute error。url not found for api: %@", [apiRequest api]);
        return nil;
    }
    NSString* method = nil;  // 分配新对象，防止影响到原有的参数，导致应用层异常。
    NSMutableDictionary* parameters = [[NSMutableDictionary alloc] initWithDictionary:[apiRequest parameters]];
    if ([apiRequest method] == POST) {
        method = @"POST";
        if ([apiRequest sign] == SIGN_WITH_TIMESTAMP) {
            //签名post请求， 采用appId、时间戳签名
            NSMutableDictionary* data = [[NSMutableDictionary alloc] initWithCapacity:4];
            [data setObject:[GUSignUtils  timestamp] forKey:@"timestamp"];
            [data setObject:[self appId] forKey:@"appId"];
            NSString* sign = [GUSignUtils signParameter:data withKey:[self appSecret]];
            [parameters setObject:sign forKey:@"sign"];
            [parameters addEntriesFromDictionary:data];
        }else if ([apiRequest sign] == SIGN_WITHOUT_TIMESTAMP){
            //签名post请求， 采用appId、时间戳签名
            NSMutableDictionary* data = [[NSMutableDictionary alloc] initWithCapacity:4];
            [data setObject:@"K8wsCVE8bMEqP7HY" forKey:@"p"];
            [data setObject:@"1" forKey:@"signType"];
            [data setObject:[self noneSignTimeAppId] forKey:@"appId"];
            NSString* sign = [GUSignUtils signParameter:data withKey:[self noneSignTimeAppSecret]];
            [parameters setObject:sign forKey:@"sign"];
            [parameters addEntriesFromDictionary:data];
        }
        
    }else{
        method = @"GET";
        if ([apiRequest sign] == SIGN_WITH_TIMESTAMP) {
            //签名get请求， 签名所有请求参数、appId、时间戳签名
            [parameters setObject:[GUSignUtils  timestamp] forKey:@"timestamp"];
            [parameters setObject:[self appId] forKey:@"appId"];
            NSString* sign = [GUSignUtils signParameter:parameters withKey:[self appSecret]];
            [parameters setObject:sign forKey:@"sign"];
        }else if ([apiRequest sign] == SIGN_WITHOUT_TIMESTAMP){
            [parameters setObject:@"1" forKey:@"signType"];
            [parameters setObject:[self noneSignTimeAppId] forKey:@"appId"];
            NSString* sign = [GUSignUtils signParameter:parameters withKey:[self noneSignTimeAppSecret]];
            [parameters setObject:sign forKey:@"sign"];
        }
    }
    
    
    GUHttpClient* client = nil;
    if ([apiRequest server] && [apiRequest server].length > 0) {
        NSString* configServer = [[self apiMap] objectForKey:[apiRequest server]];
        if (configServer == nil || configServer.length == 0) {
            NSLog(@"api request execute error。server %@ not found for api: %@", configServer, [apiRequest api]);
            return nil;
        }
        client = [self clientForServer:configServer];
        if (client == nil) {
            NSLog(@"api request execute error。server %@ not found for api: %@", configServer, [apiRequest api]);
            return nil;
        }
    }else{
        client = self;
    }
    
    NSMutableURLRequest * request =  [client requestWithMethod:method path:path parameters:parameters];
    [request setCachePolicy:NSURLRequestUseProtocolCachePolicy];
    [request setTimeoutInterval:8];
    NSLog(@"api %@ %@ request for url: %@", [apiRequest api], method, [request URL]);
    return request;

}

-(GUHttpClient*) clientForServer:(NSString*)serverUrl{
    if (serverUrl == nil) {
        return nil;
    }
    GUHttpClient* client = [[self servetClients] objectForKey:serverUrl];
    if (client != nil) {
        return client;
    }
    
    @synchronized(self){
        client = [[self servetClients] objectForKey:serverUrl];
        if (client != nil) {
            return client;
        }
        client = [[GUHttpClient alloc] initWithBaseURL:[NSURL URLWithString:serverUrl]];
        [[self servetClients] setObject:client forKey:serverUrl];
    }
    return client;
}




@end
