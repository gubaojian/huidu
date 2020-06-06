package net.java.efurture.huidu.adapter;

import java.util.List;

import net.java.efurture.huidu.R;
import net.java.efurture.huidu.domain.Article;
import net.java.efurture.huidu.listener.OnClickListener;
import net.java.efurture.huidu.util.DateFormatUtils;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ArticleListAdapter  extends BaseAdapter{

	public static interface ArticleClickListener{
		 public void click(View node, Article article);
	};
	
	private Context mContext;
	private List<Article> articles;
	private ArticleClickListener articleClickListener;
	
	public ArticleListAdapter(Context context, List<Article> articles) {
		super();
		this.mContext = context;
		this.articles = articles;
	}
	
	public ArticleClickListener getArticleClickListener() {
		return articleClickListener;
	}


	public void setArticleClickListener(ArticleClickListener articleClickListener) {
		this.articleClickListener = articleClickListener;
	}


	@Override
	public int getCount() {
		if (articles == null) {
			return articles.size();
		}
		return articles.size();
	}

	@Override
	public Object getItem(int position) {
		return articles.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(R.layout.article_list_item, parent, false);
			convertView.setOnClickListener(new OnClickListener() {
		    	
				@Override
				public void onClickEvent(View v) {
					ViewHolder holder = (ViewHolder) v.getTag();
					if (holder == null 
							|| holder.getArticle() == null
							|| articleClickListener == null) {
						return;
					}
					articleClickListener.click(v, holder.getArticle());
				}
			});
		}
		ViewHolder holder = (ViewHolder) convertView.getTag();
		if (holder == null) {
			holder = new ViewHolder(convertView);
		}
		Article article = articles.get(position);
		holder.bindArticle(article);
		return convertView;
	}
	
	private static class ViewHolder{
		private TextView titleTextView;
        private TextView authorTextView;
        private TextView publishDateTextView;
        private TextView shortDescTextView;
        private Article article;
        
        public ViewHolder(View node){
        	    titleTextView = (TextView) node.findViewById(R.id.title);
        	    authorTextView = (TextView) node.findViewById(R.id.author);
        	    publishDateTextView = (TextView) node.findViewById(R.id.publish_time);
        	    shortDescTextView = (TextView) node.findViewById(R.id.short_desc);
        	    node.setTag(this);
        }
		
        public void bindArticle(Article article){
        	     titleTextView.setText(article.getTitle());
        	     authorTextView.setText(article.getAuthor());
        	     publishDateTextView.setText(DateFormatUtils.format(article.getPublishTime()));
        	     shortDescTextView.setText(article.getShortDesc());
        	     this.article = article;
        }
        
        public Article getArticle(){
        	    return article;
        }
        
	}
	
	

}
