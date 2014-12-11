package persistence;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;

import javax.validation.Constraint;
import javax.validation.Payload;

@Retention(RUNTIME)
@Constraint(validatedBy = NotFullValidator.class)
@Documented
public @interface NotFull {

    String message() default "department.is.full";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
    
    

}