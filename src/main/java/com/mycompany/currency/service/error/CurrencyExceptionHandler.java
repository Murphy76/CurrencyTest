package com.mycompany.currency.service.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CurrencyExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(ErrorCreateObjectException.class)
    protected ResponseEntity<CustemException> handleErrorCreationObjectException(ErrorCreateObjectException ex) {
        return new ResponseEntity<>(new CustemException(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }
	@ExceptionHandler(UsernameNotFoundException.class)
    protected ResponseEntity<CustemException> handleUnauthorizedUserException(UsernameNotFoundException ex) {
        return new ResponseEntity<>(new CustemException(ex.getMessage()), HttpStatus.UNAUTHORIZED);
    }
	@ExceptionHandler(ErrorExchangeException.class)
    protected ResponseEntity<CustemException> handleUnableExchangeException(ErrorExchangeException ex) {
        return new ResponseEntity<>(new CustemException(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }
	
	

	
	private static class CustemException {
        private String message;

		public CustemException(String message) {
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
