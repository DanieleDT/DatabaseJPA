package control;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import model.Satellite;

public class TestMain {

	public static void main(String[] args) {

		EntityManagerFactory emf = Persistence.createEntityManagerFactory("JPA");
		EntityManager em = emf.createEntityManager();
		List<Satellite> sat = new ArrayList<Satellite>();

		// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

		System.out.println("Test prendere tutti strumenti con satellite e mappe");
		TypedQuery<Satellite> queryShowAll = em.createQuery("SELECT s FROM Satellite s", model.Satellite.class);
		List<Satellite> resultAll = queryShowAll.getResultList();

		for (Satellite c : resultAll) {
			 
            sat.add(c);

       }
		for (Satellite s : sat) {
			System.out.println(s.getNome() + " _ " + s.getAgenzia() + s.getPrimaosservazione());
		}
	}
}
