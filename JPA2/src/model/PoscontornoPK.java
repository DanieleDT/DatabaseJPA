package model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the poscontorno database table.
 * 
 */
@Embeddable
public class PoscontornoPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private double latitudine;

	private double longitudine;

	@Column(insertable=false, updatable=false)
	private Integer idfil;

	public PoscontornoPK() {
	}
	public double getLatitudine() {
		return this.latitudine;
	}
	public void setLatitudine(double latitudine) {
		this.latitudine = latitudine;
	}
	public double getLongitudine() {
		return this.longitudine;
	}
	public void setLongitudine(double longitudine) {
		this.longitudine = longitudine;
	}
	public Integer getIdfil() {
		return this.idfil;
	}
	public void setIdfil(Integer idfil) {
		this.idfil = idfil;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof PoscontornoPK)) {
			return false;
		}
		PoscontornoPK castOther = (PoscontornoPK)other;
		return 
			(this.latitudine == castOther.latitudine)
			&& (this.longitudine == castOther.longitudine)
			&& this.idfil.equals(castOther.idfil);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (java.lang.Double.doubleToLongBits(this.latitudine) ^ (java.lang.Double.doubleToLongBits(this.latitudine) >>> 32)));
		hash = hash * prime + ((int) (java.lang.Double.doubleToLongBits(this.longitudine) ^ (java.lang.Double.doubleToLongBits(this.longitudine) >>> 32)));
		hash = hash * prime + this.idfil.hashCode();
		
		return hash;
	}
}