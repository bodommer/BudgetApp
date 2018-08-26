package cz.cuni.mff.javaui.budgetapp.handlers.record;

import java.sql.Date;

import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Shell;

import cz.cuni.mff.javaui.budgetapp.database.DBManipulator;
import cz.cuni.mff.javaui.budgetapp.dialogs.RecordDialog;
import cz.cuni.mff.javaui.budgetapp.misc.DataLoader;

/**
 * Handler for the event of adding a record.
 * 
 * @author Andrej Jurco
 *
 */
public class AddRecordHandler {

	@CanExecute
	public boolean canExecute(MApplication application) {
		if (DataLoader.getPeriod(application) > 0)
			return true;
		return false;
	}

	@Execute
	public void execute(Shell shell, MApplication application) {
		RecordDialog rd = new RecordDialog(shell, 0, new Date(System.currentTimeMillis()), "");
		if (rd.open() == Window.OK) {
			DBManipulator.addRecord(shell, rd.getAmount(), rd.getDate(), rd.getNote(),
					DataLoader.getPeriod(application));
			DataLoader.getRecords(application).loadRecords(DataLoader.getPeriod(application), shell);
			DataLoader.getUserInfo(application).updateUserInfo(application, shell);
		}
	}
}
