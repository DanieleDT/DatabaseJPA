package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the scheletro database table.
 * 
 */
@Entity
@NamedQuery(name="Scheletro.findAll", query="SELECT s FROM Scheletro s")
public class Scheletro implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Integer id;

	private Boolean tipo;

	//bi-directional many-to-one association to Posscheletro
	@OneToMany(mappedBy="scheletro")
	private List<Posscheletro> posscheletros;

	//bi-directional many-to-one association to Filamento
	@ManyToOne
	@JoinColumn(name="idfilamento")
	private Filamento filamento;

	public Scheletro() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Boolean getTipo() {
		return this.tipo;
	}

	public void setTipo(Boolean tipo) {
		this.tipo = tipo;
	}

	public List<Posscheletro> getPosscheletros() {
		return this.posscheletros;
	}

	public void setPosscheletros(List<Posscheletro> posscheletros) {
		this.posscheletros = posscheletros;
	}

	public Posscheletro addPosscheletro(Posscheletro posscheletro) {
		getPosscheletros().add(posscheletro);
		posscheletro.setScheletro(this);

		return posscheletro;
	}

	public Posscheletro removePosscheletro(Posscheletro posscheletro) {
		getPosscheletros().remove(posscheletro);
		posscheletro.setScheletro(null);

		return posscheletro;
	}

	public Filamento getFilamento() {
		return this.filamento;
	}

	public void setFilamento(Filamento filamento) {
		this.filamento = filamento;
	}

}