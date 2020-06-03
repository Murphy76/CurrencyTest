package com.mycompany.currency.service;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mycompany.currency.model.Currency;
import com.mycompany.currency.model.Rates;
import com.mycompany.currency.service.error.ErrorExchangeException;

@Service
public class ExchangeService {

	Logger logger = LoggerFactory.getLogger(ExchangeService.class);

	private static final int PRESISION_EXCHANGE_RESULT = 2;
	@Autowired
	RateService rateService;

	
	public BigDecimal exchangeDirect(BigDecimal amountFrom, Currency currencyFrom, Currency currencyTo) {
		Rates rate = rateService.findByCurrFromAndCurrTo(currencyFrom, currencyTo);
		BigDecimal res;
		if (rate != null) {
			BigDecimal commission = BigDecimal.ZERO;
			if (rate.getCommissions().doubleValue() > 0) {
				commission = amountFrom.multiply(rate.getCommissions().movePointLeft(2));
			}
			res = (amountFrom.subtract(commission)).multiply(rate.getRate().multiply(rate.getMultiplicity()));
			

		} else {
			logger.warn("Rexchange rate not defined ");
			throw new ErrorExchangeException("Rexchange rate not defined");

		}
		return res.setScale(PRESISION_EXCHANGE_RESULT, RoundingMode.DOWN);
	}

	public BigDecimal exchangeBack(BigDecimal amountTo, Currency currencyFrom, Currency currencyTo) {
		Rates rate = rateService.findByCurrFromAndCurrTo(currencyFrom, currencyTo);
		BigDecimal res;
		if (rate != null && rate.getRate().doubleValue() > 0) {
			
			BigDecimal amount = amountTo.movePointRight(2).divide(BigDecimal.valueOf(100).subtract(rate.getCommissions()),5,RoundingMode.HALF_UP);
			res = amount.multiply(rate.getRate().divide(rate.getMultiplicity()));
		} else {
			logger.warn("Rexchange rate not defined or 0");
			throw new ErrorExchangeException("Rexchange rate not defined or 0");
		}
		return res.setScale(PRESISION_EXCHANGE_RESULT, RoundingMode.DOWN);
	}

}
