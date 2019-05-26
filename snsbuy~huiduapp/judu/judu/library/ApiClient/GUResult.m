//
//  GUResult.m
//  layer
//
//  Created by world on 13-7-29.
//  Copyright (c) 2013年 world. All rights reserved.
//

#import "GUResult.h"

@implementation GUResult

@synthesize success = _success;

@synthesize resultCode = _resultCode;

@synthesize result = _result;

@synthesize models = _models;


-(id) result{
    return _result;
}

-(BOOL)isSuccess{
    return _success;
}

-(void) setSuccess:(BOOL)success{
    _success = success;
}

-(BOOL) hasMore{
    if (_models == nil || [_models objectForKey:@"hasMore"] == nil) {
        return NO;
    }
    BOOL hasMore = [[_models objectForKey:@"hasMore"] boolValue];
    return hasMore;
}









@end
