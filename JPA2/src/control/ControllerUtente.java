package control;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import model.Utente;

public class ControllerUtente {
	public Boolean existUtente(String username) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("JPA");
		EntityManager em = emf.createEntityManager();
		Utente utente = em.find(Utente.class, username );
		em.close();
		emf.close();
		if(utente == null) {
			return false;
		}
		return true;
	}
	
	public void insertUtente(String username, String nome, String cognome,String password, String email, Boolean tipo) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("JPA");
		EntityManager em = emf.createEntityManager();
		Utente utente = new Utente();
		utente.setNome(nome);
		utente.setCognome(cognome);
		utente.setUserid(username);
		utente.setTipo(tipo);
		utente.setPassword(password);
		
		em.getTransaction().begin();
		em.persist(utente);

		em.getTransaction().commit();
		em.close();
	}
}
