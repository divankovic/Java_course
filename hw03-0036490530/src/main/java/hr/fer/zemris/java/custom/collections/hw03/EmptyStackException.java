package hr.fer.zemris.java.custom.collections.hw03;

public class EmptyStackException extends RuntimeException{
	
	private static final long serialVersionUID = 8649440350758699570L;

	public EmptyStackException() {
		
	}
	
	public EmptyStackException(String message) {
		super(message);
	}
	
	public EmptyStackException(Throwable throwable) {
		super(throwable);
	}

}
