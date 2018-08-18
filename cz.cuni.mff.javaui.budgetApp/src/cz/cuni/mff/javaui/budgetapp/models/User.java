package cz.cuni.mff.javaui.budgetapp.models;

import java.util.Calendar;
import java.sql.Date;
import java.util.GregorianCalendar;

public class User {

	private String name;
	private int balance;
	private Date lastEdit;
	private Date created;
	
	public User(String name, Date created) {
		this.name = name;
		this.created = created;
		balance = 0;
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
	
	public Date getLastEdit() {
		return lastEdit;
	}
	
	public void setLastedit(Date date) {
		lastEdit = date;
	}
	
	public Date getCreated() {
		return created;
	}
}
