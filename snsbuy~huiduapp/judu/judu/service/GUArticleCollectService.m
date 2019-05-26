//
//  GUArticleCollectService.m
//  huidu
//
//  Created by lurina on 14-4-23.
//  Copyright (c) 2014年 baobao. All rights reserved.
//

#import "GUArticleCollectService.h"

#define DB_FILE    "my_collect_article.sqlite"

@implementation GUArticleCollectService

+(GUArticleCollectService*) shareInstance{
    static  GUArticleCollectService* shareInstance = nil;
    static dispatch_once_t onceToken;
    dispatch_once(&onceToken, ^{
        shareInstance = [[GUArticleCollectService alloc] init];
    });
    return shareInstance;
}

+(BOOL) isCollectArticle:(NSNumber*) articleId{
    if (articleId == nil) {
        return NO;
    }
    return [[[GUArticleCollectService shareInstance] collectArticleIds] containsObject:articleId];
}

+(void) addCollectArticle:(GUArticle*) article{
    if (article == nil
        || [article id] == nil
        || [article title] == nil
        || [article shortDesc] == nil
        || [article publishTime] == nil) {
        return;
    }
    
    if ([GUArticleCollectService isCollectArticle:[article id]]) {
        return;
    }
    
    NSUserDefaults* defaults = [NSUserDefaults standardUserDefaults];
    NSDictionary* categoryDic = [NSDictionary dictionaryWithObjectsAndKeys:[article id], @"id",
                                 [article author], @"author", [article shortDesc], @"shortDesc",
                                 [article title], @"title", [article publishTime], @"publishTime", nil];
    [defaults setObject: categoryDic forKey: [[article id] stringValue]];
    
    NSMutableArray* collectArticleIds = [[GUArticleCollectService shareInstance] collectArticleIds];
    [collectArticleIds insertObject:[article id] atIndex:0];
    [[GUArticleCollectService shareInstance] saveCollectArticleIds];
}

+(GUArticle*) getCollectArticleById:(NSNumber*) articleId{
    if (articleId == nil) {
        return nil;
    }
    
    NSUserDefaults* defaults = [NSUserDefaults standardUserDefaults];
    NSDictionary* articleDic = [defaults objectForKey:[articleId stringValue]];
    if (articleDic == nil) {
        return nil;
    }
    
    GUArticle* article = [[GUArticle alloc] init];
    article.id = [articleDic objectForKey:@"id"];
    article.author = [articleDic objectForKey:@"author"];
    article.shortDesc = [articleDic objectForKey:@"shortDesc"];
    article.title = [articleDic objectForKey:@"title"];
    article.publishTime =[articleDic objectForKey:@"publishTime"];
    return article;
}



+(BOOL) removeCollectArticle:(GUArticle*) article{
    if (article == nil
        || [article id] == nil) {
        return NO;
    }
    NSUserDefaults* defaults = [NSUserDefaults standardUserDefaults];
    [defaults removeObjectForKey:[[article id] stringValue]];
    NSMutableArray* collectArticleIds = [[GUArticleCollectService shareInstance] collectArticleIds];
    [collectArticleIds removeObject:[article id]];
    [[GUArticleCollectService shareInstance] saveCollectArticleIds];
    return YES;
}

+(BOOL) removeCollectArticle:(GUArticle*) article  position:(NSUInteger)position{
    if (![self removeCollectArticle:article]) {
        [[[GUArticleCollectService shareInstance] collectArticleIds] removeObjectAtIndex:position];
        [[GUArticleCollectService shareInstance] saveCollectArticleIds];
    }
    return YES;
}

+(NSArray*)articleIds{
    return [[GUArticleCollectService shareInstance] collectArticleIds];
}

+(GUArticle*)articleAtIndex:(int)index{
    NSArray* array = [[GUArticleCollectService shareInstance] collectArticleIds];
    if ([array count] <= index) {
        return nil;
    }
    NSNumber* articleId = [array objectAtIndex:index];
    if (articleId == nil) {
        return nil;
    }
    return [GUArticleCollectService getCollectArticleById:articleId];
}

+(int)articleCount{
    NSArray* array = [[GUArticleCollectService shareInstance] collectArticleIds];
    return [array count];
}


-(NSMutableArray*)collectArticleIds{
    if (_collectArticleIds == nil) {
        NSUserDefaults* defaults = [NSUserDefaults standardUserDefaults];
        id  datas = [defaults objectForKey:@"myCollectArticleIds"];
        if(datas == nil){
            _collectArticleIds = [[NSMutableArray alloc] initWithCapacity:4];
        }else{
            _collectArticleIds = [[NSMutableArray alloc] initWithArray:datas];
        }
    }
    return _collectArticleIds;
}

-(void) saveCollectArticleIds{
    NSUserDefaults* defaults = [NSUserDefaults standardUserDefaults];
    [defaults setObject:_collectArticleIds forKey:@"myCollectArticleIds"];
}


@end
