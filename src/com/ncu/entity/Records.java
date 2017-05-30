package com.ncu.entity;

import java.util.Date;

public class Records {
	private int id;
	private int type_id;
	private int category_id;
	private int member_id;
	private double sum;
	private Date date;
	
	public void setId(int id) {
		this.id = id;
	}
	public int getId() {
		return id;
	}
	public int getTypeId() {
		return type_id;
	}
	public void setTypeId(int type_id) {
		this.type_id = type_id;
	}
	public int getCategoryId() {
		return category_id;
	}
	public void setCategoryId(int category_id) {
		this.category_id = category_id;
	}
	public int getMemberId() {
		return member_id;
	}
	public void setMemberId(int member_id) {
		this.member_id = member_id;
	}
	public double getSum() {
		return sum;
	}
	public void setSum(double sum) {
		this.sum = sum;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
}
