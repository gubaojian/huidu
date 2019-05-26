//
//  HTTPConnection.m
//  
//
//  Created by Jianhong Yang on 12-1-3.
//  Copyright (c) 2012年 __MyCompanyName__. All rights reserved.
//

#import "GUHTTPConnection.h"


#define  MAXNUMBER_URLCONNECTION           10

#define TASKSTATUS_RUN       @"Run"
#define TASKSTATUS_WAIT      @"Wait"


@interface GUHTTPConnection (Private)
- (void)startURLConnection;
@end


@implementation GUHTTPConnection

@synthesize maxNumberOfURLConnection = _maxNumberOfURLConnection;
@synthesize delegate = _delegate;

- (id)init
{
    self = [super init];
    if (self) {
        // Custom initialization.
        _numberOfURLConnection = 0;
        _maxNumberOfURLConnection = MAXNUMBER_URLCONNECTION;
        _marrayTaskDic = [[NSMutableArray alloc] initWithCapacity:5];
    }
    return self;
}

- (void)dealloc
{
    //清空任务
    [self clearRequest];
    //
    [_marrayTaskDic release];
    
    [super dealloc];
}


#pragma mark -
#pragma mark Public

// 根据URL获取Web数据
- (BOOL)requestWebDataWithURL:(NSString *)strURL andParam:(NSDictionary *)dicParam priority:(BOOL)priority
{
    if (nil == dicParam) {
        return NO;
    }
    
    NSURL *url = [NSURL URLWithString:strURL];
    NSURLRequest *request = [[NSURLRequest alloc] initWithURL:url];
    BOOL success = [self requestWebDataWithRequest:request andParam:dicParam priority:priority];
    [request release];
    return success;
}

// 根据URLRequest获取Web数据
- (BOOL)requestWebDataWithRequest:(NSURLRequest *)request andParam:(NSDictionary *)dicParam priority:(BOOL)priority
{
    if (nil == dicParam) {
        return NO;
    }
    //正在处理或等待处理的任务不再接收
    for (NSDictionary *dicTask in _marrayTaskDic) {
        //
        if ([dicParam isEqualToDictionary:[dicTask objectForKey:@"param"]]) {
            HTTPLOG(@"任务重复:%@", dicParam);
            //需优先处理且为等待状态
            if (priority && [[dicParam objectForKey:@"status"] isEqualToString:TASKSTATUS_WAIT]) {
                [dicTask retain];
                [_marrayTaskDic removeObject:dicTask];
                [_marrayTaskDic insertObject:dicTask atIndex:0];
                [dicTask release];
            }
            return NO;
        }
    }
    
    HTTPLOG(@"添加新任务，参数:%@", dicParam);
    NSMutableDictionary *mdicTask = [[NSMutableDictionary alloc] initWithCapacity:3];
    //设置数据缓存
    NSMutableData *mdataCache = [[NSMutableData alloc] init];
    [mdicTask setObject:mdataCache forKey:@"cache"];
    [mdataCache release];
    //参数
    [mdicTask setObject:dicParam forKey:@"param"];
    //状态
    [mdicTask setObject:TASKSTATUS_WAIT forKey:@"status"];
    //创建HTTP网络连接
    NSURLConnection *urlConnection = [[NSURLConnection alloc] initWithRequest:request delegate:self startImmediately:NO];
    [mdicTask setObject:urlConnection forKey:@"connect"];
    [urlConnection release];
    //保存到数组
    if (priority) {
        [_marrayTaskDic insertObject:mdicTask atIndex:0];
    }
    else {
        [_marrayTaskDic addObject:mdicTask];
    }
    [mdicTask release];
    
    [self startURLConnection];
    return YES;
}

// 取消网络请求
- (BOOL)cancelRequest:(NSDictionary *)dicParam
{
    if (nil == dicParam) {
        return NO;
    }
    //遍历所有任务
    for (int i = 0; i < _marrayTaskDic.count; i++) {
        //查看任务是否相同
        NSDictionary *dicTask = [_marrayTaskDic objectAtIndex:i];
        if ([dicParam isEqualToDictionary:[dicTask objectForKey:@"param"]]) {
            //取消网络请求
            NSURLConnection *connect = [dicTask objectForKey:@"connect"];
            //未启动的须先启动再取消，不然有内存泄露
            if ([TASKSTATUS_WAIT isEqualToString:[dicTask objectForKey:@"status"]]) {
                [connect start];
            }
            else {
                _numberOfURLConnection -= 1;
            }
            [connect cancel];
            //从任务队列中删除
            [_marrayTaskDic removeObjectAtIndex:i];
            return YES;
        }
    }
    return NO;
}

// 清空网络请求
- (void)clearRequest
{
    //遍历所有任务
    for (NSDictionary *dicTask in _marrayTaskDic) {
        NSURLConnection *connect = [dicTask objectForKey:@"connect"];
        //未启动的须先启动再取消，不然有内存泄露
        if ([TASKSTATUS_WAIT isEqualToString:[dicTask objectForKey:@"status"]]) {
            [connect start];
        }
        else {
            //
            _numberOfURLConnection -= 1;
        }
        [connect cancel];
    }
    //从任务队列中删除
    [_marrayTaskDic removeAllObjects];
}

// 获取已经下载到的数据
- (void)loadReceivedDataOf:(NSDictionary *)dicParam into:(NSMutableData *)mdataReceived
{
    for (NSDictionary *dicTask in _marrayTaskDic) {
        //找到相应的数据
        if ([dicParam isEqualToDictionary:[dicTask objectForKey:@"param"]]) {
            //取出已经下载到的数据
            NSData *data = [dicTask objectForKey:@"cache"];
            if (data.length > 0) {
                [mdataReceived setData:data];
                break;
            }
        }
    }
}


#pragma mark -
#pragma mark NSURLConnectionDataDelegate

- (void)connection:(NSURLConnection *)connection didFailWithError:(NSError *)error
{
    HTTPLOG(@"网络请求错误:%@", error);
    //找到当前失败的任务
    int indexTask = 0;
    NSDictionary *dicTask = nil;
    for (int i = 0; i < _marrayTaskDic.count; i++) {
        NSDictionary *dic = [_marrayTaskDic objectAtIndex:i];
        //找到网络连接相应的数据字典
        if ([dic objectForKey:@"connect"] == connection) {
            indexTask = i;
            dicTask = [dic retain];
            break;
        }
    }
    //删除失败的任务
    if (dicTask) {
        //删除
        _numberOfURLConnection -= 1;
        [_marrayTaskDic removeObjectAtIndex:indexTask];
        //启动新任务
        [self startURLConnection];
        //通知上层任务失败
        NSDictionary *dicParam = [dicTask objectForKey:@"param"];
        if ([_delegate respondsToSelector:@selector(httpConnect:error:with:)]) {
            [_delegate httpConnect:self error:error with:dicParam];
        }
    }
    [dicTask release];
}

- (void)connection:(NSURLConnection *)connection didReceiveResponse:(NSURLResponse *)response
{
    HTTPLOG(@"网络请求收到响应");
    //找到相应的任务
    NSDictionary *dicTask = nil;
    for (int i = 0; i < _marrayTaskDic.count; i++) {
        NSDictionary *dic = [_marrayTaskDic objectAtIndex:i];
        //找到网络连接相应的数据字典
        if ([dic objectForKey:@"connect"] == connection) {
            dicTask = dic;
            break;
        }
    }
    //
    if ([response isMemberOfClass:NSHTTPURLResponse.class]) {
        NSHTTPURLResponse *responseHTTP = (NSHTTPURLResponse *)response;
        NSUInteger statusCode = responseHTTP.statusCode;
        NSDictionary *dicAllHeaderFields = responseHTTP.allHeaderFields;
        NSDictionary *dicParam = [dicTask objectForKey:@"param"];
        //收到服务器返回的HTTP信息头
        SEL receiveResponse = @selector(httpConnect:receiveResponseWithStatusCode:andAllHeaderFields:with:);
        if ([self.delegate respondsToSelector:receiveResponse]) {
            [self.delegate httpConnect:self receiveResponseWithStatusCode:statusCode 
                    andAllHeaderFields:dicAllHeaderFields with:dicParam];
        }
    }
}

- (void)connection:(NSURLConnection *)connection didReceiveData:(NSData *)data
{
    HTTPLOG(@"网络请求收到数据");
    //找到相应的任务
    NSDictionary *dicTask = nil;
    for (int i = 0; i < _marrayTaskDic.count; i++) {
        NSDictionary *dic = [_marrayTaskDic objectAtIndex:i];
        //找到网络连接相应的数据字典
        if ([dic objectForKey:@"connect"] == connection) {
            dicTask = dic;
            break;
        }
    }
    //
    if (dicTask) {
        //向缓存中添加数据
        NSMutableData *mdataCache = [dicTask objectForKey:@"cache"];
        [mdataCache appendData:data];
        NSDictionary *dicParam = [dicTask objectForKey:@"param"];
        HTTPLOG(@"该数据的参数：%@", dicParam);
        //收到部分数据
        if ([self.delegate respondsToSelector:@selector(httpConnect:receiveProgressChanged:with:)]) {
            [self.delegate httpConnect:self receiveProgressChanged:mdataCache.length with:dicParam];
        }
    }
    HTTPLOG(@"网络请求收到数据并处理完成");
}

- (void)connectionDidFinishLoading:(NSURLConnection *)connection
{
    HTTPLOG(@"网络请求完成");
    //找到当前完成的任务
    int indexTask = 0;
    NSDictionary *dicTask = nil;
    for (int i = 0; i < _marrayTaskDic.count; i++) {
        NSDictionary *dic = [_marrayTaskDic objectAtIndex:i];
        //找到网络连接相应的数据字典
        if ([dic objectForKey:@"connect"] == connection) {
            indexTask = i;
            dicTask = [dic retain];
            break;
        }
    }
    //删除已经完成的任务
    if (dicTask) {
        //删除
        _numberOfURLConnection -= 1;
        [_marrayTaskDic removeObjectAtIndex:indexTask];
        //启动新任务
        [self startURLConnection];
        //通知上层完成任务
        NSData *dataCache = [dicTask objectForKey:@"cache"];
        NSDictionary *dicParam = [dicTask objectForKey:@"param"];
        if ([_delegate respondsToSelector:@selector(httpConnect:finish:with:)]) {
            [_delegate httpConnect:self finish:dataCache with:dicParam];
        }
    }
    [dicTask release];
}


#pragma mark - Private

- (void)startURLConnection
{
    if (_numberOfURLConnection < _maxNumberOfURLConnection) {
        if (_numberOfURLConnection < _marrayTaskDic.count) {
            //找到等待状态的任务
            for (NSMutableDictionary *mdicTask in _marrayTaskDic) {
                if ([TASKSTATUS_WAIT isEqualToString:[mdicTask objectForKey:@"status"]]) {
                    //修改状态
                    [mdicTask setObject:TASKSTATUS_RUN forKey:@"status"];
                    //启动
                    NSURLConnection *urlConnection = [mdicTask objectForKey:@"connect"];
                    [urlConnection start];
                    break;
                }
            }
            //
            _numberOfURLConnection += 1;
        }
    }
    HTTPLOG(@"正在处理的网络请求数：%i，等待处理的网络请求：%i", 
            _numberOfURLConnection, _marrayTaskDic.count-_numberOfURLConnection);
}

@end
