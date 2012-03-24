package br.com.hibernateUtil;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class UtilHibernate {

	
	
	private static final SessionFactory sessionFactory;

	static {
	        Configuration configuration = new Configuration().configure();
	        configuration.buildMappings();

	        sessionFactory = configuration.buildSessionFactory();
	    }

	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	
	
}
