package cz.cuni.mff.javaui.budgetapp.handlers;

import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TableItem;

import cz.cuni.mff.javaui.budgetapp.database.DBManipulator;
import cz.cuni.mff.javaui.budgetapp.misc.DataLoader;
import cz.cuni.mff.javaui.budgetapp.parts.RecordInformationPart;

public class DeleteRecordHandler {
	@CanExecute
	public boolean canExecute(MApplication application) {
		if (DataLoader.getPeriod(application) > 0 & DataLoader.getRecords(application).isSelected())
			return true;
		return false;
	}

	@Execute
	public void execute(Shell shell, MApplication application) {
		RecordInformationPart records = DataLoader.getRecords(application);
		int[] items = records.getSelectedItems();
		for (int i = 0; i < items.length; i++) {
			TableItem ti = records.getItem(items[i]);
			if (ti != null) {
				DBManipulator.deleteRecord(shell, (int) ti.getData("idrecord"));
				records.loadRecords(DataLoader.getPeriod(application), shell);
				DataLoader.getPeriods(application).refresh();
				records.refresh();
			}
		}
	}
}
