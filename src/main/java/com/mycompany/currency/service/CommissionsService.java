package com.mycompany.currency.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.mycompany.currency.dto.Commission;
import com.mycompany.currency.model.Currency;
import com.mycompany.currency.model.Rates;
import com.mycompany.currency.repository.RateRepository;
import com.mycompany.currency.service.error.ErrorCreateObjectException;

@Service
public class CommissionsService {

	Logger logger = LoggerFactory.getLogger(CommissionsService.class);

	@Autowired
	RateRepository rateRepo;

	public Commission setCommission(Commission com) {

		if (com.getCommissionPt().doubleValue() >= 0 && com.getCommissionPt().doubleValue() < 100) {
			Rates direct = getRates(com.getFrom(), com.getTo());
			direct.setCommissions(com.getCommissionPt());
			try {
				direct = rateRepo.save(direct);
				
			} catch (IllegalArgumentException e) {
				logger.warn("Commission write error.");
				throw new ErrorCreateObjectException("Commission write error.");
			}
		} else {
			logger.warn("Wrong commission value.");
			throw new ErrorCreateObjectException("Wrong commission value.");
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

	public List<Rates> getAllCommissions() {
		List<Rates> list = rateRepo.findByCommissionsNotNull();
		return ObjectUtils.isEmpty(list)?new ArrayList<Rates>():list;
	}
}
