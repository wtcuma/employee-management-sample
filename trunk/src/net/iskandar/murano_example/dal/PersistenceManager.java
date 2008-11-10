package net.iskandar.murano_example.dal;

public interface PersistenceManager {

	public static final String COMPONENT_NAME = "persistenceManager";
	
	void beginTrasaction();
	void commitTrasaction();
	void closeSession();
}
