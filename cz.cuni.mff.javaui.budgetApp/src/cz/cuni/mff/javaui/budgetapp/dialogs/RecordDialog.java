package cz.cuni.mff.javaui.budgetapp.dialogs;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Text;

public class RecordDialog extends Dialog {

	private Label amountLabel;
	private Spinner amount;
	private Label dateLabel;
	private DateTime date;
	private Label noteLabel;
	private Text note;

	private int oldAmount;
	private Date oldDate;
	private String oldText;

	public RecordDialog(Shell shell, int amount, Date date, String text) {
		super(shell);
		this.oldAmount = amount;
		this.oldDate = date;
		this.oldText = text;
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		Composite container = (Composite) super.createDialogArea(parent);
		container.setLayout(new GridLayout(2, false));

		amountLabel = new Label(container, SWT.SINGLE);
		amount = new Spinner(container, SWT.NONE);
		dateLabel = new Label(container, SWT.SINGLE);
		date = new DateTime(container, SWT.NONE);
		noteLabel = new Label(container, SWT.SINGLE);
		note = new Text(container, SWT.BORDER);

		amountLabel.setText("Amount: ");
		dateLabel.setText("Date: ");
		noteLabel.setText("Note: ");
		note.setTextLimit(120);
		note.setToolTipText("Enter a note to this record. Maximum of 120 characters");

		amount.setMinimum(0);
		amount.setMaximum(Integer.MAX_VALUE);
		amount.setIncrement(10);
		amount.setPageIncrement(1000);
		amount.setSelection(oldAmount);
		LocalDate loc = oldDate.toLocalDate();
		date.setDate(loc.getYear(), loc.getMonthValue() - 1, loc.getDayOfMonth());
		note.setText(oldText);

		GridData amountLabel_gd = new GridData();
		GridData amount_gd = new GridData();
		GridData dateLabel_gd = new GridData();
		GridData date_gd = new GridData();
		GridData noteLabel_gd = new GridData();
		GridData note_gd = new GridData();

		amount_gd.grabExcessHorizontalSpace = true;
		date_gd.grabExcessHorizontalSpace = true;
		note_gd.heightHint = 75;
		note_gd.widthHint = 170;

		amountLabel.setLayoutData(amountLabel_gd);
		amount.setLayoutData(amount_gd);
		dateLabel.setLayoutData(dateLabel_gd);
		date.setLayoutData(date_gd);
		noteLabel.setLayoutData(noteLabel_gd);
		note.setLayoutData(note_gd);

		return container;
	}

	@Override
	protected void createButtonsForButtonBar(Composite parent) {

		createButton(parent, IDialogConstants.OK_ID, "OK", true);
		createButton(parent, IDialogConstants.CANCEL_ID, IDialogConstants.CANCEL_LABEL, false);
	}

	@Override
	protected void okPressed() {
		this.oldAmount = amount.getSelection();
		this.oldText = note.getText();
		try {
			String myDate = getStringDate();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			java.util.Date parsedDate = sdf.parse(myDate);
			long millis = parsedDate.getTime();
			this.oldDate = new Date(millis);
		} catch (ParseException pe) {
		}
		super.okPressed();
	}

	private String getStringDate() {
		StringBuilder sb = new StringBuilder();
		sb.append(date.getYear());
		sb.append("/");
		sb.append(date.getMonth() + 1);
		sb.append("/");
		sb.append(date.getDay() + 1);
		sb.append(" 00:00:00");
		return sb.toString();
	}

	@Override
	protected void configureShell(Shell shell) {
		super.configureShell(shell);
		shell.setText("Record configuration");
	}

	public String getNote() {
		return this.oldText;
	}

	public int getAmount() {
		return this.oldAmount;
	}

	public Date getDate() {
		return this.oldDate;
	}
}
