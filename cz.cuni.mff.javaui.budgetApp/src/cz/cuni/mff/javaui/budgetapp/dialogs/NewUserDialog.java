package cz.cuni.mff.javaui.budgetapp.dialogs;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import cz.cuni.mff.javaui.budgetapp.database.DBData;

/**
 * This class represents the dialog for creating a new user.
 * 
 * @author Andrej Jurco
 *
 */
public class NewUserDialog extends Dialog {

	private Text name;
	private String nameValue;
	private Button okButton;

	/**
	 * The constructor.
	 * 
	 * @param parentShell
	 */
	public NewUserDialog(Shell parentShell) {
		super(parentShell);
	}

	/**
	 * Creates custom dialog.
	 */
	@Override
	protected Control createDialogArea(Composite parent) {

		Composite container = (Composite) super.createDialogArea(parent);
		container.setLayout(new GridLayout(2, false));

		Label nameLabel = new Label(container, SWT.FILL);
		nameLabel.setText("User name: ");

		name = new Text(container, SWT.BORDER);
		name.setFocus();
		name.setTextLimit(45);
		name.setSize(80, 15);
		name.setToolTipText("Characters: A-Za-z_0-9 and the name must be unique");

		GridData nameLabelGridData = new GridData();

		GridData nameGridData = new GridData();
		nameGridData.horizontalAlignment = GridData.FILL;
		nameGridData.grabExcessHorizontalSpace = true;

		nameLabel.setLayoutData(nameLabelGridData);
		name.setLayoutData(nameGridData);

		// getNames();

		name.addModifyListener(new ModifyListener() {

			@Override
			public void modifyText(ModifyEvent e) {
				if (name.getText().matches("\\w+") && name.getText().length() <= 45) {
					okButton.setEnabled(true);
					return;
				}
				okButton.setEnabled(false);
			}
		});

		return container;
	}

	@Override
	protected void createButtonsForButtonBar(Composite parent) {

		createButton(parent, IDialogConstants.OK_ID, "Create", true);
		createButton(parent, IDialogConstants.CANCEL_ID, IDialogConstants.CANCEL_LABEL, false);

		okButton = getButton(IDialogConstants.OK_ID);
		okButton.setEnabled(false);
	}

	@Override
	protected void configureShell(Shell shell) {
		super.configureShell(shell);
		shell.setText("Create New User");
	}

	@Override
	protected void okPressed() {
		nameValue = name.getText();
		super.okPressed();
	}

	/**
	 * Text widget getter.
	 * 
	 * @return Text widget value.
	 */
	public String getName() {
		return nameValue;
	}
}
