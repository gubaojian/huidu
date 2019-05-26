//
//  GUSignUtils.h
//  layer
//
//  Created by world on 13-7-29.
//  Copyright (c) 2013年 world. All rights reserved.
//

#import <Foundation/Foundation.h>
/**
 * 对参数进行签名处理
 */
@interface GUSignUtils : NSObject

/**
 * 首先将key排序。然后采用如下格式拼接：
 * secret&key1=xx&key2=xx&key3=xx&secret
 * 然后对拼接的字符串采用SHA256_HMAC()用secret作为密码对字符串进行加密, 对加密结果采用Base64编码
 * 返回Base64加密的字符串
 */
+(NSString*) signParameter:(NSDictionary*) values withKey:(NSString*)secret;

/**
 * 返回一个系统的统一的请求时间戳，时间单位为秒 。
 * 时间戳以10分钟的有效期进行处理
 */
+(NSString*) timestamp;

/**
 * 对data采用秘钥key进行hmacsha256加密，并将加密结果用base64转码后返回
 */
+(NSString*) hmacSha256:(NSString*)data withKey:(NSString*)key;

@end
