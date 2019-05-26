//
//  GUConfigService.h
//  judu
//
//  Created by lurina on 13-7-30.
//  Copyright (c) 2013年 baobao. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface GUConfigService : NSObject

+(BOOL) isShowArticleList;

+(void) setShowArticleList:(BOOL) articleList;


+(BOOL) isAutoDownloadArticleOn;

+(void) turnOnAutoDownloadArticle;

+(void) turnOffAutoDownloadArticle;


@end
