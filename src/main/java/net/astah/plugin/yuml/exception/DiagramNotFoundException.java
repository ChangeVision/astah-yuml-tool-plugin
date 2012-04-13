package net.astah.plugin.yuml.exception;

public class DiagramNotFoundException extends Exception {
	private static final long serialVersionUID = 1L;

	public DiagramNotFoundException() {
		super();
	}

	public DiagramNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public DiagramNotFoundException(String message) {
		super(message);
	}

	public DiagramNotFoundException(Throwable cause) {
		super(cause);
	}
}
