package cz.cuni.mff.javaui.budgetapp.parts;

import java.sql.Date;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.e4.ui.workbench.modeling.EPartService.PartState;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import cz.cuni.mff.javaui.budgetapp.database.DBManipulator;
import cz.cuni.mff.javaui.budgetapp.dialogs.RecordDialog;
import cz.cuni.mff.javaui.budgetapp.misc.DataLoader;
import cz.cuni.mff.javaui.budgetapp.models.Record;

public class RecordInformationPart {

	private Table table;
	private TableColumn amountColumn;
	private TableColumn commentColumn;
	private TableColumn dateColumn;
	private List<Record> records;
	
	private boolean noteAsc = true;
	private boolean dateAsc = true;
	private boolean amountAsc = true;
	
	private PeriodsPart periodsPart;

	@PostConstruct
	public void createComposite(Composite parent, MApplication application, EPartService service) {
		records = null;
		periodsPart = DataLoader.getPeriods(application);
		
		parent.setLayout(new GridLayout(1, false));

		application.getContext().set("recordInformationPart", this);

		service.showPart("cz.cuni.mff.javaui.budgetapp.part.graph", PartState.CREATE);
		
		table = new Table(parent, SWT.BORDER | SWT.FULL_SELECTION | SWT.MULTI);
		GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
		table.setLayoutData(data);

		dateColumn = new TableColumn(table, SWT.CENTER | SWT.BORDER);
		dateColumn.setText("Date");
		dateColumn.setWidth(100);
		dateColumn.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (records == null) return;
				if (dateAsc) {
					Collections.sort(records, new Comparator<Record>(){
						  public int compare(Record r1, Record r2){
						    return r1.getDate().compareTo(r2.getDate());
						  }
						});
				} else {
					Collections.sort(records, new Comparator<Record>(){
						  public int compare(Record r1, Record r2){
						    return r2.getDate().compareTo(r1.getDate());
						  }
						});
				}
				dateAsc = !(dateAsc);
				addTableItems();
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		amountColumn = new TableColumn(table, SWT.CENTER);
		amountColumn.setText("Amount");
		amountColumn.setWidth(80);
		amountColumn.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (records == null) return;
				if (amountAsc) {
					Collections.sort(records, new Comparator<Record>(){
						  public int compare(Record r1, Record r2){
						    return r1.getAmount() - r2.getAmount();
						  }
						});
				} else {
					Collections.sort(records, new Comparator<Record>(){
						  public int compare(Record r1, Record r2){
						    return r2.getAmount() - r1.getAmount();
						  }
						});
				}
				amountAsc = !(amountAsc);
				addTableItems();
				update();
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
			}
		});

		commentColumn = new TableColumn(table, SWT.CENTER);
		commentColumn.setText("Comment");
		commentColumn.setWidth(320);
		commentColumn.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (records == null) return;
				if (noteAsc) {
					Collections.sort(records, new Comparator<Record>(){
						  public int compare(Record r1, Record r2){
						    return r1.getNote().compareTo(r2.getNote());
						  }
						});
				} else {
					Collections.sort(records, new Comparator<Record>(){
						  public int compare(Record r1, Record r2){
						    return r2.getNote().compareTo(r1.getNote());
						  }
						});
				}
				noteAsc = !(noteAsc);
				addTableItems();
				update();
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		table.addListener(SWT.MouseDown, new Listener() {
			
	        public void handleEvent(Event e) {
	    	    update();
	        }
	    });
		table.addListener(SWT.MouseDoubleClick, new Listener() {
			
			@Override
			public void handleEvent(Event event) {
				TableItem ti = getSelectedItem();
				RecordDialog rd = new RecordDialog(Display.getDefault().getActiveShell(), (int) ti.getData("amount"), (Date) ti.getData("date"), (String) ti.getData("note"));
				if (rd.open() == Window.OK) {
					DBManipulator.editRecord(Display.getDefault().getActiveShell(), rd.getAmount(), rd.getDate(), rd.getNote(), (int) ti.getData("idrecord"));
					DataLoader.getRecords(application).loadRecords(DataLoader.getPeriod(application), Display.getDefault().getActiveShell());
				}
				update();
			}
		});
		
		table.setLinesVisible(true);
		table.setHeaderVisible(true);
	}
	
	public boolean isSelectedOne() {
		if (table.getSelectionCount() == 1) {
			return true;
		}
		return false;
	}
	
	public boolean isSelected() {
		if (table.getSelectionCount() > 0) {
			return true;
		}
		return false;
	}
	
	public int[] getSelectedItems() {
		return table.getSelectionIndices();
	}
	
	public TableItem getItem(int index) {
		try {
			return table.getItem(index);
		} catch (IllegalArgumentException iae) {
			return null;
		}
	}
	
	public void refresh() {
		table.forceFocus();
	}
	
	public void update() { 
		periodsPart.refresh();
		refresh();
	}

	public void loadRecords(int periodID, Shell shell) {
		this.records = DBManipulator.getRecords(shell, periodID);
		addTableItems();
	}
	
	public TableItem getSelectedItem() {
		return table.getItem(table.getSelectionIndex());
	}

	private void addTableItems() {
		table.removeAll();
		for (Record r : this.records) {
			TableItem ti = new TableItem(table, SWT.BORDER);
			ti.setText(0, r.getDate().toString());
			ti.setText(1, "" + r.getAmount());
			ti.setText(2, r.getNote());
			ti.setData("idrecord", r.getID());
			ti.setData("idperiod", r.getPeriod());
			ti.setData("amount", r.getAmount());
			ti.setData("note", r.getNote());
			ti.setData("date", r.getDate());

		}
	}
}
