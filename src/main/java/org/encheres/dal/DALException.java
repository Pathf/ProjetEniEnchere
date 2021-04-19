package org.encheres.dal;

public class DALException extends Exception {
	private static final long serialVersionUID = -3777587573749742506L;

	public DALException() {
		super();
	}

	public DALException(String message) {
		super(message);
	}

	public DALException(String message, Throwable cause) {
		super(message, cause);
	}

	@Override
	public String getMessage() {
		StringBuffer sb = new StringBuffer("Couche DAL - ");
		sb.append(super.getMessage());
		return sb.toString();
	}
}
