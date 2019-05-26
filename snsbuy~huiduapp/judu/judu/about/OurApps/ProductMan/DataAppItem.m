//
//  DataAppItem.m
//  
//
//  Created by yangjianhong-MAC on 12-1-4.
//  Copyright (c) 2012年 __MyCompanyName__. All rights reserved.
//

#import "DataAppItem.h"


@implementation DataAppItem

@synthesize appID = _lldAppID;
@synthesize appIcon = _imageAppIcon;
@synthesize appURL = _strAppURL;
@synthesize appIconSize = _sizeIcon;
@synthesize appIconURL = _strIconURL;
@synthesize appName = _strAppName;
@synthesize appGenre = _strAppGenre;
@synthesize appVersion = _strAppVersion;
@synthesize appReleasedDate = _strAppReleasedDate;
@synthesize appPrice = _strAppPrice;

- (void)dealloc
{
    [_imageAppIcon release];
    [_strAppURL release];
    [_strIconURL release];
    [_strAppName release];
    [_strAppGenre release];
    [_strAppVersion release];
    [_strAppReleasedDate release];
    [_strAppPrice release];
    
    [super dealloc];
}

@end
