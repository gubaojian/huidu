package net.java.efurture.huidu.activity;

import java.util.ArrayList;
import java.util.List;

import net.java.efurture.huidu.R;
import net.java.efurture.huidu.adapter.ArticleListAdapter;
import net.java.efurture.huidu.adapter.ArticleListAdapter.ArticleClickListener;
import net.java.efurture.huidu.adapter.CategoryClickListener;
import net.java.efurture.huidu.adapter.FavoriteCategoryAdapter;
import net.java.efurture.huidu.common.TabBaseActivity;
import net.java.efurture.huidu.config.PageNum;
import net.java.efurture.huidu.config.Pages;
import net.java.efurture.huidu.config.Params;
import net.java.efurture.huidu.domain.Article;
import net.java.efurture.huidu.domain.Category;
import net.java.efurture.huidu.service.ArticleService;
import net.java.efurture.huidu.service.FavoriteService;
import net.java.efurture.huidu.util.ErrorUtils;
import net.java.efurture.huidu.view.LoadMaskView;
import net.java.efurture.huidu.view.LoadMaskView.ReloadTask;
import net.java.efurture.huidu.view.LoadMoreFooter;
import net.java.efurture.huidu.view.LoadMoreFooter.LoadMoreTask;
import net.java.efurture.icon.IconTextView;
import net.java.efurture.nav.Nav;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;

import com.google.code.callback.GUCallback;
import com.google.code.client.GUClient;
import com.google.code.result.Result;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

public class FavoriteActivity extends TabBaseActivity {
	
	
	public static final String REFRESH = "REFRESH_FavoriteActivity";
	
	private PullToRefreshListView mPullToRefreshListView;
	private ListView mListView;
	private LoadMaskView mLoadMaskView;
	private LoadMoreFooter mLoadMoreFooter;
	private ArrayList<Article> mArticleList;
	private ArticleListAdapter mArticleListAdapter;
	private IconTextView mSwitchTextView;
	private ArticleService mArticleService;
	private GUCallback<List<Article>> mLoadCallback;
	private FavoriteCategoryAdapter mCategoryAdapter;
	private View  mGuideView;
	private BroadcastReceiver refreshBroadcastReceiver = new BroadcastReceiver(){
		@Override
		public void onReceive(Context context, Intent intent) {
			refreshForFavoriteState(true);
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_favorite);
		initIfNeed();
	}

	@Override
	protected void onLoad(Uri uri) {
		if (FavoriteService.isFavoriteAritlceList(getBaseContext())) {
			loadArticleForPage(PageNum.FIRST_PAGE);
		}
	}
	
	@Override
	protected void onResume(){
		super.onResume();
		if (mGuideView.getVisibility() == View.VISIBLE
			|| FavoriteService.favoriteCategoryIds(this).isEmpty()) {
			refreshForFavoriteState(true);
		}else {
			if (mCategoryAdapter != null && FavoriteService.isFavoriteTag(this)) {
				mCategoryAdapter.notifyDataSetChanged();
			}
		}
	}


	@Override
	protected void onDestoryClean() {
		LocalBroadcastManager.getInstance(this).unregisterReceiver(refreshBroadcastReceiver);
		if (mLoadCallback != null) {
			mLoadCallback.cancel();
		}
		mArticleList = null;
		mArticleListAdapter = null;
		mArticleService = null;
		mCategoryAdapter = null;
		mGuideView = null;
		mListView = null;
		mLoadMaskView = null;
		mLoadMoreFooter = null;
		mPullToRefreshListView = null;
		mSwitchTextView = null;
		super.onDestoryClean();
	}
	
	private void initIfNeed(){
		if (mSwitchTextView != null) {
			return;
		}
		mSwitchTextView = (IconTextView) this.findViewById(R.id.iconTextView);
		mSwitchTextView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				FavoriteService.switchFavorite(getBaseContext());
				refreshForFavoriteState(true);
			}
		});
		mLoadMaskView = (LoadMaskView) this.findViewById(R.id.load_mask);
		mLoadMaskView.setReloadTask(new ReloadTask() {
			@Override
			public void reloadForError() {
				loadArticleForPage(PageNum.FIRST_PAGE);
			}
		});
	    mPullToRefreshListView = (PullToRefreshListView) this.findViewById(R.id.pull_to_refresh_listview);
	    mPullToRefreshListView.setOnRefreshListener(new OnRefreshListener<ListView>() {
			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				if (FavoriteService.isFavoriteTag(getBaseContext())) {
					mCategoryAdapter.notifyDataSetChanged();
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
				loadArticleForPage(PageNum.FIRST_PAGE);
			}
        });
	    
	    mListView = mPullToRefreshListView.getRefreshableView();
        mLoadMoreFooter = new LoadMoreFooter(getBaseContext());
        mLoadMoreFooter.initLoadMoreScrollerListener(mListView);
        mListView.setDividerHeight(0);
        mCategoryAdapter = new FavoriteCategoryAdapter(this);
        mCategoryAdapter.setCategoryClickListener(new CategoryClickListener() {
			@Override
			public void click(View v, Category category) {
				Nav.from(FavoriteActivity.this).toPath(Pages.CATEGORY_ARTICLE_LIST,
						Params.ID, category.getId(), Params.NAME, category.getName());
			}
		});
        
        mArticleList = new ArrayList<Article>();
	    mArticleListAdapter = new ArticleListAdapter(getBaseContext(), mArticleList);
	    mArticleListAdapter.setArticleClickListener(new ArticleClickListener() {
				@Override
				public void click(View node, Article article) {
					Nav.from(FavoriteActivity.this).toPath(Pages.ARTICLE_DETAIL, Params.ID, article.getId());
				}
	    });
	    mGuideView = this.findViewById(R.id.guide_discover);
	    this.findViewById(R.id.guide_button).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(FavoriteActivity.this, DiscoverActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
				startActivity(intent);
			}
		});
	    LocalBroadcastManager.getInstance(this).registerReceiver(refreshBroadcastReceiver, 
	    		new IntentFilter(REFRESH));
	    refreshForFavoriteState(false);
	}
	
	private void  refreshForFavoriteState(boolean loadArticle){
		if (FavoriteService.favoriteCategoryIds(this).isEmpty()) {
			mPullToRefreshListView.setMode(Mode.DISABLED);
			mListView.setVisibility(View.GONE);
			mGuideView.setVisibility(View.VISIBLE);
			mSwitchTextView.setVisibility(View.GONE);
			mLoadMoreFooter.setVisibility(View.GONE);
			if(mLoadMaskView.isShowError()){
				mGuideView.setVisibility(View.GONE);
			}
			return;
		}else {
			mGuideView.setVisibility(View.GONE);
			mSwitchTextView.setVisibility(View.VISIBLE);
			mPullToRefreshListView.setMode(Mode.PULL_FROM_START);
			mListView.setVisibility(View.VISIBLE);
		}

		if (FavoriteService.isFavoriteAritlceList(getBaseContext())) {
			mSwitchTextView.setText(getResources().getString(R.string.fa_tag));
			 mListView.setAdapter(mArticleListAdapter);
			 mLoadMoreFooter.reset();
			 mLoadMoreFooter.setLoadMoreTask(new LoadMoreTask() {
					@Override
					public void load(int pageNum) {
						loadArticleForPage(pageNum);
					}
			});
			mLoadMoreFooter.setVisibility(View.GONE);
			if (mArticleList.size() == 0 ) {
				mLoadMaskView.showLoadingMask();
			}
			if (loadArticle) {
				loadArticleForPage(PageNum.FIRST_PAGE);
			}
		}else {
			mSwitchTextView.setText(getResources().getString(R.string.fa_list_alt));
			mLoadMaskView.hideMask();
			mListView.setAdapter(mCategoryAdapter);
			mLoadMoreFooter.setLoadResult(true, false);
		}
	}
	
	
	
	
	
	private void loadArticleForPage(final int pageNum){
		 mLoadCallback = new GUCallback<List<Article>>() {
				@Override
				public void onResult(Result<List<Article>> result) {
					initIfNeed();
					mPullToRefreshListView.onRefreshComplete();
					mLoadMoreFooter.setLoadResult(result.isSuccess(), result.hasMore());
					mLoadMaskView.hideMask();
					if (!result.isSuccess()) {
						if (mArticleList.size() == 0) {
							mGuideView.setVisibility(View.GONE);
							mLoadMaskView.showErrorMask();
						}else {
							ErrorUtils.showErrorResult(getBaseContext(), result);
						}
						return;
					}
					if (pageNum == PageNum.FIRST_PAGE) {
						mLoadMoreFooter.reset();
						mArticleList.clear();
					}
					mArticleList.addAll(result.getResult());
					mArticleListAdapter.notifyDataSetChanged();
					mListView.setVisibility(View.VISIBLE);
				}
		 };
		
        getArticleService().getFavoriteArticleList(FavoriteService.favoriteCategoryIdsToString(this), pageNum, mLoadCallback);
	}
	

	private ArticleService getArticleService(){
		if (mArticleService == null) {
			mArticleService = GUClient.create(ArticleService.class);
		}
		return mArticleService;
	}

}
