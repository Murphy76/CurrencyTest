package com.mycompany.currency.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.mycompany.currency.service.error.ErrorCreateObjectException;

@Entity
@Table(name = "Rates")
public class Rates {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private long id;

	private Currency currFrom;

	private Currency currTo;

	@Column(scale = 3)
	private BigDecimal rate;

	private BigDecimal multiplicity = BigDecimal.ONE;

	@Column(scale = 2)
	private BigDecimal commissions = BigDecimal.ZERO;

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

	public BigDecimal getRate() {
		return rate;
	}

	public void setRate(BigDecimal rate) {
		if (rate != null && rate.scale()<=3) {
			this.rate = rate;
		}else {
			throw new ErrorCreateObjectException("Error");
		}
	}

	public BigDecimal getCommissions() {
		return commissions;
	}

	public void setCommissions(BigDecimal commissions) {
		this.commissions = commissions;

	}

	public BigDecimal getMultiplicity() {
		return multiplicity;
	}

	public void setMultiplicity(BigDecimal multiplicity) {
		this.multiplicity = multiplicity;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((commissions == null) ? 0 : commissions.hashCode());
		result = prime * result + ((currFrom == null) ? 0 : currFrom.hashCode());
		result = prime * result + ((currTo == null) ? 0 : currTo.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((rate == null) ? 0 : rate.hashCode());
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
		Rates other = (Rates) obj;
		if (commissions == null) {
			if (other.commissions != null)
				return false;
		} else if (!commissions.equals(other.commissions))
			return false;
		if (currFrom != other.currFrom)
			return false;
		if (currTo != other.currTo)
			return false;
		if (id != other.id)
			return false;
		if (rate == null) {
			if (other.rate != null)
				return false;
		} else if (!rate.equals(other.rate))
			return false;
		return true;
	}

}
