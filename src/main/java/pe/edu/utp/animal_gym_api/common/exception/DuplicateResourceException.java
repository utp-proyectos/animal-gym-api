package pe.edu.utp.animal_gym_api.common.exception;

public class DuplicateResourceException extends RuntimeException {
	public DuplicateResourceException(String message) {
		super(message);
	}
}
