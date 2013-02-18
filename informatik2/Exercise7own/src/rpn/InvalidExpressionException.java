package rpn;

public class InvalidExpressionException extends Exception {
	private static final long serialVersionUID = 1L;

	public InvalidExpressionException() {
	}

	public String getMessage() {
		return ("No valid expression.");
	}
}