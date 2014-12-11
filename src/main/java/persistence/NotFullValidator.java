package persistence;


import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


public class NotFullValidator implements ConstraintValidator<NotFull, Department> {
	
	@Inject private EntityManager em; //we will not use it, but if we neeed it for a very complex validation, this would be a good approach

	public void initialize(NotFull constraintAnnotation) {
    }

    public boolean isValid(Department department, ConstraintValidatorContext constraintContext) {
    	System.err.println("============= EM AT VALIDATOR: "+em);
    	if (department == null) return true;
        return department.getEmployees().size() <= department.getSize();
        
    }

}