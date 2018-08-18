package cz.cuni.mff.javaui.budgetapp.handlers;

import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.jface.dialogs.IInputValidator;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Shell;

import cz.cuni.mff.javaui.budgetapp.database.DBManipulator;
import cz.cuni.mff.javaui.budgetapp.dialogs.NewPeriodDialog;
import cz.cuni.mff.javaui.budgetapp.misc.DataLoader;

public class AddPeriodHandler {
	@CanExecute
	public boolean canExecute(MApplication application) {
		if (DataLoader.getUser(application) > 0) {
			return true;
		}
		return false;
	}	
	
	@Execute
	public void execute(Shell shell, MApplication application) {
		NewPeriodDialog npd = new NewPeriodDialog(shell, " Add Period", "Enter period name.", "", new IInputValidator() {
			
			@Override
			public String isValid(String newText) {
				if (newText.matches("\\w+") && newText.length() <= 32) {
					return null;
				}
				return "Name must have 1-32 characters and the allowed symbols are letters, digits and underscore.";
			}
		});
		if (npd.open() == Window.OK) {
			String name = npd.getName();
			DBManipulator.addPeriod(name, DataLoader.getUser(application), shell);
			DataLoader.getPeriods(application).loadPeriods(application, shell);
			DataLoader.getUserInfo(application).refresh();
			DataLoader.getPeriods(application).refresh();
		}
	}
}
