package rpn;

public class StackUnderflowException extends Exception {
	private static final long serialVersionUID = 1L;

	public StackUnderflowException() {
	}

	public String getMessage() {
		return ("Stack underflow error.");
	}
}