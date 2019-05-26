//
//  GUArticleViewCell.h
//  judu
//
//  Created by lurina on 13-7-28.
//  Copyright (c) 2013年 baobao. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface GUArticleViewCell : UITableViewCell{
     UILabel* _titleLabel;
     UILabel* _authorLabel;
     UILabel* _timeLabel;
     UILabel* _shortDescLabel;
}

@property(nonatomic, strong) UILabel* titleLabel;

@property (nonatomic, strong) UILabel* authorLabel;

@property (nonatomic, strong) UILabel* timeLabel;

@property (nonatomic, strong) UILabel* shortDescLabel;

@end
