package com.mycompany.currency.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.mycompany.currency.dto.Comission;
import com.mycompany.currency.dto.ExchangeRate;
import com.mycompany.currency.dto.ExchangeRequest;
import com.mycompany.currency.service.CurrencyService;

@RestController
public class CurrencyController {
	
	@Autowired
	CurrencyService service;
	

    @GetMapping("/api/comissions")
    public List<Comission> getAllComissions () {
    	
    	return service.getAllComissions();
    }
    @PostMapping("/api/comissions")
    public String setComission (@RequestBody Comission object) {
    	service.setComission(object);
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
    
    
    @PostMapping("/api/exchange")
    public ExchangeRequest setComission (@RequestBody ExchangeRequest object) {
    	
    	return service.doChange(object);
    }

    
}
