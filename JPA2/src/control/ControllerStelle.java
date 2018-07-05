package control;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import bean.BeanStelleInFilamento;
import model.Stella;
import model.Filamento;
import model.Poscontorno;

public class ControllerStelle {
	// RF9
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
		BeanStelleInFilamento bean = new BeanStelleInFilamento(stelleInFil, countProtostelle, countPrestelle,
				countUnbound);
		return bean;
	}

	// RF 10 (da ottimizzare tempi lunghi)
	public ArrayList<BeanStelleInFilamento> StelleInFilRettangolo(double latCentro, double lonCentro, double base, double altezza) {
		//prima stelle nei filamenti, poi non;
		ArrayList<Stella> stelle = LoadStelleInRettangolo(latCentro, lonCentro, base, altezza);
		ArrayList<Stella> stelleInFil = new ArrayList<>();
		ArrayList<Stella> stelleNotInFil = new ArrayList<>();
		ArrayList<Filamento> filamentiInRettangolo = ControllerFilamento.ricercaFilementiRettangolo(latCentro, lonCentro, base, altezza);
		for (int i = 0; i <= filamentiInRettangolo.size() - 1; i++) {
			List<Poscontorno>contorno = filamentiInRettangolo.get(i).getPoscontornos();
			for (int j = 0; j <= stelle.size() - 1; j++) {
				int sum = 0;
				for (int k = 0; k <= contorno.size() - 2; k++) {
					Poscontorno pos0 = contorno.get(k);
					Poscontorno pos1 = contorno.get(k + 1);
					double num = (pos0.getId().getLongitudine() - (stelle.get(j)).getLongitudine())
							* (pos1.getId().getLatitudine() - stelle.get(j).getLatitudine())
							- (pos0.getId().getLatitudine() - stelle.get(j).getLatitudine())
									* (pos1.getId().getLongitudine() - stelle.get(j).getLongitudine());
					double den = (pos0.getId().getLongitudine() - (stelle.get(j)).getLongitudine())
							* (pos1.getId().getLongitudine() - stelle.get(j).getLongitudine())
							+ (pos0.getId().getLatitudine() - stelle.get(j).getLatitudine())
									* (pos1.getId().getLatitudine() - stelle.get(j).getLatitudine());
					double arctan = (double) Math.atan(num / den);
					sum += arctan;
				}
				if (Math.abs(sum) >= 0.01) {
					// aggiungo la j stella nell'array delle stelle comprese nei filamenti
					stelleInFil.add(stelle.get(j));
				} else {
					stelleNotInFil.add(stelle.get(j));
				}
			}
		}
		int countProtostelle = 0;
		int countPrestelle = 0;
		int countUnbound = 0;
		for (int i = 0; i < stelleInFil.size(); i++) {
			if (stelle.get(i).getTipologia() == "UNBOUND") {
				countUnbound++;
			} else if (stelle.get(i).getTipologia() == "PRESTELLAR") {
				countPrestelle++;
			} else {
				countProtostelle++;
			}
		}
		BeanStelleInFilamento beanStelleInFil = new BeanStelleInFilamento(stelleInFil, countProtostelle, countPrestelle,
				countUnbound);
		
		int countProtostelle1 = 0;
		int countPrestelle1 = 0;
		int countUnbound1 = 0;
		for (int i = 0; i < stelleNotInFil.size(); i++) {
			if (stelle.get(i).getTipologia() == "UNBOUND") {
				countUnbound++;
			} else if (stelle.get(i).getTipologia() == "PRESTELLAR") {
				countPrestelle++;
			} else {
				countProtostelle++;
			}
		}
		BeanStelleInFilamento beanStelleNotInFil = new BeanStelleInFilamento(stelleNotInFil, countProtostelle1, countPrestelle1,
				countUnbound1);
		ArrayList<BeanStelleInFilamento> result = new ArrayList<>();
		result.add(0,beanStelleInFil);
		result.add(1,beanStelleNotInFil);
		return result;
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
		em.close();
		emf.close();
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
		em.close();
		emf.close();
		return pos;
	}

	public ArrayList<Stella> LoadStelleInRettangolo(double latCentro, double lonCentro, double base, double altezza) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("JPA");
		EntityManager em = emf.createEntityManager();
		ArrayList<Stella> stelle = new ArrayList<Stella>();
		ArrayList<Stella> result = new ArrayList<Stella>();
		double semibase = base / 2;
		double semialtezza = altezza / 2;

		TypedQuery<Stella> queryShowAll = em.createQuery("SELECT s FROM Stella s ", model.Stella.class);
		List<Stella> resultAll = queryShowAll.getResultList();

		for (Stella c : resultAll) {

			stelle.add(c);

		}
		for (int i = 0; i < stelle.size(); i++) {
			Stella stella = stelle.get(i);
			if (stella.getLongitudine() <= (lonCentro + semibase) 
					&& stella.getLongitudine() >= (lonCentro - semibase)
					&& stella.getLatitudine() >= (latCentro - semialtezza)
					&& stella.getLatitudine() <= (latCentro + semialtezza)) {
				result.add(stella);
			}
		}
		em.close();
		emf.close();
		return result;
	}
}
