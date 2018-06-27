package model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the poscontorno database table.
 * 
 */
@Entity
@NamedQuery(name="Poscontorno.findAll", query="SELECT p FROM Poscontorno p")
public class Poscontorno implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private PoscontornoPK id;

	//bi-directional many-to-one association to Filamento
	@ManyToOne
	@JoinColumn(name="idfil")
	private Filamento filamento;

	public Poscontorno() {
	}

	public PoscontornoPK getId() {
		return this.id;
	}

	public void setId(PoscontornoPK id) {
		this.id = id;
	}

	public Filamento getFilamento() {
		return this.filamento;
	}

	public void setFilamento(Filamento filamento) {
		this.filamento = filamento;
	}

}