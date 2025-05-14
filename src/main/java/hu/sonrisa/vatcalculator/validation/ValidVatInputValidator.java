package hu.sonrisa.vatcalculator.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * Validator that ensures the value is a valid number and greater than zero.
 */
public class ValidVatInputValidator implements ConstraintValidator<ValidVatInput, String> {

  /**
   * Validates the input string.
   *
   * @param value object to validate
   * @param context context in which the constraint is evaluated
   *
   * @return {@code true} if the value is {@code null} or a valid number greater than zero;
   *         {@code false} otherwise
   */
  @Override
  public boolean isValid(final String value, final ConstraintValidatorContext context) {
    if(Objects.isNull(value)) {
      return true;
    }

    try {
      return new BigDecimal(value).compareTo(BigDecimal.ZERO) == 1;
    } catch (final NumberFormatException ex) {
      return false;
    }
  }
}
