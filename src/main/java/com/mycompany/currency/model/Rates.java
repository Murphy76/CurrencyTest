package com.mycompany.currency.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Rates")
public class Rates {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private long id;
	
	private Currency currFrom;
	
	private Currency currTo;

	private double rate=0;

	private double comissions=0;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Currency getCurrFrom() {
		return currFrom;
	}

	public void setCurrFrom(Currency currFrom) {
		this.currFrom = currFrom;
	}

	public Currency getCurrTo() {
		return currTo;
	}

	public void setCurrTo(Currency currTo) {
		this.currTo = currTo;
	}

	public double getRate() {
		return rate;
	}

	public void setRate(double rate) {
		this.rate = rate;
	}

	public double getComissions() {
		return comissions;
	}

	public void setComissions(double comissions) {
		this.comissions = comissions;
	}



}
