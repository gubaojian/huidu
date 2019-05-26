//
//  GUArticleCollectService.h
//  huidu
//
//  Created by lurina on 14-4-23.
//  Copyright (c) 2014年 baobao. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "GUService.h"
#import "GUArticle.h"

@interface GUArticleCollectService : NSObject{
    @private NSMutableArray* _collectArticleIds;

}

+(BOOL) isCollectArticle:(NSNumber*) articleId;

+(void) addCollectArticle:(GUArticle*) article;

+(BOOL) removeCollectArticle:(GUArticle*) article;

+(BOOL) removeCollectArticle:(GUArticle*) article  position:(NSUInteger)position;

+(GUArticle*) getCollectArticleById:(NSNumber*) articleId;

+(NSArray*)articleIds;

+(GUArticle*)articleAtIndex:(int)index;

+(int)articleCount;


@end
