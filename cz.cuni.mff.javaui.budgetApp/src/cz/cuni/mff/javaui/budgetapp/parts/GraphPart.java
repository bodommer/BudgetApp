package cz.cuni.mff.javaui.budgetapp.parts;

import javax.annotation.PostConstruct;

import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.graphics.Color;

public class GraphPart {
	
	private Canvas canvas;
	
	@PostConstruct
	public void createComposite(Composite parent, MApplication application) {
		parent.setLayout(new GridLayout(1, false));
		
		application.getContext().set("graphPart", this);
		
		canvas = new Canvas(parent,SWT.NO_REDRAW_RESIZE);
		
		Color color = new Color(Display.getCurrent(), 5,5,5);
		
		canvas.setBackground(color);
		GridData gd = new GridData(SWT.FILL, SWT.FILL, true, true);
		canvas.setLayoutData(gd);
	}
}
