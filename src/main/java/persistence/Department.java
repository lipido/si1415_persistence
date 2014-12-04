package persistence;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Department {
	@Id
	@GeneratedValue
	private int id;
	private String name;
	
	@OneToMany(mappedBy="department")
	private List<Employee> employees = new LinkedList<Employee>();
	
	
	public Department() { }
	
	public Department(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void addEmployee(Employee emp) {
		this.employees.add(emp);
	}
	
}
