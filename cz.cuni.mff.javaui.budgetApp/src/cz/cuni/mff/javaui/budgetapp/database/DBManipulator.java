package cz.cuni.mff.javaui.budgetapp.database;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TableItem;

import cz.cuni.mff.javaui.budgetapp.misc.DataLoader;
import cz.cuni.mff.javaui.budgetapp.models.ChartData;
import cz.cuni.mff.javaui.budgetapp.models.Record;
import cz.cuni.mff.javaui.budgetapp.models.User;

/**
 * This static class executes the queries needed by the application.
 * 
 * @author Andrej Jurco
 *
 */
public class DBManipulator {

	/**
	 * Adds a period to the database.
	 * 
	 * @param periodName
	 * @param userID
	 * @param shell
	 * @return true is the transaction was successful, false if the transaction
	 *         failed.
	 */
	public static boolean addPeriod(String periodName, int userID, Shell shell) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
		} catch (Exception ex) {
			MessageDialog.openError(shell, "Unable to connect",
					"The application was unable to connect to the database. Please try again later.");
			return false;
		}

		try (Connection conn = DriverManager
				.getConnection(String.format("jdbc:mysql://%s/%s?user=%s&password=%s&serverTimezone=%s", DBData.host,
						DBData.database, DBData.user, DBData.password, DBData.serverTimeZone))) {
			PreparedStatement ps = conn
					.prepareStatement("INSERT INTO budget_db.period (iduser, period_name) VALUES (?, ?)");
			ps.setInt(1, userID);
			ps.setString(2, periodName);
			if (ps.execute()) {
				return true;
			}
		} catch (SQLException ex) {
			MessageDialog.openError(shell, "DB Connection Error",
					"Database error. Check your internet connection and try again.");
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}
		return false;
	}

	/**
	 * Deletes a period from the database.
	 * 
	 * @param periodID period which has to be deleted.s
	 * @param shell
	 * @return true is the transaction was successful, false if the transaction
	 *         failed.
	 */
	public static boolean deletePeriod(int periodID, Shell shell) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
		} catch (Exception ex) {
			MessageDialog.openError(shell, "Unable to connect",
					"The application was unable to connect to the database. Please try again later.");
			return false;
		}

		try (Connection conn = DriverManager
				.getConnection(String.format("jdbc:mysql://%s/%s?user=%s&password=%s&serverTimezone=%s", DBData.host,
						DBData.database, DBData.user, DBData.password, DBData.serverTimeZone))) {
			PreparedStatement ps = conn.prepareStatement("DELETE FROM budget_db.period WHERE idperiod=? ");
			ps.setInt(1, periodID);
			if (ps.execute()) {
				return true;
			}
		} catch (SQLException ex) {
			MessageDialog.openError(shell, "DB Connection Error",
					"Database error. Check your internet connection and try again.");
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}
		return false;
	}

	/**
	 * Changes the selected period's name.
	 * 
	 * @param newName
	 * @param periodID
	 * @param shell
	 * @return true is the transaction was successful, false if the transaction
	 *         failed.
	 */
	public static boolean editPeriod(String newName, int periodID, Shell shell) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
		} catch (Exception ex) {
			MessageDialog.openError(shell, "Unable to connect",
					"The application was unable to connect to the database. Please try again later.");
			return false;
		}

		try (Connection conn = DriverManager
				.getConnection(String.format("jdbc:mysql://%s/%s?user=%s&password=%s&serverTimezone=%s", DBData.host,
						DBData.database, DBData.user, DBData.password, DBData.serverTimeZone))) {
			PreparedStatement ps = conn.prepareStatement("UPDATE budget_db.period SET period_name=? WHERE idperiod=?");
			ps.setString(1, newName);
			ps.setInt(2, periodID);
			if (ps.execute()) {
				return true;
			}
		} catch (SQLException ex) {
			MessageDialog.openError(shell, "DB Connection Error",
					"Database error. Check your internet connection and try again.");
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}
		return false;
	}

	/**
	 * Adds a record to the database with the input attributes.
	 * 
	 * @param shell
	 * @param amount
	 * @param date
	 * @param text
	 * @param periodID
	 * @return true is the transaction was successful, false if the transaction
	 *         failed.
	 */
	public static boolean addRecord(Shell shell, int amount, Date date, String text, int periodID) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
		} catch (Exception ex) {
			MessageDialog.openError(shell, "Unable to connect",
					"The application was unable to connect to the database. Please try again later.");
			return false;
		}

		try (Connection conn = DriverManager
				.getConnection(String.format("jdbc:mysql://%s/%s?user=%s&password=%s&serverTimezone=%s", DBData.host,
						DBData.database, DBData.user, DBData.password, DBData.serverTimeZone))) {
			PreparedStatement ps = conn.prepareStatement(
					"INSERT INTO budget_db.record (idperiod, amount, date_added, comment) VALUES (?, ?, ?, ?)");
			ps.setInt(1, periodID);
			ps.setInt(2, amount);
			ps.setDate(3, date);
			ps.setString(4, text);
			if (ps.execute()) {
				return true;
			}
		} catch (SQLException ex) {
			MessageDialog.openError(shell, "DB Connection Error",
					"Database error. Check your internet connection and try again.");
			System.out.println("Period ID: " + periodID);
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}
		return false;
	}

	/**
	 * Loads records for the given period.
	 * 
	 * @param shell
	 * @param periodID
	 * @return Returns a list of Record class instances for the given period; null
	 *         if the transactionw as unsuccesful.
	 */
	public static List<Record> getRecords(Shell shell, int periodID) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
		} catch (Exception ex) {
			MessageDialog.openError(shell, "Unable to connect",
					"The application was unable to connect to the database. Please try again later.");
			return null;
		}

		try (Connection conn = DriverManager
				.getConnection(String.format("jdbc:mysql://%s/%s?user=%s&password=%s&serverTimezone=%s", DBData.host,
						DBData.database, DBData.user, DBData.password, DBData.serverTimeZone))) {
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM budget_db.record WHERE idperiod=?");
			ps.setInt(1, periodID);
			ResultSet rs = ps.executeQuery();
			List<Record> ret = new ArrayList<Record>();
			while (rs.next()) {
				ret.add(new Record(rs.getInt("idrecord"), rs.getInt("idperiod"), rs.getInt("amount"),
						rs.getString("comment"), rs.getDate("date_added")));
			}
			return ret;
		} catch (SQLException ex) {
			MessageDialog.openError(shell, "DB Connection Error",
					"Database error. Check your internet connection and try again.");
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}
		return null;
	}

	/**
	 * Updates a record's data provided as attributes of this method.
	 * 
	 * @param shell
	 * @param amount
	 * @param date
	 * @param note
	 * @param recordID Identifies which record to udate.
	 * @return true is the transaction was successful, false if the transaction
	 *         failed.
	 */
	public static boolean editRecord(Shell shell, int amount, Date date, String note, int recordID) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
		} catch (Exception ex) {
			MessageDialog.openError(shell, "Unable to connect",
					"The application was unable to connect to the database. Please try again later.");
			return false;
		}

		try (Connection conn = DriverManager
				.getConnection(String.format("jdbc:mysql://%s/%s?user=%s&password=%s&serverTimezone=%s", DBData.host,
						DBData.database, DBData.user, DBData.password, DBData.serverTimeZone))) {
			PreparedStatement ps = conn
					.prepareStatement("UPDATE budget_db.record SET amount=?, date_added=?, comment=? WHERE idrecord=?");
			ps.setInt(1, amount);
			ps.setDate(2, date);
			ps.setString(3, note);
			ps.setInt(4, recordID);
			if (ps.execute()) {
				return true;
			}
		} catch (SQLException ex) {
			MessageDialog.openError(shell, "DB Connection Error",
					"Database error. Check your internet connection and try again.");
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}
		return false;
	}

	/**
	 * Deletes given record.
	 * 
	 * @param shell
	 * @param recordID
	 * @return true is the transaction was successful, false if the transaction
	 *         failed.
	 */
	public static boolean deleteRecord(Shell shell, int recordID) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
		} catch (Exception ex) {
			MessageDialog.openError(shell, "Unable to connect",
					"The application was unable to connect to the database. Please try again later.");
			return false;
		}

		try (Connection conn = DriverManager
				.getConnection(String.format("jdbc:mysql://%s/%s?user=%s&password=%s&serverTimezone=%s", DBData.host,
						DBData.database, DBData.user, DBData.password, DBData.serverTimeZone))) {
			PreparedStatement ps = conn.prepareStatement("DELETE FROM budget_db.record WHERE idrecord=? ");
			ps.setInt(1, recordID);
			if (ps.execute()) {
				return true;
			}
		} catch (SQLException ex) {
			MessageDialog.openError(shell, "DB Connection Error",
					"Database error. Check your internet connection and try again.");
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}
		return false;
	}

	/**
	 * Updates the balance of user based on the sum of all his records.
	 * 
	 * @param shell
	 * @param userID
	 * @return Returns the newly calculated balance.
	 */
	public static int updateBalance(Shell shell, int userID) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
		} catch (Exception ex) {
			MessageDialog.openError(shell, "Unable to connect",
					"The application was unable to connect to the database. Please try again later.");
			return 0;
		}

		try (Connection conn = DriverManager
				.getConnection(String.format("jdbc:mysql://%s/%s?user=%s&password=%s&serverTimezone=%s", DBData.host,
						DBData.database, DBData.user, DBData.password, DBData.serverTimeZone))) {
			PreparedStatement ps = conn.prepareStatement("SELECT CASE WHEN bal.balance IS NOT NULL THEN bal.balance "
					+ "ELSE 0 END AS currentBalance " + "FROM (SELECT SUM(amount) AS balance "
					+ "FROM budget_db.record " + "WHERE idperiod = ANY (" + "SELECT idperiod "
					+ "FROM budget_db.period " + "WHERE iduser = ?)" + ") AS bal;");
			ps.setInt(1, userID);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				return rs.getInt("currentBalance");
			}
		} catch (SQLException ex) {
			MessageDialog.openError(shell, "DB Connection Error",
					"Database error. Check your internet connection and try again.");
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}
		return 0;
	}

	/**
	 * Updates user field 'balance' with the given value.
	 * 
	 * @param shell
	 * @param userID
	 * @param value
	 * @return true is the transaction was successful, false if the transaction
	 *         failed.
	 */
	public static boolean setBalance(Shell shell, int userID, int value) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
		} catch (Exception ex) {
			MessageDialog.openError(shell, "Unable to connect",
					"The application was unable to connect to the database. Please try again later.");
			return false;
		}

		try (Connection conn = DriverManager
				.getConnection(String.format("jdbc:mysql://%s/%s?user=%s&password=%s&serverTimezone=%s", DBData.host,
						DBData.database, DBData.user, DBData.password, DBData.serverTimeZone))) {
			PreparedStatement ps = conn.prepareStatement("UPDATE budget_db.user SET balance=? WHERE iduser=?");
			ps.setInt(1, value);
			ps.setInt(2, userID);
			if (ps.execute()) {
				return true;
			}
		} catch (SQLException ex) {
			MessageDialog.openError(shell, "DB Connection Error",
					"Database error. Check your internet connection and try again.");
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}
		return false;
	}

	/**
	 * Returns a User class instance of corresponding to the userID provided in the
	 * argument.
	 * 
	 * @param shell
	 * @param userID
	 * @return Returns User instance or null if the transaction failed.
	 */
	public static User getUser(Shell shell, int userID) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
		} catch (Exception ex) {
			MessageDialog.openError(shell, "Unable to connect",
					"The application was unable to connect to the database. Please try again later.");
			return null;
		}
		try (Connection conn = DriverManager
				.getConnection(String.format("jdbc:mysql://%s/%s?user=%s&password=%s&serverTimezone=%s", DBData.host,
						DBData.database, DBData.user, DBData.password, DBData.serverTimeZone))) {
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM budget_db.user WHERE iduser=? LIMIT 1");
			ps.setInt(1, userID);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				User u = new User(rs.getString("name"), (Date) rs.getDate("created"));
				u.setBalance(((Integer) rs.getInt("balance")));
				return u;
			}

		} catch (SQLException ex) {

			MessageDialog.openError(shell, "DB Connection Error",
					"Database error. Check your internet connection and try again.");
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
			return null;
		}
		return null;
	}
	
	/**
	 * Creates a dictionary that maps userID to users's name.
	 * 
	 * @param shell
	 * @return Created dictionary or null if the transaction failed.
	 */
    public static Map<Integer, String> getUsers(Shell shell) {
    	try {
	        Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
	    } catch (Exception ex) {
	        MessageDialog.openError(shell, "Unable to connect", "The application was unable to connect to the database. Please try again later.");
	        return null;
	    }
    	
    	String jdbcUrl = String.format(
    		    "jdbc:mysql://google/%s?cloudSqlInstance=%s"
    		        + "&socketFactory=com.google.cloud.sql.mysql.SocketFactory&useSSL=false",
    		    DBData.database,
    		    DBData.instance);
    	
	    try (Connection conn =
 	           DriverManager.getConnection(String.format(jdbcUrl, DBData.user, DBData.password))) {
	        
	        PreparedStatement ps = conn.prepareStatement("SELECT iduser, name FROM budget_db.user");
	        ResultSet rs = ps.executeQuery();
        	Map<Integer, String> ret = new HashMap<Integer, String>();
        	while (rs.next()) {
        		ret.put(rs.getInt("iduser"), rs.getString("name"));
        	}
        	return ret;

	    } catch (SQLException ex) {
	    	MessageDialog.openError(shell, "DB Connection Error", "Database error. Check your internet connection and try again.");
	        System.out.println("SQLException: " + ex.getMessage());
	        System.out.println("SQLState: " + ex.getSQLState());
	        System.out.println("VendorError: " + ex.getErrorCode());
	        return null;
	    }
    }
    

	/**
	 * Returns ChartData for the graph-drawing ready to use. 
	 * 
	 * @param application
	 * @param shell
	 * @return Returns ChartData class isntance or null if transaction failed.
	 */
	public static ChartData getChartData(MApplication application, Shell shell) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
		} catch (Exception ex) {
			MessageDialog.openError(shell, "Unable to connect",
					"The application was unable to connect to the database. Please try again later.");
			return null;
		}
		try (Connection conn = DriverManager
				.getConnection(String.format("jdbc:mysql://%s/%s?user=%s&password=%s&serverTimezone=%s", DBData.host,
						DBData.database, DBData.user, DBData.password, DBData.serverTimeZone))) {
			PreparedStatement ps = conn.prepareStatement(
					"SELECT MIN(records.date_added) AS min, MAX(records.date_added)AS max FROM (SELECT * FROM budget_db.record WHERE idperiod=? GROUP BY date_added) AS records");
			ps.setInt(1, DataLoader.getPeriod(application));
			System.out.println(DataLoader.getPeriod(application) + " period");
			ResultSet rs = ps.executeQuery();

			ChartData data = null;

			while (rs.next()) {
				data = new ChartData(rs.getDate("min"), rs.getDate("max"));
			}

			if (data == null) {
				return null;
			}

			ps = conn.prepareStatement(
					"SELECT date_added, SUM(amount) AS amount FROM budget_db.record WHERE idperiod=? GROUP BY date_added ORDER BY date_added ");
			ps.setInt(1, DataLoader.getPeriod(application));
			rs = ps.executeQuery();

			while (rs.next()) {
				data.addData(((Date) rs.getDate("date_added")).toLocalDate(), (Integer) rs.getInt("amount"));
			}

			return data;

		} catch (SQLException ex) {

			MessageDialog.openError(shell, "DB Connection Error",
					"Database error. Check your internet connection and try again.");
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
			return null;
		}
	}
}
