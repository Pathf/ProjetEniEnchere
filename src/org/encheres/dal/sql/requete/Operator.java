package org.encheres.dal.sql.requete;

public enum Operator {
	EQUAL("="),
	GREATER(">"),
	LESS("<"),
	GREATER_EQUAL(">="),
	LESS_EQUAL("<="),
	NOT_EQUAL("!="),
	LIKE("LIKE"),
	IN("IN");

	private String op;

	Operator(String opToString) {
		this.op = opToString;
	}

	public String getOp() {
		return this.op;
	}
}
