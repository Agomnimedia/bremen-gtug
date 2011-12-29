package org.gtugs.bremen.eventmanagement.android.exceptions;

public class NotAllowedException extends Exception{

	/**
	 * generated serialVersionUID.
	 */
	private static final long serialVersionUID = 7362447903527044532L;

	public NotAllowedException(final String mesString, final Throwable cause) {
	    super(mesString, cause);
	}

	public NotAllowedException(final String mesString) {
	    super(mesString);
	}
}
