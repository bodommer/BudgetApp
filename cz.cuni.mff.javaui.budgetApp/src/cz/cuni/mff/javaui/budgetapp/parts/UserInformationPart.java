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
		
		name = new Label(parent, SWT.NONE);
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
		
		GridData gd1 = new GridData();
		GridData gd2 = new GridData();
		GridData gd3 = new GridData();
		GridData gd4 = new GridData();
		GridData gd5 = new GridData();
		GridData gd6 = new GridData();
		GridData gd7 = new GridData();
		GridData gd8 = new GridData();
		
		gd1.horizontalIndent = 10;
		gd1.verticalIndent = 10;
		gd2.horizontalIndent = 10;
		gd2.verticalIndent = 10;
		gd3.horizontalIndent = 10;
		gd3.verticalIndent = 10;
		gd4.horizontalIndent = 10;
		gd4.verticalIndent = 10;
		gd5.horizontalIndent = 10;
		gd5.verticalIndent = 10;
		gd6.horizontalIndent = 10;
		gd6.verticalIndent = 10;
		gd7.horizontalIndent = 10;
		gd7.verticalIndent = 10;
		gd8.horizontalIndent = 10;
		gd8.verticalIndent = 10;
		
		
		nameLabel.setLayoutData(gd1);
		name.setLayoutData(gd2);
		balanceLabel.setLayoutData(gd3);
		balance.setLayoutData(gd4);
		createdLabel.setLayoutData(gd5);
		created.setLayoutData(gd6);
		lastChangeLabel.setLayoutData(gd7);
		lastChange.setLayoutData(gd8);
		
		
		
	}
}
