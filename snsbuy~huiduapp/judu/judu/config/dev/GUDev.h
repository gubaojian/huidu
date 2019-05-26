//
//  GUDev.h
//  Seven
//
//  Created by lurina on 12-12-13.
//  Copyright (c) 2012年 baobao. All rights reserved.
//

#ifndef Seven_GUDev_h
#define Seven_GUDev_h


#ifndef LOG_LEVEL
#define LOG_LEVEL  0
#endif

#ifndef APP_ADMOB_TEST_VERSION
#define APP_ADMOB_TEST_VERSION
#endif

#ifndef APP_ADMOB_TEST_SIMULATOR_VERSION
#define APP_ADMOB_TEST_SIMULATOR_VERSION
#endif

#ifdef DEBUG
#undef DEBUG
#endif

#ifndef ITMS_PROD_VERIFY_RECEIPT_URL
#define ITMS_PROD_VERIFY_RECEIPT_URL        @"https://sandbox.itunes.apple.com/verifyReceipt"
#endif

#define NSLog(...) NSLog(__VA_ARGS__)


#endif
