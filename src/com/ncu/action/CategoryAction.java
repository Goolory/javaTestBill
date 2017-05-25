package com.ncu.action;

import com.ncu.dao.CategoryDao;

public class CategoryAction {
	static CategoryDao categoryDao = new CategoryDao();
	public static int updateCategory(int id,String name){
		
		return categoryDao.update(id, name);
	}
	public static int deleteCategory(int id){
		return categoryDao.delete(id);
	}
}
