package com.mycompany.currency.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mycompany.currency.model.Currency;
import com.mycompany.currency.model.Rates;
import com.mycompany.currency.repository.RateRepository;
import com.mycompany.currency.service.error.ErrorExchangeException;

@Service
public class ExchangeService {
	
	Logger logger = LoggerFactory.getLogger(ExchangeService.class);
	
	private static final int PRESISION_EXCHANGE_RESULT = 3;
	@Autowired
	RateRepository rateRepo;


	public static double roundAvoid(double value, int places) {
		double scale = Math.pow(10, places);
		return Math.round(value * scale) / scale;
	}

	public List<Rates> getAllComissions() {
		return rateRepo.findAll();
	}

	public double exchangeDirect(double amountFrom, Currency currencyFrom, Currency currencyTo) {
		Rates rate = rateRepo.findByCurrFromAndCurrTo(currencyFrom, currencyTo);
		double result = 0;
		if (rate !=null && rate.getRate()>0) {
			double res;
			double comission;
			comission = amountFrom*rate.getComissions()/100;
			res = (amountFrom - comission )* rate.getRate();
			result = roundAvoid(res,PRESISION_EXCHANGE_RESULT); 
			
		}else {
			logger.warn("Rexchange rate not defined or 0");
			throw new ErrorExchangeException("Rexchange rate not defined or 0");
			
		}
		return result;
	}

	public double exchangeBack(double amountTo, Currency currencyFrom, Currency currencyTo) {
		Rates rate = rateRepo.findByCurrFromAndCurrTo(currencyFrom, currencyTo);
		double result = 0;
		if (rate !=null && rate.getRate()>0) {
			double res = amountTo*100/(100-rate.getComissions());
			result = roundAvoid(res/rate.getRate(),PRESISION_EXCHANGE_RESULT); 
		}else {
			logger.warn("Rexchange rate not defined or 0");
			
			throw new ErrorExchangeException("Rexchange rate not defined or 0");
		}
		return result;
	}

}
