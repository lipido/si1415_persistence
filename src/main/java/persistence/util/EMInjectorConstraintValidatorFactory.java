package persistence.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorFactory;

/**
 * This class is a simple ConstraintValidationFactory that injects an 
 * {@code EntityManager} when crating {@code ConstraintValidator} objects 
 * (see validation.xml). Those objects should have a field of type 
 * {@code EntityManager} annotated with @Inject (CDI, see JSR 346).
 * This class is intended for environments where the CDI provider is not 
 * available (such as testing) This class works with a set of Thread local 
 * entity managers that should be provided manually just before 
 * you create the EntityManager
 * 
 * @author lipido
 *
 */
public class EMInjectorConstraintValidatorFactory implements ConstraintValidatorFactory {

	private static ThreadLocal<EntityManager> entityManagers = 
			new ThreadLocal<EntityManager>();

	public static void setThreadLocalEntityManager(EntityManager em){
		entityManagers.set(em);
	}
		
	public <T extends ConstraintValidator<?, ?>> T 
		getInstance(Class<T> constraintValidatorClass) {
		
		try {
			T validator = constraintValidatorClass.newInstance();
			
			for (Field f : constraintValidatorClass.getDeclaredFields()){
				
				for (Annotation a : f.getAnnotations()){
					if (a.annotationType().equals(Inject.class) && 
							EntityManager.class.isAssignableFrom(f.getType())){
						
						//force access to private or package-private fields
						Field.setAccessible(new AccessibleObject[]{f}, true);
						
						f.set(validator, entityManagers.get());
					}
				}
			}
			return validator;
			
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void releaseInstance(ConstraintValidator<?, ?> arg0) { }
}
