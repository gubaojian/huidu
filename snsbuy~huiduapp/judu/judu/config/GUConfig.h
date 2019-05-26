//
//  GUSwitch.h
//  hscode
//
//  Created by lurina on 12-12-13.
//  Copyright (c) 2012年 baobao. All rights reserved.
//

#import <Foundation/Foundation.h>

#ifndef GUConfig_h
#define GUConfig_h

/**app store id参数 */

#ifndef APP_STORE_ID
#define APP_STORE_ID   692997116
#endif



#ifndef DEVELOPER_APP_INFO_ID
#define DEVELOPER_APP_INFO_ID   579919958
#endif

/**app store id参数结束*/

/**copy bundle的文件到文档中**/

#ifndef COPY_FROM_BUNDLE_TO_DOCUMENT
#define COPY_FROM_BUNDLE_TO_DOCUMENT  NO
#endif
/**广告参数 */
#ifndef SHOW_ADMOB
#define SHOW_ADMOB  NO
#endif

#ifndef GU_ADMOB_IPAD_UNIT_ID
#define GU_ADMOB_IPAD_UNIT_ID   @"a150cf20df9e60d"
#endif

#ifndef GU_ADMOB_IPONE_UNIT_ID
#define GU_ADMOB_IPONE_UNIT_ID  @"a150cf1ef892e27"
#endif

#ifndef UM_APP_KEY
#define UM_APP_KEY  @"51fa51af56240b475b0043b8"
#endif

/**广告参数结束 */

/** in-apppurchase 参数 */
#ifndef APP_VERIFY_SECRET
   #define APP_VERIFY_SECRET    @"dccdf8c7583a4058b0f1d24a56635bef"
#endif

/** 商品标示符 */
#ifndef APP_PURCHASE_IDENTIFY
  #define  APP_PURCHASE_IDENTIFY   @"RemoveAppAdmob"
#endif

/** in-apppurchase 参数结束 */


#ifndef GU_NOTIFY_APP_WILL_ENTER_BACKGROUND
#define GU_NOTIFY_APP_WILL_ENTER_ BACKGROUND   @"GU_NOTIFY_APP_WILL_ENTER_BACKGROUND"
#endif

#ifndef GU_NOTIFY_APP_DID_BACOME_ACTIVE
#define GU_NOTIFY_APP_DID_BACOME_ACTIVE  @"GU_NOTIFY_APP_DID_BACOME_ACTIVE"
#endif


#ifndef DATABASE_FILE_NAME
#define DATABASE_FILE_NAME   @"IntroduceGuide"
#endif

#ifndef GU_ZH_HANS
#define GU_ZH_HANS  @"zh-Hans"
#endif




//最大不超过5MB
#ifndef GU_MAX_FILE_SIZE
#define GU_MAX_FILE_SIZE     (1024*1024*5)
#endif


#ifndef GU_PAGE_START_NUM
#define GU_PAGE_START_NUM     (1)
#endif


#ifndef GLOBAL_BACKGROUND_COLOR
#define GLOBAL_BACKGROUND_COLOR     [UIColor colorWithHexString:@"EEEEEE"]
#endif

#import "GUProduct.h"

#import "GULog.h"

#endif