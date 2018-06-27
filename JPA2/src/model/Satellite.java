package model;

import java.io.Serializable;
import javax.persistence.*;

import model.Satellite;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the satellite database table.
 * 
 */
@Entity
@NamedQuery(name="Satellite.findAll", query="SELECT s FROM Satellite s")
public class Satellite implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String nome;

	private String agenzia;

	@Temporal(TemporalType.DATE)
	private Date primaosservazione;

	@Temporal(TemporalType.DATE)
	private Date termineattivita;

	//bi-directional many-to-one association to Filamento
	@OneToMany(mappedBy="satellite")
	private List<Filamento> filamentos;

	//bi-directional many-to-one association to Strumento
	@OneToMany(mappedBy="satelliteBean")
	private List<Strumento> strumentos;

	public Satellite() {
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getAgenzia() {
		return this.agenzia;
	}

	public void setAgenzia(String agenzia) {
		this.agenzia = agenzia;
	}

	public Date getPrimaosservazione() {
		return this.primaosservazione;
	}

	public void setPrimaosservazione(Date primaosservazione) {
		this.primaosservazione = primaosservazione;
	}

	public Date getTermineattivita() {
		return this.termineattivita;
	}

	public void setTermineattivita(Date termineattivita) {
		this.termineattivita = termineattivita;
	}

	public List<Filamento> getFilamentos() {
		return this.filamentos;
	}

	public void setFilamentos(List<Filamento> filamentos) {
		this.filamentos = filamentos;
	}

	public Filamento addFilamento(Filamento filamento) {
		getFilamentos().add(filamento);
		filamento.setSatellite(this);

		return filamento;
	}

	public Filamento removeFilamento(Filamento filamento) {
		getFilamentos().remove(filamento);
		filamento.setSatellite(null);

		return filamento;
	}

	public List<Strumento> getStrumentos() {
		return this.strumentos;
	}

	public void setStrumentos(List<Strumento> strumentos) {
		this.strumentos = strumentos;
	}

	public Strumento addStrumento(Strumento strumento) {
		getStrumentos().add(strumento);
		strumento.setSatelliteBean(this);

		return strumento;
	}

	public Strumento removeStrumento(Strumento strumento) {
		getStrumentos().remove(strumento);
		strumento.setSatelliteBean(null);

		return strumento;
	}

}