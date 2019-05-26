//
//  GUArticle.m
//  judu
//
//  Created by lurina on 13-8-11.
//  Copyright (c) 2013年 baobao. All rights reserved.
//

#import "GUArticle.h"

@implementation GUArticle

@synthesize url = _url;

@synthesize title = _title;

@synthesize content = _content;

@synthesize id = _id;

@synthesize author = _author;

@synthesize shortDesc = _shortDesc;

@synthesize publishTime = _publishTime;

-(NSDate*) publishDate{
    if (_publishTime == nil) {
        return nil;
    }
    return [NSDate dateWithTimeIntervalSince1970:[_publishTime doubleValue]/1000];
}

-(NSString*)formatPublishDate{
    return [GUFormateUtils formateDate:[self publishDate]];
}

@end
