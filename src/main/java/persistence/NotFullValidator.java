package persistence;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


public class NotFullValidator 
						implements ConstraintValidator<NotFull, Department> {
	
	// If we need to access the database for validating an object?
	// Solution: Use java CDI and request an EntityManager to be injected.
	// We will not use it in this validator, 
	// but if we need it for a very complex validation, 
	// this would be a good approach
	@Inject private EntityManager em; 

	public void initialize(NotFull constraintAnnotation) {
    }

    public boolean isValid(
    		Department department, 
    		ConstraintValidatorContext constraintContext) {
    	
    	System.err.println("============= EM AT VALIDATOR: "+em);
    	if (department == null) return true;
        return department.getEmployees().size() <= department.getSize();
        
    }

}