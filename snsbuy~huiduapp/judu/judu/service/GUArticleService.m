//
//  GUArticleService.m
//  judu
//
//  Created by lurina on 13-8-11.
//  Copyright (c) 2013年 baobao. All rights reserved.
//

#import "GUArticleService.h"
#import "GUResultDB.h"
#import "GUSwitch.h"



#define CallbackWapper(request)   Callback callbackWraper = ^(GUResult *result){\
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



@implementation GUArticleService

-(void) getArticleList:(int) pageNum withCallback:(Callback)callback{
    if (_articleListRequestOperation != nil) {
        [_articleListRequestOperation cancel];
        _articleListRequestOperation  = nil;
    }

    if (_articleListRequest != nil) {
        _articleListRequest = nil;
    }
    _articleListRequest =  [[GUApiRequest alloc] init];
    [_articleListRequest setApi:@"net.java.efurture.judu.ArticleList"];
    [_articleListRequest setModelClass:[GUArticle class]];
    [_articleListRequest addParameter:@"pageSize" value:@"10"];
    [_articleListRequest addParameter:@"pageNum" value:[NSString stringWithFormat:@"%d", pageNum]];
    
    
    
    CallbackWapper(_articleListRequest);
    
    _articleListRequestOperation = [GUApiClient execute:_articleListRequest withCallback:callbackWraper];
}


-(void) getArticleDetail:(NSNumber*) articleId withCallback:(Callback)callback{
    if (_articleDetailRequestOperation != nil) {
        [_articleDetailRequestOperation cancel];
        _articleDetailRequestOperation = nil;
    }
    if (_articleDetailRequest != nil) {
        _articleDetailRequest = nil;
    }
    
    _articleDetailRequest = [[GUApiRequest alloc] init];
    _articleDetailRequest.cachePolicy = NSURLRequestReloadIgnoringCacheData;
    _articleDetailRequest.api = @"net.java.efurture.judu.ArticleDetail";
    [_articleDetailRequest setModelClass:[GUArticle class]];
    [_articleDetailRequest setModelsClass:[NSDictionary dictionaryWithObjectsAndKeys:[GUCategory class], @"categoryList", nil]];
    [_articleDetailRequest addParameter:@"id" value:[NSString stringWithFormat:@"%@", articleId]];
    if ([GUSwitch isUseCDN]) {
        [_articleDetailRequest setServer:CDN_SERVER];
        [_articleDetailRequest setSign:SIGN_WITHOUT_TIMESTAMP];
    }
    
    GUResult* result = [GUResultDB resultForKey:_articleDetailRequest];
    if ([result isSuccess]) {
        if (callback) {
            callback(result);
        }
        return;
    }
    
    
    
    Callback callbackWraper = ^(GUResult *result){
        if (![result isSuccess]) {
            [GUSwitch switchUseServer];
        }
        if (callback) {
            callback(result);
        }
        dispatch_async(dispatch_get_global_queue(DISPATCH_QUEUE_PRIORITY_DEFAULT, 0), ^{
            if ([result isSuccess] && [result result]) {
                [GUResultDB setResult:result forRequest:_articleDetailRequest];
            }
        });
    };
    _articleDetailRequestOperation = [GUApiClient execute:_articleDetailRequest withCallback:callbackWraper];
}



-(void)getCategoryArticleList:(NSNumber*) categoryId forPage:(int) pageNum  withCallback:(Callback)callback{
    if (_categoryArticleListRequest != nil) {
        _categoryArticleListRequest = nil;
    }
    if (_categoryArticleListRequestOperation != nil) {
        [_categoryArticleListRequestOperation cancel];
        _categoryArticleListRequestOperation = nil;
    }
    
    if (categoryId == nil) {
        categoryId = [NSNumber numberWithInt:-1];
    }
    
    _categoryArticleListRequest = [[GUApiRequest alloc] init];
    _categoryArticleListRequest.api = @"net.java.efurture.judu.CategoryArticleList";
    [_categoryArticleListRequest setModelClass:[GUArticle class]];
    [_categoryArticleListRequest addParameter:@"pageNum" value:[NSString stringWithFormat:@"%d", pageNum]];
    [_categoryArticleListRequest addParameter:@"pageSize" value:@"20"];
    [_categoryArticleListRequest addParameter:@"categoryId" value:[categoryId stringValue]];
    
    
    CallbackWapper(_categoryArticleListRequest);
    
    _categoryArticleListRequestOperation = [GUApiClient execute:_categoryArticleListRequest withCallback:callbackWraper];
}


-(void)getFavoriteArticleList:(NSArray*)categoryIds forPage:(int) pageNum  withCallback:(Callback)callback{
    if (_favoriteCategoryArticleListRequest != nil) {
        _favoriteCategoryArticleListRequest = nil;
    }
    
    if (_favoriteCategoryArticleListRequestOperation != nil) {
        [_favoriteCategoryArticleListRequestOperation cancel];
        _favoriteCategoryArticleListRequestOperation = nil;
    }
    
    if (categoryIds == nil || [categoryIds count] == 0) {
        GUResult* result = [[GUResult alloc] init];
        [result setSuccess:NO];
        callback(result);
        return;
    }
    
    
    _favoriteCategoryArticleListRequest = [[GUApiRequest alloc] init];
    _favoriteCategoryArticleListRequest.api = @"net.java.efurture.judu.FavoriteCategoryArticleList";
    [_favoriteCategoryArticleListRequest setModelClass:[GUArticle class]];
    [_favoriteCategoryArticleListRequest addParameter:@"categoryIds" value:[categoryIds componentsJoinedByString:@","]];
    [_favoriteCategoryArticleListRequest addParameter:@"pageNum" value:[NSString stringWithFormat:@"%d", pageNum]];
    [_favoriteCategoryArticleListRequest addParameter:@"pageSize" value:@"20"];
    
    CallbackWapper(_favoriteCategoryArticleListRequest);
    
    _favoriteCategoryArticleListRequestOperation = [GUApiClient execute:_favoriteCategoryArticleListRequest withCallback:callbackWraper];
}



-(void) dealloc{
    [self cancelRequestOperation];
}

-(void) cancelRequestOperation{
    if (_favoriteCategoryArticleListRequestOperation != nil) {
        [_favoriteCategoryArticleListRequestOperation cancel];
        _favoriteCategoryArticleListRequestOperation = nil;
    }
    
    if (_favoriteCategoryArticleListRequest != nil) {
        _favoriteCategoryArticleListRequest = nil;
    }
    
    if (_categoryArticleListRequestOperation != nil) {
        [_categoryArticleListRequestOperation cancel];
        _categoryArticleListRequestOperation = nil;
    }
    
    if (_categoryArticleListRequest != nil) {
        _categoryArticleListRequest = nil;
    }
    
    if (_articleListRequestOperation != nil) {
        [_articleListRequestOperation cancel];
        _articleListRequestOperation  = nil;
    }
    
    if (_articleListRequest != nil) {
        _articleListRequest = nil;
    }
    
    if (_articleDetailRequestOperation != nil) {
        [_articleDetailRequestOperation cancel];
        _articleDetailRequestOperation = nil;
    }
    
    if (_articleDetailRequest != nil) {
        _articleDetailRequest = nil;
    }   
}

@end
