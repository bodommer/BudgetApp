package cz.cuni.mff.javaui.budgetapp.handlers.other;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.workbench.IWorkbench;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;

/**
 * A quit handler asks user to confirm exiting the application.
 * 
 * @author Andrej Jurco
 *
 */
public class QuitHandler {
	@Execute
	public void execute(IWorkbench workbench, Shell shell) {
		if (MessageDialog.openConfirm(shell, "Confirmation", "Do you want to exit?")) {
			workbench.close();
		}
	}
}
