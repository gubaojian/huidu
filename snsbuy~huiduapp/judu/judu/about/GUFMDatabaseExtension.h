//
//  GUFMDBExtension.h
//  asm
//
//  Created by lurina on 13-1-19.
//  Copyright (c) 2013年 baobao. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "FMDatabase.h"
#import "GUDynQuery.h"
#import "GULog.h"

@interface  FMDatabase (GUFMDatabaseExtension)

- (NSArray* )arrayForQuery:(NSString*)objs, ...;



- (int)intForGUQuery:(GUDynQuery*)query;

- (NSArray* ) arrayForGUQuery:(GUDynQuery*)query;
- (FMResultSet *)executeGUQuery:(GUDynQuery*) query;


@end
