package cz.cuni.mff.javaui.budgetapp.models;

import java.util.GregorianCalendar;

/**
 * Represents one period. Deprecated.
 * 
 * @author Andrej Jurco
 *
 */
public class Period {
	
	private int id;
	private GregorianCalendar from;
	private GregorianCalendar to;
	
	public Period(int id, GregorianCalendar from, GregorianCalendar to) {
		this.id = id;
		this.from = from;
		this.to = to;
	}
	
	public int getID() {
		return id;
	}
	
	public GregorianCalendar getFrom() {
		return from;
	}
	
	public void setFrom(GregorianCalendar from) {
		this.from = from;
	}
	
	public GregorianCalendar getTo() {
		return to;
	}
	
	public void setTo(GregorianCalendar to) {
		this.to = to;
	}
}
