package persistence;

import static org.junit.Assert.*;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class DepartmentTest {
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
	public void testCreateDepartmentWithEmployee() {
		final Employee emp = new Employee();
		emp.setName("emp-testCreateDepartmentWithEmployee");
		
		final Department d = new Department();
		d.setName("dept-testCreateDepartmentWithEmployee");
		
		emp.setDepartment(d);
		d.addEmployee(emp);
		
		
		TransactionUtil.doTransaction(emf, new Transaction(){

			@Override
			public void doTransation(EntityManager em) {
				em.persist(d);
				em.persist(emp);
			}
			
		});
		
		TransactionUtil.doTransaction(emf, new Transaction(){
			@Override
			public void doTransation(EntityManager em) {
				List<Employee> emps = em.createQuery("SELECT e FROM Employee e where "
						+ "e.name = 'emp-testCreateDepartmentWithEmployee'", 
						Employee.class)
				.getResultList();
				
				assertEquals(1, emps.size());
				
				assertEquals(emps.get(0).getDepartment().getName(),
						"dept-testCreateDepartmentWithEmployee");
			}
		});
	}
}
