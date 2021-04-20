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
	Utilisateur utilisateur;

	// FK : Categorie
	Categorie categorie;

	// FK : Retrait
	Retrait retrait;

	public ArticleVendu(Integer no_article, String nom_article, String description, Date date_debut_encheres,
			Date date_fin_encheres, Integer prix_initial, Integer prix_vente, Utilisateur utilisateur,
			Categorie categorie, Retrait retrait) {
		this.no_article = no_article;
		this.nom_article = nom_article;
		this.description = description;
		this.date_debut_encheres = date_debut_encheres;
		this.date_fin_encheres = date_fin_encheres;
		this.prix_initial = prix_initial;
		this.prix_vente = prix_vente;
		this.utilisateur = utilisateur;
		this.categorie = categorie;
		this.retrait = retrait;
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


	public Utilisateur getUtilisateur() {
		return this.utilisateur;
	}

	public void setUtilisateur(Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}

	public Categorie getCategorie() {
		return this.categorie;
	}

	public void setCategorie(Categorie categorie) {
		this.categorie = categorie;
	}

	public Retrait getRetrait() {
		return this.retrait;
	}

	public void setRetrait(Retrait retrait) {
		this.retrait = retrait;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.date_debut_encheres == null) ? 0 : this.date_debut_encheres.hashCode());
		result = prime * result + ((this.date_fin_encheres == null) ? 0 : this.date_fin_encheres.hashCode());
		result = prime * result + ((this.description == null) ? 0 : this.description.hashCode());
		result = prime * result + ((this.no_article == null) ? 0 : this.no_article.hashCode());
		result = prime * result + ((this.categorie == null) ? 0 : this.categorie.hashCode());
		result = prime * result + ((this.retrait == null) ? 0 : this.retrait.hashCode());
		result = prime * result + ((this.utilisateur == null) ? 0 : this.utilisateur.hashCode());
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
		if (!Objects.equals(this.categorie, other.categorie)) {
			return false;
		}
		if (!Objects.equals(this.retrait, other.retrait)) {
			return false;
		}
		if (!Objects.equals(this.utilisateur, other.utilisateur)) {
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
				+ ", no_utilisateur=" + this.utilisateur + ", no_categorie=" + this.categorie + ", no_retrait=" + this.retrait
				+ "]";
	}
}
