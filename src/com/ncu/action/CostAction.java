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
	
//	��ȡ����֧���˵�
	public static ArrayList<CostModel> getRecordCost(){
		return recordDao.getAllCostModel(2);
	}
//	��ȡ����֧������
	public static ArrayList<Category> getTypeCost(){
		CategoryDao categoryDao = new CategoryDao();
		return categoryDao.getAll(2);//֧����
	}
//	��ȡ������������
	public static ArrayList<Category> getTypeIncome(){
		CategoryDao categoryDao = new CategoryDao();
		return categoryDao.getAll(1);//֧����
	}
	
	//���֧���˵�
	public static int addItem(int type_id,int category_id,int member_id,double sum,Date date ){
		java.sql.Date dates = new java.sql.Date(date.getTime()); 
		return recordDao.addItem(type_id, category_id, member_id, sum, dates);
	}
	//����֧���˵�
	public static int updateItem(int id,int category_id,int member_id,double sum,Date date){
		java.sql.Date dates = new java.sql.Date(date.getTime()); 
		return recordDao.updateItem(id, category_id, member_id, sum, dates);
	}
	//ɾ����Ŀ
	public static int deleteItem(int id){
		return recordDao.deleteItem(id);
	}
	
	//����category_id note��ѯ
	public static ArrayList<CostModel> findByA(int category_id,String note){
		return recordDao.findByA(category_id, note);
	}
	
	//��֧��������
	public static ArrayList<FormModel> findFormModel(){
		return recordDao.findFormModel(2);
	}
	
	//֧������ͼ
	public static ArrayList<WeekModel> findWeekMode(){
		return recordDao.findWeekModel(2);
	}
}
