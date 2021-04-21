package org.encheres.dal;

public class ConstantesSQL {

	public static final String requeteSelect(String table) {
		return requeteSelect(table, null);
	}

	public static final String requeteSelect(String table, String[] champs) {
		return requeteSelect(table, champs, null);
	}

	public static final String requeteSelect(String table, String[] champs, String[] champWheres) {
		return requeteSelectInnerJoin(new String[] {table}, champs, champWheres);
	}

	public static final String requeteSelectInnerJoin(String[] tables, String[] champs, String[] champWheres) {
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT ");
		if(champs != null) {
			for(String champ : champs) {
				sb.append(champ);
				sb.append(",");
			}
			sb.deleteCharAt(sb.length()-1);
		} else {
			sb.append("*");
		}

		sb.append(" FROM ");
		if(tables.length > 1) {
			String table1 = tables[0].split(" ")[0];
			String idTable1 = tables[0].split(" ")[1];
			sb.append(table1);
			for(int i=1; i < tables.length; i++) {
				String table2 = tables[i].split(" ")[0];
				String idTable2 = tables[i].split(" ")[1];
				sb.append(" INNER JOIN ");
				sb.append(table2);
				sb.append(" ON ");
				sb.append(table1);
				sb.append(".");
				sb.append(idTable1);
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
				sb.append("=?,");
			}
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

	public static final String requeteUpdate(String table, String[] champs) {
		StringBuffer sb = new StringBuffer();
		sb.append("UPDATE ");
		sb.append(table);
		sb.append(" SET ");
		for(String champ : champs) {
			sb.append(champ);
			sb.append("=?,");
		}
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
