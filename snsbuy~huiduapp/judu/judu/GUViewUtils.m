//
//  GUViewUtils.m
//  huidu
//
//  Created by lurina on 13-10-30.
//  Copyright (c) 2013年 baobao. All rights reserved.
//

#import "GUViewUtils.h"
#import "GUSystemUtils.h"
#import "UIDevice+Custom.h"

@implementation GUViewUtils


+(CGFloat)viewStartOffset{
    if ([GUSystemUtils isIos7]) {
        return 20;
    }
    return 0;
}


+(UIImage*) tabbarNormalImage:(NSString*)code{
    FAKFontAwesome *home = [FAKFontAwesome iconWithCode:code size:25];
    
    [home addAttribute:NSForegroundColorAttributeName value:[UIColor colorWithRed:171/255.0f green:171/255.0f blue:171/255.0f alpha:1.0f]];
    UIImage* homeSelect = [home imageWithSize:CGSizeMake(25, 25)];
    return homeSelect;
}

+(UIImage*) tabbarSelectImage:(NSString*)code{
    FAKFontAwesome *home = [FAKFontAwesome  iconWithCode:code size:25];
    [home addAttribute:NSForegroundColorAttributeName value:[UIColor colorWithRed:246/255.0f green:144/255.0f blue:6/255.0f alpha:1.0f]];
    UIImage* homeSelect = [home imageWithSize:CGSizeMake(25, 25)];
    return homeSelect;
}

+(UIImage*) cell25Image:(NSString*)code{
    FAKFontAwesome *home = [FAKFontAwesome  iconWithCode:code size:25];
    [home addAttribute:NSForegroundColorAttributeName value:[UIColor colorWithRed:102/255.0f green:102/255.0f blue:102/255.0f alpha:1.0f]];
    UIImage* homeSelect = [home imageWithSize:CGSizeMake(25, 25)];
    return homeSelect;
}

+(UIImage*) cell22Image:(NSString*)code{
    FAKFontAwesome *home = [FAKFontAwesome  iconWithCode:code size:22];
    [home addAttribute:NSForegroundColorAttributeName value:[UIColor colorWithRed:102/255.0f green:102/255.0f blue:102/255.0f alpha:1.0f]];
    UIImage* homeSelect = [home imageWithSize:CGSizeMake(22, 22)];
    return homeSelect;
}


+(UIImage*) cell15Image:(NSString*)code{
    FAKFontAwesome *home = [FAKFontAwesome  iconWithCode:code size:15];
    [home addAttribute:NSForegroundColorAttributeName value:[UIColor colorWithRed:102/255.0f green:102/255.0f blue:102/255.0f alpha:1.0f]];
    UIImage* homeSelect = [home imageWithSize:CGSizeMake(15, 15)];
    return homeSelect;
}


+(UIImage*) cell20Image:(NSString*)code{
    FAKFontAwesome *home = [FAKFontAwesome  iconWithCode:code size:18];
    [home addAttribute:NSForegroundColorAttributeName value:[UIColor colorWithRed:102/255.0f green:102/255.0f blue:102/255.0f alpha:1.0f]];
    UIImage* homeSelect = [home imageWithSize:CGSizeMake(18, 18)];
    return homeSelect;
}


+(float) screenWidth{
    float width = [[UIScreen mainScreen] bounds].size.width;
    if (width > 0) {
        return width;
    }

    if ([GUSystemUtils isIpad]) {
        return 768;
    }
    return 320;

}

@end
