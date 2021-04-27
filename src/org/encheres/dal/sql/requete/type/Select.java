package org.encheres.dal.sql.requete.type;

import org.encheres.dal.sql.requete.Operator;
import org.encheres.dal.sql.requete.Request;

public class Select extends Request {
	public Select() {
		this.setRequest("SELECT");
	}

	private Select select(String request) {
		this.setRequest(request);
		return this;
	}

	public Select attributs(String field, String tableOfField) {
		return this.attributs(new String[] { field }, tableOfField);
	}

	public Select attributs(String[] fields, String tableOfFields) {
		StringBuffer sb = new StringBuffer().append(this.req).append(("SELECT".equals(this.req))? " " : ",");
		for(String field : fields) {
			sb.append(tableOfFields).append(".").append(field).append(",");
		}
		sb.deleteCharAt(sb.length()-1);
		return this.select(sb.toString());
	}

	public Select from(String table) {
		StringBuffer sb = new StringBuffer().append(this.req);
		if("SELECT".equals(this.req)) {
			sb.append(" *");
		}
		sb.append(" FROM ").append(table);

		return this.select(sb.toString());
	}

	// TODO: A améliorere pour plusieur id
	public Select leftJoin(String table, String idTable, String onTable, String onId) {
		StringBuffer sb = new StringBuffer().append(this.req)
				.append(" LEFT JOIN ").append(table).append(" ON ")
				.append(onTable).append(".").append(onId).append(" = ").append(table).append(".").append(idTable);
		return this.select(sb.toString());
	}

	public Select where() {
		StringBuffer sb = new StringBuffer().append(this.req).append(" WHERE ");
		return this.select(sb.toString());
	}

	/**
	 * Il existe une surcharge de cette methode avec un objet Select à la palce de table2AndPointAndId2 pour faire des requêtes imbriqués.
	 * @param table est la table qui sert à lever l'ambiguité si il en existe une
	 * @param id est l'id de la table from qui corespond à l'égalité à vérifié (ex: "Table1 id1")
	 * @param operator est un opérateur de comparaison présent dans le langage SQL
	 * @param table2AndSpaceAndId2 peut être :
	 * 	- un string composé d'une table et d'un id. Il sont forcément séparé par un espace (ex: "Table2 id2")
	 *  - un string plus complexe pour faire des associations sur des requêtes imbriqué ou autres (ex: "(1,2,3,4)")
	 * @return un Select et ajoute le morceaux de requete a l'objet courant
	 */
	public Select condition(String table, String id, Operator operator, String table2AndSpaceAndId2) {
		StringBuffer sb = new StringBuffer().append(this.req);
		String[] tmp2 = table2AndSpaceAndId2.split(" ");

		sb.append(table).append(".").append(id).append(" ").append(operator.getOp()).append(" ");
		if(tmp2.length != 2) {
			sb.append(table2AndSpaceAndId2);
		} else {
			sb.append(tmp2[0]).append(".").append(tmp2[1]);
		}
		return this.select(sb.toString());
	}

	public Select condition(String table1, String id1, Operator operator, Select subQuery) {
		StringBuffer sb = new StringBuffer().append("(").append(subQuery.toString()).append(")");
		return this.condition(table1, id1, operator, sb.toString());
	}

	public Select and() {
		StringBuffer sb = new StringBuffer().append(this.req).append(" AND ");
		return this.select(sb.toString());
	}

	public Select or() {
		StringBuffer sb = new StringBuffer().append(this.req).append(" OR ");
		return this.select(sb.toString());
	}
}
