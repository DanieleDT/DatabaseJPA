package csv;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import model.Filamento;
import model.Poscontorno;
import model.PoscontornoPK;
import model.Posscheletro;
import model.PosscheletroPK;
import model.Satellite;
import model.Scheletro;
import model.Stella;
import model.Strumento;
import model.StrumentoPK;

public class CsvManager {
	String path;

	public CsvManager(String path) {
		this.path = path;
	}

	public boolean uploadFile(int type) {
		/*
		 * tipo di file 0 file contorni filamento 1 file filamenti 2 file scheletro
		 * filamenti 3 file stelle
		 */
		BufferedReader bufferedReader = null;
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("JPA");
		EntityManager em = emf.createEntityManager();

		if (type > 3 || type < 0)
			return false;
		try {
			bufferedReader = new BufferedReader(new FileReader(this.path));
			bufferedReader.readLine(); // salta header
		} catch (Exception e) {
			e.printStackTrace();
		}
		switch (type) {
		case 0:
			insertContorni(bufferedReader, em);
		case 1:
			insertFilamenti(bufferedReader, em);
		case 2:
			insertScheletro(bufferedReader, em);
		case 3:
			insertStelle(bufferedReader, em);
		}

		return true;
	}

	private void insertContorni(BufferedReader bufRead, EntityManager em) {
		String line;
		try {
			while ((line = bufRead.readLine()) != null) {
				String[] linePart = line.split(",");
				int ID = Integer.parseInt(linePart[0].trim());
				double lon = Double.parseDouble(linePart[1].trim());
				double lat = Double.parseDouble(linePart[2].trim());
				Filamento filamento = em.find(Filamento.class, ID);
				PoscontornoPK pk = new PoscontornoPK();
				pk.setIdfil(ID);
				pk.setLatitudine(lat);
				pk.setLongitudine(lon);
				Poscontorno pos = em.find(Poscontorno.class, pk);
				if (filamento != null && pos == null) {
					Poscontorno posizione = new Poscontorno();
					posizione.setFilamento(filamento);
					posizione.setId(pk);
					em.getTransaction().begin();
					em.persist(posizione);

					em.getTransaction().commit();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void insertStelle(BufferedReader bufRead, EntityManager em) {
		String line;
		try {
			while ((line = bufRead.readLine()) != null) {
				String[] linePart = line.split(",");
				int ID = Integer.parseInt(linePart[0].trim());
				String nome = linePart[1].trim();
				double lon = Double.parseDouble(linePart[2].trim());
				double lat = Double.parseDouble(linePart[3].trim());
				double flusso = Double.parseDouble(linePart[4].trim());
				String tipologia = linePart[5].trim();
				Stella stella = em.find(Stella.class, ID);
				if (stella == null) {
					Stella star = new Stella();
					star.setId(ID);
					star.setNome(nome);
					star.setLongitudine(lon);
					star.setLatitudine(lat);
					star.setValoreflusso(flusso);
					star.setTipologia(tipologia);
					em.getTransaction().begin();
					em.persist(stella);

					em.getTransaction().commit();
				} else {
					// esiste-->aggiorno
					em.getTransaction().begin();
					stella.setId(ID);
					stella.setNome(nome);
					stella.setLongitudine(lon);
					stella.setLatitudine(lat);
					stella.setValoreflusso(flusso);
					stella.setTipologia(tipologia);
					em.getTransaction().commit();
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void insertFilamenti(BufferedReader bufRead, EntityManager em) {
		String line;
		try {
			while ((line = bufRead.readLine()) != null) {
				String[] linePart = line.split(",");
				int ID = Integer.parseInt(linePart[0].trim());
				String nome = linePart[1].trim();
				double flusso = Double.parseDouble(linePart[2].trim());
				double densita = Double.parseDouble(linePart[3].trim());
				double temperatura = Double.parseDouble(linePart[4].trim());
				double ellitticita = Double.parseDouble(linePart[5].trim());
				double contrasto = Double.parseDouble(linePart[6].trim());
				String satellite = linePart[7].trim();
				String strumento = linePart[8].trim();
				Filamento filamento = em.find(Filamento.class, ID);
				if (filamento == null) {
					Filamento fil = new Filamento();
					fil.setId(ID);
					fil.setNome(nome);
					fil.setFlussototale(flusso);
					fil.setDensitamedia(densita);
					fil.setTempmedia(temperatura);
					fil.setEllitticita(ellitticita);
					fil.setContrasto(contrasto);
					Satellite sat = em.find(Satellite.class, satellite);
					fil.setSatellite(sat);
					em.getTransaction().begin();
					em.persist(fil);

					em.getTransaction().commit();
				} else {
					// esiste-->aggiorno
					em.getTransaction().begin();
					filamento.setId(ID);
					filamento.setNome(nome);
					filamento.setFlussototale(flusso);
					filamento.setDensitamedia(densita);
					filamento.setTempmedia(temperatura);
					filamento.setEllitticita(ellitticita);
					filamento.setContrasto(contrasto);
					Satellite sat = em.find(Satellite.class, satellite);
					filamento.setSatellite(sat);
					em.getTransaction().commit();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void insertScheletro(BufferedReader bufRead, EntityManager em) {
		String line;
		try {
			while((line = bufRead.readLine())!= null) {
				String[] linePart = line.split(",");
				int idFil = Integer.parseInt(linePart[0].trim());
				int id = Integer.parseInt(linePart[1].trim());
				String tipo = linePart[2].trim();
				Boolean tipologia = false;
				if(tipo.equals("S")) {
					tipologia = true;
				}
				double lon = Double.parseDouble(linePart[3].trim());
				double lat = Double.parseDouble(linePart[4].trim());
				int numProg = Integer.parseInt(linePart[5].trim());
				double flusso = Double.parseDouble(linePart[6].trim());
				Filamento filamento = em.find(Filamento.class, idFil);
				if(filamento == null) {
					//aggiungo lo scheletro (se non esiste)
					Scheletro scheletro = em.find(Scheletro.class, id);
					if(scheletro == null){
						scheletro = new Scheletro();
						scheletro.setId(id);
						scheletro.setTipo(tipologia);
						scheletro.setFilamento(filamento);
						
						em.getTransaction().begin();
						em.persist(scheletro);
						em.getTransaction().commit();
					}else {
						em.getTransaction().begin();
						scheletro.setId(id);
						scheletro.setTipo(tipologia);
						scheletro.setFilamento(filamento);
					}
					//aggiungo il punto dello scheletro (se non esiste)
					PosscheletroPK pk = new PosscheletroPK();
					pk.setLatitudine(lat);
					pk.setLongitudine(lon);
					Posscheletro pos = em.find(Posscheletro.class, pk);
					if(pos == null) {
						pos = new Posscheletro();
						pos.setId(pk);
						pos.setScheletro(scheletro);
						pos.setNumeroprogressivo(numProg);
						pos.setFlusso(flusso);
						em.getTransaction().begin();
						em.persist(pos);
						em.getTransaction().commit();
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
