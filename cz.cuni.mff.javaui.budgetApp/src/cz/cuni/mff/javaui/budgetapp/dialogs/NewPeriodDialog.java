package cz.cuni.mff.javaui.budgetapp.dialogs;

import org.eclipse.jface.dialogs.IInputValidator;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.swt.widgets.Shell;

public class NewPeriodDialog extends InputDialog {

	private String result;
	
	public NewPeriodDialog(Shell parentShell, String dialogTitle, String dialogMessage, String initialValue,
			IInputValidator validator) {
		super(parentShell, dialogTitle, dialogMessage, initialValue, validator);
	}
	
	@Override
	protected void okPressed() {
		result = getText().getText();
		super.okPressed();
	}
	
	public String getName() {
		return result;
	}
}
