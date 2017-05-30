package com.ncu.action;

import java.util.ArrayList;

import com.ncu.dao.MemberDao;
import com.ncu.entity.Member;

public class MemberAction {
	private static MemberDao memberDao = new MemberDao();
	
	public static ArrayList<Member> findAll(){
		return memberDao.findAll();
	}
	public static int delete(int id){
		return memberDao.delete(id);
	}
	
	public static int addMember(String name){
		return memberDao.add(name);
	}
	
	public static int updateMember(int id, String name){
		return memberDao.update(id, name);
	}
}
