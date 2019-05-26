//
//  HTTPConnection.h
//  
//
//  Created by Jianhong Yang on 12-1-3.
//  Copyright (c) 2012年 __MyCompanyName__. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "GUConfig.h"


@protocol GUHTTPConnectionDelegate;

@interface GUHTTPConnection : NSObject {
@private
    //
    int _numberOfURLConnection;
    int _maxNumberOfURLConnection;
    NSMutableArray *_marrayTaskDic;
    
    id <GUHTTPConnectionDelegate> _delegate;
}

@property (nonatomic, assign) int maxNumberOfURLConnection;
@property (nonatomic, assign) id <GUHTTPConnectionDelegate> delegate;

// 根据URL获取Web数据
- (BOOL)requestWebDataWithURL:(NSString *)strURL andParam:(NSDictionary *)dicParam priority:(BOOL)priority;

// 根据URLRequest获取Web数据
- (BOOL)requestWebDataWithRequest:(NSURLRequest *)request andParam:(NSDictionary *)dicParam priority:(BOOL)priority;

// 取消网络请求
- (BOOL)cancelRequest:(NSDictionary *)dicParam;

// 清空网络请求
- (void)clearRequest;

// 获取已经下载到的数据
- (void)loadReceivedDataOf:(NSDictionary *)dicParam into:(NSMutableData *)mdataReceived;

@end


@protocol GUHTTPConnectionDelegate <NSObject>

@optional

// 网络数据下载失败
- (void)httpConnect:(GUHTTPConnection *)httpConnect error:(NSError *)error with:(NSDictionary *)dicParam;

// 服务器返回的HTTP信息头
- (void)httpConnect:(GUHTTPConnection *)httpConnect receiveResponseWithStatusCode:(NSInteger)statusCode 
 andAllHeaderFields:(NSDictionary *)dicAllHeaderFields with:(NSDictionary *)dicParam;

// 接收到的数据量发生变化
- (void)httpConnect:(GUHTTPConnection *)httpConnect receiveProgressChanged:(long long)dataLength with:(NSDictionary *)dicParam;

// 网络数据下载完成
- (void)httpConnect:(GUHTTPConnection *)httpConnect finish:(NSData *)data with:(NSDictionary *)dicParam;

@end


#ifdef DEBUG

#define HTTPLOG(fmt,...)     NSLog((@"HTTP->%s(%d):"fmt),__PRETTY_FUNCTION__,__LINE__,##__VA_ARGS__)

#else

#define HTTPLOG(fmt,...)     NSLog(fmt,##__VA_ARGS__)

#endif
