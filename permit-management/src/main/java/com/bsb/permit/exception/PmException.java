package com.bsb.permit.exception;

public class PmException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5122509999901508335L;

	public PmException(String message) {
		super(message);
	}

	public PmException(String message, Throwable t) {
		super(message, t);
	}
}
