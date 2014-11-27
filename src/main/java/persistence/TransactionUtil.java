package persistence;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

public class TransactionUtil {
	public static void doTransaction(EntityManagerFactory emf, Transaction t) {
		EntityManager em = emf.createEntityManager();
		try{
			doTransaction(em, t);
		}finally {
			em.close();
		}
	}
	
	public static void doTransaction(EntityManager em, Transaction t) {
		EntityTransaction tx = null;
		try{
			tx = em.getTransaction();
			tx.begin();
				t.doTransation(em);
			tx.commit();
		} finally {
			if (tx != null && tx.isActive()) {
				tx.rollback();
			}
		}
	}
	
}
