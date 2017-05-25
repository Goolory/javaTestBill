package com.ncu.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.ncu.dbUtil.DBUtil;
import com.ncu.entity.Member;

public class MemberDao {
	//查询所有成员
	public ArrayList<Member> findAll(){
		ArrayList<Member> mList = new ArrayList<Member>();
		Connection conn = null;
		ResultSet rs =null;
		PreparedStatement st = null;
		try {
			conn = DBUtil.getConnection();
			String sql = "select * from member";
			st = conn.prepareStatement(sql);
			rs = st.executeQuery();
			while(rs.next()){
				Member m = new Member();
				m.setId(rs.getInt("id"));
				m.setName(rs.getString("name"));
				mList.add(m);
			}
			return mList;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}finally{
			try {
				if(st!=null){
					st.close();
					st = null;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			try {
				if(rs!=null){
					rs.close();
					rs = null;
				}
			} catch (SQLException e) {
				e.printStackTrace();

			}
		}
	}
	//删除
			public int delete(int id){
				int count;
				Connection conn = null;
				PreparedStatement st = null;
				try {
					conn = DBUtil.getConnection();
					String sql = "delete from member where id=?";
					st = conn.prepareStatement(sql);
					st.setInt(1, id);
					count = st.executeUpdate();
					return count;
				} catch (SQLException e) {
					e.printStackTrace();
					return 0;
				}
				finally{
					try {
						if(st!=null){
							st.close();
							st = null;
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
					
				}
			}
			//添加
			public int add(String name){
				int count;
				Connection conn = null;
				PreparedStatement st = null;
				try {
					conn = DBUtil.getConnection();
					String sql = "insert into member (name) values (?)";
					st = conn.prepareStatement(sql);
					st.setString(1, name);
					count = st.executeUpdate();
					return count;
				} catch (SQLException e) {
					e.printStackTrace();
					return 0;
				}
				finally{
					try {
						if(st!=null){
							st.close();
							st = null;
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
					
				}
			}
			//更新
			public int update(int id, String name){
				int count;
				Connection conn = null;
				PreparedStatement st = null;
				try {
					conn = DBUtil.getConnection();
					String sql = "update member set `name`=? where id=?";
					st = conn.prepareStatement(sql);
					st.setString(1, name);
					st.setInt(2, id);
					count = st.executeUpdate();
					return count;
				} catch (SQLException e) {
					e.printStackTrace();
					return 0;
				}
				finally{
					try {
						if(st!=null){
							st.close();
							st = null;
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
					
				}
				
			}
}
