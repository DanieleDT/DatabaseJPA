package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the filamento database table.
 * 
 */
@Entity
@NamedQuery(name="Filamento.findAll", query="SELECT f FROM Filamento f")
public class Filamento implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Integer id;

	private double contrasto;

	private double densitamedia;

	private double ellitticita;

	private double flussototale;

	private String nome;

	private double tempmedia;

	//bi-directional many-to-one association to Satellite
	@ManyToOne
	@JoinColumn(name="nomsatellite" ,insertable=false, updatable=false )
	private Satellite satellite;

	//bi-directional many-to-one association to Strumento
	@ManyToOne
	@JoinColumns({
		@JoinColumn(name="nomsatellite", referencedColumnName="nome"),
		@JoinColumn(name="nomstrumento", referencedColumnName="satellite")
		})
	private Strumento strumento;

	//bi-directional many-to-one association to Poscontorno
	@OneToMany(mappedBy="filamento")
	private List<Poscontorno> poscontornos;

	//bi-directional many-to-one association to Scheletro
	@OneToMany(mappedBy="filamento")
	private List<Scheletro> scheletros;

	public Filamento() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public double getContrasto() {
		return this.contrasto;
	}

	public void setContrasto(double contrasto) {
		this.contrasto = contrasto;
	}

	public double getDensitamedia() {
		return this.densitamedia;
	}

	public void setDensitamedia(double densitamedia) {
		this.densitamedia = densitamedia;
	}

	public double getEllitticita() {
		return this.ellitticita;
	}

	public void setEllitticita(double ellitticita) {
		this.ellitticita = ellitticita;
	}

	public double getFlussototale() {
		return this.flussototale;
	}

	public void setFlussototale(double flussototale) {
		this.flussototale = flussototale;
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public double getTempmedia() {
		return this.tempmedia;
	}

	public void setTempmedia(double tempmedia) {
		this.tempmedia = tempmedia;
	}

	public Satellite getSatellite() {
		return this.satellite;
	}

	public void setSatellite(Satellite satellite) {
		this.satellite = satellite;
	}

	public Strumento getStrumento() {
		return this.strumento;
	}

	public void setStrumento(Strumento strumento) {
		this.strumento = strumento;
	}

	public List<Poscontorno> getPoscontornos() {
		return this.poscontornos;
	}

	public void setPoscontornos(List<Poscontorno> poscontornos) {
		this.poscontornos = poscontornos;
	}

	public Poscontorno addPoscontorno(Poscontorno poscontorno) {
		getPoscontornos().add(poscontorno);
		poscontorno.setFilamento(this);

		return poscontorno;
	}

	public Poscontorno removePoscontorno(Poscontorno poscontorno) {
		getPoscontornos().remove(poscontorno);
		poscontorno.setFilamento(null);

		return poscontorno;
	}

	public List<Scheletro> getScheletros() {
		return this.scheletros;
	}

	public void setScheletros(List<Scheletro> scheletros) {
		this.scheletros = scheletros;
	}

	public Scheletro addScheletro(Scheletro scheletro) {
		getScheletros().add(scheletro);
		scheletro.setFilamento(this);

		return scheletro;
	}

	public Scheletro removeScheletro(Scheletro scheletro) {
		getScheletros().remove(scheletro);
		scheletro.setFilamento(null);

		return scheletro;
	}

}