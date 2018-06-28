package control;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import bean.BeanStelleInFilamento;
import model.Satellite;

public class TestMain {

	public static void main(String[] args) {
		ControllerStelle con = new ControllerStelle();
		BeanStelleInFilamento bean = con.FindStelleInFilamento(666);
		System.out.println(bean.getStelle().size());
		System.out.println("END");
	}
}
