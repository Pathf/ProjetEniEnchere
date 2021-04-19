package org.encheres.bo;

import java.util.Date;

public class ArticleVendu {
	// PK
	Integer no_article;
	String nom_article;
	String description;
	Date date_debut_encheres;
	Date date_fin_encheres;
	Integer prix_initial;
	Integer prix_vente;

	// FK : Utilisateurs
	Integer no_utilisateur;

	// FK : Categorie
	Integer no_categorie;

	// FK : Retrait
	Integer no_retrait;
}
