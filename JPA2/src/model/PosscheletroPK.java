package model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the posscheletro database table.
 * 
 */
@Embeddable
public class PosscheletroPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private double latitudine;

	private double longitudine;

	public PosscheletroPK() {
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

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof PosscheletroPK)) {
			return false;
		}
		PosscheletroPK castOther = (PosscheletroPK)other;
		return 
			(this.latitudine == castOther.latitudine)
			&& (this.longitudine == castOther.longitudine);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (java.lang.Double.doubleToLongBits(this.latitudine) ^ (java.lang.Double.doubleToLongBits(this.latitudine) >>> 32)));
		hash = hash * prime + ((int) (java.lang.Double.doubleToLongBits(this.longitudine) ^ (java.lang.Double.doubleToLongBits(this.longitudine) >>> 32)));
		
		return hash;
	}
}