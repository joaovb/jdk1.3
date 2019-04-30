/*
 * Copyright 2002 Sun Microsystems, Inc. All rights reserved.
 * SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

import java.sql.*;
     
// Create the Coffee Table.
public class CreateCoffees {

	public static void main(String args[]) {		  
		  
      /****************
	  DataTable dt = new JDBCAdapter(
            "jdbc:sybase://dbtest:1455/spring",
            "SELECT * FROM test",
            "connect.sybase.SybaseDriver",
            "guest",
            "trustworthy");
	  *******************************/

		String url = "jdbc:sybase://dbtest:1455/spring";
		Connection con;
		String createString;
		createString = "create table COFFEES " +
							"(COF_NAME varchar(32), " +
							"SUP_ID int, " +
							"PRICE float, " +
							"SALES int, " +
							"TOTAL int)";
		Statement stmt;
	
		try {
			Class.forName("connect.sybase.SybaseDriver");

		} catch(java.lang.ClassNotFoundException e) {
			System.err.print("ClassNotFoundException: "); 
			System.err.println(e.getMessage());
		}

		try {
			con = DriverManager.getConnection(url, 
									 "guest", "trustworthy");
	
			stmt = con.createStatement();							
	   		    stmt.executeUpdate(createString);
	
			stmt.close();
			con.close();
	
		} catch(SQLException ex) {
			System.err.println("SQLException: " + ex.getMessage());
		}

	}
}

