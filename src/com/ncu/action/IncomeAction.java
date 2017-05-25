package com.ncu.action;

import java.util.ArrayList;

import com.ncu.model.CostModel;

public class IncomeAction {
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
			c.setMemberName("¸¸Ç×");
			c.setSum(233);
			c.setTypeName("ÒÂ·þ");
			c.setDate(null);
			costModel.add(c);
		}
		return costModel;
		
		
	}
}
