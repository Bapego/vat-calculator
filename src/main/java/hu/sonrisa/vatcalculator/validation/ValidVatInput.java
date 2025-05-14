package hu.sonrisa.vatcalculator.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation for validation fields in the request dto.
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidVatInputValidator.class)
public @interface ValidVatInput {
  String message() default "The value must be a number greater than zero.";
  Class<?>[] groups() default {};
  Class<? extends Payload>[] payload() default {};
}
