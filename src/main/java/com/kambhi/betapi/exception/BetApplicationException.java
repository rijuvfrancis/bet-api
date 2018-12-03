package com.kambhi.betapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * The Class BetApplicationException.
 */

@ResponseStatus(HttpStatus.NOT_FOUND)
public class BetApplicationException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The Constructor.
	 *
	 * @param message the message
	 */
	public BetApplicationException(String message) {
		super(message);
	}

}
