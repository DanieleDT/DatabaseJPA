package model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the banda database table.
 * 
 */
@Entity
@NamedQuery(name="Banda.findAll", query="SELECT b FROM Banda b")
public class Banda implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private double valore;

	//bi-directional many-to-one association to Strumento
	@ManyToOne
	@JoinColumns({
		@JoinColumn(name="nomesatellite", referencedColumnName="nome"),
		@JoinColumn(name="nomestrumento", referencedColumnName="satellite")
		})
	private Strumento strumento;

	public Banda() {
	}

	public double getValore() {
		return this.valore;
	}

	public void setValore(double valore) {
		this.valore = valore;
	}

	public Strumento getStrumento() {
		return this.strumento;
	}

	public void setStrumento(Strumento strumento) {
		this.strumento = strumento;
	}

}