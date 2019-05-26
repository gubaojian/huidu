//
//  Common-ResultCode.h
//  layer
//
//  Created by world on 13-7-29.
//  Copyright (c) 2013年 world. All rights reserved.
//



#ifndef Api_Client_Common_ResultCode_h
#define Api_Client_Common_ResultCode_h
#import "GUResultCode.h"
#import "GUConfig.h"

#define RESULT_CODE_EMPTY_JSON_DATA_RESULT_ERROR  \
       [GUResultCode code:-2 message:@"服务器未响应或响应JSON为空或无效"]

#define RESULT_CODE_API_NOT_EXIST_ERROR  \
[GUResultCode code:-3 message:@"请求Api不存在，请在Api.plist配置此Api"]

#define RESULT_CODE_CREATE_HTTP_REQUEST_ERROR  \
[GUResultCode code:-5 message:@"创建HTTP请求对象失败"]

#define RESULT_CODE_CONVERT_JSON_EXCEPTION_ERROR  \
       [GUResultCode code:-4  message:@"转换JSON异常"]

#define RESULT_CODE_RESULT_MODEL_INIT_FROM_JSON_ERROR \
       [GUResultCode code:-8  message:@"转换JSONweiResult失败"]

#define RESULT_CODE_DEFAULT_MODEL_INIT_FROM_JSON_ERROR \
       [GUResultCode code:-9 message:@"转换JSON为Result的result属性失败"]

#define RESULT_CODE_MODELS_INIT_FROM_JSON_ERROR  \
    [GUResultCode code:-10 message:@"转换JSON为Result的models属性失败"]

//#define RESULT_CODE_JSON_EMPTYF_RESULT_CODE_ERROR   \
      [GUResultCode code:-12 message:@"JSON数据未包含ResultCode"]

#endif
