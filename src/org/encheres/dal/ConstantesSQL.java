package org.encheres.dal;

public class ConstantesSQL {

	public static final String requeteSelect(String table) {
		return requeteSelect(table, null);
	}

	public static final String requeteSelect(String table, String[] champs) {
		return requeteSelect(table, champs, null);
	}

	public static final String requeteSelect(String table, String[] champs, String[] champWheres) {
		return requeteSelectLeftJoin(new String[] {table}, champs, champWheres);
	}

	// Ne marche que si les id sont identique a la table 1
	// String[] tables = new String[]{"table1", "table2 no_retrait", "table3 no_utilisateur"};
	// String[] champs = new String[]{"no_table1 att1 att2 att3","no_table2 attribut1 attribut2","no_table3 champ1 champ2"};
	// String[] champWheres = new String[]{"no_table1"};
	public static final String requeteSelectLeftJoin(String[] tables, String[] champs, String[] champWheres) {
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

	public static final String requeteInsert(String table, String[] champs) {
		StringBuffer sb = new StringBuffer();
		sb.append("INSERT INTO ");
		sb.append(table);
		sb.append(" (");
		for(String champ : champs) {
			sb.append(champ);
			sb.append(",");
		}
		sb.deleteCharAt(sb.length()-1);
		sb.append(") VALUES (?");
		for(int i = 1; i < champs.length; i++) {
			sb.append(",?");
		}
		sb.append(")");
		return sb.toString();
	}

	public static final String requeteUpdate(String table, String[] champs, String[] champWheres) {
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

	public static final String requeteDelete(String table) {
		return requeteDelete(table, null);
	}

	public static final String requeteDelete(String table, String[] champs) {
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
}
