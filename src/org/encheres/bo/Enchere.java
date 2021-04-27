package org.encheres.bo;

import java.sql.Date;
import java.util.Objects;

public class Enchere {
	// PK
	Integer no_enchere;
	Date date_enchere;
	Integer montant_enchere;

	// FK : ArticleVendu
	ArticleVendu article;

	// FK : Utilisateurs
	Utilisateur utilisateur;

	public Enchere(Integer no_enchere, Date date_enchere, Integer montant_enchere, ArticleVendu article,
			Utilisateur utilisateur) {
		this.no_enchere = no_enchere;
		this.date_enchere = date_enchere;
		this.montant_enchere = montant_enchere;
		this.article = article;
		this.utilisateur = utilisateur;
	}

	public Integer getNo_enchere() {
		return this.no_enchere;
	}

	public void setNo_enchere(Integer no_enchere) {
		this.no_enchere = no_enchere;
	}

	public Date getDate_enchere() {
		return this.date_enchere;
	}

	public void setDate_enchere(Date date_enchere) {
		this.date_enchere = date_enchere;
	}

	public Integer getMontant_enchere() {
		return this.montant_enchere;
	}

	public void setMontant_enchere(Integer montant_enchere) {
		this.montant_enchere = montant_enchere;
	}

	public ArticleVendu getArticle() {
		return this.article;
	}

	public void setArticle(ArticleVendu article) {
		this.article = article;
	}

	public Utilisateur getUtilisateur() {
		return this.utilisateur;
	}

	public void setUtilisateur(Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (this.getClass() != obj.getClass()) {
			return false;
		}
		Enchere other = (Enchere) obj;
		if (!Objects.equals(this.date_enchere, other.date_enchere)) {
			return false;
		}
		if (!Objects.equals(this.montant_enchere, other.montant_enchere)) {
			return false;
		}
		if (!Objects.equals(this.article, other.article)) {
			return false;
		}
		if (!Objects.equals(this.no_enchere, other.no_enchere)) {
			return false;
		}
		if (!Objects.equals(this.utilisateur, other.utilisateur)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "Enchere [no_enchere=" + this.no_enchere + ", date_enchere=" + this.date_enchere + ", montant_enchere="
				+ this.montant_enchere + ", no_article=" + this.article + ", no_utilisateur=" + this.utilisateur + "]";
	}
}
