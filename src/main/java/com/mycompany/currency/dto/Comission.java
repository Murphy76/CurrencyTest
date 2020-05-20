package com.mycompany.currency.dto;

import com.mycompany.currency.model.Currency;

public class Comission {
	private double commissionPt;
	private Currency from; 
	private Currency to;
	
	public Comission(double commissionPt, Currency from, Currency to) {
		this.commissionPt = commissionPt;
		this.from = from;
		this.to = to;
	}
	public double getCommissionPt() {
		return commissionPt;
	}
	public void setCommissionPt(double commissionPt) {
		this.commissionPt = commissionPt;
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
