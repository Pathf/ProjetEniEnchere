package org.encheres.dal.sql;

import org.encheres.dal.sql.requete.Operator;
import org.encheres.dal.sql.requete.type.Delete;
import org.encheres.dal.sql.requete.type.Insert;

public class SQLRequete {

	public static final String select(String table) {
		return select(null, table);
	}

	public static final String select(String[] champs, String table) {
		return select(champs, table, null);
	}

	public static final String select(String[] champs, String table,  String[] champWheres) {
		return selectLeftJoin(champs, new String[] {table}, champWheres);
	}

	// Ne marche que si les id sont identique a la table 1
	// String[] tables = new String[]{"table1", "table2 no_retrait", "table3 no_utilisateur"};
	// String[] champs = new String[]{"no_table1 att1 att2 att3","no_table2 attribut1 attribut2","no_table3 champ1 champ2"};
	// String[] champWheres = new String[]{"no_table1"};
	public static final String selectLeftJoin(String[] champs, String[] tables, String[] champWheres) {
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT ");
		if(champs != null) {
			if(tables.length > 1) {
				for(int i = 0; i < champs.length; i++) {
					String tableTMP = tables[i].split(" ")[0];
					for(String champ : champs[i].split(" ")) {
						sb.append(tableTMP);
						sb.append(".");
						sb.append(champ);
						sb.append(",");
					}
				}
				sb.deleteCharAt(sb.length()-1);
			} else {
				for(String champ : champs) {
					sb.append(champ);
					sb.append(",");
				}
				sb.deleteCharAt(sb.length()-1);
			}
		} else {
			sb.append("*");
		}

		sb.append(" FROM ");
		if(tables.length > 1) {
			String table1 = tables[0];
			sb.append(table1);
			for(int i=1; i < tables.length; i++) {
				String table2 = tables[i].split(" ")[0];
				String idTable2 = tables[i].split(" ")[1];
				sb.append(" LEFT JOIN ");
				sb.append(table2);
				sb.append(" ON ");
				sb.append(table1);
				sb.append(".");
				sb.append(idTable2);
				sb.append(" = ");
				sb.append(table2);
				sb.append(".");
				sb.append(idTable2);
			}
		} else {
			sb.append(tables[0]);
		}

		if(champWheres != null) {
			sb.append(" WHERE ");
			for(String champWhere : champWheres) {
				sb.append(champWhere);
				sb.append("=?");
				sb.append(" AND ");
			}
			sb.deleteCharAt(sb.length()-1);
			sb.deleteCharAt(sb.length()-1);
			sb.deleteCharAt(sb.length()-1);
			sb.deleteCharAt(sb.length()-1);
			sb.deleteCharAt(sb.length()-1);
		}

		return sb.toString();
	}

	/**
	 *	Fonction d'insertion dans une base de donnée
	 *
	 * @param table La table dans laquelle on va faire l'insertion des nouvelles données
	 * @param champs la liste des champs que l'on va modifié
	 * @return Une requète d'insertion sous le format String
	 */
	public static final String insert(String table, String[] champs) {
		return new Insert().attributs(table, champs).values(null).toString();
	}

	public static final String update(String table, String[] champs, String[] champWheres) {
		StringBuffer sb = new StringBuffer();
		sb.append("UPDATE ");
		sb.append(table);
		sb.append(" SET ");
		for(String champ : champs) {
			sb.append(champ);
			sb.append("=?,");
		}
		sb.deleteCharAt(sb.length()-1);

		sb.append(" WHERE ");
		for(String champWhere : champWheres) {
			sb.append(champWhere);
			sb.append("=?");
			sb.append(" AND ");
		}
		sb.deleteCharAt(sb.length()-1);
		sb.deleteCharAt(sb.length()-1);
		sb.deleteCharAt(sb.length()-1);
		sb.deleteCharAt(sb.length()-1);
		sb.deleteCharAt(sb.length()-1);

		return sb.toString();
	}

	public static final String delete(String table) {
		return new Delete().table(table).toString();
	}

	public static final String delete(String table, String[] champs) {
		StringBuffer sb = new StringBuffer();
		sb.append("DELETE FROM ");
		sb.append(table);
		if(champs != null) {
			sb.append(" WHERE ");
			for(String champ : champs) {
				sb.append(champ);
				sb.append("=?,");
			}
			sb.deleteCharAt(sb.length()-1);
		}
		return sb.toString();
	}
	/**
	 * Fonction de suppression de ligne spécifique
	 *
	 * @param table la table qui va subir des suppressions
	 * @param table1EtEspaceEtChamp1S est un tableau de String contenant une table et le champ de cette table, les deux séparés d'un espace (ex: "table idDeLaTable")
	 * @param operators contient les opérations de chaque conditions
	 * @param table2EtEspaceEtChamp2S est un tableau de String contenant une table et le champ de cette table, les deux séparés d'un espace. Si on met null à la place de la chaine de caractère alors sa sera remplacé par un "?" (ex: "table1 idDeLaTable", null)
	 * @return
	 */
	public static final String delete(String table, String[] table1EtEspaceEtChamp1S, Operator[] operators, String[] table2EtEspaceEtChamp2S) {
		Delete request = new Delete().table(table).where();
		for(int i=0; i < table1EtEspaceEtChamp1S.length; i++) {
			String[] tmp = table1EtEspaceEtChamp1S[i].split(" ");
			request.condition(tmp[0], tmp[1], operators[i], table2EtEspaceEtChamp2S[i]);
		}
		return request.toString();
	}
}
