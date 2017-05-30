package com.ncu.service;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ncu.action.MemberAction;
import com.ncu.entity.Member;

import net.sf.json.util.JSONStringer;

/**
 * Servlet implementation class MemberService
 */
@WebServlet("/MemberService")
public class MemberService extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MemberService() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		if(action.equals("find")){
			ArrayList<Member> mList = new ArrayList<Member>();
			mList = MemberAction.findAll();
			if(mList!=null){
				JSONStringer stringer = new JSONStringer();
				stringer.array();
				for(int i=0;i<mList.size();i++){
					stringer.object().key("id").value(mList.get(i).getId()).
					key("name").value(mList.get(i).getName()).endObject();
				}
				stringer.endArray();
				response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
				response.setContentType("text/json; charset=UTF-8");
			}
		}else if(action.equals("delete")){
			String id = request.getParameter("id");
			JSONStringer stringer = new JSONStringer();
			int count = MemberAction.delete(Integer.parseInt(id));
			stringer.array();
			if(count!=0){
				stringer.object().key("success").value("true").endObject();
			}else{
				stringer.object().key("success").value("false").endObject();
			}
			stringer.endArray();
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
			response.setContentType("text/json; charset=UTF-8");
		}else if(action.equals("add")){
			String name = request.getParameter("name");
			int count = MemberAction.addMember(name);
			JSONStringer stringer = new JSONStringer();
			stringer.array();
			if(count!=0){
				stringer.object().key("success").value("true").endObject();
			}else{
				stringer.object().key("success").value("false").endObject();
			}
			stringer.endArray();
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
			response.setContentType("text/json; charset=UTF-8"); 
		}else if(action.equals("update")){
			String id = request.getParameter("id");
			String name = request.getParameter("name");
			int count = MemberAction.updateMember(Integer.parseInt(id), name);
			JSONStringer stringer = new JSONStringer();
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

		doGet(request, response);
	}

}
