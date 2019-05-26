//
//  GUArticleService.h
//  judu
//
//  Created by lurina on 13-8-11.
//  Copyright (c) 2013年 baobao. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "GUApiClient.h"
#import "GUArticle.h"
#import "GUCategory.h"
#import "GUService.h"


@interface GUArticleService : NSObject<GUService>{
     GUApiRequest* _articleListRequest;
     AFJSONRequestOperation* _articleListRequestOperation;
    
    GUApiRequest* _articleDetailRequest;
    AFJSONRequestOperation* _articleDetailRequestOperation;
    
    
    GUApiRequest* _categoryArticleListRequest;
    AFJSONRequestOperation* _categoryArticleListRequestOperation;
    
    GUApiRequest* _favoriteCategoryArticleListRequest;
    AFJSONRequestOperation* _favoriteCategoryArticleListRequestOperation;
    
   
}

-(void) getArticleList:(int) pageNum withCallback:(Callback)callback;

-(void) getArticleDetail:(NSNumber*) articleId withCallback:(Callback)callback;


-(void)getCategoryArticleList:(NSNumber*) categoryId forPage:(int) pageNum  withCallback:(Callback)callback;


-(void)getFavoriteArticleList:(NSArray*)categoryIds forPage:(int) pageNum  withCallback:(Callback)callback;






@end
