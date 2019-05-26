//
//  GUQuery.m
//  asm
//
//  Created by lurina on 13-1-19.
//  Copyright (c) 2013年 baobao. All rights reserved.
//

#import "GUDynQuery.h"

@implementation GUDynQuery

@synthesize  query, params;

-(GUDynQuery*) appendQuery:(NSString*) subQuery, ...{
    if (query == nil) {
        query = [[NSMutableString alloc] initWithString:@""];
    }
    if (params == nil) {
        params = [[NSMutableArray alloc] initWithCapacity:4];
    }
    if (subQuery) {
        [query appendString:@" "];
        [query appendString:subQuery];
        [query appendString:@" "];

        va_list argsList;
        va_start(argsList, subQuery);
        int count = [subQuery countOfSubstring:@"?"];
        for (int i= 0;  i<count;  i++) {
            id param = va_arg(argsList, id);
            [params addObject:param];
        }
        va_end(argsList);
    }
    return self;
}


-(GUDynQuery*) addLimit:(int) start withSize:(int) size{
    return [self appendQuery:@" limit ?, ? ", [NSNumber numberWithInt:start], [NSNumber numberWithInt:size]];
}



@end
