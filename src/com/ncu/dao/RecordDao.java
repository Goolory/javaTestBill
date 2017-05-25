package com.ncu.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import com.ncu.dbUtil.DBUtil;
import com.ncu.entity.Records;

public class RecordDao {
	//查询所有支出项目
	public ArrayList<Records> getAllCost(){
		ArrayList<Records> rList = new ArrayList<Records>();
		Connection conn = null;
		ResultSet rs =null;
		PreparedStatement st = null;
		try {
			conn = DBUtil.getConnection();
			String sql = "select * from record where type_id = 2";
			st = conn.prepareStatement(sql);
			rs = st.executeQuery();
			while(rs.next()){
				Records s = new Records();
				s.setTypeId(rs.getInt("type_id"));
				s.setCategoryId(rs.getInt("category_id"));
				s.setMemberId(rs.getInt("member_id"));
				s.setSum(rs.getDouble("sum"));
				s.setDate(rs.getDate("date"));
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
	//查询所有收入项目
	public ArrayList<Records> getAllIncome(){
		ArrayList<Records> rList = new ArrayList<Records>();
		Connection conn = null;
		ResultSet rs =null;
		PreparedStatement st = null;
		try {
			conn = DBUtil.getConnection();
			String sql = "select * from record where type_id = 1";
			st = conn.prepareStatement(sql);
			rs = st.executeQuery();
			while(rs.next()){
				Records s = new Records();
				s.setTypeId(rs.getInt("type_id"));
				s.setCategoryId(rs.getInt("category_id"));
				s.setMemberId(rs.getInt("member_id"));
				s.setSum(rs.getDouble("sum"));
				s.setDate(rs.getDate("date"));
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
	
	//添加项目
	public void addItem(int type_id,int category_id,int member_id,double sum, Date date){
		Connection conn = null;
		PreparedStatement st = null;
		try {
			conn = DBUtil.getConnection();
			String sql = "insert into record (type_id,category_id,member_id,`sum`,`date`) values(?,?,?,?,?)";
			st = conn.prepareStatement(sql);
			st.setInt(1, type_id);
			st.setInt(2, category_id);
			st.setInt(3, member_id);
			st.setDouble(4, sum);
			st.setDate(5, (java.sql.Date) date);
			st.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
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
	//删除项目
	public void deleteItem(int id){
		Connection conn = null;
		PreparedStatement st = null;
		try {
			conn = DBUtil.getConnection();
			String sql = "delete from record where id=?";
			st = conn.prepareStatement(sql);
			st.setInt(1, id);
			st.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
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
