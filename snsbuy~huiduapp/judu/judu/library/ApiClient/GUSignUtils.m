//
//  GUSignUtils.m
//  layer
//
//  Created by world on 13-7-29.
//  Copyright (c) 2013年 world. All rights reserved.
//

#import "GUSignUtils.h"
#import <CommonCrypto/CommonCrypto.h>
#import "MFBase64Additions.h"
#import "GUConfig.h"

@implementation GUSignUtils

+(NSString*) signParameter:(NSDictionary*) parameter withKey:(NSString*)secret{
    NSArray*  keys = [parameter allKeys];
    keys = [keys sortedArrayUsingComparator:^NSComparisonResult(id one, id two) {
        return [one compare:two];
    }];
    NSMutableString* value = [[NSMutableString alloc] initWithCapacity:128];
    [value appendString:secret]; //签名规则
    [value appendString:@"&"];
    for (NSString* key in keys) {
        [value appendString:key];
        id object = [parameter objectForKey:key];
        [value appendString:@"="];
        if (object) {
            [value appendString:object];
        }
        [value appendString:@"&"];
    }
    [value appendString:secret];
    return  [GUSignUtils hmacSha256:value withKey:secret];
}


+(NSString*) timestamp{
    NSDate* now = [NSDate dateWithTimeIntervalSinceNow:0];
    unsigned long long timestamp = floor([now timeIntervalSince1970] / (30*60.0)) * 30*60.0;
    NSString* signTimeStamp = [NSString stringWithFormat:@"%llu", timestamp];
    return signTimeStamp;
}


+(NSString*) hmacSha256:(NSString*)data withKey:(NSString*)key{
    
    const char *cData = [data cStringUsingEncoding:NSUTF8StringEncoding];
    const char *cKey = [key cStringUsingEncoding:NSUTF8StringEncoding];

    char cHashData[CC_SHA256_DIGEST_LENGTH];
    CCHmac(kCCHmacAlgSHA256, cKey, strlen(cKey), cData , strlen(cData), cHashData);
    NSData* encryptData = [NSData dataWithBytes:cHashData length:CC_SHA256_DIGEST_LENGTH];
    

    return [encryptData base64String];
}


+(NSString*) hexChars:(char[]) datas length:(int)length{
    NSMutableString *hex = [NSMutableString string];
    for (int i=0; i<length; i++) {
        [hex appendFormat:@"%02X" , datas[i] & 0x00FF];
    }
    return hex;
}

+(NSString*) hexData:(NSData*) data{
    char *utf8;
    utf8 = (char*)[data bytes];
    NSMutableString *hex = [NSMutableString string];
    for (int i=0; i<[data length]; i++) {
        [hex appendFormat:@"%02X" , *utf8 & 0x00FF];
        utf8++;
    }
    return hex;
}

@end
