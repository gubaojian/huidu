//
//  GUFavoriteService.h
//  judu
//
//  Created by lurina on 13-8-13.
//  Copyright (c) 2013年 baobao. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "GUService.h"
#import "GUCategory.h"

@interface GUFavoriteService : NSObject{
     @private NSMutableArray* _categoryIds;
}

/**返回添加结果
 */
+(BOOL) addFavoriteCategory:(GUCategory*)category;

+(BOOL) removeFavoriteCategory:(NSNumber*) categoryId;

+(BOOL) isFavoriteCategory:(NSNumber*) categoryId;

+(GUCategory*) getCategoryById:(NSNumber*) categoryId;

/**返回category的标识符
 */
+(NSArray*) favoriteCategoryIds;


@end
