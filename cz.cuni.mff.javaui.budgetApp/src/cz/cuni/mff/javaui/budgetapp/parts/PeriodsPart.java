package cz.cuni.mff.javaui.budgetapp.parts;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import cz.cuni.mff.javaui.budgetapp.database.DBData;
import cz.cuni.mff.javaui.budgetapp.database.DBManipulator;
import cz.cuni.mff.javaui.budgetapp.misc.DataLoader;

public class PeriodsPart {

	private List list;
	private Map<Integer, Integer> periodListMapper;
	
	@PostConstruct
	public void createComposite(Composite parent, MApplication application, Shell shell) {
		parent.setLayout(new GridLayout(1, false));
		
		application.getContext().set("periodsPart", this);
		
		list = new List(parent, SWT.SINGLE);
		GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
		data.widthHint = 350;
		list.setLayoutData(data);
		
		list.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
			} 
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {	
				application.getContext().set("period", getSelected());
				if (getSelected() >= 0) DataLoader.getRecords(application).loadRecords(DataLoader.getPeriod(application), shell);
				DataLoader.getUserInfo(application).refresh();
				DataLoader.getPeriods(application).refresh();
			}
		});
	}
	
	public boolean loadPeriods(MApplication application, Shell shell) {
		list.removeAll();
    	try {
	        Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
	    } catch (Exception ex) {
	        MessageDialog.openError(shell, "Unable to connect", "The application was unable to connect to the database. Please try again later.");
	        return false;
	    }
    	
	    try (Connection conn =
 	           DriverManager.getConnection(String.format("jdbc:mysql://%s/%s?user=%s&password=%s&serverTimezone=%s",
 	        		   DBData.host, DBData.database, DBData.user, DBData.password, DBData.serverTimeZone))) {
	        PreparedStatement ps = conn.prepareStatement("SELECT * FROM budget_db.period WHERE iduser=? ORDER BY period_name ASC");
	        ps.setInt(1, (int) application.getContext().get("user"));
	        ResultSet rs = ps.executeQuery();
	        
	        periodListMapper = new HashMap<Integer, Integer>();
	        int counter = 0;
	        while (rs.next()) {
	        	Integer id = rs.getInt("idperiod");
	        	String name = rs.getString("period_name");
	        	addItem(name);
	        	periodListMapper.put(counter, id);
	        	counter++;
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
		list.forceFocus();
	}
	
	public boolean canDelete() {
		if (list.getSelectionCount() == 1) {
			return true;
		}
		return false;
	}
	
	public int getSelected() {
		if (list.getSelectionCount() == 0) {
			return -1;
		}
		return periodListMapper.get(list.getSelectionIndex());
	}
	
	public void addItem(String s) {
		list.add(s);
		list.select(0);
	}	
	
	public String getSelectedString() {
		if (list.getSelectionCount() == 1) {
			return list.getSelection()[0];
		}
		return "";
	}
}
