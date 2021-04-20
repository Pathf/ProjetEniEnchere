package org.encheres.bo;

import java.sql.Date;
import java.util.Objects;

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
	Utilisateur no_utilisateur;

	// FK : Categorie
	Categorie no_categorie;

	// FK : Retrait
	Retrait no_retrait;

	public ArticleVendu(Integer no_article, String nom_article, String description, Date date_debut_encheres,
			Date date_fin_encheres, Integer prix_initial, Integer prix_vente, Utilisateur no_utilisateur,
			Categorie no_categorie, Retrait no_retrait) {
		this.no_article = no_article;
		this.nom_article = nom_article;
		this.description = description;
		this.date_debut_encheres = date_debut_encheres;
		this.date_fin_encheres = date_fin_encheres;
		this.prix_initial = prix_initial;
		this.prix_vente = prix_vente;
		this.no_utilisateur = no_utilisateur;
		this.no_categorie = no_categorie;
		this.no_retrait = no_retrait;
	}

	public Integer getNo_article() {
		return this.no_article;
	}

	public void setNo_article(Integer no_article) {
		this.no_article = no_article;
	}

	public String getNom_article() {
		return this.nom_article;
	}

	public void setNom_article(String nom_article) {
		this.nom_article = nom_article;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getDate_debut_encheres() {
		return this.date_debut_encheres;
	}

	public void setDate_debut_encheres(Date date_debut_encheres) {
		this.date_debut_encheres = date_debut_encheres;
	}

	public Date getDate_fin_encheres() {
		return this.date_fin_encheres;
	}

	public void setDate_fin_encheres(Date date_fin_encheres) {
		this.date_fin_encheres = date_fin_encheres;
	}

	public Integer getPrix_initial() {
		return this.prix_initial;
	}

	public void setPrix_initial(Integer prix_initial) {
		this.prix_initial = prix_initial;
	}

	public Integer getPrix_vente() {
		return this.prix_vente;
	}

	public void setPrix_vente(Integer prix_vente) {
		this.prix_vente = prix_vente;
	}

	public Utilisateur getNo_utilisateur() {
		return this.no_utilisateur;
	}

	public void setNo_utilisateur(Utilisateur no_utilisateur) {
		this.no_utilisateur = no_utilisateur;
	}

	public Categorie getNo_categorie() {
		return this.no_categorie;
	}

	public void setNo_categorie(Categorie no_categorie) {
		this.no_categorie = no_categorie;
	}

	public Retrait getNo_retrait() {
		return this.no_retrait;
	}

	public void setNo_retrait(Retrait no_retrait) {
		this.no_retrait = no_retrait;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.date_debut_encheres == null) ? 0 : this.date_debut_encheres.hashCode());
		result = prime * result + ((this.date_fin_encheres == null) ? 0 : this.date_fin_encheres.hashCode());
		result = prime * result + ((this.description == null) ? 0 : this.description.hashCode());
		result = prime * result + ((this.no_article == null) ? 0 : this.no_article.hashCode());
		result = prime * result + ((this.no_categorie == null) ? 0 : this.no_categorie.hashCode());
		result = prime * result + ((this.no_retrait == null) ? 0 : this.no_retrait.hashCode());
		result = prime * result + ((this.no_utilisateur == null) ? 0 : this.no_utilisateur.hashCode());
		result = prime * result + ((this.nom_article == null) ? 0 : this.nom_article.hashCode());
		result = prime * result + ((this.prix_initial == null) ? 0 : this.prix_initial.hashCode());
		result = prime * result + ((this.prix_vente == null) ? 0 : this.prix_vente.hashCode());
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
		ArticleVendu other = (ArticleVendu) obj;
		if (!Objects.equals(this.date_debut_encheres, other.date_debut_encheres)) {
			return false;
		}
		if (!Objects.equals(this.date_fin_encheres, other.date_fin_encheres)) {
			return false;
		}
		if (!Objects.equals(this.description, other.description)) {
			return false;
		}
		if (!Objects.equals(this.no_article, other.no_article)) {
			return false;
		}
		if (!Objects.equals(this.no_categorie, other.no_categorie)) {
			return false;
		}
		if (!Objects.equals(this.no_retrait, other.no_retrait)) {
			return false;
		}
		if (!Objects.equals(this.no_utilisateur, other.no_utilisateur)) {
			return false;
		}
		if (!Objects.equals(this.nom_article, other.nom_article)) {
			return false;
		}
		if (!Objects.equals(this.prix_initial, other.prix_initial)) {
			return false;
		}
		if (!Objects.equals(this.prix_vente, other.prix_vente)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "ArticleVendu [no_article=" + this.no_article + ", nom_article=" + this.nom_article + ", description="
				+ this.description + ", date_debut_encheres=" + this.date_debut_encheres + ", date_fin_encheres="
				+ this.date_fin_encheres + ", prix_initial=" + this.prix_initial + ", prix_vente=" + this.prix_vente
				+ ", no_utilisateur=" + this.no_utilisateur + ", no_categorie=" + this.no_categorie + ", no_retrait=" + this.no_retrait
				+ "]";
	}
}
