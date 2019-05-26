//
//  GUQuery.h
//  asm
//
//  Created by lurina on 13-1-19.
//  Copyright (c) 2013年 baobao. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "GUNSStringExtension.h"

@interface GUDynQuery : NSObject

@property NSMutableArray*  params;

@property NSMutableString* query;

/**仅支持 ？ 进行参数替换*/
-(GUDynQuery*) appendQuery:(NSString*) subQuery, ...;


-(GUDynQuery*) addLimit:(int) start withSize:(int) size;



@end
