package cz.cuni.mff.javaui.budgetapp.parts;

import javax.annotation.PostConstruct;

import org.swtchart.Chart;
import org.swtchart.IAxis;
import org.swtchart.IAxisSet;
import org.swtchart.ISeries;
import org.swtchart.ISeries.SeriesType;
import org.swtchart.ISeriesSet;

import cz.cuni.mff.javaui.budgetapp.database.DBManipulator;
import cz.cuni.mff.javaui.budgetapp.misc.DataLoader;
import cz.cuni.mff.javaui.budgetapp.models.ChartData;

import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class GraphPart {
	
	private Chart chart;
	
	@PostConstruct
	public void createComposite(Composite parent, MApplication application, Shell shell) {
		FillLayout fl = new FillLayout();
		fl.marginWidth = 20;
		parent.setLayout(fl);
		
		application.getContext().set("graphPart", this);
		
		chart = new Chart(parent, SWT.NONE);
		
		IAxis xAxis = chart.getAxisSet().getXAxis(0); 
		xAxis.enableCategory(true);
		xAxis.getTitle().setText("Date");
		xAxis.getTitle().setForeground(new Color(Display.getDefault(), 0, 0, 0));
		xAxis.getTick().setForeground(new Color(Display.getDefault(), 0, 0, 0));
		
		IAxis yAxis = chart.getAxisSet().getYAxis(0);
		yAxis.getTitle().setText("Change");
		yAxis.getTitle().setForeground(new Color(Display.getDefault(), 0, 0, 0));
		yAxis.getTick().setForeground(new Color(Display.getDefault(), 0, 0, 0));
		
		chart.getTitle().setText("Money change over this period");
		chart.getTitle().setForeground(new Color(Display.getDefault(), 0, 0, 0));
		chart.getLegend().setVisible(false);
		
		IAxisSet axisSet = chart.getAxisSet();
		axisSet.adjustRange();
		
		
	}
	
	public void loadData(MApplication application, Shell shell) {
		
		ChartData data = DBManipulator.getChartData(application, shell);
		if (data == null) {
			System.out.println("null");
			return;
		}
		ISeriesSet seriesSet = chart.getSeriesSet();
		ISeries series = seriesSet.createSeries(SeriesType.LINE, "line series");
		series.setYSeries(data.getValues());
		
		IAxisSet axisSet = chart.getAxisSet();
		
		IAxis xAxis = axisSet.getXAxis(0);
		xAxis.setCategorySeries(data.getStrings());
		xAxis.enableCategory(true);

		axisSet.adjustRange();
	}
}
