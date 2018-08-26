package cz.cuni.mff.javaui.budgetapp.models;

import java.sql.Date;

/**
 * Represents one record from the database. Contains all the relevant data that
 * can be accessed easily.
 * 
 * @author Andrej Jurco
 *
 */
public class Record {

	private int period;
	private int amount;
	private String note;
	private Date date;
	private int id;

	public Record(int id, int period, int amount, String note, Date date) {
		this.id = id;
		this.period = period;
		this.amount = amount;
		this.note = note;
		this.date = date;
	}

	public int getID() {
		return id;
	}

	public int getPeriod() {
		return period;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

}
