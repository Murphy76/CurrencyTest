package com.mycompany.currency.dto;

import com.mycompany.currency.model.Currency;

public class ExchangeRequest {
	private double amountFrom =0;
	private double amountTo=0;
	private Currency currencyFrom;
	private Currency currencyTo;
	private String operationType;
	
	
	public double getAmountFrom() {
		return amountFrom;
	}
	public void setAmountFrom(double amountFrom) {
		this.amountFrom = amountFrom;
	}
	public double getAmountTo() {
		return amountTo;
	}
	public void setAmountTo(double amountTo) {
		this.amountTo = amountTo;
	}
	public Currency getCurrencyFrom() {
		return currencyFrom;
	}
	public void setCurrencyFrom(Currency currencyFrom) {
		this.currencyFrom = currencyFrom;
	}
	public Currency getCurrencyTo() {
		return currencyTo;
	}
	public void setCurrencyTo(Currency currencyTo) {
		this.currencyTo = currencyTo;
	}
	public String getOperationType() {
		return operationType;
	}
	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}
	

}
