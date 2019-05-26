//
//  GUSearchDisplayView.m
//  judu
//
//  Created by lurina on 13-7-28.
//  Copyright (c) 2013年 baobao. All rights reserved.
//

#import "GUSearchDisplayView.h"
#import "UIColor+Expanded.h"

@implementation GUSearchDisplayView

@synthesize searchResultsTableView = _searchResultsTableView;

- (id)initWithFrame:(CGRect)frame
{
    self = [super initWithFrame:frame];
    if (self) {
        // Initialization code
        self.backgroundColor = [UIColor colorWithRed:0 green:0 blue:0 alpha:0.5];
        [self searchResultsTableView];
        _searchState = END;
    }
    return self;
}

/*
// Only override drawRect: if you perform custom drawing.
// An empty implementation adversely affects performance during animation.
- (void)drawRect:(CGRect)rect
{
    // Drawing code
}
*/
-(UITableView*)searchResultsTableView{
    if (_searchResultsTableView == nil) {
        _searchResultsTableView = [[UITableView alloc] initWithFrame:CGRectMake(0, 0, self.frame.size.width, self.frame.size.height) style:UITableViewStylePlain];
        _searchResultsTableView.backgroundColor = [UIColor colorWithHexString:@"e7e8e9"];
        _searchResultsTableView.separatorStyle = UITableViewCellSeparatorStyleNone;
        _searchResultsTableView.hidden = YES;
        [self addSubview:_searchResultsTableView];
    }
    return _searchResultsTableView;
}


-(UILabel*)emptyTips{
    if (_emptyTips == nil) {
        _emptyTips = [[UILabel alloc] initWithFrame:CGRectMake(0, 0,  self.frame.size.width, 80)];
        _emptyTips.backgroundColor = [UIColor clearColor];
        _emptyTips.text = @"没有找到相关主题";
        _emptyTips.textAlignment = NSTextAlignmentCenter;
        _emptyTips.textColor = [UIColor colorWithHexString:@"565656"];
    }
    return _emptyTips;
}


-(void) setActiveSearchForState:(SearchState) state{
    switch (state) {
        case END:[self searchForEndState];
            break;
        case SEARCHING: [self searchForSearchState];
            break;
        case DISPLAY_RESULTS:[self searchForDisplayResultsState];
            break;
        default:
            break;
    }
    _searchState = state;
}

-(SearchState) searchState{
    return _searchState;
}


-(void) searchForDisplayResultsState{
    [self setHidden:NO];
    [[self searchResultsTableView] setHidden:NO];
    [self removeGestureRecognizer:[self tapHiddenKeyboardGesture]];
    [self.superview bringSubviewToFront:self];
    if ([self searchResultsTableView].dataSource) {
        int count = [[self searchResultsTableView].dataSource tableView:[self searchResultsTableView] numberOfRowsInSection:0];
        if (count == 0) {
            [self searchResultsTableView].tableHeaderView = [self emptyTips];
        }else{
            [self searchResultsTableView].tableHeaderView = [[UIView alloc] initWithFrame:CGRectZero];
        }
    }
    [[self searchResultsTableView] reloadData];
}

-(void) searchForSearchState{
    [self setHidden:NO];
    [self addGestureRecognizer:[self tapHiddenKeyboardGesture]];
    [[self searchResultsTableView] setHidden:YES];
    [self.superview bringSubviewToFront:self];
}


-(void) searchForEndState{
    [self setHidden:YES];
    [self removeGestureRecognizer:[self tapHiddenKeyboardGesture]];
}

-(void) setSearchBar:(UISearchBar*)searchBar{
    _searchBar = searchBar;
}

-(UITapGestureRecognizer*)tapHiddenKeyboardGesture{
    if (_tapHiddenKeyboardGesture == nil) {
        _tapHiddenKeyboardGesture = [[UITapGestureRecognizer alloc] initWithTarget:self action:@selector(tap)];
    }
    return _tapHiddenKeyboardGesture;
}

-(void) tap{
    if (_searchState == SEARCHING) {
        [self searchForEndState];
        if (_searchBar) {
            if ([_searchBar.delegate respondsToSelector:@selector(searchBarCancelButtonClicked:)]) {
                [_searchBar.delegate searchBarCancelButtonClicked:_searchBar];
            }
        }
    }
}



@end
