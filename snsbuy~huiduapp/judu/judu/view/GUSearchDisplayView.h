//
//  GUSearchDisplayView.h
//  judu
//
//  Created by lurina on 13-7-28.
//  Copyright (c) 2013年 baobao. All rights reserved.
//

#import <UIKit/UIKit.h>
typedef  enum SearchState{
     END = 0,
     SEARCHING = 1,
     DISPLAY_RESULTS = 2
} SearchState;

@interface GUSearchDisplayView : UIView{
     @private UITableView* _searchResultsTableView;
     @private SearchState  _searchState;
     @private UISearchBar* _searchBar;
     @private  UILabel*   _emptyTips;
    @private UITapGestureRecognizer* _tapHiddenKeyboardGesture;
}

@property(nonatomic,strong) UITableView* searchResultsTableView;

-(void) setSearchBar:(UISearchBar*)searchBar;
-(void) setActiveSearchForState:(SearchState) state;

-(SearchState) searchState;

@end
