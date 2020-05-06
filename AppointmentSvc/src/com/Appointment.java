package com;

import java.sql.*;


public class Appointment {

	private Connection connect(){
		
	Connection con = null;
			try
				{
				Class.forName("com.mysql.cj.jdbc.Driver");
				//Provide the correct details: DBServer/DBName, username, password
				con = DriverManager.getConnection("jdbc:mysql://localhost:3306/appointment?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
				}
			catch (Exception e){
				e.printStackTrace();
			}
	return con;
	
	}
	
	//insert-----------------------------------------------------------------------------------------------------------------------------
	
	public String insertappointment(String code, String name, String price, String desc)
    {
			String output = "";
			try
			{
				Connection con = connect();
				if (con == null)
				{    
					return "Error while connecting to the database for inserting.";
				}
				// create a prepared statement
				String query = "insert into appointments"
						+"(`appointmentID`,`appointmentCode`,`appointmentName`,`appointmentPrice`,`appointmentDesc`)"
						 + " values (?, ?, ?, ?, ?)";
						PreparedStatement preparedStmt = con.prepareStatement(query); 
				// binding values
				preparedStmt.setInt(1, 0);
				preparedStmt.setString(2, code);
				preparedStmt.setString(3, name);
				preparedStmt.setDouble(4, Double.parseDouble(price));
				preparedStmt.setString(5, desc); 
	
				// execute the statement
				preparedStmt.execute();
				con.close();
				
				String newItems = readItems();
				output = "{\"status\":\"success\", \"data\": \"" +newItems + "\"}";
			}
				catch (Exception e)
			{
					output = "{\"status\":\"error\", \"data\": \"Error while inserting the item.\"}";
					System.err.println(e.getMessage());
			}
			return output;
    		}
	
	
	//view------------------------------------------------------------------------------------------------------------------------
	public String readItems(){
		
		String output = "";
			try{
			Connection con = connect();
			
			if (con == null){
				return "Error while connecting to the database for reading."; 
			}
			// Prepare the html table to be displayed
			output = "<table border='1'>"
					+ "<tr><th>Item Code</th>"
					+ "<th>Item Name</th>"
					+ "<th>Item Price</th>"
					+ "<th>Item Description</th>"
					+ "<th>Update</th>"
					+ "<th>Remove</th></tr>";
			
			String query = "select * from appointments";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			// iterate through the rows in the result set
			while (rs.next()){
				
				String appointmentID = Integer.toString(rs.getInt("appointmentID"));
				String appointmentCode = rs.getString("appointmentCode");
				String appointmentName = rs.getString("appointmentName");
				String appointmentPrice = Double.toString(rs.getDouble("appointmentPrice"));
				String appointmentDesc = rs.getString("appointmentDesc");

				
				// Add into the html table
				output += "<tr><td><input id='hidItemIDUpdate'name='hidItemIDUpdate' type='hidden' value='" + appointmentID+ "'>" + appointmentCode + "</td>";
				output += "<td>" + appointmentName + "</td>";
				output += "<td>" + appointmentPrice + "</td>";
				output += "<td>" + appointmentDesc + "</td>";
				 // buttons
				
				output += "<td><input name='btnUpdate'type='button' "
						+ "value='Update'class='btnUpdate btn btn-secondary'></td>"
						+ "<td><input name='btnRemove'type='button' "
						+ "value='Remove'class='btnRemove btn btn-danger'data-itemid='"+ appointmentID + "'>" + "</td></tr>";
			}
			
			con.close();
			// Complete the html table
			output += "</table>";
			}
			catch (Exception e){
				output = "Error while reading the items.";
				System.err.println(e.getMessage());
			}
			
	return output;
	
	}
	
	
	//update----------------------------------------------------------------------------------------------------------------
	public String updateItem(String ID, String code, String name, String price, String desc) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for updating.";
			}
			// create a prepared statement
			String query = "UPDATE items SET appointmentCode=?,appointmentName=?,appointmentPrice=?,appointmentDesc=? WHERE appointmentID=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setString(1, code);
			preparedStmt.setString(2, name);
			preparedStmt.setDouble(3, Double.parseDouble(price));
			preparedStmt.setString(4, desc);
			preparedStmt.setInt(5, Integer.parseInt(ID));
			// execute the statement
			preparedStmt.execute();
			con.close();
			
			String newItems = readItems();
			output = "{\"status\":\"success\", \"data\": \"" + newItems + "\"}";;
		} catch (Exception e) {
			output = "{\"status\":\"error\", \"data\": \"Error while updating the item.\"}";
			System.err.println(e.getMessage());
		}
		return output;
	}

	
	//delete-------------------------------------------------------------------------------------------------------------------
	public String deleteItem(String itemID) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {

				return "Error while connecting to the database for deleting.";
			}
			// create a prepared statement
			String query = "delete from appointments where appointmentID=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setInt(1, Integer.parseInt(itemID));
			// execute the statement
			preparedStmt.execute();
			con.close();
			
			String newItems = readItems();
			output = "{\"status\":\"success\", \"data\": \"" + newItems + "\"}";
		} catch (Exception e) {
			output = "{\"status\":\"error\", \"data\": \"Error while deleting the item.\"}";
			System.err.println(e.getMessage());
		}
		return output;
	}
	
}
