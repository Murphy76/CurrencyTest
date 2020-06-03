package com.mycompany.currency.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.mycompany.currency.dto.Commission;
import com.mycompany.currency.dto.ExchangeRate;
import com.mycompany.currency.dto.ExchangeRequest;
import com.mycompany.currency.service.CurrencyService;

@RestController
public class CurrencyController {
	
	@Autowired
	CurrencyService service;
	

    @GetMapping("/api/commissions")
    public List<Commission> getAllCommissions () {
    	
    	return service.getAllCommissions();
    }
    @PostMapping("/api/commissions")
    @ResponseStatus(HttpStatus.CREATED)
    public String setCommission (@RequestBody Commission object) {
    	service.setCommission(object);
    	return "ok";
    }

    @GetMapping("/api/exchange-rates")
    public List<ExchangeRate> getAllRates () {
    	
    	return service.getAllExchangeRates();
    }
    @PostMapping("/api/exchange-rates")
    public String setExchangeRate (@RequestBody ExchangeRate object) {
    	return service.setExchangeRate(object);
    }
    
    

    @RequestMapping(value = "/api/exchange", method = RequestMethod.POST)
    public ExchangeRequest setCommission (@RequestBody ExchangeRequest object) {
    	return service.doChange(object);
    }

    
}
