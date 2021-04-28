package org.encheres.bo;

import java.util.Objects;

public class Utilisateur {
	// PK
	Integer no_utilisateur;
	String pseudo;
	String nom;
	String prenom;
	String email;
	String telephone;
	String rue;
	String code_postal;
	String ville;
	String mot_de_passe;
	Integer credit;
	boolean administrateur;

	public Utilisateur(Integer no_utilisateur, String pseudo, boolean administrateur) {
		this(no_utilisateur, pseudo, null, null, null, null, null, null, null, null, null, administrateur);
	}

	public Utilisateur(Integer no_utilisateur, String pseudo, String nom, String prenom, String email, String telephone,
			String rue, String code_postal, String ville, String mot_de_passe, Integer credit, boolean administrateur) {
		this.no_utilisateur = no_utilisateur;
		this.pseudo = pseudo;
		this.nom = nom;
		this.prenom = prenom;
		this.email = email;
		this.telephone = telephone;
		this.rue = rue;
		this.code_postal = code_postal;
		this.ville = ville;
		this.mot_de_passe = mot_de_passe;
		this.credit = credit;
		this.administrateur = administrateur;
	}

	public Integer getNo_utilisateur() {
		return this.no_utilisateur;
	}

	public void setNo_utilisateur(Integer no_utilisateur) {
		this.no_utilisateur = no_utilisateur;
	}

	public String getPseudo() {
		return this.pseudo;
	}

	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}

	public String getNom() {
		return this.nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return this.prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelephone() {
		return this.telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getRue() {
		return this.rue;
	}

	public void setRue(String rue) {
		this.rue = rue;
	}

	public String getCode_postal() {
		return this.code_postal;
	}

	public void setCode_postal(String code_postal) {
		this.code_postal = code_postal;
	}

	public String getVille() {
		return this.ville;
	}

	public void setVille(String ville) {
		this.ville = ville;
	}

	public String getMot_de_passe() {
		return this.mot_de_passe;
	}

	public void setMot_de_passe(String mot_de_passe) {
		this.mot_de_passe = mot_de_passe;
	}

	public Integer getCredit() {
		return this.credit;
	}

	public void setCredit(Integer credit) {
		this.credit = credit;
	}

	public boolean getAdministrateur() {
		return this.administrateur;
	}

	public void setAdministrateur(boolean administrateur) {
		this.administrateur = administrateur;
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
		Utilisateur other = (Utilisateur) obj;
		if (this.administrateur != other.administrateur) {
			return false;
		}
		if (!Objects.equals(this.code_postal, other.code_postal)) {
			return false;
		}
		if (!Objects.equals(this.credit, other.credit)) {
			return false;
		}
		if (!Objects.equals(this.email, other.email)) {
			return false;
		}
		if (!Objects.equals(this.mot_de_passe, other.mot_de_passe)) {
			return false;
		}
		if (!Objects.equals(this.no_utilisateur, other.no_utilisateur)) {
			return false;
		}
		if (!Objects.equals(this.nom, other.nom)) {
			return false;
		}
		if (!Objects.equals(this.prenom, other.prenom)) {
			return false;
		}
		if (!Objects.equals(this.pseudo, other.pseudo)) {
			return false;
		}
		if (!Objects.equals(this.rue, other.rue)) {
			return false;
		}
		if (!Objects.equals(this.telephone, other.telephone)) {
			return false;
		}
		if (!Objects.equals(this.ville, other.ville)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "Utilisateur [no_utilisateur=" + this.no_utilisateur + ", pseudo=" + this.pseudo + ", nom=" + this.nom + ", prenom="
				+ this.prenom + ", email=" + this.email + ", telephone=" + this.telephone + ", rue=" + this.rue + ", code_postal="
				+ this.code_postal + ", ville=" + this.ville + ", mot_de_passe=" + this.mot_de_passe + ", credit=" + this.credit
				+ ", administrateur=" + this.administrateur + "]";
	}
}
