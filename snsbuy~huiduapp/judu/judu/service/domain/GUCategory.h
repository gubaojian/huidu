//
//  GUCategory.h
//  judu
//
//  Created by lurina on 13-8-11.
//  Copyright (c) 2013年 baobao. All rights reserved.
//

#import "JSONModel.h"

@interface GUCategory : JSONModel{
    NSNumber* _id;
    NSString* _name;


}


@property(nonatomic, strong)   NSNumber* id;

@property(nonatomic, strong) NSString* name;


@end
