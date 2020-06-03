package com.mycompany.currency.dto;

import java.math.BigDecimal;

import com.mycompany.currency.model.Currency;
import com.mycompany.currency.service.error.ErrorCreateObjectException;

public class ExchangeRate {

	private BigDecimal rate;
	private Currency from;
	private Currency to;

	public ExchangeRate() {

	}

	public ExchangeRate(BigDecimal rate, Currency from, Currency to) {
		this.from = from;
		this.to = to;
		if (rate != null && rate.scale() <= 3) {
			this.rate = rate;
		}
	}

	public BigDecimal getRate() {
		return rate;
	}

	public void setRate(BigDecimal rate) {
		if (rate != null && rate.scale() <= 3) {
			this.rate = rate;
		} else {
			throw new ErrorCreateObjectException("Error");
		}

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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((from == null) ? 0 : from.hashCode());
		result = prime * result + ((rate == null) ? 0 : rate.hashCode());
		result = prime * result + ((to == null) ? 0 : to.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ExchangeRate other = (ExchangeRate) obj;
		if (from != other.from)
			return false;
		if (rate == null) {
			if (other.rate != null)
				return false;
		} else if (!rate.equals(other.rate))
			return false;
		if (to != other.to)
			return false;
		return true;
	}

}
