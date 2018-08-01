package cz.cuni.mff.javaui.budgetapp.parts;

import javax.annotation.PostConstruct;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;

public class UserInformationPart {

	private Label name;
	private Label balance;
	private Label created;
	private Label lastChange;

	private Label nameLabel;
	private Label balanceLabel;
	private Label createdLabel;
	private Label lastChangeLabel;
	
	@PostConstruct
	public void createComposite(Composite parent) {
		parent.setLayout(new GridLayout(2, false));
		
		nameLabel = new Label(parent, SWT.SHADOW_IN);
		nameLabel.setText("Name: ");
		
		name = new Label(parent, SWT.SEPARATOR);
		name.setText("John Cena");
		
		balanceLabel = new Label(parent, SWT.VERTICAL);
		balanceLabel.setText("Balance: ");
		
		balance = new Label(parent, SWT.HORIZONTAL);
	    balance.setText("12345");
		
		createdLabel = new Label(parent, SWT.SHADOW_NONE);
		createdLabel.setText("Date create: ");
		
		created = new Label(parent, SWT.CENTER);
		created.setText("03/04/1997");
		
		lastChangeLabel = new Label(parent, SWT.WRAP);
		lastChangeLabel.setText("Last change:");
		
		lastChange = new Label(parent, SWT.RIGHT);
		lastChange.setText("01/08/2018");
		
	}
}
