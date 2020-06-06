package net.java.efurture.huidu.activity;

import java.util.ArrayList;
import java.util.List;

import net.java.efurture.huidu.R;
import net.java.efurture.huidu.adapter.CategoryClickListener;
import net.java.efurture.huidu.adapter.CategoryListAdapter;
import net.java.efurture.huidu.common.NavBaseActivity;
import net.java.efurture.huidu.config.PageNum;
import net.java.efurture.huidu.config.Pages;
import net.java.efurture.huidu.config.Params;
import net.java.efurture.huidu.domain.Category;
import net.java.efurture.huidu.service.CategoryService;
import net.java.efurture.huidu.util.ErrorUtils;
import net.java.efurture.huidu.view.LoadMaskView;
import net.java.efurture.huidu.view.LoadMaskView.ReloadTask;
import net.java.efurture.huidu.view.LoadMoreFooter;
import net.java.efurture.huidu.view.LoadMoreFooter.LoadMoreTask;
import net.java.efurture.huidu.view.SearchView;
import net.java.efurture.huidu.view.SearchView.SearchListener;
import net.java.efurture.nav.Nav;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ListView;

import com.google.code.callback.GUCallback;
import com.google.code.client.GUClient;
import com.google.code.result.Result;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

public class SearchActivity extends NavBaseActivity {

	private PullToRefreshListView mPullToRefreshListView;
	private ListView mListView;
	private LoadMoreFooter mLoadMoreFooter;
	private GUCallback<List<Category>> mLoadCallback;
	private LoadMaskView mLoadMaskView;
	private CategoryService mCategoryService;
	private List<Category> mCategoryList;
	private CategoryListAdapter mCategoryListAdapter;
	private SearchView   mSearchView;
	private String mKeyword;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		initIfNeed();
	}

	@Override
	protected void onLoad(Uri uri) {
		loadCategoryForPage(PageNum.FIRST_PAGE);
		mKeyword = uri.getQueryParameter(Params.KEYWORD);
	}

	@Override
	protected void onDestoryClean() {
		if (mLoadCallback != null) {
			mLoadCallback.cancel();
		}
		mCategoryList = null;
		mCategoryListAdapter = null;
		mCategoryService = null;
		mListView = null;
		mLoadCallback = null;
		mLoadMaskView = null;
		mLoadMoreFooter = null;
		super.onDestoryClean();
	}

	
	private void initIfNeed(){
		if (mPullToRefreshListView != null) {
			return;
		}
		mSearchView = (SearchView) this.findViewById(R.id.search);
		mSearchView.setSearchListener(new SearchListener() {
			
			@Override
			public void search(String keyword) {
				mKeyword = keyword;
				loadCategoryForPage(PageNum.FIRST_PAGE);
			}
		});
		mLoadMaskView = (LoadMaskView) this.findViewById(R.id.load_mask);
		mLoadMaskView.setReloadTask(new ReloadTask() {	
				@Override
				public void reloadForError() {
	                 loadCategoryForPage(PageNum.FIRST_PAGE);
				}
	     });
		 mLoadMaskView.hideMask();
		    
		 mPullToRefreshListView = (PullToRefreshListView) this.findViewById(R.id.pull_to_refresh_listview);
	     mPullToRefreshListView.setOnRefreshListener(new OnRefreshListener<ListView>() {
				@Override
				public void onRefresh(PullToRefreshBase<ListView> refreshView) {
					if (mLoadMoreFooter.isLoading()) {
						mPullToRefreshListView.postDelayed(new Runnable() {
							@Override
							public void run() {
								if (mPullToRefreshListView != null) {
									mPullToRefreshListView.onRefreshComplete();
								}
							}
						}, 200);
						return;
					}
					loadCategoryForPage(PageNum.FIRST_PAGE);
				}
		  });
	     mCategoryList = new ArrayList<Category>();
	     mCategoryListAdapter = new CategoryListAdapter(getBaseContext(), mCategoryList);
	     mCategoryListAdapter.setCategoryClickListener(new CategoryClickListener(){
			@Override
			public void click(View v, Category category) {
				Nav.from(SearchActivity.this).toPath(Pages.CATEGORY_ARTICLE_LIST, 
						Params.ID, category.getId(),
						Params.NAME, category.getName());
			}
	     });
	     mListView = mPullToRefreshListView.getRefreshableView();
	     mLoadMoreFooter = new LoadMoreFooter(getBaseContext());
	     mLoadMoreFooter.setLoadMoreTask(new LoadMoreTask() {
				@Override
				public void load(int pageNum) {
					loadCategoryForPage(pageNum);
				}
			});
	      mLoadMoreFooter.initLoadMoreScrollerListener(mListView);
	      mListView.setDividerHeight(0);
	      mListView.setAdapter(mCategoryListAdapter);
	}
	
	
	private void loadCategoryForPage(final int pageNum){
		if (TextUtils.isEmpty(mKeyword)) {
		   if (mCategoryListAdapter != null) {
			   mCategoryList.clear();
			   mCategoryListAdapter.notifyDataSetChanged();
		   }
		   return;
		}
		mLoadCallback = new GUCallback<List<Category>>() {
				@Override
				public void onResult(Result<List<Category>> result) {
					initIfNeed();
					mPullToRefreshListView.onRefreshComplete();
					mLoadMoreFooter.setLoadResult(result.isSuccess(), result.hasMore());
					mLoadMaskView.hideMask();
					if (!result.isSuccess()) {
						if (mCategoryList.size() == 0) {
							mLoadMaskView.showErrorMask();
						}else {
							ErrorUtils.showErrorResult(getBaseContext(), result);
						}
						return;
					}
					if (pageNum == PageNum.FIRST_PAGE) {
						mLoadMoreFooter.reset();
						mCategoryList.clear();
					}
					mCategoryList.addAll(result.getResult());
					mCategoryListAdapter.notifyDataSetChanged();
				}
		 };
		 getCategoryService().searchCategoryList(mKeyword ,pageNum, mLoadCallback);
	}
	
	private CategoryService getCategoryService(){
		if (mCategoryService == null) {
			mCategoryService = GUClient.create(CategoryService.class);
		}
		return mCategoryService;
	}


}
