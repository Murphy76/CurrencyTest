package com.mycompany.currency.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mycompany.currency.dto.Comission;
import com.mycompany.currency.dto.ExchangeRate;
import com.mycompany.currency.dto.ExchangeRequest;
import com.mycompany.currency.service.error.ErrorCreateObjectException;

@Service
public class CurrencyService {

	@Autowired
	ComissionsService comissionServive;

	@Autowired
	ExchangeService exchangeService;

	@Autowired
	RateService rateService;

	public void setComission(Comission object) {
		if (object.getCommissionPt() > 0 && object.getCommissionPt()<100) {
			comissionServive.setComission(object);
		} else {
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
		}).collect(Collectors.toList());
	}

	public ExchangeRequest doChange(ExchangeRequest object) {

		switch (object.getOperationType()) {
		case "GIVE":
			object.setAmountTo(exchangeService.exchangeDirect(object.getAmountFrom(), object.getCurrencyFrom(),
					object.getCurrencyTo()));
			break;
		case "GET":
			object.setAmountFrom(exchangeService.exchangeBack(object.getAmountTo(), object.getCurrencyFrom(),
					object.getCurrencyTo()));
			break;
		default:
			return null;
		}

		return object;
	}

	public String setExchangeRate(ExchangeRate obj) {
		if (obj.getRate() > 0) {
			return rateService.setRate(obj.getRate(), obj.getFrom(), obj.getTo());
		} else {
			throw new ErrorCreateObjectException("Rate write error");
		}
		
	}

}
