package com.ncu.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import com.ncu.dbUtil.DBUtil;
import com.ncu.entity.Records;
import com.ncu.model.CostModel;
import com.ncu.model.FormModel;
import com.ncu.model.WeekModel;

public class RecordDao {
	
	//查询反回折线图（过去7天）
	public ArrayList<WeekModel> findWeekModel(int type){
		ArrayList<WeekModel> wList = new ArrayList<WeekModel>();
		Connection conn = null;
		ResultSet rs =null;
		PreparedStatement st = null;
		try {
			conn = DBUtil.getConnection();
			String sql ="select type_id ,sum(sum) as sumC,date from record  where type_id = ? group by date order by date limit 7";
			st = conn.prepareStatement(sql);
			st.setInt(1, type);
			rs = st.executeQuery();
			while(rs.next()){
				WeekModel w = new WeekModel();
				w.setType(rs.getInt("type_id"));
				w.setSum(rs.getDouble("sumC"));
				w.setDate(rs.getDate("date"));
				wList.add(w);
			}
			return wList;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	//查询返回支出统计图表
	
	public ArrayList<FormModel> findFormModel(int type){
		ArrayList<FormModel> fList = new ArrayList<FormModel>();
		Connection conn = null;
		ResultSet rs =null;
		PreparedStatement st = null;
		try {
			conn = DBUtil.getConnection();
			String sql ="select c.name as category_name, sum(`sum`) as sum from record as r left join category as c on r.category_id = c.id where type_id =? group by c.name ;";
			st = conn.prepareStatement(sql);
			st.setInt(1, type);
			rs = st.executeQuery();
			while(rs.next()){
				FormModel f = new FormModel();
				f.setCategoryName(rs.getString("category_name"));
				f.setSum(rs.getDouble("sum"));
				fList.add(f);
			}
			return fList;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		
	}
	//按条件查询以CostModel对象显示
	public ArrayList<CostModel> findByA(int category_id,String note){
		ArrayList<CostModel> cList = new ArrayList<CostModel>();
		Connection conn = null;
		ResultSet rs =null;
		PreparedStatement st = null;
		try {
			conn = DBUtil.getConnection();
			String sql = "select r.id as id,m.`name` as member_name ,c.name as category_name ,sum,type_id,`date` from record as r left join category as c on c.id = r.category_id left join member as m on m.id = r.member_id where c.id =? and m.name like ?";
			st = conn.prepareStatement(sql);
			st.setInt(1, category_id);
			st.setString(2, "%"+note+"%");
			rs = st.executeQuery();
			while(rs.next()){
				CostModel c = new CostModel();
				c.setId(rs.getInt("id"));
				c.setMemberName(rs.getString("member_name"));
				c.setSum(rs.getDouble("sum"));
				c.setDate(rs.getDate("date"));
				c.setTypeName(rs.getString("category_name"));
				cList.add(c);
				
			}
			return cList;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	//查询所有项目以CostModel对象显示
	public ArrayList<CostModel> getAllCostModel(int type){
		ArrayList<CostModel> cList = new ArrayList<CostModel>();
		Connection conn = null;
		ResultSet rs =null;
		PreparedStatement st = null;
		try {
			conn = DBUtil.getConnection();
			String sql = "select r.id as id,m.`name` as member_name ,c.name as category_name ,sum,type_id,`date` from record as r left join category as c on c.id = r.category_id left join member as m on m.id = r.member_id where type_id =? ";
			st = conn.prepareStatement(sql);
			st.setInt(1, type);
			rs = st.executeQuery();
			while(rs.next()){
				CostModel c = new CostModel();
				c.setId(rs.getInt("id"));
				c.setMemberName(rs.getString("member_name"));
				c.setSum(rs.getDouble("sum"));
				c.setDate(rs.getDate("date"));
				c.setTypeName(rs.getString("category_name"));
				cList.add(c);
				
			}
			return cList;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	//查询所有项目type=1（收入项目）type=2（支付项目）
	public ArrayList<Records> getAll(int type){
		ArrayList<Records> rList = new ArrayList<Records>();
		Connection conn = null;
		ResultSet rs =null;
		PreparedStatement st = null;
		try {
			conn = DBUtil.getConnection();
			String sql = "select * from record where type_id = ?";
			st = conn.prepareStatement(sql);
			st.setInt(1, type);
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
	
//	更新项目
	public int updateItem(int id,int category_id,int member_id,double sum, Date date){
		Connection conn = null;
		PreparedStatement st = null;
		try {
			conn = DBUtil.getConnection();
			String sql = "update record set category_id=? , member_id=?,sum=?,date=? where id=?";
			st = conn.prepareStatement(sql);
			st.setInt(1, category_id);
			st.setInt(2, member_id);
			st.setDouble(3, sum);
			st.setDate(4, (java.sql.Date) date);
			st.setInt(5, id);
			return st.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
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
	
	//添加项目type_id =1 收入；type_id =2 支出
	public int addItem(int type_id,int category_id,int member_id,double sum, Date date){
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
			return st.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
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
	public int deleteItem(int id){
		Connection conn = null;
		PreparedStatement st = null;
		try {
			conn = DBUtil.getConnection();
			String sql = "delete from record where id=?";
			st = conn.prepareStatement(sql);
			st.setInt(1, id);
			return st.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
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
