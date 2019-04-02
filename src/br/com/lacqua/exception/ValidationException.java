package br.com.lacqua.exception;

/**
 * Exce��o lan�ada quando ocorre uma valida��o de neg�cio
 */
@SuppressWarnings("serial")
public class ValidationException extends Exception {

	public ValidationException() {
	}

	public ValidationException(String message) {
		super(message);
	}

	public ValidationException(Throwable cause) {
		super(cause);
	}

	public ValidationException(String message, Throwable cause) {
		super(message, cause);
	}
}