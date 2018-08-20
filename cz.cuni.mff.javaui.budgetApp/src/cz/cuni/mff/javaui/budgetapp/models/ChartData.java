package cz.cuni.mff.javaui.budgetapp.models;

import java.time.temporal.ChronoUnit;
import java.sql.Date;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class ChartData {

	private List<String> strings;
	private List<Double> values;
	private int gapCounter = 0;
	private int interval;
	private HashSet<Integer> intervals = new HashSet<Integer>();
	
	private LocalDate lastDate = null;
	
	public ChartData(Date from, Date to) {
		values = new ArrayList<Double>();
		strings = new ArrayList<String>();
		
		int daysDifference = (int) ChronoUnit.DAYS.between(from.toLocalDate(), to.toLocalDate());
		intervals.add(0);
		if (daysDifference > 0) {
			if (daysDifference > 5) {
				int interval = daysDifference / 5;
				for (int i=0; i < 5; i++) {
					intervals.add(i*interval);
				}
				intervals.add(daysDifference-1);
			} else {
				for (int i=1; i <= daysDifference; i++) {
					intervals.add(i);
				}
			}
		}

	}
	
	public void addData(LocalDate date, int amount) {
		LocalDate currentDate = date;
		int currentAmount = amount;

		if (lastDate != null) {
			lastDate = lastDate.plusDays(1);
			while (lastDate.compareTo(currentDate) != 0) {
				if (intervals.contains(gapCounter)) {
					if (gapCounter == 0) {
						strings.add(lastDate.getDayOfMonth() + "/" + lastDate.getMonth().toString().substring(0, 3) + "/" + lastDate.getYear());
					} else {
						strings.add(lastDate.getDayOfMonth() + "/" + lastDate.getMonth().toString().substring(0, 3));
					}
				} else {
					strings.add("");
				}
				values.add((double) 0);
				gapCounter++;
				lastDate = lastDate.plusDays(1);
			}
		}
		if (intervals.contains(gapCounter)) {
			if (gapCounter == 0) {
				strings.add(currentDate.getDayOfMonth() + "/" + currentDate.getMonth().toString().substring(0, 3) + "/" + currentDate.getYear());
			} else {
				strings.add(lastDate.getDayOfMonth() + "/" + lastDate.getMonth().toString().substring(0, 3));
			}
		} else {
			strings.add("");
		}
		values.add((double) currentAmount);
		gapCounter++;
		lastDate = currentDate;
	}
	
	public String[] getStrings() {
		String[] ret = new String[strings.size()];
		return strings.toArray(ret);
	}
	
	public double[] getValues() {
		double[] ret = new double[values.size()];
		for (int i=0; i<values.size(); i++) {
			ret[i] = values.get(i);
		}
		return ret;
	}
	
}
