package org.encheres.bll;

public class EnchereManagerException extends Exception {
	private static final long serialVersionUID = 3640154309408899123L;

	public EnchereManagerException() {
		super();
	}

	public EnchereManagerException(String message) {
		super(message);
	}

	@Override
	public String getMessage() {
		StringBuffer sb = new StringBuffer("Couche BLL EnchereManagerException - ");
		sb.append(super.getMessage());
		return sb.toString();
	}
}
