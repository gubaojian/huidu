//
//  GUFavoriteService.m
//  judu
//
//  Created by lurina on 13-8-13.
//  Copyright (c) 2013年 baobao. All rights reserved.
//

#import "GUFavoriteService.h"

@implementation GUFavoriteService

+(GUFavoriteService*) shareInstance{
    static  GUFavoriteService* shareInstance = nil;
    static dispatch_once_t onceToken;
    dispatch_once(&onceToken, ^{
        shareInstance = [[GUFavoriteService alloc] init];
    });
    return shareInstance;
}

+(BOOL) addFavoriteCategory:(GUCategory*)category{
    if(category == nil || [category id] == nil || [category name] == nil){
        return NO;
    }
    
    if ([GUFavoriteService isFavoriteCategory:[category id]]) {
        return NO;
    }
    NSUserDefaults* defaults = [NSUserDefaults standardUserDefaults];
    NSDictionary* categoryDic = [NSDictionary dictionaryWithObjectsAndKeys:[category id], @"id", [category name], @"name", nil];
    [defaults setObject: categoryDic forKey:[[category id] stringValue]];
    
    NSMutableArray* categoryIds = [[GUFavoriteService shareInstance] categoryIds];
    [categoryIds addObject:[category id]];
    [[GUFavoriteService shareInstance] saveCategoryIds];
    return YES;
}

+(BOOL) removeFavoriteCategory:(NSNumber*) categoryId{
    if (categoryId == nil) {
        return YES;
    }
    if (![GUFavoriteService isFavoriteCategory:categoryId]) {
        return YES;
    }
    
    NSUserDefaults* defaults = [NSUserDefaults standardUserDefaults];
    [defaults removeObjectForKey:[categoryId stringValue]];
    NSMutableArray* categoryIds = [[GUFavoriteService shareInstance] categoryIds];
    [categoryIds removeObject:categoryId];
    [[GUFavoriteService shareInstance] saveCategoryIds];
    return YES;
}

+(BOOL) isFavoriteCategory:(NSNumber*) categoryId{
    if (categoryId == nil) {
        return NO;
    }
    NSMutableArray* categoryIds = [[GUFavoriteService shareInstance] categoryIds];
    return [categoryIds containsObject:categoryId];
}

+(GUCategory*) getCategoryById:(NSNumber*) categoryId{
    if (categoryId == nil) {
        return nil;
    }
    
    NSUserDefaults* defaults = [NSUserDefaults standardUserDefaults];
    NSDictionary* categoryDic = [defaults objectForKey:[categoryId stringValue]];
    if (categoryDic == nil) {
        return nil;
    }
    
    GUCategory* category = [[GUCategory alloc] init];
    category.id = [categoryDic objectForKey:@"id"];
    category.name = [categoryDic objectForKey:@"name"];
    return category;
}

/**返回category的标识符
 */
+(NSArray*) favoriteCategoryIds{
   return [[GUFavoriteService shareInstance] categoryIds];
}

-(NSMutableArray*)categoryIds{
    if (_categoryIds == nil) {
         NSUserDefaults* defaults = [NSUserDefaults standardUserDefaults];
        _categoryIds = [[NSMutableArray alloc] initWithArray:[defaults objectForKey:@"categoryIds"]];
        if (_categoryIds == nil) {
            _categoryIds = [[NSMutableArray alloc] initWithCapacity:4];
        }
    }
    return _categoryIds;
}

-(void) saveCategoryIds{
    NSUserDefaults* defaults = [NSUserDefaults standardUserDefaults];
    [defaults setObject:[self categoryIds] forKey:@"categoryIds"];
}




@end
