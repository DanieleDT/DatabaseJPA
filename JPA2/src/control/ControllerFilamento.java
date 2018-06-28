package control;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import bean.BeanFilamentiConEll;
import bean.BeanFilamento;
import model.Filamento;
import model.Poscontorno;
import model.Satellite;
import model.Scheletro;

public class ControllerFilamento {
	// RF5 Id
	public BeanFilamento InformazioniFilamentoId(int id) {

		double latCentro;
		double lonCentro;
		double latEstensione;
		double lonEstensione;
		int segmenti;

		EntityManagerFactory emf = Persistence.createEntityManagerFactory("JPA");
		EntityManager em = emf.createEntityManager();
		ArrayList<Poscontorno> pos = new ArrayList<Poscontorno>();

		TypedQuery<Poscontorno> queryShowAll = em
				.createQuery("SELECT p FROM Poscontorno p WHERE p.filamento.id = " + (id), model.Poscontorno.class);
		List<Poscontorno> resultAll = queryShowAll.getResultList();

		for (Poscontorno c : resultAll) {

			pos.add(c);

		}
		ArrayList<Double> estensione = estensioneFilamento(pos);
		latEstensione = estensione.get(0);
		lonEstensione = estensione.get(1);

		latCentro = avgLat(pos);
		lonCentro = avgLon(pos);

		TypedQuery<Scheletro> querySeg = em.createQuery("SELECT s FROM Scheletro s WHERE s.filamento.id = " + (id),
				model.Scheletro.class);
		List<Scheletro> result = querySeg.getResultList();
		segmenti = result.size();

		BeanFilamento bean = new BeanFilamento(segmenti, latCentro, lonCentro, latEstensione, lonEstensione);
		return bean;

	}

	// RF 5 Designazione
	public BeanFilamento InformazioniFilamentoDesignazione(String desig) {
		String designazione = "'" + desig + "'";
		double latCentro;
		double lonCentro;
		double latEstensione;
		double lonEstensione;
		int segmenti;

		EntityManagerFactory emf = Persistence.createEntityManagerFactory("JPA");
		EntityManager em = emf.createEntityManager();
		ArrayList<Poscontorno> pos = new ArrayList<Poscontorno>();

		TypedQuery<Poscontorno> queryShowAll = em.createQuery(
				"SELECT p FROM Poscontorno p WHERE p.filamento.nome = " + (designazione), model.Poscontorno.class);
		List<Poscontorno> resultAll = queryShowAll.getResultList();

		for (Poscontorno c : resultAll) {

			pos.add(c);

		}
		ArrayList<Double> estensione = estensioneFilamento(pos);
		latEstensione = estensione.get(0);
		lonEstensione = estensione.get(1);

		latCentro = avgLat(pos);
		lonCentro = avgLon(pos);

		TypedQuery<Scheletro> querySeg = em.createQuery(
				"SELECT s FROM Scheletro s WHERE s.filamento.nome = " + (designazione), model.Scheletro.class);
		List<Scheletro> result = querySeg.getResultList();
		segmenti = result.size();

		BeanFilamento bean = new BeanFilamento(segmenti, latCentro, lonCentro, latEstensione, lonEstensione);
		return bean;

	}

	// RF 6
	public BeanFilamentiConEll RicercaContEll(int brillanza, double ellMin, double ellMax) {

		EntityManagerFactory emf = Persistence.createEntityManagerFactory("JPA");
		EntityManager em = emf.createEntityManager();
		ArrayList<Filamento> fil = new ArrayList<Filamento>();

		TypedQuery<Filamento> queryFilamenti = em
				.createQuery(
						"SELECT p FROM Filamento p WHERE p.contrasto > " + (1 + ((double) brillanza) / 100)
								+ " AND p.ellitticita < " + ellMax + " AND  p.ellitticita > " + ellMin,
						model.Filamento.class);
		List<Filamento> resultAll = queryFilamenti.getResultList();

		for (Filamento c : resultAll) {

			fil.add(c);

		}
		List<Filamento> totale = em.createNamedQuery("Filamento.findAll").getResultList();

		BeanFilamentiConEll bean = new BeanFilamentiConEll(fil, totale.size(), fil.size());
		return bean;

	}

	// RF 7
	public ArrayList<Filamento> ricercaFilamentiNumSeg(int min, int max) {

		EntityManagerFactory emf = Persistence.createEntityManagerFactory("JPA");
		EntityManager em = emf.createEntityManager();
		ArrayList<Filamento> fil = new ArrayList<Filamento>();
		ArrayList<Filamento> result = new ArrayList<Filamento>();
		List<Filamento> totale = em.createNamedQuery("Filamento.findAll").getResultList();
		for (Filamento c : totale) {

			fil.add(c);
		}
		if (min == -1) {
			for (int i = 0; i < fil.size(); i++) {
				Filamento f = fil.get(i);
				if ((f.getScheletros().size() < max)) {
					result.add(f);
				}
			}
		} else if (max == -1) {
			for (int i = 0; i < fil.size(); i++) {
				Filamento f = fil.get(i);
				if ((f.getScheletros().size() > min)) {
					result.add(f);
				}
			}
		} else {
			for (int i = 0; i < fil.size(); i++) {
				Filamento f = fil.get(i);
				if ((f.getScheletros().size() < max) && (f.getScheletros().size() > min)) {
					result.add(f);
				}
			}
		}
		return result;
	}

	// RF 8 Quadrato
	public ArrayList<Filamento> ricercaFilementiQuadrato(double latCentro, double lonCentro, double lato) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("JPA");
		EntityManager em = emf.createEntityManager();
		ArrayList<Filamento> fil = new ArrayList<Filamento>();
		ArrayList<Filamento> result = new ArrayList<Filamento>();
		double semilato = lato / 2;
		List<Filamento> totale = em.createNamedQuery("Filamento.findAll").getResultList();
		for (Filamento c : totale) {

			fil.add(c);
		}
		for (int i = 0; i < fil.size(); i++) {
			Filamento f = fil.get(i);
			List<Poscontorno> contorno = f.getPoscontornos();
			Boolean breaked = false;
			Boolean inside = false;
			for (int j = 0; j < contorno.size(); j++) {
				double latitudine = contorno.get(j).getId().getLatitudine();
				double longitudine = contorno.get(j).getId().getLongitudine();
				if (!(latitudine < (latCentro + semilato) && latitudine > (latCentro - semilato)
						&& longitudine < (lonCentro + semilato) && longitudine > (lonCentro - semilato))) {
					breaked = true;
					break;
				}
				inside = true;
			}
			if (!breaked) {
				if (inside) {
					result.add(f);
				}
			}
			inside = false;
			breaked = false;
		}
		return result;
	}

	// RF 8 Cerchio
	public ArrayList<Filamento> ricercaFilementiCerchio(double latCentro, double lonCentro, double raggio) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("JPA");
		EntityManager em = emf.createEntityManager();
		ArrayList<Filamento> fil = new ArrayList<Filamento>();
		ArrayList<Filamento> result = new ArrayList<Filamento>();
		List<Filamento> totale = em.createNamedQuery("Filamento.findAll").getResultList();
		for (Filamento c : totale) {

			fil.add(c);
		}
		for (int i = 0; i < fil.size(); i++) {
			Filamento f = fil.get(i);
			List<Poscontorno> contorno = f.getPoscontornos();
			Boolean breaked = false;
			Boolean inside = false;
			for (int j = 0; j < contorno.size(); j++) {
				double latitudine = contorno.get(j).getId().getLatitudine();
				double longitudine = contorno.get(j).getId().getLongitudine();
				if (!(Math.sqrt(Math.abs(Math.pow((latitudine - latCentro), 2)
						+ Math.abs(Math.pow((longitudine - lonCentro), 2)))) <= raggio)) {
					breaked = true;
					break;
				}
				inside = true;
			}
			if (!breaked) {
				if (inside) {
					result.add(f);
				}
			}
			inside = false;
			breaked = false;
		}
		return result;
	}

	public double avgLat(ArrayList<Poscontorno> pos) {
		double sum = 0;
		for (Poscontorno p : pos) {
			sum += p.getId().getLatitudine();
		}
		return (sum / (pos.size()));
	}

	public double avgLon(ArrayList<Poscontorno> pos) {
		double sum = 0;
		for (Poscontorno p : pos) {
			sum += p.getId().getLongitudine();
		}
		return (sum / (pos.size()));
	}

	public ArrayList<Double> estensioneFilamento(ArrayList<Poscontorno> pos) {
		// prima latitudine poi longitudine
		double minLat = Double.MAX_VALUE;
		double maxLat = Double.MIN_VALUE;
		double minLon = Double.MAX_VALUE;
		double maxLon = Double.MIN_VALUE;
		ArrayList<Double> result = new ArrayList<>();
		for (Poscontorno p : pos) {
			double lat = p.getId().getLatitudine();
			double lon = p.getId().getLongitudine();
			if (lat < minLat) {
				minLat = lat;
			} else if (lat > maxLat) {
				maxLat = lat;
			}
			if (lon < minLon) {
				minLon = lon;
			} else if (lon > maxLon) {
				maxLon = lon;
			}
		}
		result.add((maxLat - minLat));
		result.add(1, (maxLon - minLon));
		return result;
	}
}
