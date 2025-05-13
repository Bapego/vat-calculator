package hu.sonrisa.vatcalculator.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation for validation amount fields in the request dto.
 */
@Documented
@Constraint(validatedBy = ExactlyOneAmountFieldValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ExactlyOneAmountField {

  String message() default "Exactly one of netAmount, grossAmount, or vatAmount must be set";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
