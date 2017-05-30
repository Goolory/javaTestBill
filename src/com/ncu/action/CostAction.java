package com.ncu.action;

import java.util.ArrayList;
import java.util.Date;

import com.ncu.dao.CategoryDao;
import com.ncu.dao.RecordDao;
import com.ncu.entity.Category;
import com.ncu.model.CostModel;
import com.ncu.model.FormModel;
import com.ncu.model.WeekModel;

public class CostAction {
	private static RecordDao recordDao = new RecordDao();
	
//	获取所有支出账单
	public static ArrayList<CostModel> getRecordCost(){
		return recordDao.getAllCostModel(2);
	}
//	获取所有支出类型
	public static ArrayList<Category> getTypeCost(){
		CategoryDao categoryDao = new CategoryDao();
		return categoryDao.getAll(2);//支出项
	}
//	获取所有收入类型
	public static ArrayList<Category> getTypeIncome(){
		CategoryDao categoryDao = new CategoryDao();
		return categoryDao.getAll(1);//支出项
	}
	
	//添加支出账单
	public static int addItem(int type_id,int category_id,int member_id,double sum,Date date ){
		java.sql.Date dates = new java.sql.Date(date.getTime()); 
		return recordDao.addItem(type_id, category_id, member_id, sum, dates);
	}
	//更新支出账单
	public static int updateItem(int id,int category_id,int member_id,double sum,Date date){
		java.sql.Date dates = new java.sql.Date(date.getTime()); 
		return recordDao.updateItem(id, category_id, member_id, sum, dates);
	}
	//删除项目
	public static int deleteItem(int id){
		return recordDao.deleteItem(id);
	}
	
	//根据category_id note查询
	public static ArrayList<CostModel> findByA(int category_id,String note){
		return recordDao.findByA(category_id, note);
	}
	
	//按支出类别分类
	public static ArrayList<FormModel> findFormModel(){
		return recordDao.findFormModel(2);
	}
	
	//支出折线图
	public static ArrayList<WeekModel> findWeekMode(){
		return recordDao.findWeekModel(2);
	}
}
