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
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

import cz.cuni.mff.javaui.budgetapp.database.DBData;
import cz.cuni.mff.javaui.budgetapp.database.DBManipulator;

/**
 * Displays the dialog which prompts the user to choose a created user form the list of user that is to be 'opened' - loaded.
 * 
 * @author Andrej Jurco
 *
 */
public class OpenUserDialog extends Dialog {
    
	private org.eclipse.swt.widgets.List list;
	private Map<Integer, String> names;
	private int choice;
	private Button okButton;
	private Map<Integer, Integer> listMapper;
	
	/**
	 * The contrusctor.
	 * 
	 * @param parentShell
	 */
	public OpenUserDialog(Shell parentShell) {
        super(parentShell);
    }

	/**
	 * Creates the dialog area and populates it with relevant widgets.
	 */
    @Override
    protected Control createDialogArea(Composite parent) {
        Composite container = (Composite) super.createDialogArea(parent);
        container.setLayout(new GridLayout(1, false));
        
        Label label = new Label(container, SWT.NONE);
        label.setText("Choose the user you want to load:");
        GridData labelGD = new GridData(SWT.LEFT, SWT.NONE, true, false);
        labelGD.heightHint = 20;
        label.setLayoutData(labelGD);
        
        list = new org.eclipse.swt.widgets.List(container, SWT.SINGLE);
		GridData listGD = new GridData(SWT.FILL, SWT.FILL, true, true);
		listGD.widthHint = 205;
		list.setLayoutData(listGD);
        list.setEnabled(true);      
        
        names = DBManipulator.getUsers(getParentShell());
        
        listMapper = new HashMap<Integer, Integer>();
        
        int counter = 0;
        
        list.removeAll();
        if (names != null) {
	        for (int i : names.keySet()) {
	        	list.add(names.get(i));
	        	listMapper.put(counter, i);
	        	counter++;
	        }
	        list.select(0);
        }
        
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
    	// gets the selection index and maps it to the userID.
    	this.choice = listMapper.get(list.getSelectionIndex());
    	super.okPressed();
    }
    
    @Override
    protected void configureShell(Shell shell) {
        super.configureShell(shell);
        shell.setText(" Load User Data");
        shell.setSize(225, 300);
     }

    /**
     * Returns the list selection value.
     * 
     * @return
     */
    public int getChoice() {
    	return this.choice;
    }
}
