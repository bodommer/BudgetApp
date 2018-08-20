package cz.cuni.mff.javaui.budgetapp.handlers.other;

import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.jface.dialogs.IInputValidator;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Shell;

import cz.cuni.mff.javaui.budgetapp.database.DBManipulator;
import cz.cuni.mff.javaui.budgetapp.dialogs.NewPeriodDialog;
import cz.cuni.mff.javaui.budgetapp.misc.DataLoader;
import cz.cuni.mff.javaui.budgetapp.parts.GraphPart;

public class ShowGraphHandler {
	@CanExecute
	public boolean canExecute(MApplication application) {
		return true;
	}	
	
	@Execute
	public void execute(Shell shell, MApplication application) {
		GraphPart part = DataLoader.getGraph(application);
		if (part == null) {
			System.out.println("null part");
			return;
		}
		part.loadData(application, shell);
		System.out.println("drawn");
	}
}
