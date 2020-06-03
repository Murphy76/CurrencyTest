package com.mycompany.currency.service;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mycompany.currency.dto.Commission;
import com.mycompany.currency.dto.ExchangeRate;
import com.mycompany.currency.dto.ExchangeRequest;
import com.mycompany.currency.service.error.ErrorCreateObjectException;
import com.mycompany.currency.service.error.ErrorExchangeException;

@Service
public class CurrencyService {
	private static final String DESIRED_EXCHANGE_OPERATION = "GET";

	private static final String DIRECT_EXCHANGE_OPERATION = "GIVE";

	Logger logger = LoggerFactory.getLogger(CurrencyService.class);

	@Autowired
	CommissionsService commissionServive;

	@Autowired
	ExchangeService exchangeService;

	@Autowired
	RateService rateService;

	public void setCommission(Commission object) {
		if ((object.getCommissionPt().doubleValue() >= 0 && object.getCommissionPt().doubleValue() < 100)
				|| (object.getFrom() != object.getTo())) {
			commissionServive.setCommission(object);
		} else {
			logger.warn("Commission write error");
			throw new ErrorCreateObjectException("Commission write error. Commission value should be in range [0:100)");
		}
	}

	public List<Commission> getAllCommissions() {
		return commissionServive.getAllCommissions().stream().map(obj -> {
			return new Commission(obj.getCommissions(), obj.getCurrFrom(), obj.getCurrTo());
		}).collect(Collectors.toList());

	}

	public List<ExchangeRate> getAllExchangeRates() {

		return rateService.getAllRates().stream().map(obj -> {
			return new ExchangeRate(obj.getRate(), obj.getCurrFrom(), obj.getCurrTo());
		}).collect(Collectors.toList());

	}

	public ExchangeRequest doChange(ExchangeRequest object) {
		switch (object.getOperationType()) {
		case DIRECT_EXCHANGE_OPERATION:
			if (object.getAmountFrom()!=null && object.getAmountFrom().doubleValue() > 0) {

				object.setAmountTo(exchangeService.exchangeDirect(object.getAmountFrom(), object.getCurrencyFrom(),
						object.getCurrencyTo()));
			} else {
				throw new ErrorExchangeException("Bad value amountFrom");
			}
			break;
		case DESIRED_EXCHANGE_OPERATION:
			if (object.getAmountTo()!=null && object.getAmountTo().doubleValue() > 0) {
				object.setAmountFrom(exchangeService.exchangeBack(object.getAmountTo(), object.getCurrencyFrom(),
						object.getCurrencyTo()));
			} else {
				throw new ErrorExchangeException("Bad value amountTo");
			}
			break;
		default:
			logger.warn("Operation was not set correctly");
			return null;
		}

		return object;
	}

	public String setExchangeRate(ExchangeRate obj) {
		if ((obj.getRate().doubleValue() > 0) && (obj.getFrom() != obj.getTo())) {
			return rateService.setRate(obj.getRate(), obj.getFrom(), obj.getTo());
		} else {
			logger.warn("Rate write error");
			throw new ErrorCreateObjectException("Rate write error");
		}

	}

}
