package persistence;

import static org.junit.Assert.assertEquals;
import static persistence.util.TransactionUtil.doTransaction;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.validation.ConstraintViolationException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import persistence.util.Transaction;

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
		
		d.addEmployee(emp);
		
		
		doTransaction(emf, new Transaction(){

			@Override
			public void doTransation(EntityManager em) {
				em.persist(d);
				em.persist(emp);
			}
			
		});
		
		doTransaction(emf, new Transaction(){
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
	
	@Test
	public void testBidirectionalPattern() {
		Department d1 = new Department();
		Department d2 = new Department();
		
		Employee mobileEmployee = new Employee();
		
		d1.addEmployee(mobileEmployee);
		
		assertEquals(d1, mobileEmployee.getDepartment());
		
		//move the employee
		mobileEmployee.setDepartment(d2);
		
		assertEquals(0, d1.getEmployees().size());
		assertEquals(1, d2.getEmployees().size());
	}
	
	@Test(expected=ConstraintViolationException.class)
	public void testValidationNameNotNull() {
		final Department d1 = new Department();
		doTransaction(emf, new Transaction() {

			@Override
			public void doTransation(EntityManager em) {
				em.persist(d1); //throws exception since the name is null
			}
			
		});
	}
	
	@Test(expected=ConstraintViolationException.class)
	public void testValidationNameTooShort() {
		final Department d1 = new Department();
		d1.setName("s"); // too short
		doTransaction(emf, new Transaction() {

			@Override
			public void doTransation(EntityManager em) {
				em.persist(d1); //throws exception since the name is null
			}
			
		});
	}
	
	@Test(expected=ConstraintViolationException.class)
	public void testValidationNotFull() {
		final Department d = new Department();
		d.setName("department-testValidationNotFull");
		d.setSize(2);
		final Employee e1 = new Employee();
		final Employee e2 = new Employee();
		final Employee e3 = new Employee();
		d.addEmployee(e1);
		d.addEmployee(e2);
		d.addEmployee(e3);
		
		doTransaction(emf, new Transaction() {

			@Override
			public void doTransation(EntityManager em) {
				em.persist(d); //throws exception since the name is null
				em.persist(e1);
				em.persist(e2);
				em.persist(e3);
			}
			
		});
	}
}
