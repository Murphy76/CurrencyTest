package com.mycompany.currency.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mycompany.currency.model.Currency;
import com.mycompany.currency.model.Rates;
import com.mycompany.currency.repository.RateRepository;
import com.mycompany.currency.service.error.ErrorExchangeException;

@Service
public class ExchangeService {

	@Autowired
	RateRepository rateRepo;

//	private Rates getRates(Currency from, Currency to) {
//		Rates rate =  rateRepo.findByCurrFromAndCurrTo(from, to);
//		if (rate == null) {
//			rate = new Rates();
//			rate.setCurrFrom(from);
//			rate.setCurrTo(to);
//		}
//		return rate;
//	}

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
			res = amountFrom * rate.getRate();
			comission = res*rate.getComissions()/100;
			result = roundAvoid(res-comission,2); 
			
		}else {
			throw new ErrorExchangeException("Exchange error");
		}
		return result;
	}

	public double exchangeBack(double amountTo, Currency currencyFrom, Currency currencyTo) {
		Rates rate = rateRepo.findByCurrFromAndCurrTo(currencyFrom, currencyTo);
		double result = 0;
		if (rate !=null && rate.getRate()>0) {
			double res = amountTo*100/(100-rate.getComissions());
			result = roundAvoid(res/rate.getRate(),2); 
		}else {
			throw new ErrorExchangeException("Exchange error");
		}
		return result;
	}

}
