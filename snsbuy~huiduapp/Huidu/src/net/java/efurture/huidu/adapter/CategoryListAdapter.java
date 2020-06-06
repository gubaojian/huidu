package net.java.efurture.huidu.adapter;

import java.util.List;

import net.java.efurture.huidu.R;
import net.java.efurture.huidu.domain.Category;
import net.java.efurture.huidu.listener.OnClickListener;
import net.java.efurture.huidu.service.FavoriteService;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class CategoryListAdapter  extends BaseAdapter{


	private Context mContext;
	private List<Category> mCategoryList;
	private CategoryClickListener mCategoryClickListener;
	
	
	public CategoryListAdapter(Context context, List<Category> categoryList) {
		super();
		this.mContext = context;
		this.mCategoryList = categoryList;
	}

	@Override
	public int getCount() {
		if (mCategoryList == null) {
			return 0;
		}
		return mCategoryList.size();
	}

	@Override
	public Object getItem(int position) {
		return mCategoryList.get(position);
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
						return;
					}
					if (FavoriteService.isFavoriteCategoryId(mContext, category.getId())) {
						FavoriteService.removeCategoryById(mContext, category.getId());
						v.setBackgroundResource(R.drawable.add_favorite_bg);
					}else {		
						FavoriteService.addCategory(mContext, category);
						v.setBackgroundResource(R.drawable.cancel_favorite_bg);
					}
				}
			});
			convertView.findViewById(R.id.name).setOnClickListener(new OnClickListener() {
				@Override
				public void onClickEvent(View v) {
					CategoryViewHolder holder  = (CategoryViewHolder) target.getTag();
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
		holder.bindCategory(position, mCategoryList.get(position));
		return convertView;
	}
	

	public CategoryClickListener getCategoryClickListener() {
		return mCategoryClickListener;
	}

	public void setCategoryClickListener(CategoryClickListener categoryClickListener) {
		this.mCategoryClickListener = categoryClickListener;
	}
}
