package exceptions;

// This exception class won't be necessary if the DB foreign keys are set up correctly.
public class NoSuchToolTypeException extends Exception {
	public NoSuchToolTypeException(String message) {
		super(message);
	}
}
