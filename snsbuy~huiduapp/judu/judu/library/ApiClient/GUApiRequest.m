//
//  GURequest.m
//  layer
//
//  Created by world on 13-7-29.
//  Copyright (c) 2013年 world. All rights reserved.
//

#import "GUApiRequest.h"


@implementation GUApiRequest

@synthesize modelClass = _modelClass;
@synthesize modelsClass = _modelsClass;
@synthesize method = _method;
@synthesize api = _api;
@synthesize parameters = _parameters;
@synthesize cachePolicy = _cachePolicy;
@synthesize server = _server;
@synthesize sign = _sign;

- (id)init{
    self = [super  init];
    if (self) {
        _server = nil;
        _sign = SIGN_WITH_TIMESTAMP;
    }
    return self;
}


-(NSMutableDictionary*) parameters{
    if (_parameters == nil) {
        _parameters = [[NSMutableDictionary alloc] initWithCapacity:2];
    }
    return _parameters;
}


-(void) addParameter:(NSString*) name value:(NSObject*)value{
    if (_parameters == nil) {
        _parameters = [[NSMutableDictionary alloc] initWithCapacity:4];
    }
    [_parameters setObject:value forKey:name];
}


-(void) setModelClass:(Class)modelClass{
    /**
     * 创建一个临时对象，检查其是否继承JSONModel用
     * [modelClass isSubclassOfClass:[JSONModel class]]; 会返回NO
     */
    if (![modelClass conformsToProtocol: @protocol(AbstractJSONModelProtocol)]) {
        [NSException raise:@"Model Class Must be subclass of JSONModel" format:@" current Model Class is %@", NSStringFromClass(modelClass)];
    }

    _modelClass = modelClass;
}



@end
