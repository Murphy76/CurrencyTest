package com.mycompany.currency.dto;

import java.math.BigDecimal;

import com.mycompany.currency.model.Currency;
import com.mycompany.currency.service.error.ErrorCreateObjectException;

public class ExchangeRequest {
	private BigDecimal amountFrom;
	private BigDecimal amountTo;
	private Currency currencyFrom;
	private Currency currencyTo;
	private String operationType;

	public BigDecimal getAmountFrom() {
		return amountFrom;
	}

	public void setAmountFrom(BigDecimal amountFrom) {
		if (amountFrom.scale() <= 2) {
			this.amountFrom = amountFrom;
		} else {
			throw new ErrorCreateObjectException("Error");
		}
	}

	public BigDecimal getAmountTo() {
		return amountTo;
	}

	public void setAmountTo(BigDecimal amountTo) {
		if (amountTo.scale() <= 2) {
			this.amountTo = amountTo;
		} else {
			throw new ErrorCreateObjectException("Error");
		}
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
