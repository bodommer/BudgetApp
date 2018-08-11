package cz.cuni.mff.javaui.budgetapp.models;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class User {

	private String name;
	private int balance;
	private GregorianCalendar lastEdit;
	private GregorianCalendar created;
	
	public User(String name, GregorianCalendar created) {
		this.name = name;
		this.created = created;
		balance = 0;
		lastEdit = new GregorianCalendar(1980, 0, 1);
	}
	
	public String getName() {
		return name;
	}
	
	public int getBalance() {
		return balance;
	}
	
	public void setBalance(int balance) {
		this.balance = balance;
	}
	
	public GregorianCalendar getLastEdit() {
		return lastEdit;
	}
	
	public void setLastedit(GregorianCalendar date) {
		lastEdit = date;
	}
	
	public GregorianCalendar getCreated() {
		return created;
	}
}
