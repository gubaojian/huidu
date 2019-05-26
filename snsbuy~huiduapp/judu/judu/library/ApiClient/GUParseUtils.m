//
//  GUResultUtils.m
//  layer
//
//  Created by world on 13-7-29.
//  Copyright (c) 2013年 world. All rights reserved.
//

#import "GUParseUtils.h"
#import "GUConfig.h"

@implementation GUParseUtils

+(GUResult*) parseResult:(NSDictionary*) json withContext:(GUApiRequest*) request{
    @try {
        if (json == nil || [json count] == 0) {
            GUResult* result = [[GUResult alloc] init];
            result.success = NO;
            result.resultCode = RESULT_CODE_EMPTY_JSON_DATA_RESULT_ERROR;
            return result;
        }
        
        //解析GUResult对象
        NSError* error;
        GUResult* result = [[GUResult alloc] initWithDictionary:json error:&error];
        if (error) {
            NSLog(@"parse result error %@", error);
            if (result == nil) {
               result = [[GUResult alloc] init];
            }
            result.success = NO;
            result.resultCode = RESULT_CODE_RESULT_MODEL_INIT_FROM_JSON_ERROR;
            return result;
        }
        
        id targetResult = [json objectForKey:@"result"];
        id targetModels = [json objectForKey:@"models"];
        //解析result
        if ([request modelClass] != nil) {
            if (isNull(targetResult)) {
                result.result = nil;
            }else{
                result.result = [GUParseUtils json:targetResult to:[request modelClass] error:&error];
                if (error) {
                    NSLog(@"parse model error %@", error);
                    result.success = NO;
                    result.resultCode = RESULT_CODE_DEFAULT_MODEL_INIT_FROM_JSON_ERROR;
                }
            }
        }
        
        //解析Models
       if ([request modelsClass] != nil) {
              if (isNull(targetModels)) {
                    result.models = nil; //结果设置成空
              }else{
                   //处理models中得的对象
                   result.models = [[NSMutableDictionary alloc] initWithCapacity:2];
                   [targetModels enumerateKeysAndObjectsUsingBlock:^(id key, id jsonValue, BOOL *stop){
                       *stop = NO;
                       Class  targetClass  = [[request modelsClass] objectForKey:key];
                       if (targetClass == nil) { //未设置映射，采用原生类
                           [result.models setObject:jsonValue forKey:key];
                       }else{ //设置映射，采用用户自定义的映射
                           NSError* modelError = nil;
                           id objectValue = [GUParseUtils json:jsonValue to:targetClass error:&modelError];
                           if (modelError) {
                               NSLog(@"parse models error %@", modelError);
                               result.success = NO;
                               result.resultCode = RESULT_CODE_MODELS_INIT_FROM_JSON_ERROR;
                               *stop = YES;
                           }else{
                               if (objectValue != nil) { 
                                   [result.models setObject:objectValue forKey:key];
                               }else{
                                   [result.models setObject:[NSNull null] forKey:key];
                               }
                           }
                       }
                       
                   }];
              }
         }
         return result;
    }
    @catch (NSException *exception) {
        NSLog(@"parse json exception %@", exception);
        GUResult* result = [[GUResult alloc] init];
        result.success = NO;
        result.resultCode = RESULT_CODE_CONVERT_JSON_EXCEPTION_ERROR;
        return result;
    }
}

+(id) json:(id) datas to:(Class) targetClass error:(NSError**) error{
    if (isNull(datas)) {
        return nil;
    }
    //未设置对象model，直接返回原声对象
    if (targetClass == nil) {
        return datas;
    }
    
    if ([targetClass isSubclassOfClass:[NSDictionary class]]) {
        return datas;
    }
    
    //非数组，则代表其为一个元素
    if (![datas isKindOfClass:[NSArray class]]) {
        return [[targetClass alloc] initWithDictionary:datas error:error];
    }
    
    //数组元素，返回对象的数组
    NSMutableArray* targets = [[NSMutableArray alloc] initWithCapacity:[datas count]];
    for (id data in datas) {
        id target =  [[targetClass alloc] initWithDictionary:data error:error];
        if (*error) {
            return nil;
        }
        [targets addObject:target];
    }
    return targets;
}




/**
+(void) validJson:(NSMutableDictionary*) json{
    BOOL valid = YES;
   

}
*/


@end
