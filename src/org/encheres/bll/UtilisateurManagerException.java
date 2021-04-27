package org.encheres.bll;

public class UtilisateurManagerException extends Exception {
	private static final long serialVersionUID = 7864045885912687512L;

	public UtilisateurManagerException() {
		super();
	}

	public UtilisateurManagerException(String message) {
		super(message);
	}

	@Override
	public String getMessage() {
		StringBuffer sb = new StringBuffer("Couche BLL UtilisateurManagerException - ");
		sb.append(super.getMessage());
		return sb.toString();
	}

}
