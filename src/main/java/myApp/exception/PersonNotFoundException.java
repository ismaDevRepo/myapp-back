package myApp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class PersonNotFoundException extends RuntimeException {

	/**
	 * Default version id for serializable class
	 */
	private static final long serialVersionUID = 1L;

	public PersonNotFoundException(Integer id) {
	    super("Could not find employee " + id);
	}
}