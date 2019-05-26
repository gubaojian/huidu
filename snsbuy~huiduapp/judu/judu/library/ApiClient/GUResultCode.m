//
//  GUResultCode.m
//  layer
//
//  Created by world on 13-7-29.
//  Copyright (c) 2013年 world. All rights reserved.
//

#import "GUResultCode.h"

@implementation GUResultCode

@synthesize code = _code;

@synthesize message = _message;

+(GUResultCode*) code:(int) code message:(NSString*) message{
    GUResultCode* resultCode = [[self alloc] init];
    resultCode.code = code;
    resultCode.message = message;
   return  resultCode;
}

-(BOOL) isEqual:(id)object{
    if (self == object) {
        return YES;
    }
    if (!object || ![object isKindOfClass:[self class]]){
        return NO;
    }
    int otherCode = [(GUResultCode*)object code];
    
    return [self code] == otherCode;
}

-(NSUInteger) hash{
    return _code*37 + 8;
}


-(NSString*) description{
    return [NSString stringWithFormat:@"Code %d Message %@", _code, _message];
}


@end
