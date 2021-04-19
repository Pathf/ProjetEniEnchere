package org.encheres.bo;

import java.util.Date;

public class Enchere {
	// PK
	Integer no_enchere;
	Date date_enchere;
	Integer montant_enchere;

	// FK : ArticleVendu
	Integer no_article;

	// FK : Utilisateurs
	Integer no_utilisateur;
}
