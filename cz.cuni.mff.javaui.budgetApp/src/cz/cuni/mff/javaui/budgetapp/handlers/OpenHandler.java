package cz.cuni.mff.javaui.budgetapp.handlers;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;

import cz.cuni.mff.javaui.budgetapp.dialogs.OpenUserDialog;
public class OpenHandler {

	@Execute
	public void execute(Shell shell){
		OpenUserDialog oud = new OpenUserDialog(shell);
	    if (oud.open() == Window.OK) {
	    	
	    }
	}
}
