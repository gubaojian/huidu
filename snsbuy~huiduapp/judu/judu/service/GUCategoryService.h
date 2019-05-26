//
//  GUCategoryService.h
//  judu
//
//  Created by lurina on 13-8-12.
//  Copyright (c) 2013年 baobao. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "GUApiClient.h"
#import "GUCategory.h"
#import "GUService.h"

@interface GUCategoryService : NSObject<GUService>{
    GUApiRequest* _categoryListRequest;
    AFJSONRequestOperation* _categoryListRequestOperation;
    
    GUApiRequest* _searchCategoryRequest;
    AFJSONRequestOperation* _searchCategoryRequestOperation;
}


-(void) getCategoryList:(int) pageNum withCallback:(Callback)callback;


-(void)searchCategory:(NSString*)keyword forPage:(int) pageNum withCallback:(Callback)callback;


@end
