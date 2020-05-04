package com.test.automation.selenium.framework;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
//import org.postgresql.Driver;
public class DBUtil {
	
	
	
	public static void updateQuery(String strQuery) throws Exception, ClassNotFoundException, SQLException {
		
		String dbUrl = "jdbc:postgresql://qa-env-edgepaydb.czzbnjuacdwe.us-west-1.rds.amazonaws.com:5432/edgepay_gateway_db";
		String username = "qaenvedgepay";  
		String password = "qaenvedgepay";  		
	
			
		Class.forName("org.postgresql.Driver");  
		Connection con = DriverManager.getConnection(dbUrl,username,password);  			
        Statement stmt = con.createStatement();   		
        stmt.executeUpdate(strQuery);
        con.close();        
		
	} 
		
	public static ResultSet fetchQuery(String strQuery) throws Exception, ClassNotFoundException, SQLException {
		
		String dbUrl = "jdbc:postgresql://qa-env-edgepaydb.czzbnjuacdwe.us-west-1.rds.amazonaws.com:5432/edgepay_gateway_db";
		String username = "qaenvedgepay"; 
		String password = "qaenvedgepay";
		Class.forName("org.postgresql.Driver");  
		Connection con = DriverManager.getConnection(dbUrl,username,password);  			
        Statement stmt = con.createStatement();   		
        ResultSet rs =  stmt.executeQuery(strQuery);   	
        con.close();
	    return rs;
	  
	}

	
}
