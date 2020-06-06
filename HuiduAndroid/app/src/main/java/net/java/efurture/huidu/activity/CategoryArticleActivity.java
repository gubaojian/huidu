package net.java.efurture.huidu.activity;

import java.util.ArrayList;
import java.util.List;

import net.java.efurture.huidu.R;
import net.java.efurture.huidu.adapter.ArticleListAdapter;
import net.java.efurture.huidu.adapter.ArticleListAdapter.ArticleClickListener;
import net.java.efurture.huidu.common.NavBaseActivity;
import net.java.efurture.huidu.config.PageNum;
import net.java.efurture.huidu.config.Pages;
import net.java.efurture.huidu.config.Params;
import net.java.efurture.huidu.domain.Article;
import net.java.efurture.huidu.domain.Category;
import net.java.efurture.huidu.listener.BackListener;
import net.java.efurture.huidu.service.ArticleService;
import net.java.efurture.huidu.service.FavoriteService;
import net.java.efurture.huidu.util.ErrorUtils;
import net.java.efurture.huidu.view.LoadMaskView;
import net.java.efurture.huidu.view.LoadMaskView.ReloadTask;
import net.java.efurture.huidu.view.LoadMoreFooter;
import net.java.efurture.huidu.view.LoadMoreFooter.LoadMoreTask;
import net.java.efurture.nav.Nav;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.code.callback.GUCallback;
import com.google.code.client.GUClient;
import com.google.code.result.Result;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

public class CategoryArticleActivity extends NavBaseActivity{

	private ArticleService mArticleService;
	private PullToRefreshListView mPullToRefreshListView;
	private ListView mListView;
	private ArrayList<Article> mArticleList;
	private ArticleListAdapter mArticleListAdapter;
	private LoadMoreFooter mLoadMoreFooter;
	private GUCallback<List<Article>> mLoadCallback;
	private LoadMaskView mLoadMaskView;
	private Category mCategory;
	private Button mFavoriteButton;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_article);
        initIfNeed();
    }
	
	@Override
	protected void onLoad(Uri uri) {
		long categoryId = NumberUtils.toLong(uri.getQueryParameter(Params.ID));
		String name = StringUtils.trimToNull(uri.getQueryParameter(Params.NAME));
		if (categoryId <= 0 || name == null) {
			ErrorUtils.showError(this, "类目id或name为空");
			finish();
			return;
		}
		mCategory = new Category();
		mCategory.setId(categoryId);
		mCategory.setName(name);
		loadArticleForPage(PageNum.FIRST_PAGE);
	}
	
	@Override
	protected void onResume(){
		super.onResume();
		refreshFavoriteButton();
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
         getArticleService().getCategoryArticleList(mCategory.getId(), pageNum, mLoadCallback);
	}
	
	private void initIfNeed(){
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
					Nav.from(CategoryArticleActivity.this).toPath(Pages.ARTICLE_DETAIL, Params.ID, article.getId());
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
	       
	       this.findViewById(R.id.back).setOnClickListener(new BackListener(this));
	       ((TextView)this.findViewById(R.id.category_name)).setText(mCategory.getName());
	       mFavoriteButton = (Button) findViewById(R.id.favorite_button);
	       mFavoriteButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (FavoriteService.isFavoriteCategoryId(getBaseContext(), mCategory.getId())) {
					FavoriteService.removeCategoryById(getBaseContext(), mCategory.getId());
					v.setBackgroundResource(R.drawable.add_favorite_bg);
				}else {		
					FavoriteService.addCategory(getBaseContext(), mCategory);
					v.setBackgroundResource(R.drawable.cancel_favorite_bg);
				}
			}
		 });  
	      refreshFavoriteButton();
	}
	
	
	private void refreshFavoriteButton(){
		if (FavoriteService.isFavoriteCategoryId(getBaseContext(), mCategory.getId())) {
			mFavoriteButton.setBackgroundResource(R.drawable.cancel_favorite_bg);
		}else {
		    mFavoriteButton.setBackgroundResource(R.drawable.add_favorite_bg);;
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
