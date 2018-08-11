package cz.cuni.mff.javaui.budgetapp.handlers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.workbench.IWorkbench;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;

public class OpenUserHandler {
	@Execute
	public void execute(Shell shell){
	try {
        Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
    } catch (Exception ex) {
        // handle the error
    }
		
    Connection conn = null;
    try {
        conn =
           DriverManager.getConnection("jdbc:mysql://localhost/budget_db?" +
                                       "user=root&password=root&serverTimezone=UTC");
        Statement stmt = conn.createStatement();
        String query = "SELECT * FROM budget_db.user";
        ResultSet rs = stmt.executeQuery(query);
        if (rs != null) {
        	while (rs.next()) {
        		String s = rs.getString("name");
        		MessageDialog.openConfirm(shell, s, "Please confirm");
        	}
        }
        
    } catch (SQLException ex) {
        // handle any errors
        System.out.println("SQLException: " + ex.getMessage());
        System.out.println("SQLState: " + ex.getSQLState());
        System.out.println("VendorError: " + ex.getErrorCode());
    }
		
	}
}
