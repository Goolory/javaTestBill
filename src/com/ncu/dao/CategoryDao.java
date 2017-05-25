package com.ncu.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.ncu.dbUtil.DBUtil;
import com.ncu.entity.Category;

public class CategoryDao {
	//查询所有支出类别
		public ArrayList<Category> getAll(int type){
			ArrayList<Category> rList = new ArrayList<Category>();
			Connection conn = null;
			ResultSet rs =null;
			PreparedStatement st = null;
			try {
				conn = DBUtil.getConnection();
				String sql = "select * from category where type = ?";
				st = conn.prepareStatement(sql);
				st.setInt(1, type);
				rs = st.executeQuery();
				while(rs.next()){
					Category s = new Category();
					s.setId(rs.getInt("id"));
					s.setName(rs.getString("name"));
					s.setType(rs.getInt("type"));
					rList.add(s);
				}
				return rList;
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
		
		//更新
		public int update(int id, String name){
			int count;
			Connection conn = null;
			PreparedStatement st = null;
			try {
				conn = DBUtil.getConnection();
				String sql = "update category set `name`=? where id=?";
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
		//删除
		public int delete(int id){
			int count;
			Connection conn = null;
			PreparedStatement st = null;
			try {
				conn = DBUtil.getConnection();
				String sql = "delete from category where id=?";
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
		public int add(String name,int type){
			int count;
			Connection conn = null;
			PreparedStatement st = null;
			try {
				conn = DBUtil.getConnection();
				String sql = "insert into category (name,type) values (?,?)";
				st = conn.prepareStatement(sql);
				st.setString(1, name);
				st.setInt(2, type);
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
