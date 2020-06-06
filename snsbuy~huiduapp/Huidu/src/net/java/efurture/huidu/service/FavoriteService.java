package net.java.efurture.huidu.service;

import java.util.ArrayList;
import java.util.List;

import net.java.efurture.huidu.domain.Category;
import net.java.efurture.huidu.util.GUConfig;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;

import android.content.Context;

import com.google.code.efurture.kvdb.KVDB;
import com.google.code.efurture.kvdb.KVDBManager;

public class FavoriteService {
	private static final String  IS_ARTICLE_LIST = "IS_ARTICLE_List";
	
	private static List<Long> favoriteCategoryIds;
	private static final String SPLIT_CHAR = ",";
	private static final String CATEGORY_KEY = "CATEGORY_KEY";
	private static final KVDB kvdb = KVDBManager.with("favorite_category.db");
	
	
	
	
	public static boolean isFavoriteAritlceList(Context context){
		String valueString = GUConfig.get(context, IS_ARTICLE_LIST);
		if (valueString == null) {
			return true;
		}
		return valueString.equals("true");
	}
	
	public static boolean isFavoriteTag(Context context){
		String valueString = GUConfig.get(context, IS_ARTICLE_LIST);
		if (valueString == null) {
			return false;
		}
		return valueString.equals("false");
	}
	
	public static void switchFavorite(Context context){
		if (isFavoriteAritlceList(context)) {
			GUConfig.put(context, IS_ARTICLE_LIST, "false");
		}else {
			GUConfig.put(context, IS_ARTICLE_LIST, "true");
		}
	}
	
	
	public static List<Long> favoriteCategoryIds(Context context){
		loadFavoriteCategoryIds(context);
		return favoriteCategoryIds;
	}

	public static boolean isFavoriteCategoryId(Context context, long categoryId){
		loadFavoriteCategoryIds(context);
		return favoriteCategoryIds.contains(categoryId);
	}
	
	
	
	public static Category getCategoryById(Long id){
		if (id == null) {
			return null;
		}
		return kvdb.get(id.toString());
	}
	
	public static boolean removeCategoryById(Context context, Long id){
		if (id == null) {
			return false;
		}
		loadFavoriteCategoryIds(context);
		if (favoriteCategoryIds.contains(id)) {
			 favoriteCategoryIds.remove(id);
			 String value = favoriteCategoryIdsToString(context);
			 GUConfig.put(context, CATEGORY_KEY, value);
			 kvdb.remove(id.toString());
			return true;
		}
		return false;
	}
	
	public static boolean addCategory(Context context, Category category){
		if (category.getId() == null || category.getName() == null) {
			return false;
		}
		loadFavoriteCategoryIds(context);
		if (!favoriteCategoryIds.contains(category.getId())) {
			 favoriteCategoryIds.add(0, category.getId());
			 String value = favoriteCategoryIdsToString(context);
			 GUConfig.put(context, CATEGORY_KEY, value);
			 kvdb.asncPut(category.getId().toString(), category);
			return true;
		}
		return false;
	}
	
	public static final String favoriteCategoryIdsToString(Context context){
		loadFavoriteCategoryIds(context);
		StringBuilder sb = new StringBuilder();
		for(Long idLong : favoriteCategoryIds){
			sb.append(idLong);
			sb.append(SPLIT_CHAR);
		}
		return sb.toString();
	}
	
	private static final List<Long> loadFavoriteCategoryIds(Context context){
		if (favoriteCategoryIds == null) {
			String value = GUConfig.get(context, CATEGORY_KEY);
			favoriteCategoryIds = new ArrayList<Long>();
			if (StringUtils.isEmpty(value)) {
				return favoriteCategoryIds;
			}
			String[] idsString = value.split(SPLIT_CHAR);
			for(String id : idsString){
				if(StringUtils.isEmpty(id)){
					continue;
				}
				long categoryId = NumberUtils.toLong(id);
				if (categoryId <= 0) {
					continue;
				}
				favoriteCategoryIds.add(categoryId);
			}
		}
		return favoriteCategoryIds;
	}
	
	
	
	
	
	
	
	
}
