package com.ncu.service;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ncu.action.CostAction;
import com.ncu.action.IncomeAction;
import com.ncu.model.CostModel;
import com.ncu.model.FormModel;
import com.ncu.model.WeekModel;

import net.sf.json.util.JSONStringer;

/**
 * Servlet implementation class CostService
 */
@WebServlet("/CostService")
public class CostService extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CostService() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		request.setCharacterEncoding("utf-8");
		String action = request.getParameter("action");
		JSONStringer stringer = new JSONStringer();
		if(action.equals("find")){
			ArrayList<CostModel> costModel = CostAction.getRecordCost();
			stringer.array();
			for(int i=0;i<costModel.size();i++){
				stringer.object().key("id").value(costModel.get(i).getId()).
					key("num").value(costModel.get(i).getTypeName()).
					key("category").value(costModel.get(i).getTypeName()).
					key("member").value(costModel.get(i).getMemberName()).
					key("sum").value(costModel.get(i).getSum()).
					key("date").value(costModel.get(i).getDate()).endObject();
			}
			stringer.endArray();
		}else if(action.equals("add")){
			String category_id = request.getParameter("category_id");
			String member_id = request.getParameter("member_id");
			String number = request.getParameter("number");
			String date = request.getParameter("date");
			System.out.println(category_id+member_id+number+date);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 
			Date dates;
			int count;
			
			try {
				dates = sdf.parse(date);
				System.out.println(dates.toString());
				if(category_id!=""&&member_id!=""&&number!=""&&date!=""){
					count = CostAction.addItem(2, Integer.parseInt(category_id), Integer.parseInt(member_id), Double.valueOf(number), dates);
					stringer.array();
					if(count!=0){
						stringer.object().key("success").value("true").endObject();
					}else{
						stringer.object().key("success").value("false").endObject();
					}
					stringer.endArray();
				}
				
			} catch (ParseException e) {
				e.printStackTrace();
				stringer.object().key("success").value("false").endObject();
				stringer.endArray();
			}

		}else if(action.equals("update")){
			String id = request.getParameter("id");
			String category_id = request.getParameter("category_id");
			String member_id = request.getParameter("member_id");
			String number = request.getParameter("number");
			String date = request.getParameter("date");
			System.out.println(category_id+member_id+number+date);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 
			Date dates;
			int count;
			
			try {
				dates = sdf.parse(date);
				System.out.println(dates.toString());
				if(id!=""&&category_id!=""&&member_id!=""&&number!=""&&date!=""){
					count = CostAction.updateItem(Integer.parseInt(id), Integer.parseInt(category_id),Integer.parseInt(member_id), Double.valueOf(number),  dates);
					stringer.array();
					if(count!=0){
						stringer.object().key("success").value("true").endObject();
					}else{
						stringer.object().key("success").value("false").endObject();
					}
					stringer.endArray();
				}
				
			} catch (ParseException e) {
				e.printStackTrace();
				stringer.object().key("success").value("false").endObject();
				stringer.endArray();
			}
		}else if(action.equals("delete")){
			String id = request.getParameter("id");
			int count;
			if(id!=""){
				count=CostAction.deleteItem(Integer.parseInt(id));
				stringer.array();
				if(count!=0){
					stringer.object().key("success").value("true").endObject();
				}else{
					stringer.object().key("success").value("false").endObject();
				}
				stringer.endArray();
			}
		}else if(action.equals("select")){
			String category_id = request.getParameter("id");
			String note = request.getParameter("note");
			System.out.println(note);
			ArrayList<CostModel> costModel = CostAction.findByA(Integer.parseInt(category_id), note);

			stringer.array();
			if(costModel!=null){
				for(int i=0;i<costModel.size();i++){
					stringer.object().key("id").value(costModel.get(i).getId()).
						key("num").value(costModel.get(i).getTypeName()).
						key("category").value(costModel.get(i).getTypeName()).
						key("member").value(costModel.get(i).getMemberName()).
						key("sum").value(costModel.get(i).getSum()).
						key("date").value(costModel.get(i).getDate()).endObject();
				}
			}else{
				stringer.object().endObject();
			}
			stringer.endArray();
			
		}else if(action.equals("form")){
			ArrayList<FormModel> fList = new ArrayList<FormModel>();
			fList = CostAction.findFormModel();
			stringer.array();
			for(int i=0;i<fList.size();i++){
				stringer.object().key("cate").value(fList.get(i).getCategoryName()).
					key("sum").value(fList.get(i).getSum()).endObject();
			}
			stringer.endArray();
		}else if(action.equals("week")){
			ArrayList<WeekModel> wList = new ArrayList<WeekModel>();
			wList = CostAction.findWeekMode();
			stringer.array();
			if(wList!=null){
				for(int i=0;i<wList.size();i++){
					stringer.object().key("type").value(wList.get(i).getType()).
						key("sum").value(wList.get(i).getSum()).
						key("date").value(wList.get(i).getDate()).endObject();
				}
			}
			stringer.endArray();
		}
		response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		response.setContentType("text/json; charset=UTF-8");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
