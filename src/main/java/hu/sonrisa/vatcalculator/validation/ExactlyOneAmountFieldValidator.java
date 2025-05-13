package hu.sonrisa.vatcalculator.validation;

import hu.sonrisa.vatcalculator.model.VatCalculationRequestDTO;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Objects;

/**
 * Validator that ensures exactly one of the amount fields (netAmount, grossAmount, vatAmount) is
 * provided (i.e., not null).
 */
public class ExactlyOneAmountFieldValidator implements
    ConstraintValidator<ExactlyOneAmountField, VatCalculationRequestDTO> {

  /**
   * Validates that exactly one of netAmount, grossAmount, or vatAmount is set.
   *
   * @param dto     the incoming VAT calculation request DTO
   * @param context the constraint context
   * @return true if exactly one field is provided, false otherwise
   */
  @Override
  public boolean isValid(final VatCalculationRequestDTO dto,
      final ConstraintValidatorContext context) {
    int filledCount = 0;

    if (!Objects.isNull(dto.netAmount())) {
      filledCount++;
    }
    if (!Objects.isNull(dto.grossAmount())) {
      filledCount++;
    }
    if (!Objects.isNull(dto.vatAmount())) {
      filledCount++;
    }

    return filledCount == 1;
  }
}
