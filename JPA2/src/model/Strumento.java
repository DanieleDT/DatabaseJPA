package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the strumento database table.
 * 
 */
@Entity
@NamedQuery(name="Strumento.findAll", query="SELECT s FROM Strumento s")
public class Strumento implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private StrumentoPK id;

	//bi-directional many-to-one association to Banda
	@OneToMany(mappedBy="strumento")
	private List<Banda> bandas;

	//bi-directional many-to-one association to Filamento
	@OneToMany(mappedBy="strumento")
	private List<Filamento> filamentos;

	//bi-directional many-to-one association to Satellite
	@ManyToOne
	@JoinColumn(name="satellite")
	private Satellite satelliteBean;

	public Strumento() {
	}

	public StrumentoPK getId() {
		return this.id;
	}

	public void setId(StrumentoPK id) {
		this.id = id;
	}

	public List<Banda> getBandas() {
		return this.bandas;
	}

	public void setBandas(List<Banda> bandas) {
		this.bandas = bandas;
	}

	public Banda addBanda(Banda banda) {
		getBandas().add(banda);
		banda.setStrumento(this);

		return banda;
	}

	public Banda removeBanda(Banda banda) {
		getBandas().remove(banda);
		banda.setStrumento(null);

		return banda;
	}

	public List<Filamento> getFilamentos() {
		return this.filamentos;
	}

	public void setFilamentos(List<Filamento> filamentos) {
		this.filamentos = filamentos;
	}

	public Filamento addFilamento(Filamento filamento) {
		getFilamentos().add(filamento);
		filamento.setStrumento(this);

		return filamento;
	}

	public Filamento removeFilamento(Filamento filamento) {
		getFilamentos().remove(filamento);
		filamento.setStrumento(null);

		return filamento;
	}

	public Satellite getSatelliteBean() {
		return this.satelliteBean;
	}

	public void setSatelliteBean(Satellite satelliteBean) {
		this.satelliteBean = satelliteBean;
	}

}