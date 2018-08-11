package cz.cuni.mff.javaui.budgetapp.handlers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.workbench.IWorkbench;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

import cz.cuni.mff.javaui.budgetapp.database.DBData;
import cz.cuni.mff.javaui.budgetapp.dialogs.OpenUserDialog;

public class OpenUserHandler {
	@Execute
	public void execute(Shell shell, MApplication application){
		OpenUserDialog oud = new OpenUserDialog(shell);
	    if (oud.open() == Window.OK) {
	    	application.getContext().set("user", oud.getChoice());
	    	
	    	try {
		        Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
		    } catch (Exception ex) {
		    	application.getContext().remove("user");
		        MessageDialog.openError(shell, "Unable to connect", "The application was unable to connect to the database. Please try again later.");
		    }
				
		    try (Connection conn =
	 	           DriverManager.getConnection(String.format("jdbc:mysql://%s/%s?user=%s&password=%s&serverTimezone=%s",
	 	        		   DBData.host, DBData.database, DBData.user, DBData.password, DBData.serverTimeZone))) {
		        PreparedStatement ps = conn.prepareStatement("SELECT * FROM budget_db.period");
		        ResultSet rs = ps.executeQuery();
	        	List<String> ret = new ArrayList<String>();
	        	while (rs.next()) {
	        		ret.add(rs.getString("name"));
	        	}

		    } catch (SQLException ex) {
		    	application.getContext().remove("user");
		    	MessageDialog.openError(shell, "DB Connection Error", "Database error. Check your internet connection and try again.");
		        System.out.println("SQLException: " + ex.getMessage());
		        System.out.println("SQLState: " + ex.getSQLState());
		        System.out.println("VendorError: " + ex.getErrorCode());
		    }
	    	
	    	
	    	
	    	
	    	
	    	
	    	
	    	
	    	
	    	
	    	
	    	
	    	
	    	
	    	
	    	
	    	
	    	
	    	
	    	
	    	
	    	
	    	
	    	
	    	
	    	
	    	
	    	
	    	
	    	
	    	
	    	
	    	
	    	
	    	
	    	
	    	
	    	
	    	
	    	
	    	
	    	
	    	
	    	
	    	
	    	
	    	
	    	
	    	
	    	
	    	
	    	
	    	
	    	
	    	
	    	
	    	
	    	
	    	
	    	
	    }
		oud = null;
		
	}
}
