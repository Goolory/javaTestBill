package com.ncu.service;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ncu.action.CostAction;
import com.ncu.model.CostModel;

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
		String action = request.getParameter("action");
		if(action.equals("find")){
			ArrayList<CostModel> costModel = CostAction.getRecordCost();
			JSONStringer stringer = new JSONStringer();
			
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
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
			response.setContentType("text/json; charset=UTF-8");
			
		}
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
