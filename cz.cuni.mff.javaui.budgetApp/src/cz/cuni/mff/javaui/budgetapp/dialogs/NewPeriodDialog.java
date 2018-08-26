package cz.cuni.mff.javaui.budgetapp.dialogs;

import org.eclipse.jface.dialogs.IInputValidator;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.swt.widgets.Shell;

/**
 * This class represents The dialog displayed when a new period is added and
 * prompts user to input relevant data. This dialog is also recycled for editing
 * a period.
 * 
 * @author Andrej Jurco
 *
 */
public class NewPeriodDialog extends InputDialog {

	private String result;

	/**
	 * The constructor.
	 * 
	 * @param parentShell
	 * @param dialogTitle   Either 'new period' or 'edit period' - displayed in the
	 *                      top bar of the dialog
	 * @param dialogMessage Prompt for user to either add new name or edit name.
	 * @param initialValue  If the dialog is set to be edit dialog, this parameter
	 *                      sets the old value to the text widget.
	 * @param validator     A regex to make sure it only contains valid characters.
	 */
	public NewPeriodDialog(Shell parentShell, String dialogTitle, String dialogMessage, String initialValue,
			IInputValidator validator) {
		super(parentShell, dialogTitle, dialogMessage, initialValue, validator);
	}

	@Override
	protected void okPressed() {
		result = getText().getText();
		super.okPressed();
	}

	/**
	 * Gets the text widget value.
	 * 
	 * @return Text widget value.
	 */
	public String getName() {
		return result;
	}

	/**
	 * Sets widget value to the one provided in the argument.
	 * 
	 * @param s value to be assigned to the text widget
	 */
	public void setString(String s) {
		getText().setText(s);
	}
}
