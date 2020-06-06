package net.java.efurture.huidu.activity;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.java.efurture.huidu.R;
import net.java.efurture.huidu.adapter.CategoryClickListener;
import net.java.efurture.huidu.adapter.CategoryListAdapter;
import net.java.efurture.huidu.common.NavBaseActivity;
import net.java.efurture.huidu.config.Pages;
import net.java.efurture.huidu.config.Params;
import net.java.efurture.huidu.config.SystemConfig;
import net.java.efurture.huidu.domain.Article;
import net.java.efurture.huidu.domain.Category;
import net.java.efurture.huidu.listener.BackListener;
import net.java.efurture.huidu.service.ArticleService;
import net.java.efurture.huidu.share.ShareUtils;
import net.java.efurture.huidu.util.ArticleUtils;
import net.java.efurture.huidu.view.GuWebView;
import net.java.efurture.huidu.view.LoadMaskView;
import net.java.efurture.huidu.view.LoadMaskView.ReloadTask;
import net.java.efurture.nav.Nav;
import net.java.efurture.util.GLog;

import org.apache.commons.lang.math.NumberUtils;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.google.code.callback.GUCallback;
import com.google.code.client.GUClient;
import com.google.code.result.Result;

public class ReaderDetailActivity extends NavBaseActivity{

	private ArticleService mArticleService;
	private GUCallback<Article> mLoadCallback;
	private Article mArticle;
	private GuWebView mWebView;
	private LoadMaskView mLoadMaskView;
	private ViewGroup mFooterView;
	private ListView mListView;
	private List<Category> mCategoryList;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_detail);
        initIfNeed();
    }


	@Override
	protected void onLoad(Uri uri) {
		long articleId = NumberUtils.toLong(uri.getQueryParameter(Params.ID), -1);
		if (articleId <= 0) {
			Toast.makeText(getBaseContext(), "Illegal Article Id", Toast.LENGTH_SHORT).show();;
			finish();
		}
		mArticle = new Article();
		mArticle.setId(articleId);
		loadArticle();
	}

	@Override
	protected void onDestoryClean() {
		if (mLoadCallback != null) {
			mLoadCallback.cancel();
			mLoadCallback = null;
		}
		mArticle = null;
		mArticleService = null;
		mCategoryList = null;
		mFooterView = null;
		mListView = null;
		mLoadMaskView = null;
		mWebView = null;
		super.onDestoryClean();
	}
	private void initIfNeed(){
		 if (mWebView != null) {
			return;
		 }
		 mLoadMaskView = (LoadMaskView) this.findViewById(R.id.load_mask);
		 mLoadMaskView.setReloadTask(new ReloadTask() {	
				@Override
				public void reloadForError() {
	                 loadArticle();
				}
		 });
		 mWebView = (GuWebView) this.findViewById(R.id.article_webview);
		 mWebView.setActivity(this);
		 this.findViewById(R.id.back).setOnClickListener(new BackListener(this));
		 this.findViewById(R.id.share).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String text = mArticle.getTitle() + "  http://huidu.lanxijun.com/articleDetail.html?id=" + mArticle.getId() + "&from=huidu&platform=android";
				ShareUtils.share(ReaderDetailActivity.this, text);
			}
		});
			
		 mFooterView = (ViewGroup)LayoutInflater.from(getBaseContext()).inflate(R.layout.article_category_header, null);
		 mListView = (ListView) mFooterView.findViewById(R.id.article_category_listview);
		 mFooterView.findViewById(R.id.see_source).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				 Nav.from(ReaderDetailActivity.this).toPath(Pages.WEBVIEW, Params.URL, mArticle.getUrl());
			}
		});
			
	}
	
	
	private void loadArticle(){
		 mLoadCallback = new GUCallback<Article>() {
			 
			@SuppressWarnings("unchecked")
			@Override
			public void onResult(Result<Article> result) {
				  initIfNeed();
				  if (!result.isSuccess() ||  result.getResult() == null) {
					  mLoadMaskView.showErrorMask();
					  return;
				  }
				try {
					mArticle = result.getResult();
					mCategoryList = (List<Category>) result.getModels().get("categoryList");
					if (mCategoryList != null && mCategoryList.size() > 0) {
						CategoryListAdapter categoryListAdapter = new CategoryListAdapter(getBaseContext(), mCategoryList);
						categoryListAdapter.setCategoryClickListener(new CategoryClickListener() {
							@Override
							public void click(View v, Category category) {
								Nav.from(ReaderDetailActivity.this).toPath(Pages.CATEGORY_ARTICLE_LIST,
										Params.ID, category.getId(), Params.NAME, category.getName());
							}
						});
					    mListView.setAdapter(categoryListAdapter);
					}else {
						mListView.setVisibility(View.GONE);
						mFooterView.findViewById(R.id.tag_header).setVisibility(View.GONE);
					}
					mWebView.setFooterView(mFooterView);
					Map<String, Object> contextMap = new HashMap<String, Object>();
					contextMap.put("article", result.getResult());
					contextMap.put("categoryList", mCategoryList);
					String html =  ArticleUtils.renderArticle(getBaseContext(), contextMap);
				    mWebView.loadDataWithBaseURL(SystemConfig.ASSERT_DIR, html, "text/html", "UTF-8", "");
				} catch (Exception e) {
					GLog.e(TAG, "Reader Load Error", e);
					Toast.makeText(getBaseContext(), "Reader LoadError" + e.getMessage(), Toast.LENGTH_SHORT).show();
				 }
				 mWebView.postDelayed(new Runnable() {
					@Override
					public void run() {
						if (mLoadMaskView != null) {
							mLoadMaskView.hideMask();
						}
					}  
				  }, 300);
			}
		 };
		 getArticleService().getArticleDetailById(mArticle.getId(), mLoadCallback);
	}
	
	
	private ArticleService getArticleService(){
		if (mArticleService == null) {
			mArticleService = GUClient.create(ArticleService.class);
		}
		return mArticleService;
	}
	
	
}
