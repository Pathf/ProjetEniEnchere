package org.encheres.bo;

import java.sql.Date;
import java.util.Arrays;
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
	String photoNom;
	byte[] photoData;

	// FK : Utilisateurs
	Utilisateur utilisateur;

	// FK : Categorie
	Categorie categorie;

	// FK : Retrait
	Retrait retrait;

	public ArticleVendu(Integer no_article) {
		this.no_article = no_article;
	}

	public ArticleVendu(Integer no_article, String nom_article, String description, Date date_debut_encheres,
			Date date_fin_encheres, Integer prix_initial, Integer prix_vente, String photoNom, byte[] photoData,
			Utilisateur utilisateur, Categorie categorie, Retrait retrait) {
		this.no_article = no_article;
		this.nom_article = nom_article;
		this.description = description;
		this.date_debut_encheres = date_debut_encheres;
		this.date_fin_encheres = date_fin_encheres;
		this.prix_initial = prix_initial;
		this.prix_vente = prix_vente;
		this.photoNom = photoNom;
		this.photoData = photoData;
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

	public String getPhotoNom() {
		return this.photoNom;
	}

	public void setPhotoNom(String photoName) {
		this.photoNom = photoName;
	}

	public byte[] getPhotoData() {
		return this.photoData;
	}

	public void setPhotoData(byte[] photoData) {
		this.photoData = photoData;
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
		if (!Objects.equals(this.categorie, other.categorie)) {
			return false;
		}
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
		if (!Objects.equals(this.nom_article, other.nom_article)) {
			return false;
		}
		if (!Arrays.equals(this.photoData, other.photoData)) {
			return false;
		}
		if (!Objects.equals(this.photoNom, other.photoNom)) {
			return false;
		}
		if (!Objects.equals(this.prix_initial, other.prix_initial)) {
			return false;
		}
		if (!Objects.equals(this.prix_vente, other.prix_vente)) {
			return false;
		}
		if (!Objects.equals(this.retrait, other.retrait)) {
			return false;
		}
		if (!Objects.equals(this.utilisateur, other.utilisateur)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "ArticleVendu [no_article=" + this.no_article + ", nom_article=" + this.nom_article + ", description="
				+ this.description + ", date_debut_encheres=" + this.date_debut_encheres + ", date_fin_encheres="
				+ this.date_fin_encheres + ", prix_initial=" + this.prix_initial + ", prix_vente=" + this.prix_vente + ", photoName="
				+ this.photoNom + ", utilisateur=" + this.utilisateur + ", categorie=" + this.categorie + ", retrait=" + this.retrait
				+ "]";
	}
}
