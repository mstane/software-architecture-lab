package org.sm.lab.mybooks.server;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EmfService {
	private static EntityManagerFactory emfInstance = null;

	private EmfService() {
	}

	public static EntityManagerFactory getFactory() {
		if (emfInstance == null) emfInstance = Persistence.createEntityManagerFactory(AppProps.getDbPersistenceUnit());
		return emfInstance;
	}
	
	/**
	 * For testing purpose
	 * 
	 * @param factory
	 */
	public static void setFactory(EntityManagerFactory factory) {
		emfInstance = factory;
	}

}