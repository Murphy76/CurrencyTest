package com.mycompany.currency.service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mycompany.currency.model.Currency;
import com.mycompany.currency.model.Rates;
import com.mycompany.currency.repository.RateRepository;
import com.mycompany.currency.service.error.ErrorCreateObjectException;

@Service
public class RateService {

	@Autowired
	RateRepository rateRepo;

	public String setRate(double rate, Currency from, Currency to) {
		Rates direct = getRates(from, to);
		direct.setRate(rate);
		Rates back = getRates(to, from);
		back.setRate(roundAvoid(1 / rate, 3));
		try {
			rateRepo.saveAll(Stream.of(direct, back).collect(Collectors.toList()));
		} catch (Exception e) {
			throw new ErrorCreateObjectException("Set rate error");
		}
		return "ok";
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

	public static double roundAvoid(double value, int places) {
		double scale = Math.pow(10, places);
		return Math.round(value * scale) / scale;
	}

	public List<Rates> getAllComissions() {
		return rateRepo.findAll();
	}
}
