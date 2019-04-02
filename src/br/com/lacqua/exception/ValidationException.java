package br.com.lacqua.exception;

/**
 * Exceção lançada quando ocorre uma validação de negócio
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