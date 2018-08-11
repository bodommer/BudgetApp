package cz.cuni.mff.javaui.budgetapp.handlers;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Shell;

import cz.cuni.mff.javaui.budgetapp.dialogs.OpenUserDialog;
import cz.cuni.mff.javaui.budgetapp.misc.DataLoader;

public class OpenUserHandler {
	@Execute
	public void execute(Shell shell, MApplication application, EPartService service){
		OpenUserDialog oud = new OpenUserDialog(shell);
	    if (oud.open() == Window.OK) {
	    	application.getContext().set("user", oud.getChoice());
	    	if (!(
	    	DataLoader.getPeriods(application).loadPeriods(application, shell) |
	    	DataLoader.getUserInfo(application).updateUserInfo(application, shell))) {
		    	application.getContext().remove("user");
		    	System.out.println("Deleting user");
	    	}
	    }
		oud = null;
		DataLoader.getUserInfo(application).refresh();
		DataLoader.getPeriods(application).refresh();
	}
}
