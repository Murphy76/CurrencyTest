package com.mycompany.currency.service.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CREATED)
public class ErrorExchangeException extends RuntimeException {

	public ErrorExchangeException(String message) {
		super(message);
	}
	

}
