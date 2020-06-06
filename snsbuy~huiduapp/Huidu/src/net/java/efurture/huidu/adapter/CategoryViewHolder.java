package net.java.efurture.huidu.adapter;

import net.java.efurture.huidu.R;
import net.java.efurture.huidu.domain.Category;
import net.java.efurture.huidu.service.FavoriteService;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CategoryViewHolder {
	private TextView mCategorTextView;
	private Button mFavoriteButton;
	private Category mCategory;
	private Context mContext;
	private int mPosition;
	
	public CategoryViewHolder(View view){
		mCategorTextView = (TextView) view.findViewById(R.id.name);
		mFavoriteButton = (Button) view.findViewById(R.id.favorite_button);
		mContext = view.getContext();
		view.setTag(this);
	}
	
	public void bindCategory(int position, Category category){
		mPosition = position;
		mCategory = category;
		if (category == null) {
			mCategorTextView.setText("");
			mFavoriteButton.setBackgroundResource(R.drawable.cancel_favorite_bg);
			return;
		}
		mCategorTextView.setText(mCategory.getName());
		if (FavoriteService.isFavoriteCategoryId(mContext, mCategory.getId())) {
			mFavoriteButton.setBackgroundResource(R.drawable.cancel_favorite_bg);
		}else {
		    mFavoriteButton.setBackgroundResource(R.drawable.add_favorite_bg);;
		}
	}
	
	
	public Category getCategory(){
		return mCategory;
	}

	public int getPosition() {
		return mPosition;
	}

	public void setPosition(int position) {
		this.mPosition = position;
	}
	
	
}
