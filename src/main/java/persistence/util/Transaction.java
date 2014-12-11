package persistence.util;

import javax.persistence.EntityManager;

public interface Transaction {
	public void doTransation(EntityManager em);
}
