package com.mycompany.currency.service.error;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.mycompany.currency.model.Currency;

@ControllerAdvice
public class CurrencyExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(ErrorCreateObjectException.class)
	protected ResponseEntity<CustemException> handleErrorCreationObjectException(ErrorCreateObjectException ex) {
		return new ResponseEntity<>(new CustemException("Error"), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler({UsernameNotFoundException.class,ErrorUnauthorizedException.class, BadCredentialsException.class})
	protected ResponseEntity<CustemException> handleUnauthorizedUserException(Exception ex) {
		return new ResponseEntity<>(new CustemException("Unauthorized"), HttpStatus.UNAUTHORIZED);
	}
		
	@ExceptionHandler(ErrorExchangeException.class)
	protected ResponseEntity<CustemException> handleUnableExchangeException(ErrorExchangeException ex) {
		return new ResponseEntity<>(new CustemException("Error"), HttpStatus.BAD_REQUEST);
	}


	
	
	@Override
	public ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		String errorMsg = null;
		if (ex.getCause() instanceof JsonMappingException) {
			String path = ((ServletWebRequest) request).getRequest().getRequestURI().toString();
			if (path.contains("exchange-rates")) {
				errorMsg = "Rate must have up to 3 digits accuracy. Available currency values are "
						+ Currency.getAllValues();
			} else if (path.contains("exchange")) {
				errorMsg = "The amount The course must be a positive number accurate to 2 characters. Available currency values are "
						+  Currency.getAllValues();
			} else if (path.contains("commissions")) {
				errorMsg = "Commission must be a positive value from 0 and less than 100. Available currency values are "
						+  Currency.getAllValues();
			}
		}
		return handleExceptionInternal(ex, errorMsg, headers, status, request);
	}

	private static class CustemException {
		Logger logger = LoggerFactory.getLogger(CustemException.class);
		private String message;

		public CustemException(String message) {
			logger.warn(message);
			this.message = message;
		}

		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}

	}
}
