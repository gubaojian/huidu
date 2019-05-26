//
//  GUResult.h
//  layer
//
//  Created by world on 13-7-29.
//  Copyright (c) 2013年 world. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "JSONModel.h"
#import "GUResultCode.h"

/**
 *  业务操作的结果封装
 */
@interface GUResult : JSONModel{
    @private BOOL  _success;
    @private GUResultCode<Optional>* _resultCode;
    @private id<Optional>  _result;
   @private  NSMutableDictionary<Optional>* _models;
}


/**
 * 标记业务操作是否成功执行
 */
@property(nonatomic) BOOL success;

/**
 * 业务操作结果号码
 */
@property(nonatomic, strong) GUResultCode<Optional>* resultCode;

/**
 * 业务操作返回的数据模型及业务操作结果，支持对象数组类型。
 */
@property(nonatomic, strong) id<Optional> result;

/**
 *  业务操作符合的数据模型，比如业务操作返回多种字段，则可将其放入models中
 */
@property(nonatomic, strong) NSMutableDictionary<Optional>* models;

/**
 *  返回操作是否成功
 */
-(BOOL)isSuccess;

/**
 * 设置操作结果
 */
-(void)setSuccess:(BOOL)success;

/**
 * 操作返回的默认数据模型
 */
-(id) result;


-(BOOL) hasMore;


@end
