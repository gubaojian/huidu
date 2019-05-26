//
//  GUReaderViewController.h
//  judu
//
//  Created by lurina on 13-7-31.
//  Copyright (c) 2013年 baobao. All rights reserved.
//

#import "GUBaseViewController.h"
#import <GoogleMobileAds/GADBannerView.h>
#import <GoogleMobileAds/GADRequest.h>

#define  kAdViewSizeDefaultHeight  80
#define ADMOB_UNIT_ID   @""


@interface GUReaderViewController : GUBaseViewController{
    GADBannerView* _adBannerView;
}

@end
