package org.encheres.dal.sql.requete;

public abstract class Request {
	protected String req;

	public String getRequest() {
		return this.req;
	}

	public void setRequest(String req) {
		this.req = req;
	}

	@Override
	public String toString() {
		return this.getRequest();
	}
}
