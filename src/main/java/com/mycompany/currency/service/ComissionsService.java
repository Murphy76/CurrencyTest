package com.mycompany.currency.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mycompany.currency.dto.Comission;
import com.mycompany.currency.model.Currency;
import com.mycompany.currency.model.Rates;
import com.mycompany.currency.repository.RateRepository;
import com.mycompany.currency.service.error.ErrorCreateObjectException;

@Service
public class ComissionsService {

	@Autowired
	RateRepository rateRepo;

	public Comission setComission(Comission com) {

		Rates direct = getRates(com.getFrom(), com.getTo());
		direct.setComissions(roundAvoid(com.getCommissionPt(),2));
		try {
			direct = rateRepo.save(direct);
		} catch (Exception e) {
			throw new ErrorCreateObjectException("Comission creation error.");
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
