package com.ncu.action;

import java.util.ArrayList;

import com.ncu.dao.CategoryDao;
import com.ncu.entity.Category;
import com.ncu.model.CostModel;

public class CostAction {
	public static ArrayList<CostModel> getRecordCost(){
		ArrayList<CostModel> costModel = new ArrayList<CostModel>();
//		========================================
//		RecordDao recordDao = new RecordDao();
//		ArrayList<Records> rList = recordDao.getAllCost();
//		for (Records r : rList) {
//			
//		}
//		========================================
		for (int i = 0; i < 2; i++) {
			CostModel c = new CostModel();
			c.setId(1);
			c.setMemberName("����");
			c.setSum(233);
			c.setTypeName("�·�");
			c.setDate(null);
			costModel.add(c);
		}
		return costModel;
		
		
	}
	
	public static ArrayList<Category> getTypeCost(){
		ArrayList<Category> category = new ArrayList<Category>();
		CategoryDao categoryDao = new CategoryDao();
		category=categoryDao.getAll(2);//֧����
		return category;
	}
	
}