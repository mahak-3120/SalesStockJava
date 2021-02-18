package com.wipro.sales.service;

import com.wipro.sales.bean.*;
import com.wipro.sales.dao.*;

import java.util.ArrayList;
import java.util.Date;

public class Administrator {
	
	StockDao ob;	
	public Administrator() {
		// TODO Auto-generated constructor stub
		ob = new StockDao();
	}
	
	
//	INSERT STOCK CALL IN STOCK-DAO
	public String insertStock(Product stockobj) {
		if(stockobj != null) {
			if(stockobj.getProductName().length() > 2)
			{	
				String id = ob.generateProductID(stockobj.getProductName());
				stockobj.setProductId(id);
				ob.insertStock(stockobj);
				return id;
			}
		}
		return "Data not valid for insertion";
	}
	
//	DELETE STOCK CALL IN STOCK-DAO
	public String deleteStock(String ProductID) {
		int t = ob.deleteStock(ProductID);
		if(t == 1) 
			return "Stock is Deleted";
		else
			return "Record cannot be deleted";
		
	}
	
//	INSERT INTO SALES
	public String insertSales(Sales salesobj) {
		Product pd;
		int t = 0;
		if(salesobj !=null)
		{
			if( (pd=ob.getStock(salesobj.getProductId()) ) != null) {
				
				if(pd.getQuantityOnHand() < salesobj.getQuantitySold())
				{
					return "Not enough stock on hand for sales";
				}
				Date saleDate = salesobj.getSalesDate();
				Date curDate = new Date();
				System.out.println("sale date = "+saleDate+" \t cur date = "+curDate);
				if(saleDate.compareTo(curDate) > 0 )
				{
					return "Invalid Date";
				}
				SalesDao obj = new SalesDao();
				String id = obj.generateSalesID(saleDate);
				if(id!=null)
				{
					salesobj.setSalesID(id);
					t = obj.inserSales(salesobj);
				}
				if(t==1)
				{
					int update = ob.updateStock(salesobj.getProductId(), salesobj.getQuantitySold());
//					System.out.println("inserted into sales - successfully");
					if(update == 1) {
						return "Sales Completed";
					}
					else {
						return "Error";
					}
				}
			}
			else
				return "Unknown product for sales";
		}
		return "Object not valid for insertion";
	}
	
//	CALL THE GET-SALES-REPORT METHOD OF SALES-DAO
	
	public ArrayList<SalesReport> getSalesReport(){
		ArrayList<SalesReport> list = new ArrayList<>();
		list.clear();
		list = new SalesDao().getSalesReport();
//		for(SalesReport x : list) {
//			System.out.println(x.getProductID());
//		}
		return list;
	}

}
