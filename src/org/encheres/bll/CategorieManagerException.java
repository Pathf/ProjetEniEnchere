package org.encheres.bll;

public class CategorieManagerException extends Exception{

	
	public CategorieManagerException() {
		super();
	}

	public CategorieManagerException(String message) {
		super(message);
	}

	public CategorieManagerException(String message, Throwable cause) {
		super(message, cause);
	}

	@Override
	public String getMessage() {
		StringBuffer sb = new StringBuffer("Couche BLL CategorieManagerException - ");
		sb.append(super.getMessage());
		return sb.toString();
	}

}


