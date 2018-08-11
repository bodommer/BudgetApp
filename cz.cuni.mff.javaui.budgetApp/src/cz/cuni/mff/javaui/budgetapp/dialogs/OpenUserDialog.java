package cz.cuni.mff.javaui.budgetapp.dialogs;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

import cz.cuni.mff.javaui.budgetapp.database.DBData;

public class OpenUserDialog extends Dialog {
    
	private org.eclipse.swt.widgets.List list;
	private Map<Integer, String> names;
	private int choice;
	private Button okButton;
	
	public OpenUserDialog(Shell parentShell) {
        super(parentShell);
    }

    @Override
    protected Control createDialogArea(Composite parent) {
        Composite container = (Composite) super.createDialogArea(parent);
        FillLayout fl = new FillLayout();
        container.setLayout(fl);
        
        
        
        list = new org.eclipse.swt.widgets.List(container, SWT.SINGLE | SWT.BORDER);
        list.setEnabled(true);      
        
        names = getUsers();
        list.removeAll();
        for (int i : names.keySet()) {
        	list.add(names.get(i));
        }
        list.select(0);
        
        list.addSelectionListener( new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (list.getSelectionCount() == 1) {
					okButton.setEnabled(true);
					return;
				}
				okButton.setEnabled(false);
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				okPressed();
			}
		});
        
        return container;
    }
    
    private Map<Integer, String> getUsers() {
    	try {
	        Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
	    } catch (Exception ex) {
	        MessageDialog.openError(getParentShell(), "Unable to connect", "The application was unable to connect to the database. Please try again later.");
	        return null;
	    }
			
	    try (Connection conn =
 	           DriverManager.getConnection(String.format("jdbc:mysql://%s/%s?user=%s&password=%s&serverTimezone=%s",
 	        		   DBData.host, DBData.database, DBData.user, DBData.password, DBData.serverTimeZone))) {
	        
	        PreparedStatement ps = conn.prepareStatement("SELECT name FROM budget_db.user");
	        ResultSet rs = ps.executeQuery();
        	Map<Integer, String> ret = new HashMap<Integer, String>();
        	while (rs.next()) {
        		ret.put(rs.getInt("iduser"), rs.getString("name"));
        	}
        	return ret;

	    } catch (SQLException ex) {
	    	MessageDialog.openError(getParentShell(), "DB Connection Error", "Database error. Check your internet connection and try again.");
	        System.out.println("SQLException: " + ex.getMessage());
	        System.out.println("SQLState: " + ex.getSQLState());
	        System.out.println("VendorError: " + ex.getErrorCode());
	        return null;
	    }
    }
    
    @Override
    protected void createButtonsForButtonBar(Composite parent) {
    	
        createButton(parent, IDialogConstants.OK_ID, "Load", true);
        createButton(parent, IDialogConstants.CANCEL_ID,
                IDialogConstants.CANCEL_LABEL, false);
        
        okButton = getButton(IDialogConstants.OK_ID);
        okButton.setEnabled(true);
    }
    
    @Override
    protected void okPressed() {
    	this.choice = list.getItem(list.getSelectionIndex());
    	super.okPressed();
    }
    
    @Override
    protected void configureShell(Shell shell) {
        super.configureShell(shell);
        shell.setText("Choose the user you want to load");
        shell.setSize(225, 300);
     }
    
    public String getChoice() {
    	return this.choice;
    }
}
