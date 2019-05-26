//
//  GUResultDB.m
//  huidu
//
//  Created by lurina on 13-10-31.
//  Copyright (c) 2013年 baobao. All rights reserved.
//

#import "GUResultDB.h"
#import "GUParseUtils.h"
#import "KVDB.h"
#import "AFHTTPClient.h"

@implementation GUResultDB

+(void)setResult:(GUResult*)value forRequest:(const GUApiRequest*) request{
    if (value == nil || request == nil) {
        return;
    }
    @synchronized(self){
        NSDictionary* json = [value toDictionary];
        if(json == nil){
            return;
        }
        NSString* key = [GUResultDB toKey:request];
        if(key.length <= 0){
            return;
        }
        [[KVDB sharedDB] setValue:json forKey:key];
    }
}

+(GUResult*)resultForKey:(GUApiRequest*) request{
    @synchronized(self){
       NSString* key = [GUResultDB toKey:request];
        if(key.length <= 0){
            return nil;
        }
        NSDictionary* json = [[KVDB sharedDB] valueForKey:key];
        if(json == nil){
            return nil;
        }
        return [GUParseUtils parseResult:json withContext:request];
    }
}

+(void)removeResultForKey:(GUApiRequest*) request{
    @synchronized(self){
      NSString* key = [GUResultDB toKey:request];
      if(key.length <= 0){
            return;
      }
      [[KVDB sharedDB] removeValueForKey:key];
    }
}

+(NSString*)toKey:(const GUApiRequest*) request{
    if(![request isKindOfClass:[GUApiRequest class]]){
        return  @"";
    }
    if(![request respondsToSelector:@selector(parameters)]){
        return  @"";
    }
    
    NSString*  query = @"";
    if ([request parameters]) {
          query = AFQueryStringFromParametersWithEncoding([request parameters], NSUTF8StringEncoding);
    }
    return [NSString stringWithFormat:@"%@?%@", [request api], query];
}




@end
