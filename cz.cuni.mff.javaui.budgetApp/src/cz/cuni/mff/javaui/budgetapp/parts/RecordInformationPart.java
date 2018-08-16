package cz.cuni.mff.javaui.budgetapp.parts;

import java.util.Comparator;
import java.util.List;

import javax.annotation.PostConstruct;

import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import cz.cuni.mff.javaui.budgetapp.database.DBManipulator;
import cz.cuni.mff.javaui.budgetapp.models.Record;

public class RecordInformationPart {

	private Table table;
	private TableColumn amountColumn;
	private TableColumn commentColumn;
	private TableColumn dateColumn;

	@PostConstruct
	public void createComposite(Composite parent, MApplication application) {
		parent.setLayout(new GridLayout(1, false));

		application.getContext().set("recordInformationPart", this);

		table = new Table(parent, SWT.BORDER | SWT.VIRTUAL);
		GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
		table.setLayoutData(data);
		
		dateColumn = new TableColumn(table, SWT.CENTER | SWT.BORDER);
		dateColumn.setText("Date");
		dateColumn.setWidth(120);
		amountColumn = new TableColumn(table, SWT.CENTER);
		amountColumn.setText("Amount");
		amountColumn.setWidth(100);

		commentColumn = new TableColumn(table, SWT.CENTER);
		commentColumn.setText("Comment");
		commentColumn.setWidth(320);
		table.setLinesVisible(true);
		table.setHeaderVisible(true);
		amountColumn.setWidth(80);
		/*amountColumn.setData(new Comparator<TableItem>() {
			@Override
			public int compare(TableItem t1, TableItem t2) {
				int i1 = Integer.parseInt(t1.getText(0));
				int i2 = Integer.parseInt(t2.getText(0));
				if (i1 < i2)
					return -1;
				if (i1 > i2)
					return 1;
				return 0;
			}
		});

		/*dateColumn.setWidth(100);
		dateColumn.setData(new Comparator<TableItem>() {
			@Override
			public int compare(TableItem t1, TableItem t2) {
				Date d1 = (Date) t1.getData("date");
				Date d2 = (Date) t2.getData("date");
				return d1.compareTo(d2);
			}
		});*/


	}

	public void loadRecords(int periodID, Shell shell) {
		List<Record> records = DBManipulator.getRecords(shell, periodID);
		for (Record r : records) {
			TableItem ti = new TableItem(table, SWT.BORDER);
			ti.setText(0, r.getDate().toString());
			ti.setText(1, "" + r.getAmount());
			ti.setText(2, r.getNote());
			System.out.println(ti.getText(0) + " " + ti.getText(1) + " " + ti.getText(2));
/*			ti.setData("idrecord", r.getID());
			ti.setData("idperiod", r.getPeriod());
			ti.setData("amount", r.getAmount());
			ti.setData("note", r.getNote());
			ti.setData("date", r.getDate());*/
		}
	}
}
