package org.encheres.bo;

import java.util.Objects;

public class Categorie {
	// PK
	Integer no_categorie;
	String libelle;

	public Categorie(Integer no_categorie, String libelle) {
		this.no_categorie = no_categorie;
		this.libelle = libelle;
	}

	public Integer getNo_categorie() {
		return this.no_categorie;
	}

	public void setNo_categorie(Integer no_categorie) {
		this.no_categorie = no_categorie;
	}

	public String getLibelle() {
		return this.libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.libelle == null) ? 0 : this.libelle.hashCode());
		result = prime * result + ((this.no_categorie == null) ? 0 : this.no_categorie.hashCode());
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
		Categorie other = (Categorie) obj;
		if (!Objects.equals(this.libelle, other.libelle)) {
			return false;
		}
		if (!Objects.equals(this.no_categorie, other.no_categorie)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "Categorie [no_categorie=" + this.no_categorie + ", libelle=" + this.libelle + "]";
	}
}
