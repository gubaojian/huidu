package net.java.efurture.huidu.activity;


import java.util.ArrayList;
import java.util.List;

import net.java.efurture.huidu.R;
import net.java.efurture.huidu.adapter.ArticleListAdapter;
import net.java.efurture.huidu.adapter.ArticleListAdapter.ArticleClickListener;
import net.java.efurture.huidu.callback.VersionCheckCallback;
import net.java.efurture.huidu.common.TabBaseActivity;
import net.java.efurture.huidu.config.PageNum;
import net.java.efurture.huidu.config.Pages;
import net.java.efurture.huidu.config.Params;
import net.java.efurture.huidu.domain.Article;
import net.java.efurture.huidu.service.ArticleService;
import net.java.efurture.huidu.service.CheckUpdateService;
import net.java.efurture.huidu.service.RunService;
import net.java.efurture.huidu.util.ErrorUtils;
import net.java.efurture.huidu.view.LoadMaskView;
import net.java.efurture.huidu.view.LoadMaskView.ReloadTask;
import net.java.efurture.huidu.view.LoadMoreFooter;
import net.java.efurture.huidu.view.LoadMoreFooter.LoadMoreTask;
import net.java.efurture.nav.Nav;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.google.code.callback.GUCallback;
import com.google.code.client.GUClient;
import com.google.code.result.Result;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.umeng.analytics.MobclickAgent;

public class HomeActivity extends TabBaseActivity {

	private ArticleService mArticleService;
	private PullToRefreshListView mPullToRefreshListView;
	private ListView mListView;
	private ArrayList<Article> mArticleList;
	private ArticleListAdapter mArticleListAdapter;
	private LoadMoreFooter mLoadMoreFooter;
	private GUCallback<List<Article>> mLoadCallback;
	private LoadMaskView mLoadMaskView;
	private VersionCheckCallback mVersionCheckCallback;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initHomeIfNeed();
        MobclickAgent.updateOnlineConfig(this);
        initCheckUpdateIfNeed();
    }

	@Override
	protected void onLoad(Uri uri) {
		if (mPullToRefreshListView == null) {
			this.loadArticleForPage(PageNum.FIRST_PAGE);
		}
	}
	
	private void loadArticleForPage(final int pageNum){
		 mLoadCallback = new GUCallback<List<Article>>() {
				@Override
				public void onResult(Result<List<Article>> result) {
					initHomeIfNeed();
					mPullToRefreshListView.onRefreshComplete();
					mLoadMoreFooter.setLoadResult(result.isSuccess(), result.hasMore());
					mLoadMaskView.hideMask();
					if (!result.isSuccess()) {
						if (mArticleList.size() == 0) {
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
				}
		 };
         getArticleService().getArticleList(pageNum, mLoadCallback);
	}
	
	private void initHomeIfNeed(){
		    if (mPullToRefreshListView != null) {
				return;
			}
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
	        mArticleList = new ArrayList<Article>();
	        mArticleListAdapter = new ArticleListAdapter(getBaseContext(), mArticleList);
	        mArticleListAdapter.setArticleClickListener(new ArticleClickListener() {
				@Override
				public void click(View node, Article article) {
					Nav.from(HomeActivity.this).toPath(Pages.ARTICLE_DETAIL, Params.ID, article.getId());
				}
			});
	        mListView = mPullToRefreshListView.getRefreshableView();
	        mLoadMoreFooter = new LoadMoreFooter(getBaseContext());
	        mLoadMoreFooter.setLoadMoreTask(new LoadMoreTask() {
				@Override
				public void load(int pageNum) {
					loadArticleForPage(pageNum);
				}
			});
	        mLoadMoreFooter.initLoadMoreScrollerListener(mListView);
	        mListView.setDividerHeight(0);
	        mListView.setAdapter(mArticleListAdapter);
	}
	
	private void initCheckUpdateIfNeed(){
		if (RunService.shouldRunCheckUpdate(this)) {
			if (mVersionCheckCallback != null) {
				mVersionCheckCallback.cancel();
				mVersionCheckCallback = null;
			}
			mVersionCheckCallback = new VersionCheckCallback(HomeActivity.this, false);
			GUClient.create(CheckUpdateService.class).getVersion(mVersionCheckCallback);
			RunService.markRunCheckUpdate(this);
		}
	}
	
	
	private ArticleService getArticleService(){
		if (mArticleService == null) {
			mArticleService  = GUClient.create(ArticleService.class);
		}
		return mArticleService;
	}


	@Override
	protected void onDestoryClean() {
		if (mVersionCheckCallback != null) {
			mVersionCheckCallback.cancel();
			mVersionCheckCallback = null;
		}
		if (mLoadCallback != null) {
			mLoadCallback.cancel();
		}
		mArticleService  = null;
		if (mArticleList != null) {
			mArticleList.clear();
			mArticleList = null;
		}
		mListView = null;
		mArticleListAdapter = null;
		mLoadCallback = null;
		mLoadMoreFooter = null;
		mPullToRefreshListView = null;
		super.onDestoryClean();
	}
}
