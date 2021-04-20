package org.encheres.bo;

import java.util.Objects;

public class Retrait {
	// PK
	Integer no_retrait;
	String rue;
	String code_postal;
	String ville;

	public Retrait(Integer no_retrait, String rue, String code_postal, String ville) {
		this.no_retrait = no_retrait;
		this.rue = rue;
		this.code_postal = code_postal;
		this.ville = ville;
	}

	public Integer getNo_retrait() {
		return this.no_retrait;
	}

	public void setNo_retrait(Integer no_retrait) {
		this.no_retrait = no_retrait;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.code_postal == null) ? 0 : this.code_postal.hashCode());
		result = prime * result + ((this.no_retrait == null) ? 0 : this.no_retrait.hashCode());
		result = prime * result + ((this.rue == null) ? 0 : this.rue.hashCode());
		result = prime * result + ((this.ville == null) ? 0 : this.ville.hashCode());
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
		Retrait other = (Retrait) obj;
		if (!Objects.equals(this.code_postal, other.code_postal)) {
			return false;
		}
		if (!Objects.equals(this.no_retrait, other.no_retrait)) {
			return false;
		}
		if (!Objects.equals(this.rue, other.rue)) {
			return false;
		}
		if (!Objects.equals(this.ville, other.ville)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "Retrait [no_retrait=" + this.no_retrait + ", rue=" + this.rue + ", code_postal=" + this.code_postal + ", ville="
				+ this.ville + "]";
	}
}
