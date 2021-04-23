package org.encheres.dal.impl;

public class BDD {
	// ARTICLES_VENDUS :
	public static final String ARTICLESVENDUS_TABLENOM = "ARTICLES_VENDUS";
	public static final String[] ARTICLESVENDUS_IDS = new String[]{"no_article"};
	public static final String[] ARTICLESVENDUS_CHAMPS = new String[]{"nom_article","description","date_debut_encheres","date_fin_encheres","prix_initial","prix_vente","no_utilisateur","no_categorie","no_retrait"};
	public static final String[][] ARTICLESVENDUS_TABLE = new String[][] {new String[] {ARTICLESVENDUS_TABLENOM}, ARTICLESVENDUS_IDS, ARTICLESVENDUS_CHAMPS};

	// UTILISATEURS :
	public static final String UTILISATEURS_TABLENOM = "UTILISATEURS";
	public static final String[] UTILISATEURS_IDS = new String[]{"no_utilisateur"};
	public static final String[] UTILISATEURS_CHAMPS = new String[] {"pseudo", "nom", "prenom", "email", "telephone", "rue", "code_postal", "ville", "mot_de_passe", "credit", "administrateur"};
	public static final String[][] UTILISATEURS_TABLE = new String[][] {new String[] {UTILISATEURS_TABLENOM}, UTILISATEURS_IDS, UTILISATEURS_CHAMPS};

	// RETRAITS :
	public static final String RETRAITS_TABLENOM = "RETRAITS";
	public static final String[] RETRAITS_IDS = new String[]{"no_retrait"};
	public static final String[] RETRAIT_CHAMPS = new String[] {"rue", "code_postal", "ville"};
	public static final String[][] RETRAITS_TABLE = new String[][] {new String[] {RETRAITS_TABLENOM}, RETRAITS_IDS, RETRAIT_CHAMPS};

	// CATEGORIES :
	public static final String CATEGORIES_TABLENOM = "CATEGORIES";
	public static final String[] CATEGORIES_IDS = new String[]{"no_categorie"};
	public static final String[] CATEGORIES_CHAMPS = new String[] {"libelle"};
	public static final String[][] CATEGORIES_TABLE = new String[][] {new String[] {CATEGORIES_TABLENOM}, CATEGORIES_IDS, CATEGORIES_CHAMPS};
}
