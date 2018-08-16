package cz.cuni.mff.javaui.budgetapp.database;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TableItem;

import cz.cuni.mff.javaui.budgetapp.misc.DataLoader;
import cz.cuni.mff.javaui.budgetapp.models.Record;

public class DBManipulator {
	
	public static boolean addPeriod(String periodName, int userID, Shell shell) {
		try {
	        Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
	    } catch (Exception ex) {
	        MessageDialog.openError(shell, "Unable to connect", "The application was unable to connect to the database. Please try again later.");
	        return false;
	    }
    	
	    try (Connection conn =
 	           DriverManager.getConnection(String.format("jdbc:mysql://%s/%s?user=%s&password=%s&serverTimezone=%s",
 	        		   DBData.host, DBData.database, DBData.user, DBData.password, DBData.serverTimeZone))) {
	        PreparedStatement ps = conn.prepareStatement("INSERT INTO budget_db.period (iduser, period_name) VALUES (?, ?)");
	        ps.setInt(1, userID);
	        ps.setString(2, periodName);
	        if (ps.execute()) {
	        	return true;
	        }
	    } catch (SQLException ex) {
	    	MessageDialog.openError(shell, "DB Connection Error", "Database error. Check your internet connection and try again.");
	        System.out.println("SQLException: " + ex.getMessage());
	        System.out.println("SQLState: " + ex.getSQLState());
	        System.out.println("VendorError: " + ex.getErrorCode());
	    }
	    return false;
	}
	
	public static boolean deletePeriod(int periodID, Shell shell) {
		try {
	        Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
	    } catch (Exception ex) {
	        MessageDialog.openError(shell, "Unable to connect", "The application was unable to connect to the database. Please try again later.");
	        return false;
	    }
    	
	    try (Connection conn =
 	           DriverManager.getConnection(String.format("jdbc:mysql://%s/%s?user=%s&password=%s&serverTimezone=%s",
 	        		   DBData.host, DBData.database, DBData.user, DBData.password, DBData.serverTimeZone))) {
	        PreparedStatement ps = conn.prepareStatement("DELETE FROM budget_db.period WHERE idperiod=? ");
	        ps.setInt(1, periodID);
	        if (ps.execute()) {
	        	return true;
	        }
	    } catch (SQLException ex) {
	    	MessageDialog.openError(shell, "DB Connection Error", "Database error. Check your internet connection and try again.");
	        System.out.println("SQLException: " + ex.getMessage());
	        System.out.println("SQLState: " + ex.getSQLState());
	        System.out.println("VendorError: " + ex.getErrorCode());
	    }
	    return false;
	}

	public static boolean editPeriod(String newName, int periodID, Shell shell) {
		try {
	        Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
	    } catch (Exception ex) {
	        MessageDialog.openError(shell, "Unable to connect", "The application was unable to connect to the database. Please try again later.");
	        return false;
	    }
    	
	    try (Connection conn =
 	           DriverManager.getConnection(String.format("jdbc:mysql://%s/%s?user=%s&password=%s&serverTimezone=%s",
 	        		   DBData.host, DBData.database, DBData.user, DBData.password, DBData.serverTimeZone))) {
	        PreparedStatement ps = conn.prepareStatement("UPDATE budget_db.period SET period_name=? WHERE idperiod=?");
	        ps.setString(1, newName);
	        ps.setInt(2, periodID);
	        if (ps.execute()) {
	        	return true;
	        }
	    } catch (SQLException ex) {
	    	MessageDialog.openError(shell, "DB Connection Error", "Database error. Check your internet connection and try again.");
	        System.out.println("SQLException: " + ex.getMessage());
	        System.out.println("SQLState: " + ex.getSQLState());
	        System.out.println("VendorError: " + ex.getErrorCode());
	    }
	    return false;
	}
	
	public static boolean addRecord(Shell shell, int amount, Date date, String text, int periodID) {
		try {
	        Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
	    } catch (Exception ex) {
	        MessageDialog.openError(shell, "Unable to connect", "The application was unable to connect to the database. Please try again later.");
	        return false;
	    }
    	
	    try (Connection conn =
 	           DriverManager.getConnection(String.format("jdbc:mysql://%s/%s?user=%s&password=%s&serverTimezone=%s",
 	        		   DBData.host, DBData.database, DBData.user, DBData.password, DBData.serverTimeZone))) {
	        PreparedStatement ps = conn.prepareStatement("INSERT INTO budget_db.record (idperiod, amount, date_added, comment) VALUES (?, ?, ?, ?)");
	        ps.setInt(1, periodID);
	        ps.setInt(2, amount);
	        ps.setDate(3, date);
	        ps.setString(4, text);
	        if (ps.execute()) {
	        	return true;
	        }
	    } catch (SQLException ex) {
	    	MessageDialog.openError(shell, "DB Connection Error", "Database error. Check your internet connection and try again.");
	    	System.out.println("Period ID: " + periodID);
	        System.out.println("SQLException: " + ex.getMessage());
	        System.out.println("SQLState: " + ex.getSQLState());
	        System.out.println("VendorError: " + ex.getErrorCode());
	    }
	    return false;
	}
	
	public static List<Record> getRecords(Shell shell, int periodID) {
		try {
	        Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
	    } catch (Exception ex) {
	        MessageDialog.openError(shell, "Unable to connect", "The application was unable to connect to the database. Please try again later.");
	        return null;
	    }
    	
	    try (Connection conn =
 	           DriverManager.getConnection(String.format("jdbc:mysql://%s/%s?user=%s&password=%s&serverTimezone=%s",
 	        		   DBData.host, DBData.database, DBData.user, DBData.password, DBData.serverTimeZone))) {
	        PreparedStatement ps = conn.prepareStatement("SELECT * FROM budget_db.record WHERE idperiod=?");
	        ps.setInt(1, periodID);
	        ResultSet rs = ps.executeQuery();
	        List<Record> ret = new ArrayList<Record>();
	        while (rs.next()) {
	        	System.out.println("QUery executed");
	        	ret.add(new Record(rs.getInt("idrecord"), rs.getInt("idperiod"), rs.getInt("amount"), rs.getString("comment"), rs.getDate("date_added")));
	        }
	        return ret;
	    } catch (SQLException ex) {
	    	MessageDialog.openError(shell, "DB Connection Error", "Database error. Check your internet connection and try again.");
	        System.out.println("SQLException: " + ex.getMessage());
	        System.out.println("SQLState: " + ex.getSQLState());
	        System.out.println("VendorError: " + ex.getErrorCode());
	    }
	    return null;
	}
}
