package cz.cuni.mff.javaui.budgetapp.parts;

import javax.annotation.PostConstruct;

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
	public void createComposite(Composite parent) {
		parent.setLayout(new GridLayout(1, false));
		
		canvas = new Canvas(parent,SWT.NO_REDRAW_RESIZE);
		
		Color color = new Color(Display.getCurrent(), 100, 100, 100);
		
		canvas.setBackground(color);
		GridData gd = new GridData(SWT.FILL, SWT.FILL, true, true);
		canvas.setLayoutData(gd);
		System.out.println("ASSIGNED");
	}
}
