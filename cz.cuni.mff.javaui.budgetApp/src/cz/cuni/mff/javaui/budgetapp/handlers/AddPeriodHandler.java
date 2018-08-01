package cz.cuni.mff.javaui.budgetapp.handlers;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;

public class AddPeriodHandler {
	@Execute
	public void execute(Shell shell) {
		MessageDialog.openInformation(shell, "WOOO", "AA");
	}
}
