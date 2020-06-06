package net.java.efurture.huidu.adapter;

import net.java.efurture.huidu.R;
import net.java.efurture.huidu.activity.FavoriteActivity;
import net.java.efurture.huidu.domain.Category;
import net.java.efurture.huidu.listener.OnClickListener;
import net.java.efurture.huidu.service.FavoriteService;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class FavoriteCategoryAdapter  extends BaseAdapter{

	private Context mContext;
	private CategoryClickListener mCategoryClickListener;
	
	public FavoriteCategoryAdapter(Context context) {
		super();
		this.mContext = context;
	}

	@Override
	public int getCount() {
		return FavoriteService.favoriteCategoryIds(mContext).size();
	}

	@Override
	public Object getItem(int position) {
		long id = FavoriteService.favoriteCategoryIds(mContext).get(position);
		return FavoriteService.getCategoryById(id);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			final View target = LayoutInflater.from(mContext).inflate(R.layout.category_list_item, null);
			convertView = target;
			convertView.findViewById(R.id.favorite_button).setOnClickListener(new OnClickListener() {
				@Override
				public void onClickEvent(View v) {
					CategoryViewHolder holder  = (CategoryViewHolder) target.getTag();
					if (holder == null) {
						return;
					}
					Category category = holder.getCategory();
					if (category == null) {
						Long id = FavoriteService.favoriteCategoryIds(mContext).get(holder.getPosition());
						FavoriteService.removeCategoryById(mContext, id);
						notifyDataSetChanged();
						return;
					}
					if (FavoriteService.isFavoriteCategoryId(mContext, category.getId())) {
						FavoriteService.removeCategoryById(mContext, category.getId());
						v.setBackgroundResource(R.drawable.add_favorite_bg);
					}else {		
						FavoriteService.addCategory(mContext, category);
						v.setBackgroundResource(R.drawable.cancel_favorite_bg);
					}
					notifyDataSetChanged();
					if (FavoriteService.favoriteCategoryIds(mContext).isEmpty()) {
						Intent intent = new Intent(FavoriteActivity.REFRESH);
						intent.setPackage(mContext.getPackageName());
						LocalBroadcastManager.getInstance(mContext).sendBroadcast(intent);
					}
				}
			});
		    convertView.setOnClickListener(new OnClickListener() {
				@Override
				public void onClickEvent(View v) {
					CategoryViewHolder holder  = (CategoryViewHolder) v.getTag();
					if (holder == null || holder.getCategory() == null) {
						return;
					}
					if (mCategoryClickListener != null) {
						mCategoryClickListener.click(v, holder.getCategory());
					}
				}
			});
		}
		
		CategoryViewHolder holder  = (CategoryViewHolder) convertView.getTag();
		if (holder == null) {
			 holder = new CategoryViewHolder(convertView);
		}
		
		long id = FavoriteService.favoriteCategoryIds(mContext).get(position);
		Category category = FavoriteService.getCategoryById(id);
		holder.bindCategory(position, category);
		return convertView;
	}

	public CategoryClickListener getCategoryClickListener() {
		return mCategoryClickListener;
	}

	public void setCategoryClickListener(CategoryClickListener categoryClickListener) {
		this.mCategoryClickListener = categoryClickListener;
	}

	
	
}
