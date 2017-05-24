package com.ncu.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.ncu.dbUtil.DBUtil;
import com.ncu.entity.Records;

public class RecordDao {
	public ArrayList<Records> getAll(){
		ArrayList<Records> rList = new ArrayList<Records>();
		Connection conn = null;
		ResultSet rs =null;
		PreparedStatement st = null;
		try {
			conn = DBUtil.getConnection();
			String sql = "select * from record";
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
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			try {
				if(rs!=null){
					rs.close();
					rs = null;
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();

			}
		}
	}

}
