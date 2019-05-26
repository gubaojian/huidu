//
//  GUSearchBar.h
//  judu
//
//  Created by lurina on 13-7-28.
//  Copyright (c) 2013年 baobao. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface GUSearchBar : UISearchBar{
    @private UIButton* _searchCancelButton;
    @private UITextField* _searchTextField;
}

@property(nonatomic,strong) UITextField* searchTextField;

@end
