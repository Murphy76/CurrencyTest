package com.mycompany.currency.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.mycompany.currency.model.Currency;
import com.mycompany.currency.model.Rates;
import com.mycompany.currency.repository.RateRepository;
import com.mycompany.currency.service.error.ErrorCreateObjectException;

@Service
public class RateService {
	private static final int RATE_PRECISION = 3;
	Logger logger = LoggerFactory.getLogger(RateService.class);
	@Autowired
	RateRepository rateRepo;

	public String setRate(BigDecimal rate, Currency from, Currency to) {
		Rates direct = getRates(from, to);
		direct.setMultiplicity(BigDecimal.ONE);
		direct.setRate(rate);
		Rates back = getRates(to, from);
		back.setMultiplicity(BigDecimal.ONE);
		setBackRate(back,rate);
		try {
			rateRepo.saveAll(Stream.of(direct, back).collect(Collectors.toList()));
		} catch (IllegalArgumentException e) {
			logger.warn("Write rate error");
			throw new ErrorCreateObjectException("Set rate error");
		}
		return "ok";
	}

	private void setBackRate(Rates back,BigDecimal rate) {
		BigDecimal tmp = BigDecimal.ONE.divide(rate,10,RoundingMode.HALF_UP).multiply(back.getMultiplicity());
		if (tmp.doubleValue()>10  ) {
			back.setMultiplicity(back.getMultiplicity().divide(BigDecimal.TEN));
			 setBackRate( back, rate);
		}else if (tmp.doubleValue()<1) {
			back.setMultiplicity(back.getMultiplicity().multiply(BigDecimal.TEN));
			 setBackRate( back, rate);
		}
		back.setRate(tmp.setScale(RATE_PRECISION,RoundingMode.HALF_UP));
	}

	private Rates getRates(Currency from, Currency to) {
		Rates rate = rateRepo.findByCurrFromAndCurrTo(from, to);
		if (rate == null) {
			rate = new Rates();
			rate.setCurrFrom(from);
			rate.setCurrTo(to);
		}
		return rate;
	}

	public List<Rates> getAllRates() {
		List<Rates> list = rateRepo.findByRateNotNull();
		return ObjectUtils.isEmpty(list)?new ArrayList<Rates>():list;
	}

	public Rates findByCurrFromAndCurrTo(Currency currencyFrom, Currency currencyTo) {
		return rateRepo.findByCurrFromAndCurrTo(currencyFrom, currencyTo);
	}

}
