//
//  GUAutoDownloadService.h
//  huidu
//
//  Created by lurina on 13-12-2.
//  Copyright (c) 2013年 baobao. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "GUService.h"
#import "GUArticleService.h"

@interface GUAutoDownloadService : NSObject<GUService>{
      dispatch_queue_t  _articleDownloadQueue;
    
      BOOL  _wifiDownloadDone;
}

-(void) autoDownloadHomeArticles:(NSArray*)articles;

@end
