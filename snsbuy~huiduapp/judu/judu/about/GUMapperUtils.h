//
//  GUMapperUtils.h
//  asm
//
//  Created by lurina on 13-1-19.
//  Copyright (c) 2013年 baobao. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "FMResultSet.h"

@interface GUMapperUtils : NSObject

+(id) mappObject:(id)target withResultSet: (FMResultSet *)result;

@end
