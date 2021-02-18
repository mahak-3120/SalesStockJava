package com.wipro.sales.main;

import com.wipro.sales.bean.Product;
import com.wipro.sales.bean.Sales;
import com.wipro.sales.bean.SalesReport;
import com.wipro.sales.service.*;

import java.awt.EventQueue;

import javax.swing.*;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.Insets;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.table.*;
//import com.toedter.calendar.JDateChooser;

public class SalesApp {

	private JFrame frmStockSales;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JTextField tf1;
	private JTextField tf2;
	private JTextField tf3;
	private JTextField tf4;
	private JTextField tf5;
	private JTextField tf6;
	private JFormattedTextField tf7;
	private JTextField tf8;
	private JTextField tf9;
	private JPanel panel_inSales;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SalesApp window = new SalesApp();
					window.frmStockSales.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	Administrator admin;
	private JTable table;

	public SalesApp() {
		admin = new Administrator();
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		JPanel panel_insStock;
		frmStockSales = new JFrame();
		frmStockSales.getContentPane().setFont(new Font("Poppins", Font.PLAIN, 12));
		frmStockSales.setTitle("Stock & Sales");
		frmStockSales.getContentPane().setBackground(new Color(51, 204, 204));
		frmStockSales.setBounds(100, 100, 761, 491);
		frmStockSales.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmStockSales.getContentPane().setLayout(null);

//		********		HEADING			**********
		JLabel lblHead = new JLabel("INVENTORY AND SALES SYSTEM");
		lblHead.setFont(new Font("Poppins ExtraBold", Font.BOLD, 20));
		lblHead.setBounds(194, 30, 358, 40);
		frmStockSales.getContentPane().add(lblHead);

//		---- Text Area ----

		JTextArea display_txt = new JTextArea();
		display_txt.setVisible(false);
		display_txt.setEditable(false);
		display_txt.setFont(new Font("Monospaced", Font.BOLD, 13));
		display_txt.setMargin(new Insets(10, 10, 10, 10));
		display_txt.setBorder(null);
		display_txt.setRows(10);
		display_txt.setColumns(10);
		display_txt.setBounds(308, 286, 293, 50);
		frmStockSales.getContentPane().add(display_txt);

//				----****----	JTABLE	-----*****------
		String col[] = { "Sales ID", "Sales Date", "Product ID", "Product Name", "Quantity Sold", "Product Unit Price",
				"Sales Price Per Unit", "Profit Amount" };
		DefaultTableModel tablemodel = new DefaultTableModel(col, 0);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setOpaque(false);
		scrollPane.setBackground(Color.WHITE);
		scrollPane.setBounds(10, 226, 727, 163);
		frmStockSales.getContentPane().add(scrollPane);
		table = new JTable();
		table.setRowMargin(4);
		table.setRowHeight(20);
		table.setSurrendersFocusOnKeystroke(true);
		scrollPane.setViewportView(table);
		table.setModel(tablemodel);
		scrollPane.setVisible(false);
		table.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 12));
		table.setEnabled(false);

//				------	DELETE STOCK PANEL HERE	------

		JPanel panel_delStk = new JPanel();
		panel_delStk.setVisible(false);
		panel_delStk.setBackground(new Color(51, 204, 204));
		panel_delStk.setBounds(299, 80, 238, 100);
		frmStockSales.getContentPane().add(panel_delStk);
		panel_delStk.setLayout(null);

		JLabel lblProductId = new JLabel("Product ID");
		lblProductId.setBounds(0, 0, 78, 30);
		lblProductId.setLabelFor(tf5);
		panel_delStk.add(lblProductId);
		lblProductId.setFont(new Font("Montserrat Light", Font.PLAIN, 12));

		tf5 = new JTextField();
		tf5.setBounds(88, 6, 150, 25);
		panel_delStk.add(tf5);
		tf5.setFont(new Font("Calibri Light", Font.PLAIN, 11));
		tf5.setColumns(10);

		JButton btnDeleteStock = new JButton("Delete Stock");
		btnDeleteStock.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
//				delete stock logic here
				try {
					String response = "";
					String id = tf5.getText().trim();
					if (id.length() == 6)
						response = admin.deleteStock(id);
					else
						response = "Enter a valid ID";
					JOptionPane.showMessageDialog(frmStockSales, response);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(frmStockSales, "Sorry!! Product cannot be deleted. ");
					e1.printStackTrace();
				}
			}
		});
		btnDeleteStock.setBounds(52, 45, 116, 25);
		panel_delStk.add(btnDeleteStock);
		btnDeleteStock.setForeground(new Color(0, 0, 139));
		btnDeleteStock.setFont(new Font("Poppins", Font.PLAIN, 12));
		btnDeleteStock.setBackground(Color.WHITE);

//		---------	INSERT STOCK PANEL HERE		------------
		panel_insStock = new JPanel();
		panel_insStock.setVisible(false);
		panel_insStock.setBorder(null);
		panel_insStock.setBackground(new Color(51, 204, 204));
		panel_insStock.setBounds(308, 80, 293, 185);
		frmStockSales.getContentPane().add(panel_insStock);
		panel_insStock.setLayout(null);

		JLabel lblPdtName = new JLabel("Product Name");
		lblPdtName.setBounds(0, 0, 103, 30);
		lblPdtName.setLabelFor(tf1);
		panel_insStock.add(lblPdtName);
		lblPdtName.setFont(new Font("Montserrat Light", Font.PLAIN, 12));

		JLabel lblQuantityOnHand = new JLabel("Quantity On Hand");
		lblQuantityOnHand.setBounds(0, 40, 121, 30);
		lblQuantityOnHand.setLabelFor(tf2);
		panel_insStock.add(lblQuantityOnHand);
		lblQuantityOnHand.setFont(new Font("Montserrat Light", Font.PLAIN, 12));

		JLabel lblProductUnitPrice = new JLabel("Product Unit Price");
		lblProductUnitPrice.setBounds(0, 80, 121, 30);
		lblProductUnitPrice.setLabelFor(tf3);
		panel_insStock.add(lblProductUnitPrice);
		lblProductUnitPrice.setFont(new Font("Montserrat Light", Font.PLAIN, 12));

		JLabel lblReorderLevel = new JLabel("Reorder Level");
		lblReorderLevel.setBounds(0, 120, 103, 30);
		lblReorderLevel.setLabelFor(tf4);
		panel_insStock.add(lblReorderLevel);
		lblReorderLevel.setFont(new Font("Montserrat Light", Font.PLAIN, 12));

		tf1 = new JTextField();
		tf1.setFont(new Font("Calibri Light", Font.PLAIN, 11));
		tf1.setBounds(123, 0, 170, 25);
		panel_insStock.add(tf1);
		tf1.setColumns(10);

		tf2 = new JTextField();
		tf2.setFont(new Font("Calibri", Font.PLAIN, 11));
		tf2.setBounds(123, 40, 170, 25);
		panel_insStock.add(tf2);
		tf2.setColumns(10);

		tf3 = new JTextField();
		tf3.setFont(new Font("Calibri", Font.PLAIN, 11));
		tf3.setBounds(123, 80, 170, 25);
		panel_insStock.add(tf3);
		tf3.setColumns(10);

		tf4 = new JTextField();
		tf4.setFont(new Font("Calibri", Font.PLAIN, 11));
		tf4.setBounds(123, 120, 170, 25);
		panel_insStock.add(tf4);
		tf4.setColumns(10);

//		------------	INSERT STOCK BUTTON HERE 	-------------

		JButton btnInsert = new JButton("Insert Stock");

		btnInsert.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Product stockobj = new Product();
				if (tf1.getText() != "")
					stockobj.setProductName(tf1.getText().trim());
				if (tf2.getText() != "")
					stockobj.setQuantityOnHand(Integer.parseInt(tf2.getText()));
				if (tf3.getText() != "")
					stockobj.setProductUnitPrice(Double.parseDouble(tf3.getText()));
				if (tf4.getText() != "")
					stockobj.setReorderLevel(Integer.parseInt(tf4.getText()));
				String response = admin.insertStock(stockobj);
				String txt = "Stock inserted successfully.\nPRODUCT ID : " + response;
				if (response.length() == 6)
					display_txt.setText(txt);
				else
					display_txt.setText(response);
				display_txt.setVisible(true);

			}
		});
		btnInsert.setForeground(new Color(0, 0, 139));
		btnInsert.setBackground(new Color(255, 255, 255));
		btnInsert.setFont(new Font("Poppins", Font.PLAIN, 12));
		btnInsert.setBounds(90, 160, 121, 25);
		panel_insStock.add(btnInsert);

//		------------INSERT SALES PANEL HERE-----------------
		panel_inSales = new JPanel();
		panel_inSales.setVisible(false);
		panel_inSales.setBounds(299, 80, 312, 163);
		frmStockSales.getContentPane().add(panel_inSales);
		panel_inSales.setBackground(new Color(51, 204, 204));
		panel_inSales.setLayout(null);

		JLabel lblProductId_1 = new JLabel("Product ID");
		lblProductId_1.setBounds(0, 0, 78, 30);
		lblProductId_1.setLabelFor(tf6);
		panel_inSales.add(lblProductId_1);
		lblProductId_1.setFont(new Font("Montserrat Light", Font.PLAIN, 12));
		// ------------- DATE -------------------
		JLabel lblDate = new JLabel("Date");
		lblDate.setBounds(0, 32, 78, 30);
		lblDate.setLabelFor(tf7);
		panel_inSales.add(lblDate);
		lblDate.setFont(new Font("Montserrat Light", Font.PLAIN, 12));

		JLabel lblQuantitySold = new JLabel("Quantity Sold");
		lblQuantitySold.setBounds(0, 64, 109, 30);
		lblQuantitySold.setLabelFor(tf8);
		panel_inSales.add(lblQuantitySold);
		lblQuantitySold.setFont(new Font("Montserrat Light", Font.PLAIN, 12));

		JLabel lblSalesPricePer = new JLabel("Sales Price Per Unit");
		lblSalesPricePer.setBounds(0, 98, 132, 30);
		lblSalesPricePer.setLabelFor(tf9);
		panel_inSales.add(lblSalesPricePer);
		lblSalesPricePer.setFont(new Font("Montserrat Light", Font.PLAIN, 12));

		tf6 = new JTextField();
		tf6.setBounds(142, 0, 170, 25);
		panel_inSales.add(tf6);
		tf6.setFont(new Font("Calibri", Font.PLAIN, 11));
		tf6.setColumns(10);
//		--------------	DATE	-----------------------
//		DateFormat dateFormat = new SimpleDateFormat("dd/MM/YYYY");
		tf7 = new JFormattedTextField();
//		tf7.setValue(new java.util.Date());
		tf7.setText("dd/MM/yyyy");
		tf7.setBounds(142, 30, 170, 25);
		panel_inSales.add(tf7);
		tf7.setFont(new Font("Calibri", Font.PLAIN, 11));
		tf7.setColumns(10);
//		-----------------------------------------------
		tf8 = new JTextField();
		tf8.setBounds(142, 64, 170, 25);
		panel_inSales.add(tf8);
		tf8.setFont(new Font("Calibri", Font.PLAIN, 11));
		tf8.setColumns(10);

		tf9 = new JTextField();
		tf9.setBounds(142, 98, 170, 25);
		panel_inSales.add(tf9);
		tf9.setFont(new Font("Calibri", Font.PLAIN, 11));
		tf9.setColumns(10);

		JButton btnInsertSales = new JButton("Insert Sales");
		btnInsertSales.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// ...insert sales logic here...
				Sales saleobj = new Sales();
				try {
					if (tf6.getText() != "")
						saleobj.setProductId(tf6.getText().trim());
					String t = tf7.getText();
//				java.util.Date d= (java.util.Date)tf7.getValue();
					if (tf7.getText() != "") {
						Date d = new SimpleDateFormat("dd/MM/yyyy").parse(t);
						saleobj.setSalesDate(d);
					}
					if (tf8.getText() != "")
						saleobj.setQuantitySold(Integer.parseInt(tf8.getText()));
					if (tf9.getText() != "")
						saleobj.setSalesPricePerUnit(Double.parseDouble(tf9.getText()));

					String response = admin.insertSales(saleobj);
					if (response.length() == 6)
						JOptionPane.showMessageDialog(frmStockSales,
								"Sales inserted successfully.\nSALES ID : " + response);
					else
//					display_txt.setText(response);
//					display_txt.setVisible(true);
						JOptionPane.showMessageDialog(frmStockSales, response);
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(frmStockSales, "Could not insert into Sales");
				}
			}
		});
		btnInsertSales.setForeground(new Color(0, 0, 139));
		btnInsertSales.setFont(new Font("Poppins", Font.PLAIN, 12));
		btnInsertSales.setBackground(Color.WHITE);
		btnInsertSales.setBounds(98, 138, 121, 25);
		panel_inSales.add(btnInsertSales);

//				**************   RADIO BUTTONS 	******************		

		JRadioButton rdbtnNewRadioButton = new JRadioButton("Insert Stock");
		rdbtnNewRadioButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				panel_insStock.setVisible(true);
				panel_delStk.setVisible(false);
				panel_inSales.setVisible(false);
				display_txt.setVisible(false);
				scrollPane.setVisible(false);

			}
		});
		rdbtnNewRadioButton.setFont(new Font("Montserrat", Font.PLAIN, 14));
		buttonGroup.add(rdbtnNewRadioButton);
		rdbtnNewRadioButton.setBackground(new Color(51, 204, 204));
		rdbtnNewRadioButton.setBounds(31, 80, 121, 30);
		frmStockSales.getContentPane().add(rdbtnNewRadioButton);

		JRadioButton rdbtnDeleteStock = new JRadioButton("Delete Stock");
		rdbtnDeleteStock.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panel_insStock.setVisible(false);
				panel_inSales.setVisible(false);
				panel_delStk.setVisible(true);
				display_txt.setVisible(false);
				scrollPane.setVisible(false);

			}
		});
		rdbtnDeleteStock.setFont(new Font("Montserrat", Font.PLAIN, 14));
		buttonGroup.add(rdbtnDeleteStock);
		rdbtnDeleteStock.setBackground(new Color(51, 204, 204));
		rdbtnDeleteStock.setBounds(31, 110, 121, 30);
		frmStockSales.getContentPane().add(rdbtnDeleteStock);

		JRadioButton rdbtnInsertSales = new JRadioButton("Insert Sales");
		rdbtnInsertSales.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panel_insStock.setVisible(false);
				panel_delStk.setVisible(false);
				panel_inSales.setVisible(true);
				display_txt.setVisible(false);
				scrollPane.setVisible(false);

			}
		});
		rdbtnInsertSales.setFont(new Font("Montserrat", Font.PLAIN, 14));
		buttonGroup.add(rdbtnInsertSales);
		rdbtnInsertSales.setBackground(new Color(51, 204, 204));
		rdbtnInsertSales.setBounds(31, 142, 121, 30);
		frmStockSales.getContentPane().add(rdbtnInsertSales);

		JRadioButton rdbtnViewSalesReport = new JRadioButton("View Sales Report");

		rdbtnViewSalesReport.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				panel_insStock.setVisible(false);
				panel_delStk.setVisible(false);
				panel_inSales.setVisible(false);
				display_txt.setVisible(false);
				scrollPane.setVisible(true);
				ArrayList<SalesReport> list;
				list = admin.getSalesReport();
				tablemodel.setNumRows(0);

				for (int i = 0; i < list.size(); i++) {
					Object row[] = new Object[8];
					row[0] = list.get(i).getSalesid();
					row[1] = list.get(i).getSalesDate();
					row[2] = list.get(i).getProductID();
					row[3] = list.get(i).getProductName();
					row[4] = list.get(i).getQuantitySold();
					row[5] = list.get(i).getProductUnitPrice();
					row[6] = list.get(i).getSalesPricePerUnit();
					row[7] = list.get(i).getProfitAmount();
//					System.out.println(list.get(i).getSalesid()+" "+list.get(i).getProductID()+" "+list.get(i).getProductName());
					tablemodel.addRow(row);
				}
			}
		});
		rdbtnViewSalesReport.setFont(new Font("Montserrat", Font.PLAIN, 14));
		buttonGroup.add(rdbtnViewSalesReport);
		rdbtnViewSalesReport.setBackground(new Color(51, 204, 204));
		rdbtnViewSalesReport.setBounds(31, 173, 159, 30);
		frmStockSales.getContentPane().add(rdbtnViewSalesReport);

//				-------------	end of radio buttons	------------------

	}
}
