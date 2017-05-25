package com.ncu.service;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ncu.action.CategoryAction;
import com.ncu.action.CostAction;
import com.ncu.entity.Category;

import net.sf.json.util.JSONStringer;

/**
 * Servlet implementation class CategoryService
 */
@WebServlet("/CategoryService")
public class CategoryService extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CategoryService() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		if(action.equals("costType")){
			ArrayList<Category> cateList = CostAction.getTypeCost();
				if(cateList!=null){
					JSONStringer stringer = new JSONStringer();
					
					stringer.array();
					for(int i=0;i<cateList.size();i++){
						stringer.object().key("id").value(cateList.get(i).getId()).
						key("name").value(cateList.get(i).getName()).endObject();
					}
					
					response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
					response.setContentType("text/json; charset=UTF-8");
				}
		}else if(action.equals("update")){
			String id = request.getParameter("id");
			String name = request.getParameter("name");
			JSONStringer stringer = new JSONStringer();
			int count = CategoryAction.updateCategory(Integer.parseInt(id), name);
			stringer.array();
			if(count!=0){
				stringer.object().key("success").value("true").endObject();
			}else{
				stringer.object().key("success").value("false").endObject();
			}
			stringer.endArray();
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
			response.setContentType("text/json; charset=UTF-8");
		}else if(action.equals("delete")){
			String id = request.getParameter("id");
			JSONStringer stringer = new JSONStringer();
			int count = CategoryAction.deleteCategory(Integer.parseInt(id));
			stringer.array();
			if(count!=0){
				stringer.object().key("success").value("true").endObject();
			}else{
				stringer.object().key("success").value("false").endObject();
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
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
