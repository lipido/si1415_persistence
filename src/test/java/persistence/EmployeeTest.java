package persistence;

import static org.junit.Assert.assertEquals;
import static persistence.TransactionUtil.doTransaction;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
public class EmployeeTest {

	
	private static EntityManagerFactory emf;

	@BeforeClass
	public static void setUpBeforeAllTests() {
		// create the entity manager factory
		// reads the persistence.xml and
		// creates or validates the tables if necessary
		// and creates a connection pool
		emf = Persistence.createEntityManagerFactory("si-database");
	}
	
	@AfterClass
	public static void finishAllTests() {
		// drop tables if policy "create-drop" is used
		emf.close();
	}
	

	@Test
	public void testCreateEmployee() {
		
		final Employee employee = new Employee();
		employee.setName("Employee-testCreateEmployee");
		
		doTransaction(emf, new Transaction() {
			public void doTransation(EntityManager em) {
				em.persist(employee);
			}
		});

		doTransaction(emf, new Transaction() {
			public void doTransation(EntityManager em) {
				List<Employee> employees = 
					em.createQuery(
						"SELECT e FROM Employee e WHERE e.name = "
						+ "'Employee-testCreateEmployee'",
						Employee.class).getResultList();
				assertEquals(1, employees.size());
				assertEquals("Employee-testCreateEmployee", 
						employees.get(0).getName());
			}
		});
		
	}

}
