package cz.cuni.mff.javaui.budgetapp.handlers;

import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.ui.model.application.MApplication;

import cz.cuni.mff.javaui.budgetapp.misc.DataLoader;

public class EditPeriodHandler {
	@CanExecute
	public boolean canExecute(MApplication application) {
		if (DataLoader.getUser(application) > 0 & DataLoader.getPeriods(application).canDelete()) {
			return true;
		}
		return false;
	}
}
