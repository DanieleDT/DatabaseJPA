package control;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import bean.BeanStelleInFilamento;
import model.Stella;
import model.Poscontorno;

public class ControllerStelle {
	//RF9
	public BeanStelleInFilamento FindStelleInFilamento(int IdFil) {
		ArrayList<Stella> stelle, stelleInFil = null;
		ArrayList<Poscontorno> posizioniContorno;
		stelle = loadAllStelle();
		posizioniContorno = posContornoById(IdFil);
		stelleInFil = new ArrayList<Stella>();
		for (int i = 0; i <= stelle.size() - 1; i++) {
			Stella stella = stelle.get(i);
			double sum = 0;
			for (int j = 0; j <= posizioniContorno.size() - 2; j++) {
				Poscontorno pos0 = posizioniContorno.get(j);
				Poscontorno pos1 = posizioniContorno.get(j + 1);
				double num = (pos0.getId().getLongitudine() - stella.getLongitudine())
						* (pos1.getId().getLatitudine() - stella.getLatitudine())
						- (pos0.getId().getLatitudine() - stella.getLatitudine())
								* (pos1.getId().getLongitudine() - stella.getLongitudine());
				double den = (pos0.getId().getLongitudine() - stella.getLongitudine())
						* (pos1.getId().getLongitudine() - stella.getLongitudine())
						+ (pos0.getId().getLatitudine() - stella.getLatitudine())
								* (pos1.getId().getLatitudine() - stella.getLatitudine());
				double arctan = (double) Math.atan(num / den);
				sum += arctan;
			}
			if (Math.abs(sum) >= 0.01) {
				stelleInFil.add(stella);
			}
		}
		int countProtostelle = 0;
		int countPrestelle = 0;
		int countUnbound = 0;
		for (int i = 0; i < stelle.size(); i++) {
			if (stelle.get(i).getTipologia() == "UNBOUND") {
				countUnbound++;
			} else if (stelle.get(i).getTipologia() == "PRESTELLAR") {
				countPrestelle++;
			} else {
				countProtostelle++;
			}
		}
		BeanStelleInFilamento bean = new BeanStelleInFilamento(stelleInFil, countProtostelle, countPrestelle, countUnbound);
		return bean;
	}

	public ArrayList<Stella> loadAllStelle() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("JPA");
		EntityManager em = emf.createEntityManager();
		ArrayList<Stella> stelle = new ArrayList<>();

		TypedQuery<Stella> queryShowAll = em.createQuery("SELECT s FROM Stella s", model.Stella.class);
		List<Stella> resultAll = queryShowAll.getResultList();

		for (Stella c : resultAll) {

			stelle.add(c);

		}
		return stelle;
	}

	public ArrayList<Poscontorno> posContornoById(int IdFil) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("JPA");
		EntityManager em = emf.createEntityManager();
		ArrayList<Poscontorno> pos = new ArrayList<Poscontorno>();

		TypedQuery<Poscontorno> queryShowAll = em
				.createQuery("SELECT p FROM Poscontorno p WHERE p.filamento.id = " + (IdFil), model.Poscontorno.class);
		List<Poscontorno> resultAll = queryShowAll.getResultList();

		for (Poscontorno c : resultAll) {

			pos.add(c);

		}
		return pos;
	}
}
