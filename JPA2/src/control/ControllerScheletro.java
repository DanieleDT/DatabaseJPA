package control;

import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import model.Filamento;

public class ControllerScheletro {
	
	double distanza =0;
	double distMax= Integer.MIN_VALUE;
	double distMin = Integer.MAX_VALUE;
	
	public ArrayList<Double> distanzaSegmentoContorno(int idSeg){
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("JPA");
		EntityManager em = emf.createEntityManager();
		ArrayList<> fil = new ArrayList<>();
		ArrayList<> result = new ArrayList<>();
	}

}
