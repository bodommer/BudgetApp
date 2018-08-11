package cz.cuni.mff.javaui.budgetapp.handlers;

import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Shell;

import cz.cuni.mff.javaui.budgetapp.database.DBManipulator;
import cz.cuni.mff.javaui.budgetapp.misc.DataLoader;

public class DeletePeriodHandler {
	@CanExecute
	public boolean canExecute(MApplication application) {
		if (DataLoader.getUser(application) > 0 & DataLoader.getPeriods(application).canDelete()) {
			return true;
		}
		return false;
	}
	
	@Execute
	public void execute(MApplication application, Shell shell) {
		if (MessageDialog.openQuestion(shell, "Delete Period", "Deleting this period will delete all records belonging to it. Are you sure you want to delete it?")) {
			if (DBManipulator.deletePeriod(DataLoader.getPeriods(application).getSelected(), shell)) {
				DataLoader.getPeriods(application).loadPeriods(application, shell);
			}
		}
	}
}
