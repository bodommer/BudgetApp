package cz.cuni.mff.javaui.budgetapp.handlers.record;

import java.sql.Date;

import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TableItem;

import cz.cuni.mff.javaui.budgetapp.database.DBManipulator;
import cz.cuni.mff.javaui.budgetapp.dialogs.RecordDialog;
import cz.cuni.mff.javaui.budgetapp.misc.DataLoader;

public class EditRecordHandler {
	@CanExecute
	public boolean canExecute(MApplication application) {
		if (DataLoader.getPeriod(application) > 0 & DataLoader.getRecords(application).isSelectedOne()) return true;
		return false;
	}
	
	@Execute
	public void execute(Shell shell, MApplication application) {
		TableItem ti = DataLoader.getRecords(application).getSelectedItem();
		RecordDialog rd = new RecordDialog(shell, (int) ti.getData("amount"), (Date) ti.getData("date"), (String) ti.getData("note"));
		if (rd.open() == Window.OK) {
			DBManipulator.editRecord(shell, rd.getAmount(), rd.getDate(), rd.getNote(), (int) ti.getData("idrecord"));
			DataLoader.getRecords(application).loadRecords(DataLoader.getPeriod(application), shell);
			DataLoader.getUserInfo(application).updateUserInfo(application, shell);
		}
		DataLoader.getRecords(application).update();
	}
}
