package com.ncu.action;

import com.ncu.dao.RecordDao;
import com.ncu.entity.Records;
import com.ncu.model.CostModel;

public class RecordAction {
	private static RecordDao recordDao = new RecordDao();
	//����ID����record
	public static Records findById(int id){
		return recordDao.findById(id);
	}
	//����ID���ҷ���CostModel
	public static CostModel findCostModelById(int id){
		return recordDao.findCostModelById(id);
	}
}
