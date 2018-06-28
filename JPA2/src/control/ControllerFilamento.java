package control;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import bean.BeanFilamento;
import model.Poscontorno;
import model.Satellite;
import model.Scheletro;

public class ControllerFilamento {

	public BeanFilamento InformazioniFilamentoId(int id) {

		double latCentro;
		double lonCentro;
		double latEstensione;
		double lonEstensione;
		int segmenti;

		EntityManagerFactory emf = Persistence.createEntityManagerFactory("JPA");
		EntityManager em = emf.createEntityManager();
		ArrayList<Poscontorno> pos = new ArrayList<Poscontorno>();

		TypedQuery<Poscontorno> queryShowAll = em.createQuery("SELECT p FROM Poscontorno p WHERE p.filamento.id = " + (id), model.Poscontorno.class);
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
	public static void main(String[] args) {
		ControllerFilamento cont = new ControllerFilamento();
		BeanFilamento bean = cont.InformazioniFilamentoId(379);
		System.out.println(bean.getNumSeg());
	}
}
