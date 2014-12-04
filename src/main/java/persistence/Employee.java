package persistence;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Employee {

	@Id
	@GeneratedValue
	private int id;
	
	private String name;
	private double salary;
	
	@ManyToOne
	private Department department;
	
	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getSalary() {
		return salary;
	}
	public void setSalary(double salary) {
		this.salary = salary;
	}
	
	public void setDepartment(Department department) {
		if (this.department != null) {
			this.department.internalRemoveEmployee(this);
		}
		this.department = department;
		if (department != null) {
			department.internalAddEmployee(this);
		}
	}
	
	public Department getDepartment() {
		return department;
	}

	@Override
	public String toString() {
		return "Employee [id=" + id + ", name=" + name + ", salary=" + salary
				+ ", department=" + department + "]";
	}
	

}
