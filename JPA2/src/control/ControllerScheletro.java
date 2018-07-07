package control;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import model.Filamento;
import model.Poscontorno;
import model.Posscheletro;
import model.Scheletro;

public class ControllerScheletro {
	//RF 11;
	public ArrayList<Double> distanzaSegmentoContorno(int idSeg) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("JPA");
		EntityManager em = emf.createEntityManager();
		TypedQuery<Scheletro> queryShowAll = em.createQuery("SELECT p FROM scheletro p WHERE p.id = " + (idSeg),
				model.Scheletro.class);
		Scheletro segmento = queryShowAll.getSingleResult();
		double distMin = Double.MAX_VALUE;
		double distMax = Double.MAX_VALUE;
		int minProg = Integer.MAX_VALUE, maxProg = Integer.MIN_VALUE;
		Posscheletro maxPos, minPos;
		List<Posscheletro> posScheletro = segmento.getPosscheletros();
		maxPos = posScheletro.get(0);
		minPos = posScheletro.get(0);
		for (int i = 1; i < posScheletro.size(); i++) {
			// ricerca posScheletro con numProg max e min
			if (posScheletro.get(i).getNumeroprogressivo() > maxProg) {
				maxProg = posScheletro.get(i).getNumeroprogressivo();
				maxPos = posScheletro.get(i);
			}
			if (posScheletro.get(i).getNumeroprogressivo() < minProg) {
				minProg = posScheletro.get(i).getNumeroprogressivo();
				minPos = posScheletro.get(i);
			}

		}
		List<Poscontorno> contorno = segmento.getFilamento().getPoscontornos();
		for (int i = 0; i < contorno.size(); i++) {
			// calcolo la distanza del punto di massimo
			double distanza = Math.sqrt(Math.pow((maxPos.getId().getLatitudine() - contorno.get(i).getId().getLatitudine()), 2.0)
					+ Math.pow((maxPos.getId().getLongitudine() - contorno.get(i).getId().getLongitudine()), 2.0));
			
			if(distanza < distMax) {
				distMax = distanza;
			}
			//calcolo la distanza del punto di minimo
			distanza = Math.sqrt(Math.pow((minPos.getId().getLatitudine() - contorno.get(i).getId().getLatitudine()), 2.0)
					+ Math.pow((minPos.getId().getLongitudine() - contorno.get(i).getId().getLongitudine()), 2.0));
			if(distanza < distMin) {
				distMin = distanza;
			}
			
		}
		ArrayList<Double> distanze = new ArrayList<Double>();
		distanze.add(distMin);
		distanze.add(distMax);
		return distanze;
	}
	
	public Boolean existSegmento(int id) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("JPA");
		EntityManager em = emf.createEntityManager();
		Scheletro scheletro = em.find(Scheletro.class, id);
		if (scheletro != null) {
			return true;
		}
		return false;

	}
	
	

}
