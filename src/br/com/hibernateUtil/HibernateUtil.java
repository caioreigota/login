package br.com.hibernateUtil;



import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.tool.hbm2ddl.SchemaExport;

import br.com.modelo.Usuario;

public class HibernateUtil 
{
	private static SessionFactory factory;
	private static AnnotationConfiguration cfg;
	
	static {
	cfg = new AnnotationConfiguration();
	cfg.addAnnotatedClass(Usuario.class);
	factory = cfg.buildSessionFactory();
	}
	
	public static Session getSession() 
	{
	return factory.openSession();
	}
	
	public static SessionFactory getSession2() 
	{
	return factory;
	}
	
	public static AnnotationConfiguration getAnnotationConfiguration(){
		return cfg;
	}
	public static void gerarBanco(){
		new SchemaExport(cfg).create(true, true);
	}
}