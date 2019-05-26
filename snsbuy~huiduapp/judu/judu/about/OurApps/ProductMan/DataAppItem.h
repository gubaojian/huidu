//
//  DataAppItem.h
//  
//
//  Created by yangjianhong-MAC on 12-1-4.
//  Copyright (c) 2012年 __MyCompanyName__. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "GUConfig.h"


@interface DataAppItem : NSObject {
@private
    //
    long long int _lldAppID;        //ID
    NSString *_strAppURL;           //应用信息url
    UIImage *_imageAppIcon;         //应用图标
    CGSize _sizeIcon;               //图标尺寸
    NSString *_strIconURL;          //图标url
    NSString *_strAppName;          //名称
    NSString *_strAppGenre;         //类型
    NSString *_strAppVersion;       //版本
    NSString *_strAppReleasedDate;  //发布日期
    NSString *_strAppPrice;         //价格
}

@property (nonatomic, assign) long long int appID;
@property (nonatomic, copy) NSString *appURL;
@property (nonatomic, retain) UIImage *appIcon;
@property (nonatomic, assign) CGSize appIconSize;
@property (nonatomic, copy) NSString *appIconURL;
@property (nonatomic, copy) NSString *appName;
@property (nonatomic, copy) NSString *appGenre;
@property (nonatomic, copy) NSString *appVersion;
@property (nonatomic, copy) NSString *appReleasedDate;
@property (nonatomic, copy) NSString *appPrice;

@end
