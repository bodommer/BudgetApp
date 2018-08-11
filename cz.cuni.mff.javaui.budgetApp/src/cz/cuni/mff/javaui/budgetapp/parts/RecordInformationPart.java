package cz.cuni.mff.javaui.budgetapp.parts;

import javax.annotation.PostConstruct;

import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.List;

public class RecordInformationPart {
	private List list;
	
	@PostConstruct
	public void createComposite(Composite parent, MApplication application) {
		parent.setLayout(new GridLayout(1, false));
		
		application.getContext().set("recordInformationPart", this);
		
		list = new List(parent, SWT.SINGLE);
		GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
		list.setLayoutData(data);
	}
}
