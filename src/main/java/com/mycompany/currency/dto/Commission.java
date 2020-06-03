package com.mycompany.currency.dto;

import java.math.BigDecimal;

import com.mycompany.currency.model.Currency;

public class Commission {
	private static final int COMMISSION_PRECISION = 2;
	private BigDecimal commissionPt;
	private Currency from; 
	private Currency to;
	
	
	public Commission() {
		
	}
	public Commission(BigDecimal commissionPt, Currency from, Currency to) {
		
		this.commissionPt = commissionPt.setScale(COMMISSION_PRECISION);
		this.from = from;
		this.to = to;
	}
	public Commission(String commissionPt, Currency from, Currency to) {
		this(new BigDecimal(commissionPt), from, to);
	}
	public BigDecimal getCommissionPt() {
		return commissionPt;
	}
	public void setCommissionPt(BigDecimal commissionPt) {
		this.commissionPt = commissionPt.setScale(COMMISSION_PRECISION);
		
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
		result = prime * result + ((commissionPt == null) ? 0 : commissionPt.hashCode());
		result = prime * result + ((from == null) ? 0 : from.hashCode());
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
		Commission other = (Commission) obj;
		if (commissionPt == null) {
			if (other.commissionPt != null)
				return false;
		} else if (!commissionPt.equals(other.commissionPt))
			return false;
		if (from != other.from)
			return false;
		if (to != other.to)
			return false;
		return true;
	}

	
}
