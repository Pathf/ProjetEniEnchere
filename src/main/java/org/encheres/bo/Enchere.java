package org.encheres.bo;

import java.sql.Date;
import java.util.Objects;

public class Enchere {
	// PK
	Integer no_enchere;
	Date date_enchere;
	Integer montant_enchere;

	// FK : ArticleVendu
	ArticleVendu no_article;

	// FK : Utilisateurs
	Utilisateur no_utilisateur;

	public Enchere(Integer no_enchere, Date date_enchere, Integer montant_enchere, ArticleVendu no_article,
			Utilisateur no_utilisateur) {
		this.no_enchere = no_enchere;
		this.date_enchere = date_enchere;
		this.montant_enchere = montant_enchere;
		this.no_article = no_article;
		this.no_utilisateur = no_utilisateur;
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

	public ArticleVendu getNo_article() {
		return this.no_article;
	}

	public void setNo_article(ArticleVendu no_article) {
		this.no_article = no_article;
	}

	public Utilisateur getNo_utilisateur() {
		return this.no_utilisateur;
	}

	public void setNo_utilisateur(Utilisateur no_utilisateur) {
		this.no_utilisateur = no_utilisateur;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.date_enchere == null) ? 0 : this.date_enchere.hashCode());
		result = prime * result + ((this.montant_enchere == null) ? 0 : this.montant_enchere.hashCode());
		result = prime * result + ((this.no_article == null) ? 0 : this.no_article.hashCode());
		result = prime * result + ((this.no_enchere == null) ? 0 : this.no_enchere.hashCode());
		result = prime * result + ((this.no_utilisateur == null) ? 0 : this.no_utilisateur.hashCode());
		return result;
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
		if (!Objects.equals(this.no_article, other.no_article)) {
			return false;
		}
		if (!Objects.equals(this.no_enchere, other.no_enchere)) {
			return false;
		}
		if (!Objects.equals(this.no_utilisateur, other.no_utilisateur)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "Enchere [no_enchere=" + this.no_enchere + ", date_enchere=" + this.date_enchere + ", montant_enchere="
				+ this.montant_enchere + ", no_article=" + this.no_article + ", no_utilisateur=" + this.no_utilisateur + "]";
	}
}
