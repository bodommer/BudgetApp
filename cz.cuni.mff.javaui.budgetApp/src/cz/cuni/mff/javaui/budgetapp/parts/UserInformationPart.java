package cz.cuni.mff.javaui.budgetapp.parts;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

import cz.cuni.mff.javaui.budgetapp.database.DBData;

public class UserInformationPart {

	private Label name;
	private Label balance;
	private Label created;
	private Label lastChange;

	private Label nameLabel;
	private Label balanceLabel;
	private Label createdLabel;
	private Label lastChangeLabel;
	
	@PostConstruct
	public void createComposite(Composite parent, MApplication application) {
		parent.setLayout(new GridLayout(2, false));
		
		application.getContext().set("userInformationPart", this);
		
		nameLabel = new Label(parent, SWT.SHADOW_IN);
		nameLabel.setText("Name: ");
		
		name = new Label(parent, SWT.NONE);
		name.setText("John Cena");
		
		balanceLabel = new Label(parent, SWT.VERTICAL);
		balanceLabel.setText("Balance: ");
		
		balance = new Label(parent, SWT.HORIZONTAL);
	    balance.setText("12345");
		
		createdLabel = new Label(parent, SWT.SHADOW_NONE);
		createdLabel.setText("Date create: ");
		
		created = new Label(parent, SWT.CENTER);
		created.setText("03/04/1997");
		
		lastChangeLabel = new Label(parent, SWT.WRAP);
		lastChangeLabel.setText("Last change:");
		
		lastChange = new Label(parent, SWT.RIGHT);
		lastChange.setText("01/08/2018");
		
		GridData gd1 = new GridData();
		GridData gd2 = new GridData();
		GridData gd3 = new GridData();
		GridData gd4 = new GridData();
		GridData gd5 = new GridData();
		GridData gd6 = new GridData();
		GridData gd7 = new GridData();
		GridData gd8 = new GridData();
		
		gd1.horizontalIndent = 10;
		gd1.verticalIndent = 10;
		gd2.horizontalIndent = 10;
		gd2.verticalIndent = 10;
		gd3.horizontalIndent = 10;
		gd3.verticalIndent = 10;
		gd4.horizontalIndent = 10;
		gd4.verticalIndent = 10;
		gd5.horizontalIndent = 10;
		gd5.verticalIndent = 10;
		gd6.horizontalIndent = 10;
		gd6.verticalIndent = 10;
		gd7.horizontalIndent = 10;
		gd7.verticalIndent = 10;
		gd8.horizontalIndent = 10;
		gd8.verticalIndent = 10;
		
		
		nameLabel.setLayoutData(gd1);
		name.setLayoutData(gd2);
		balanceLabel.setLayoutData(gd3);
		balance.setLayoutData(gd4);
		createdLabel.setLayoutData(gd5);
		created.setLayoutData(gd6);
		lastChangeLabel.setLayoutData(gd7);
		lastChange.setLayoutData(gd8);
		
		
		
	}
	
	public boolean updateUserInfo(MApplication application, Shell shell) {
		try {
	        Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
	    } catch (Exception ex) {
	        MessageDialog.openError(shell, "Unable to connect", "The application was unable to connect to the database. Please try again later.");
	        return false;
	    }
    	System.out.println(((Integer) application.getContext().get("user")).toString());
	    try (Connection conn =
 	           DriverManager.getConnection(String.format("jdbc:mysql://%s/%s?user=%s&password=%s&serverTimezone=%s",
 	        		   DBData.host, DBData.database, DBData.user, DBData.password, DBData.serverTimeZone))) {
	        PreparedStatement ps = conn.prepareStatement("SELECT * FROM budget_db.user WHERE iduser=? LIMIT 1");
	        ps.setInt(1, (int) application.getContext().get("user"));
	        ResultSet rs = ps.executeQuery();

	        while (rs.next()) {
	        	name.setText(rs.getString("name"));
	        	balance.setText(((Integer)rs.getInt("balance")).toString());
	        	lastChange.setText(rs.getDate("last_edit").toString());
	        	created.setText(rs.getDate("created").toString());
        	}

	    } catch (SQLException ex) {
	    	application.getContext().remove("user");
	    	MessageDialog.openError(shell, "DB Connection Error", "Database error. Check your internet connection and try again.");
	        System.out.println("SQLException: " + ex.getMessage());
	        System.out.println("SQLState: " + ex.getSQLState());
	        System.out.println("VendorError: " + ex.getErrorCode());
	        return false;
	    }
	    return true;
	}
	
	public void refresh() {
		name.forceFocus();
	}
}
