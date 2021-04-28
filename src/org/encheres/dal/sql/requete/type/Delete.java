package org.encheres.dal.sql.requete.type;

import org.encheres.dal.sql.requete.Operator;
import org.encheres.dal.sql.requete.Request;

public class Delete extends Request {
	public Delete() {
		this.setRequest("DELETE FROM");
	}

	private Delete delete(String request) {
		this.setRequest(request);
		return this;
	}

	public Delete table(String table) {
		StringBuffer sb = new StringBuffer().append(this.req).append(" ").append(table);
		return this.delete(sb.toString());
	}

	public Delete where() {
		StringBuffer sb = new StringBuffer().append(this.req).append(" WHERE ");
		return this.delete(sb.toString());
	}

	public Delete condition(String table, String id, Operator operator, String table2AndSpaceAndId2) {
		StringBuffer sb = new StringBuffer().append(this.req);
		sb.append(table).append(".").append(id).append(" ").append(operator.getOp()).append(" ");
		if(table2AndSpaceAndId2 != null) {
			String[] tmp2 = table2AndSpaceAndId2.split(" ");
			if(tmp2.length != 2) {
				sb.append(table2AndSpaceAndId2);
			} else {
				sb.append(tmp2[0]).append(".").append(tmp2[1]);
			}
		} else {
			sb.append("?");
		}
		return this.delete(sb.toString());
	}

	public Delete condition(String table1, String id1, Operator operator, Select subQuery) {
		StringBuffer sb = new StringBuffer().append("(").append(subQuery.toString()).append(")");
		return this.condition(table1, id1, operator, sb.toString());
	}

	public Delete and() {
		StringBuffer sb = new StringBuffer().append(this.req).append(" AND ");
		return this.delete(sb.toString());
	}

	public Delete or() {
		StringBuffer sb = new StringBuffer().append(this.req).append(" OR ");
		return this.delete(sb.toString());
	}
}
