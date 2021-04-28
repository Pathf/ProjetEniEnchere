package org.encheres.dal.sql.requete.type;

import org.encheres.dal.sql.requete.Request;

public class Insert extends Request {
	public Insert() {
		this.setRequest("INSERT INTO ");
	}

	private Insert insert(String request) {
		this.setRequest(request);
		return this;
	}

	public Insert attributs(String table, String[] fields){
		StringBuffer sb = new StringBuffer().append(this.req);
		sb.append(table);
		sb.append(" (");
		for(String field : fields) {
			sb.append(field);
			sb.append(",");
		}
		sb.deleteCharAt(sb.length()-1);
		sb.append(")");

		return this.insert(sb.toString());
	}

	public Insert values(String[] fields) {
		StringBuffer sb = new StringBuffer().append(this.req);
		if(this.req.contains("(")) {
			sb.append("VALUES (");
			if(fields == null || fields.length == 0) {
				int nombreValues = this.req.split(",").length;
				if(nombreValues > 0) {
					sb.append("?");
					for(int i=1; i<nombreValues; i++) {
						sb.append(",?");
					}
				}
			} else {
				sb.append(fields[0]);
				for(int i = 1; i < fields.length; i++) {
					sb.append(",").append(fields[i]);
				}
			}
			sb.append(")");
		} else {
			System.err.println("Aucun parametre n'as été renseigné !");
		}

		return this.insert(sb.toString());
	}
}
