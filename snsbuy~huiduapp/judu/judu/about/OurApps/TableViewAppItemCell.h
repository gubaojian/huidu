//
//  TableViewAppItemCell.h
//  
//
//  Created by Jianhong Yang on 12-1-6.
//  Copyright (c) 2012年 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "GUConfig.h"


#define     HEIGHT_APPITEMCELL          100.0f

@class DataAppItem;

@interface TableViewAppItemCell : UITableViewCell {
@private
    //
    DataAppItem *_dataAppItem;
    //
    UIImageView *_viewAppIcon;
    UILabel *_labelAppName;
    UILabel *_labelAppVersion;
    UILabel *_labelAppReleasedDate;
    UILabel *_labelAppPrice;
}

@property (nonatomic, retain) DataAppItem *dataAppItem;
@property (nonatomic, assign) UIImage *appIcon;

@end
