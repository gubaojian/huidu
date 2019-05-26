//
//  OurAppsViewController.h
//  
//
//  Created by 建红 杨 on 12-2-24.
//  Copyright (c) 2012年 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "GUConfig.h"
#import "ProductMan.h"


@protocol OurAppsVCDelegate;

@interface OurAppsViewController : UIViewController <UITableViewDataSource, UITableViewDelegate, ProductManDelegate> {
@private
    
    long long _appID;
    //
    UITableView *_tableviewAppList;
    //
    BOOL _biPhoneAppList;        //当前是否显示iPhone应用列表
    BOOL _bLoadiPadAppList;      //是否已经加载过iPad应用列表（切换到iPad时的判断）
    BOOL _bSendiPhoneAppList;    //是否已经发送过获取iPhone应用列表的请示
    BOOL _bReceiveiPhoneAppList; //是否已经收到iPhone应用列表数据
    BOOL _bSendiPadAppList;      //是否已经发送过获取iPad应用列表的请求
    BOOL _bReceiveiPadAppList;   //是否已经收到iPad应用列表数据
    //
    NSMutableArray *_marrayiPhoneAppItem;
    NSMutableArray *_marrayiPadAppItem;
    
    //产品管家
    ProductMan *_productMan;
    
    id <OurAppsVCDelegate> _delegate;
}

@property (nonatomic, assign) long long appID;
@property (nonatomic, assign) id <OurAppsVCDelegate> delegate;

@end



@protocol OurAppsVCDelegate <NSObject>

@optional

// 返回
- (void)ourAppsVCBack:(OurAppsViewController *)ourAppsVC;

// 应用项被点击
- (void)ourAppsVC:(OurAppsViewController *)ourAppsVC clickAppItem:(DataAppItem *)dataAppItem;

@end
