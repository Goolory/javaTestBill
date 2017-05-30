package com.ncu.service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ncu.action.RecordAction;
import com.ncu.entity.Records;

import net.sf.json.util.JSONStringer;

/**
 * Servlet implementation class RecordService
 */
@WebServlet("/RecordService")
public class RecordService extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RecordService() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		JSONStringer stringer = new JSONStringer();
		if(action.equals("findId")){
			String id = request.getParameter("id");
			Records records = new Records();
			records = RecordAction.findById(Integer.parseInt(id));
			stringer.array();
			if(records!=null){
				stringer.object().key("member_id").value(records.getMemberId()).
				key("category_id").value(records.getCategoryId()).endObject();
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
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
