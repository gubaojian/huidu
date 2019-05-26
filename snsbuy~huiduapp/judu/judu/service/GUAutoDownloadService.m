//
//  GUAutoDownloadService.m
//  huidu
//
//  Created by lurina on 13-12-2.
//  Copyright (c) 2013年 baobao. All rights reserved.
//

#import "GUAutoDownloadService.h"
#import "GUFavoriteService.h"
#import "GUConfigService.h"
#import "Reachability.h"
#import "GUResultDB.h"
#import "GUSwitch.h"
#import "GUHttpClient.h"

@implementation GUAutoDownloadService


-(void) autoDownloadHomeArticles:(NSArray*)articles{
    if (![GUConfigService isAutoDownloadArticleOn]) {
        return;
    }
    
    //仅在wifi环境下载
    if ([[Reachability reachabilityForLocalWiFi] currentReachabilityStatus] != ReachableViaWiFi) {
        return;
    }
    if (_wifiDownloadDone) {
        return;
    }
    _wifiDownloadDone = true;
    _articleDownloadQueue = dispatch_queue_create("huidu.article.download", NULL);
    GUAutoDownloadService* weakSelf = self;
    dispatch_async(_articleDownloadQueue, ^{
        [weakSelf downloadHomeArticle: articles];
    });
}


-(void) downloadHomeArticle:(NSArray*)articles{
    @try {
        NSArray* homeArticleList = articles;
        if ([homeArticleList count] == 0) {
            return;
        }
        for (GUArticle* article in homeArticleList) {
            GUApiRequest* articleDetailRequest = [[GUApiRequest alloc] init];
            articleDetailRequest.cachePolicy = NSURLRequestReloadIgnoringCacheData;
            articleDetailRequest.api = @"net.java.efurture.judu.ArticleDetail";
            [articleDetailRequest setModelClass:[GUArticle class]];
            [articleDetailRequest setModelsClass:[NSDictionary dictionaryWithObjectsAndKeys:[GUCategory class], @"categoryList", nil]];
            [articleDetailRequest addParameter:@"id" value:[NSString stringWithFormat:@"%@", [article id]]];
            [articleDetailRequest setServer:CDN_SERVER];
            [articleDetailRequest setSign:SIGN_WITHOUT_TIMESTAMP];
            
            GUResult* articleDetailResult = [GUResultDB resultForKey:articleDetailRequest];
            if ([articleDetailResult  isSuccess]) {
                continue;
            }
            
            NSMutableURLRequest *request = [[GUHttpClient shareClient] requestWithApiRequest:articleDetailRequest];
            NSURLResponse* response = nil;
            NSError* error = nil;
            NSData *data = [NSURLConnection sendSynchronousRequest:request returningResponse:&response error:&error];
            NSLog(@"auto download request %@ %@", [request URL], [article id]);
            if (error) {
                NSLog(@"auto download request  error %@", error);
                break;
            }
            NSDictionary* json = [NSJSONSerialization JSONObjectWithData:data options:NSJSONReadingMutableContainers error:&error];
            if (error || json == nil) {
                NSLog(@"auto download request json error %@", error);
                break;
            }
            NSLog(@"auto download success %@ %@", [request URL], [article id]);
            GUResult* result  = [GUParseUtils parseResult:json withContext:articleDetailRequest];
            if (![result isSuccess]) {
                break;
            }
            if ([result isSuccess] && [result result]) {
                [GUResultDB setResult:result forRequest:articleDetailRequest];
            }
        }

    }
    @catch (NSException *exception) {
        NSLog(@"auto download exception %@", exception);
    }
}



-(void) dealloc{
    [self cancelRequestOperation];
}

-(void) cancelRequestOperation{
    _wifiDownloadDone = NO;
    _articleDownloadQueue = NULL;
}

@end
