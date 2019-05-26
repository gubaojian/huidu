//
//  GUResultCode.h
//  layer
//
//  Created by world on 13-7-29.
//  Copyright (c) 2013年 world. All rights reserved.
//

#import "JSONModel.h"

/**
 *  业务操作的结果代码
 */
@interface GUResultCode : JSONModel{
   @private int _code;
   @private NSString* _message;
}
/**
 * 通过代码码及信息创建结果代码
 */
+(GUResultCode*) code:(int) code message:(NSString*) message;

/**
 *  业务操作码
 */
@property(nonatomic) int code;

/**
 *  业务操作码对应描述信息
 */
@property(nonatomic, strong) NSString* message;


@end
