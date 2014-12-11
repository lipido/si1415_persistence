package persistence;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@NotFull
@Entity
public class Department {
	@Id
	@GeneratedValue
	private int id;
	
	private int size = 3;
	
	@NotNull
	@Size(min=2, max=240)
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
	
	public int getSize() {
		return size;
	}
	
	public void setSize(int size) {
		this.size = size;
	}
	
	public void addEmployee(Employee emp) {
		emp.setDepartment(this);
	}
	
	public void removeEmployee(Employee emp) {
		emp.setDepartment(null);
	}
	
	public List<Employee> getEmployees() {
		return Collections.unmodifiableList(employees);
	}
	
	//See http://blog.xebia.com/2009/03/16/jpa-implementation-patterns-
	//bidirectional-assocations/
	void internalAddEmployee(Employee e) {
		this.employees.add(e);
	}
	
	void internalRemoveEmployee(Employee e) {
		this.employees.remove(e);
	}
	
}
