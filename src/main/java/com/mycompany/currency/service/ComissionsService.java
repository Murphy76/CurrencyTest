package com.mycompany.currency.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mycompany.currency.dto.Comission;
import com.mycompany.currency.model.Currency;
import com.mycompany.currency.model.Rates;
import com.mycompany.currency.repository.RateRepository;
import com.mycompany.currency.service.error.ErrorCreateObjectException;

@Service
public class ComissionsService {

	Logger logger = LoggerFactory.getLogger(ComissionsService.class);
	
	@Autowired
	RateRepository rateRepo;

	public Comission setComission(Comission com) {

		Rates direct = getRates(com.getFrom(), com.getTo());
		direct.setComissions(roundAvoid(com.getCommissionPt(),2));
		try {
			direct = rateRepo.save(direct);
		} catch (Exception e) {
			logger.warn("Comission write error.");
			throw new ErrorCreateObjectException("Comission write error.");
		}
		return com;
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
