package control;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import model.Banda;
import model.Satellite;
import model.Strumento;
import model.StrumentoPK;

public class ControllerSatellite {
	public void insertSatellite(String nome, String agenzia, LocalDate dataInizio, LocalDate dataFine) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("JPA");
		EntityManager em = emf.createEntityManager();
		Date dataIni = Date.from(dataInizio.atStartOfDay(ZoneId.systemDefault()).toInstant());
		Date dataFin = Date.from(dataFine.atStartOfDay(ZoneId.systemDefault()).toInstant());
		Satellite satellite = new Satellite();		
		satellite.setNome(nome);
		satellite.setAgenzia(agenzia);
		satellite.setPrimaosservazione(dataIni);
		satellite.setTermineattivita(dataFin);

		em.getTransaction().begin();
		em.persist(satellite);
		em.getTransaction().commit();
		em.close();

	}
	
	public Boolean existSatellite(String nome) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("JPA");
		EntityManager em = emf.createEntityManager();
		Satellite satellite = em.find(Satellite.class, nome);
		if(satellite == null) {
			return false;
		}
		return true;
	}
	
	public Boolean existStrumento(String nome, String satellite) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("JPA");
		EntityManager em = emf.createEntityManager();
		StrumentoPK pk = new StrumentoPK();
		pk.setNome(nome);
		pk.setSatellite(satellite);
		Strumento strumento = em.find(Strumento.class, pk);
		if(strumento == null) {
			return false;
		}
		return true;
	}
	
	public void insertBanda( double valore, String nome, String satellite) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("JPA");
		EntityManager em = emf.createEntityManager();
		Banda banda = new Banda();
		StrumentoPK pk = new StrumentoPK();
		pk.setNome(nome);
		pk.setSatellite(satellite);
		Strumento strumento = em.find(Strumento.class, pk);
		banda.setStrumento(strumento);
		banda.setValore(valore);
		
		em.getTransaction().begin();
		em.persist(banda);
		em.getTransaction().commit();
		em.close();
	}
	
	public void insertStrumento(String nome, String satellite) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("JPA");
		EntityManager em = emf.createEntityManager();
		Strumento strumento = new Strumento();
		StrumentoPK pk = new StrumentoPK();
		pk.setNome(nome);
		pk.setSatellite(satellite);
		strumento.setId(pk);
		em.getTransaction().begin();
		em.persist(strumento);
		em.getTransaction().commit();
		em.close();
	}
}
