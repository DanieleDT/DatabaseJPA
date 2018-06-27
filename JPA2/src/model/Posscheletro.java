package model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the posscheletro database table.
 * 
 */
@Entity
@NamedQuery(name="Posscheletro.findAll", query="SELECT p FROM Posscheletro p")
public class Posscheletro implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private PosscheletroPK id;

	private double flusso;

	private Integer numeroprogressivo;

	//bi-directional many-to-one association to Scheletro
	@ManyToOne
	@JoinColumn(name="idscheletro")
	private Scheletro scheletro;

	public Posscheletro() {
	}

	public PosscheletroPK getId() {
		return this.id;
	}

	public void setId(PosscheletroPK id) {
		this.id = id;
	}

	public double getFlusso() {
		return this.flusso;
	}

	public void setFlusso(double flusso) {
		this.flusso = flusso;
	}

	public Integer getNumeroprogressivo() {
		return this.numeroprogressivo;
	}

	public void setNumeroprogressivo(Integer numeroprogressivo) {
		this.numeroprogressivo = numeroprogressivo;
	}

	public Scheletro getScheletro() {
		return this.scheletro;
	}

	public void setScheletro(Scheletro scheletro) {
		this.scheletro = scheletro;
	}

}