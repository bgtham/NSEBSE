package com.nse.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnect {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		
		Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            con = DriverManager.getConnection("jdbc:oracle:thin:@//localhost:1521","NSEBSEDATA","SAI@01ram");
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT * FROM nse_bonus_ratios");
            while(rs.next()) {
                System.out.print(rs.getString(1) + "\t\t\t" + rs.getString(2) + "\t\t\t" + rs.getString(3) + "\n");
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                rs.close();
                stmt.close();
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
		
	}

}
