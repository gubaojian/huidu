//
//  GURequest.h
//  layer
//
//  Created by world on 13-7-29.
//  Copyright (c) 2013年 world. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "JSONModel.h"


#define CDN_SERVER    @"cdnServer"


/**
 * Api的请求方法，目前仅支持GET及POST请求
 */
typedef enum HttpMethod{
    GET,
    POST
}HttpMethod;

typedef enum SignType{
    SIGN_WITH_TIMESTAMP,
    SIGN_WITHOUT_TIMESTAMP
}SignType;

/**
 * Api请求的对象，通过创建请求对象，调用GUApiClient执行请求，完成网络请求。
 */
@interface GUApiRequest : NSObject{
    @private  Class  _modelClass;
    @private  NSDictionary* _modelsClass;
    @private  NSString* _api;
    @private  HttpMethod _method;
    @private  NSMutableDictionary* _parameters;
    @private  NSURLRequestCachePolicy _cachePolicy;
    @private  NSString* _server;
    @private  SignType   _sign;
}

/**
 * GUResult中得result字段属性对应的class
 * 若class为其自定义的类型，则其必须继承JSONModel (自动区分是数组还是单个类型)
 * 若其为框架自带的类型，比如NSString NSDictionary NSNumber等，则不用该属性（也不能设置）。
 * 当且仅当类型为你自定义的类型时，才需要设置该属性。
 */
@property(nonatomic) Class  modelClass;

/**
 * GUResult中得models中的字段属性对应的class
 * 若class为其自定义的类型，则其必须继承JSONModel
 * 若其为框架自带的类型，比如NSString NSDictionary NSNumber等，则不用该属性（也不能设置）。
 * 当且仅当类型为你自定义的类型时，才需要设置该属性。
 * 设置示例：
 * modelsClass = [NSDictionary dictionary ];
 * [NSDictionary dictionaryWithObjectsAndKeys:[GUUser class], @"User", nil];
 */
@property(nonatomic, strong) NSDictionary* modelsClass;

/**
 * api的名字，对应Api.plist文件中的key字段属性
 */
@property(nonatomic, strong) NSString* api;


/**
 * 服务器的域名在api.plist对应的key
 */
@property(nonatomic, strong) NSString* server;

/**
 * 是否签名URL，默认签名
 */
@property(nonatomic)  SignType   sign;

/**
 * 请求的方法，默认为GET方法
 */
@property(nonatomic) HttpMethod method;


/**
 * 请求是否同步
 */
@property(nonatomic) dispatch_queue_t syncQueue;


@property(nonatomic)  NSURLRequestCachePolicy cachePolicy;

/**
 * 请求参数，添加请求参数时无需进行编码处理，系统会自动进行编码。
 * 返回值非 nil。
 */
@property(nonatomic, strong, readonly) NSMutableDictionary* parameters;



/**
 * 快捷的添加请求参数方法， 其中name为参数名，value为参数对象。 无需对参数进行编码，系统会自动编码
 */
-(void) addParameter:(NSString*) name value:(NSObject*)value;







@end
