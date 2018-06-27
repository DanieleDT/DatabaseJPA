package model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the strumento database table.
 * 
 */
@Embeddable
public class StrumentoPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(insertable=false, updatable=false)
	private String satellite;

	private String nome;

	public StrumentoPK() {
	}
	public String getSatellite() {
		return this.satellite;
	}
	public void setSatellite(String satellite) {
		this.satellite = satellite;
	}
	public String getNome() {
		return this.nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof StrumentoPK)) {
			return false;
		}
		StrumentoPK castOther = (StrumentoPK)other;
		return 
			this.satellite.equals(castOther.satellite)
			&& this.nome.equals(castOther.nome);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.satellite.hashCode();
		hash = hash * prime + this.nome.hashCode();
		
		return hash;
	}
}