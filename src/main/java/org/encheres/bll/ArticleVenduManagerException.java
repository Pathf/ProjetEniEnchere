package org.encheres.bll;

public class ArticleVenduManagerException extends Exception {
	private static final long serialVersionUID = 4712246496860071184L;

	public ArticleVenduManagerException() {
		super();
	}

	public ArticleVenduManagerException(String message) {
		super(message);
	}

	public ArticleVenduManagerException(String message, Throwable cause) {
		super(message, cause);
	}

	@Override
	public String getMessage() {
		StringBuffer sb = new StringBuffer("Couche BLL ArticleVenduManagerException - ");
		sb.append(super.getMessage());
		return sb.toString();
	}

}
