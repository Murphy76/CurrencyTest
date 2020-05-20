package com.mycompany.currency.dto;

import com.mycompany.currency.model.Currency;

public class ExchangeRate {

	private double rate;
	private Currency from; 
	private Currency to;
	
	
	public ExchangeRate(double rate, Currency from, Currency to) {
		this.rate = rate;
		this.from = from;
		this.to = to;
	}
	public double getRate() {
		return rate;
	}
	public void setRate(double rate) {
		this.rate = rate;
	}
	public Currency getFrom() {
		return from;
	}
	public void setFrom(Currency from) {
		this.from = from;
	}
	public Currency getTo() {
		return to;
	}
	public void setTo(Currency to) {
		this.to = to;
	}
	
	
}
