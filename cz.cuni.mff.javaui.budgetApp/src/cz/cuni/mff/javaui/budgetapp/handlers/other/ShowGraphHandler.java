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

/**
 * Responsible for displaying graph.
 * 
 * @author Andrej Jurco
 *
 */
public class ShowGraphHandler {
	@CanExecute
	public boolean canExecute(MApplication application) {
		if (DataLoader.getPeriod(application) > 0) {
			return true;
		}
		return false;
	}

	@Execute
	public void execute(Shell shell, MApplication application) {
		GraphPart part = DataLoader.getGraph(application);
		if (part == null) {
			return;
		}
		part.loadData(application, shell);
	}
}
