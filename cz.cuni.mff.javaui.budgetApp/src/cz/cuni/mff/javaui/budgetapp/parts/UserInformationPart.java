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
import cz.cuni.mff.javaui.budgetapp.database.DBManipulator;
import cz.cuni.mff.javaui.budgetapp.misc.DataLoader;
import cz.cuni.mff.javaui.budgetapp.models.User;

/**
 * Represents a viewpart that displays the user information.
 * 
 * @author Andrej Jurco
 *
 */
public class UserInformationPart {

	private Label name;
	private Label balance;
	private Label created;
	
	private Composite container;

	private Label nameLabel;
	private Label balanceLabel;
	private Label createdLabel;
	
	@PostConstruct
	public void createComposite(Composite parent, MApplication application) {
		parent.setLayout(new GridLayout(2, false));
		container = parent;
		
		application.getContext().set("userInformationPart", this);
		
		nameLabel = new Label(parent, SWT.SHADOW_IN);
		nameLabel.setText("Name: ");
		
		name = new Label(parent, SWT.NONE);
		name.setText("");
		name.setVisible(true);
		
		balanceLabel = new Label(parent, SWT.VERTICAL);
		balanceLabel.setText("Balance: ");
		
		balance = new Label(parent, SWT.HORIZONTAL);
		balance.setText("");
		balance.setVisible(true);
		
		createdLabel = new Label(parent, SWT.SHADOW_NONE);
		createdLabel.setText("Date created: ");
		
		created = new Label(parent, SWT.CENTER);
		created.setText("");
		created.setVisible(true);

		GridData gd1 = new GridData();
		GridData gd2 = new GridData();
		GridData gd3 = new GridData();
		GridData gd4 = new GridData();
		GridData gd5 = new GridData();
		GridData gd6 = new GridData();
		
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

		nameLabel.setLayoutData(gd1);
		name.setLayoutData(gd2);
		balanceLabel.setLayoutData(gd3);
		balance.setLayoutData(gd4);
		createdLabel.setLayoutData(gd5);
		created.setLayoutData(gd6);
	}
	
	/**
	 * Updates the user information displayed in the part.
	 * 
	 * @param application
	 * @param shell
	 * @return
	 */
	public boolean updateUserInfo(MApplication application, Shell shell) {
		int balance = DBManipulator.updateBalance(shell, DataLoader.getUser(application));
		DBManipulator.setBalance(shell, DataLoader.getUser(application), balance);
		String toDisplay = balance + "";
		System.out.println(toDisplay);
		this.balance.setText(toDisplay); 
		container.layout();
		return true;
	}
	
	/**
	 * Loads user information and displays it.
	 * 
	 * @param userID
	 * @param shell
	 * @param application
	 * @return
	 */
	public boolean loadUser(int userID, Shell shell, MApplication application) {
		User u = DBManipulator.getUser(shell, userID);
		if (u != null) {
			name.setText(u.getName());
			balance.setText(u.getBalance() + "");
			created.setText(u.getCreated().toString());
			container.layout();
			return true;
		}
		application.getContext().remove("user");
		return false;
	}
	
	public void refresh() {
		name.forceFocus();
	}

}
