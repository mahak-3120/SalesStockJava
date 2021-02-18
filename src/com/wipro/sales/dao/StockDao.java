package com.wipro.sales.dao;

import java.sql.*;

import com.wipro.sales.bean.*;
import com.wipro.sales.util.DBUtil;

public class StockDao {
	PreparedStatement ps;
	Connection con;
	public StockDao() {
		con = DBUtil.getDBConnected();
	}
//			-------------		INSERT STOCK		-----------------------
	public int insertStock(Product pdt) {
		int t =0;
		try {
			ps = con.prepareStatement("insert into TBL_STOCK VALUES(?,?,?,?,?)");
			ps.setString(1, pdt.getProductId());
			ps.setString(2, pdt.getProductName());
			ps.setInt(3, pdt.getQuantityOnHand());
			ps.setDouble(4, pdt.getProductUnitPrice());
			ps.setInt(5, pdt.getReorderLevel());
			t = ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("insertStock in StockDao issue...");
			e.printStackTrace();
		}
		return t;		
	}
//			--------------	GENERATE PDT ID		--------------------------
	public String generateProductID(String productName) {
		String id = "";
		id+=productName.substring(0, 2);
		try {
			ps=con.prepareStatement("select seq_product_id.nextval from dual");
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				id+=""+rs.getInt(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("its here .. stockdao generate pdt id");
			e.printStackTrace();
		}
		return id;
	}
//			-------------------- 	UPDATE STOCK	-------------------------
	public int updateStock(String productID, int soldQty) {
		int t=0;
		try {
			PreparedStatement p1 = con.prepareStatement("select Quantity_On_Hand from TBL_STOCK where product_id = '"+productID+"'");
			ResultSet r= p1.executeQuery();
			int value=0;
			if(r.next()) {
				value = r.getInt(1);
			}
			ps = con.prepareStatement("update TBL_STOCK set Quantity_On_Hand = ? where Product_ID = '"+productID+"'");
			value-=soldQty;
			ps.setInt(1, value);
			t=ps.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("updateStock in StockDao issue...");
			e.printStackTrace();
		}
		return t;
	}
//		-------------------------	GET STOCK	----------------------------
	public Product getStock(String productID) {
		Product obj = new Product();
		try {
			ps = con.prepareStatement("select * from TBL_STOCK where product_id ='"+productID+"'");
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				obj.setProductId(rs.getString(1));
				obj.setProductName(rs.getString(2));
				obj.setQuantityOnHand(rs.getInt(3));
				obj.setProductUnitPrice(rs.getDouble(4));
				obj.setReorderLevel(rs.getInt(5));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("getStock in StockDao issue...");
			e.printStackTrace();
		}
		return obj;
	}
//		------------------------	DELETE STOCK	------------------------
	public int deleteStock(String productID) {
		try {
			ps = con.prepareStatement("delete from TBL_STOCK where Product_ID ='" + productID+"'");
			int t = ps.executeUpdate();
			return t;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("delete stock in stock dao issue");
			e.printStackTrace();
		}
		return 0;
	}

}
