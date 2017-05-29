package com.ncu.action;

import java.util.ArrayList;

import com.ncu.dao.RecordDao;
import com.ncu.model.CostModel;
import com.ncu.model.FormModel;
import com.ncu.model.WeekModel;

public class IncomeAction {
	private static RecordDao recordDao = new RecordDao();
	public static ArrayList<CostModel> getRecordIncome(){
		ArrayList<CostModel> costModel = new ArrayList<CostModel>();
		costModel = recordDao.getAllCostModel(1);
		return costModel;
	}
	//������������
		public static ArrayList<FormModel> findFormModel(){
			return recordDao.findFormModel(1);
		}
		//��������ͼ
		public static ArrayList<WeekModel> findWeekMode(){
			return recordDao.findWeekModel(1);
		}
}
