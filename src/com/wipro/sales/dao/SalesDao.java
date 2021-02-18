package com.wipro.sales.dao;

import java.util.ArrayList;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import com.wipro.sales.bean.Sales;
import com.wipro.sales.bean.SalesReport;
import com.wipro.sales.util.*;

public class SalesDao {
	PreparedStatement ps;
	Connection con;
	public SalesDao(){
		con = DBUtil.getDBConnected();
	}
	
	
	public int inserSales(Sales sales) {
//		add code here
		int t=0;
		try {
//			Connection con = DBUtil.getDBConnected();
			
			ps = con.prepareStatement("insert into TBL_SALES values(?,?,?,?,?)");
			
			java.sql.Date sqlDate = new java.sql.Date(sales.getSalesDate().getTime());
			ps.setString(1, sales.getSalesID());
			ps.setDate(2, sqlDate);
			ps.setString(3, sales.getProductId());
			ps.setInt(4, sales.getQuantitySold());
			ps.setDouble(5, sales.getSalesPricePerUnit());
			
			t = ps.executeUpdate();
		}
		catch(Exception e) {
			System.out.println("insert Sales issue in salesdao..");

			System.out.println(e);
		}
		return t;
	}
	
	public String generateSalesID(java.util.Date salesDate){
//		add code here
//		SEQ_SALES_ID -> NAME OF THE SEQUENCE IN DB
//		Connection con = DBUtil.getDBConnected();
		DateFormat df = new SimpleDateFormat("yy");
		String formattedDate = df.format(salesDate);
		try {
			ps = con.prepareStatement("select seq_sales_id.nextval from dual");
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				return ""+formattedDate+rs.getInt(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Generte Sales id issue in salesdao..");
			e.printStackTrace();
		}
		return null;
		
	}
	public ArrayList<SalesReport> getSalesReport(){
//		add code here
//		Connection con = DBUtil.getDBConnected();
		ArrayList<SalesReport> list = new ArrayList<>();
		list.clear();
		try {
			ps = con.prepareStatement("select * from V_SALES_REPORT");
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				SalesReport ob = new SalesReport();
				ob.setSalesid(rs.getString(1));
				ob.setSalesDate(rs.getDate(2));
				ob.setProductID(rs.getString(3));
				ob.setProductName(rs.getString(4));
				ob.setQuantitySold(rs.getInt(5));
				ob.setProductUnitPrice(rs.getDouble(6));
				ob.setSalesPricePerUnit(rs.getDouble(7));
				ob.setProfitAmount(rs.getDouble(8));
				list.add(ob);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("get sales report issue in salesdao..");

			e.printStackTrace();
		}
		
		return list;
		
	}

}
