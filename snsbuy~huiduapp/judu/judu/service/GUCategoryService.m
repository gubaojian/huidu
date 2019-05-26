//
//  GUCategoryService.m
//  judu
//
//  Created by lurina on 13-8-12.
//  Copyright (c) 2013年 baobao. All rights reserved.
//

#import "GUCategoryService.h"
#import "GUResultDB.h"

#define CallbackWapper(request)  Callback callbackWraper = ^(GUResult *result){\
if (callback) {\
if (![result isSuccess]) {\
GUResult *oldCache = [GUResultDB resultForKey:request];\
if (oldCache != nil) {\
result = oldCache;\
}\
}\
callback(result);\
}\
dispatch_async(dispatch_get_global_queue(DISPATCH_QUEUE_PRIORITY_DEFAULT, 0), ^{\
if ([result isSuccess] && [result result]) {\
[GUResultDB setResult:result forRequest:request];\
}\
});\
};


@implementation GUCategoryService

-(void) getCategoryList:(int) pageNum withCallback:(Callback)callback{
    if (_categoryListRequestOperation != nil) {
        [_categoryListRequestOperation cancel];
        _categoryListRequestOperation = nil;
    }
    if (_categoryListRequest != nil) {
        _categoryListRequest = nil;
    }
    
    _categoryListRequest = [[GUApiRequest alloc] init];
    _categoryListRequest.api = @"net.java.efurture.judu.CategoryList";
    [_categoryListRequest setModelClass:[GUCategory class]];
    [_categoryListRequest addParameter:@"pageNum" value:[NSString stringWithFormat:@"%d", pageNum]];
    [_categoryListRequest addParameter:@"pageSize" value:@"40"];
    
    CallbackWapper(_categoryListRequest);
    
    
    _categoryListRequestOperation = [GUApiClient execute:_categoryListRequest withCallback:callbackWraper];
}

-(void)searchCategory:(NSString*)keyword forPage:(int) pageNum withCallback:(Callback)callback{
    if (_searchCategoryRequest) {
        _searchCategoryRequest = nil;
    }
    
    if (_searchCategoryRequestOperation) {
        [_searchCategoryRequestOperation cancel];
        _searchCategoryRequestOperation = nil;
    }
    
    if (keyword == nil) {
        keyword = @"";
    }
    
    _searchCategoryRequest = [[GUApiRequest alloc] init];
    _searchCategoryRequest.api = @"net.java.efurture.judu.SearchCategory";
    [_searchCategoryRequest setModelClass:[GUCategory class]];
    [_searchCategoryRequest addParameter:@"keyword" value:keyword];
    [_searchCategoryRequest addParameter:@"pageNum" value:[NSString stringWithFormat:@"%d", pageNum]];
    [_searchCategoryRequest addParameter:@"pageSize" value:@"40"];
    _searchCategoryRequestOperation = [GUApiClient execute:_searchCategoryRequest withCallback:callback];
}


-(void) dealloc{
    [self cancelRequestOperation];
}


-(void) cancelRequestOperation{
    if (_categoryListRequest) {
        _categoryListRequest = nil;
    }
    
    if (_categoryListRequestOperation) {
        [_categoryListRequestOperation cancel];
        _categoryListRequestOperation = nil;
    }
    
    if (_searchCategoryRequest) {
        _searchCategoryRequest = nil;
    }
    
    if (_searchCategoryRequestOperation) {
        [_searchCategoryRequestOperation cancel];
        _searchCategoryRequestOperation = nil;
    }
}


@end
