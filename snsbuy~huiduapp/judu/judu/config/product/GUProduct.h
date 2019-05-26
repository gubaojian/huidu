//
//  GUProduct.h
//  Seven
//
//  Created by lurina on 12-12-13.
//  Copyright (c) 2012年 baobao. All rights reserved.
//

#ifndef Seven_GUProduct_h
#define Seven_GUProduct_h

#ifndef LOG_LEVEL
#define LOG_LEVEL  30
#endif

#ifdef APP_ADMOB_TEST_VERSION
#undef APP_ADMOB_TEST_VERSION
#endif

#ifdef APP_ADMOB_TEST_SIMULATOR_VERSION
#undef APP_ADMOB_TEST_SIMULATOR_VERSION
#endif

#ifdef DEBUG
#undef DEBUG
#endif


#ifndef ITMS_PROD_VERIFY_RECEIPT_URL
#define ITMS_PROD_VERIFY_RECEIPT_URL        @"https://buy.itunes.apple.com/verifyReceipt"
#endif


//禁用Log
#define NSLog(...) {}


#endif
