//
//  GUArticle.h
//  judu
//
//  Created by lurina on 13-8-11.
//  Copyright (c) 2013年 baobao. All rights reserved.
//

#import "JSONModel.h"
#import "GUFormateUtils.h"

@interface GUArticle : JSONModel{
    NSNumber* _id;
    NSString* _author;
    NSString* _title;
    NSString* _shortDesc;
    NSString<Optional>* _content;
    NSURL<Optional>* _url;
    NSNumber* _publishTime;

}


@property(nonatomic, strong)   NSNumber* id;

@property(nonatomic, strong) NSString* author;

@property(nonatomic, strong) NSString* title;

@property(nonatomic, strong) NSString* shortDesc;

@property(nonatomic, strong) NSString<Optional>* content;

@property(nonatomic, strong) NSURL<Optional>* url;

@property(nonatomic, strong) NSNumber* publishTime;


-(NSDate*) publishDate;

-(NSString*)formatPublishDate;

@end
