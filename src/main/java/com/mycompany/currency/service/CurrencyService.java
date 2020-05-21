package com.mycompany.currency.service;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mycompany.currency.dto.Comission;
import com.mycompany.currency.dto.ExchangeRate;
import com.mycompany.currency.dto.ExchangeRequest;
import com.mycompany.currency.service.error.ErrorCreateObjectException;

@Service
public class CurrencyService {
	private static final String DESIRED_EXCHANGE_OPERATION = "GET";

	private static final String DIRECT_EXCHANGE_OPERATION = "GIVE";

	Logger logger = LoggerFactory.getLogger(CurrencyService.class);
	
	@Autowired
	ComissionsService comissionServive;

	@Autowired
	ExchangeService exchangeService;

	@Autowired
	RateService rateService;

	public void setComission(Comission object) {
		if ((object.getCommissionPt() > 0 && object.getCommissionPt()<100)||(object.getFrom()!=object.getTo())) {
			comissionServive.setComission(object);
		} else {
			logger.warn("Comission write error");
			throw new ErrorCreateObjectException("Comission write error");
		}
	}

	public List<Comission> getAllComissions() {

		return comissionServive.getAllComissions().stream().map(obj -> {
			return new Comission(obj.getComissions(), obj.getCurrFrom(), obj.getCurrTo());
		}).collect(Collectors.toList());

	}

	public List<ExchangeRate> getAllExchangeRates() {

		return comissionServive.getAllComissions().stream().map(obj -> {
			return new ExchangeRate(obj.getRate(), obj.getCurrFrom(), obj.getCurrTo());
		})
//				.filter(obj-> obj.getRate()>0) // get correct rates only
				.collect(Collectors.toList());

	}

	public ExchangeRequest doChange(ExchangeRequest object) {

		switch (object.getOperationType()) {
		case DIRECT_EXCHANGE_OPERATION:
			object.setAmountTo(exchangeService.exchangeDirect(object.getAmountFrom(), object.getCurrencyFrom(),
					object.getCurrencyTo()));
			break;
		case DESIRED_EXCHANGE_OPERATION:
			object.setAmountFrom(exchangeService.exchangeBack(object.getAmountTo(), object.getCurrencyFrom(),
					object.getCurrencyTo()));
			break;
		default:
			logger.warn("Operation was not set correctly");
			return null;
		}

		return object;
	}

	public String setExchangeRate(ExchangeRate obj) {
		if ((obj.getRate() > 0)&&(obj.getFrom()!=obj.getTo())) {
			return rateService.setRate(obj.getRate(), obj.getFrom(), obj.getTo());
		} else {
			logger.warn("Rate write error");
			throw new ErrorCreateObjectException("Rate write error");
		}
		
	}

}
