package com.mycompany.currency.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mycompany.currency.model.Currency;
import com.mycompany.currency.model.Rates;


public interface RateRepository extends JpaRepository<Rates, Long> {

	Rates findByCurrFromAndCurrTo(Currency curFrom, Currency curTo);



}
