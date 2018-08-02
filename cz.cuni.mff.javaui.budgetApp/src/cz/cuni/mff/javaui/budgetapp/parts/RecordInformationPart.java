package cz.cuni.mff.javaui.budgetapp.parts;

import javax.annotation.PostConstruct;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.List;

public class RecordInformationPart {
	private List list;
	
	@PostConstruct
	public void createComposite(Composite parent) {
		parent.setLayout(new GridLayout(1, false));
		
		
		
		list = new List(parent, SWT.SINGLE);
		GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
		list.setLayoutData(data);
	}
}
