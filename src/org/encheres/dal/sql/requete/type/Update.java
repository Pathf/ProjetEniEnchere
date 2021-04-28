package org.encheres.dal.sql.requete.type;

import org.encheres.dal.sql.requete.Operator;
import org.encheres.dal.sql.requete.Request;

public class Update extends Request {
	public Update() {
		this.setRequest("UPDATE");
	}

	private Update update(String request) {
		this.setRequest(request);
		return this;
	}

	public Update set(String table, String[] fields, String[] updateFields) {
		StringBuffer sb = new StringBuffer().append(this.req).append(" ").append(table).append(" SET ");
		sb.append(fields[0]).append("=").append(updateFields[0]);
		for(int i=1; i < fields.length; i++) {
			sb.append(",").append(fields[i]).append("=").append(updateFields[i]);
		}
		return this.update(sb.toString());
	}

	public Update where() {
		StringBuffer sb = new StringBuffer().append(this.req).append(" WHERE ");
		return this.update(sb.toString());
	}

	public Update condition(String table, String id, Operator operator, String table2AndSpaceAndId2) {
		StringBuffer sb = new StringBuffer().append(this.req);
		String[] tmp2 = table2AndSpaceAndId2.split(" ");

		sb.append(table).append(".").append(id).append(" ").append(operator.getOp()).append(" ");
		if(tmp2.length != 2) {
			sb.append(table2AndSpaceAndId2);
		} else {
			sb.append(tmp2[0]).append(".").append(tmp2[1]);
		}
		return this.update(sb.toString());
	}

	public Update condition(String table1, String id1, Operator operator, Select subQuery) {
		StringBuffer sb = new StringBuffer().append("(").append(subQuery.toString()).append(")");
		return this.condition(table1, id1, operator, sb.toString());
	}

	public Update and() {
		StringBuffer sb = new StringBuffer().append(this.req).append(" AND ");
		return this.update(sb.toString());
	}

	public Update or() {
		StringBuffer sb = new StringBuffer().append(this.req).append(" OR ");
		return this.update(sb.toString());
	}
}
