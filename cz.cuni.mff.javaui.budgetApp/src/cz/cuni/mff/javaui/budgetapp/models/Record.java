package cz.cuni.mff.javaui.budgetapp.models;

import java.util.GregorianCalendar;

public class Record {

	private int period;
	private int amount;
	private int balance;
	private GregorianCalendar date;
	
	public Record (int period, int amount, int balance, GregorianCalendar date) {
		this.period = period;
		this.amount = amount;
		this.balance = balance;
		this.date = date;
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
	
	public int getBalance( ) {
		return balance;
	}
	
	public void setBalance(int balance) {
		this.balance = balance;
	}
	
	public GregorianCalendar getDate() {
		return date;
	}
	
	public void setDate(GregorianCalendar date) {
		this.date = date;
	}
	
}
