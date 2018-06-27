package model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the stella database table.
 * 
 */
@Entity
@NamedQuery(name="Stella.findAll", query="SELECT s FROM Stella s")
public class Stella implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Integer id;

	private double latitudine;

	private double longitudine;

	private String nome;

	private String tipologia;

	private double valoreflusso;

	public Stella() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getTipologia() {
		return this.tipologia;
	}

	public void setTipologia(String tipologia) {
		this.tipologia = tipologia;
	}

	public double getValoreflusso() {
		return this.valoreflusso;
	}

	public void setValoreflusso(double valoreflusso) {
		this.valoreflusso = valoreflusso;
	}

}