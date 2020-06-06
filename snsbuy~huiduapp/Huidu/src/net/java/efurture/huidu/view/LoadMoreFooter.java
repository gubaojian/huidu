package net.java.efurture.huidu.view;

import net.java.efurture.huidu.R;
import net.java.efurture.huidu.config.PageNum;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

public class LoadMoreFooter extends FrameLayout {
	
	public static interface LoadMoreTask{
		public void load(int pageNum);
		
	};
	
	
	private int pageNum;
	private boolean hasMore;
	private boolean loading;
	private boolean success;
	private LoadMoreTask loadMoreTask;
	private ListView mListView;

	public LoadMoreFooter(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public LoadMoreFooter(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public LoadMoreFooter(Context context) {
		super(context);
		init();
	}

	private void init(){
		LayoutInflater.from(getContext()).inflate(R.layout.load_more_footer, this);
	    this.findViewById(R.id.load_error_tips).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startLoading();
			}
		});
	}
	
	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public boolean isHasMore() {
		return hasMore;
	}

	public void setHasMore(boolean hasMore) {
		this.hasMore = hasMore;
	}

	 public boolean isLoading() {
		 return loading;
	 }
	 
	 public boolean canLoadMore() {
		 if (loading) {
			return false;
		 }
		 if (!hasMore) {
			return false;
		 }
		 return true;
	 }


	 public void setLoading(boolean loading) {
		 this.loading = loading;
	 }

	  public boolean isSuccess() {
		   return success;
	  }

	  public void setSuccess(boolean success) {
		   this.success = success;
	  }

	  public LoadMoreTask getLoadMoreTask() {
		 return loadMoreTask;
	  }

	  public void setLoadMoreTask(LoadMoreTask loadMoreTask) {
		  this.loadMoreTask = loadMoreTask;
	  }

	  public void initLoadMoreScrollerListener(ListView listView){
		  listView.addFooterView(this, this, false);
		  listView.setOnScrollListener(new OnScrollListener() {
			
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {}
			
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,int visibleItemCount, int totalItemCount) {
				if (view.getAdapter() == null || view.getAdapter().getCount() == (mListView.getHeaderViewsCount() + 
						mListView.getFooterViewsCount())) {
					return;
				}
				if ((firstVisibleItem + visibleItemCount) >= (totalItemCount - 1)) {
					if (canLoadMore()) {
						startLoading();
					}
				}
			}
		});
		this.setVisibility(View.GONE);
		mListView = listView;
	  }

	  public int nextLoadPage(){
		  if (hasMore && success && !loading) {
			 return pageNum + 1;
		  }
		  return pageNum;
	  }
	  
	  public LoadMoreFooter startLoading(){
		    if (loadMoreTask == null) {
				return this;
			}
		    if (loading) {
				return this;
			}
		    
		    if (success && !hasMore) {
		        showLoadComplete();
				return this;
			}
		    this.findViewById(R.id.all_load).setVisibility(View.GONE);
		    this.findViewById(R.id.load_error_tips).setVisibility(View.GONE);
		    this.findViewById(R.id.loading).setVisibility(View.VISIBLE);
		    this.findViewById(R.id.loading_tips).setVisibility(View.VISIBLE);
		    setPageNum(nextLoadPage()); 
		    loadMoreTask.load(getPageNum());
		    loading = true;
		    setVisibility(View.VISIBLE);
		    return this;
	  }
	  
	  public LoadMoreFooter setLoadResult(boolean success,boolean hasMore){
		  this.success = success;
		  this.hasMore = hasMore;
		  loading = false;
		  if (success) {
		      if (!hasMore) {
		    	     showLoadComplete();
			  }	
	  	  }else {
	  		  this.findViewById(R.id.load_error_tips).setVisibility(View.VISIBLE);
		      this.findViewById(R.id.loading).setVisibility(View.GONE);
		      this.findViewById(R.id.loading_tips).setVisibility(View.GONE);
		      this.findViewById(R.id.all_load).setVisibility(View.GONE);
		  }
		  return this;
	  }
	  
	  private void showLoadComplete(){
		 this.findViewById(R.id.all_load).setVisibility(View.VISIBLE);
		 this.findViewById(R.id.load_error_tips).setVisibility(View.GONE);
	     this.findViewById(R.id.loading).setVisibility(View.GONE);
	     this.findViewById(R.id.loading_tips).setVisibility(View.GONE);
	     TextView textView = (TextView) this.findViewById(R.id.all_load);
	     if (mListView.getAdapter() != null && mListView.getAdapter().getCount() <= (mListView.getHeaderViewsCount() + mListView.getFooterViewsCount())) {
	    	     textView.setText("找不到相关数据");
		}else {
			textView.setText("加载完毕");
		}
	     this.setVisibility(View.VISIBLE);
	  }
	  

	  public LoadMoreFooter reset(){
		  pageNum = PageNum.FIRST_PAGE;
		  success = true;
		  loading = false;
		  hasMore = true;
		  return this;
	  }
	  
	  
}
