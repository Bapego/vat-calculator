package hu.sonrisa.vatcalculator.validation;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import hu.sonrisa.vatcalculator.model.VatCalculationRequestDTO;
import jakarta.validation.ConstraintValidatorContext;
import java.math.BigDecimal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ExactlyOneAmountFieldValidatorTest {

  @InjectMocks
  private ExactlyOneAmountFieldValidator validator;

  @Mock
  private ConstraintValidatorContext context;

  @BeforeEach
  void setUp() {
    validator = new ExactlyOneAmountFieldValidator();
  }

  @Nested
  @DisplayName("Valid cases")
  class ValidCases {

    @Test
    @DisplayName("Only netAmount is set")
    void validWhenOnlyNetAmountSet() {
      final VatCalculationRequestDTO dto = new VatCalculationRequestDTO(
          BigDecimal.valueOf(100), null, null, BigDecimal.valueOf(0.2));
      assertTrue(validator.isValid(dto, context));
    }

    @Test
    @DisplayName("Only grossAmount is set")
    void validWhenOnlyGrossAmountSet() {
      final VatCalculationRequestDTO dto = new VatCalculationRequestDTO(
          null, BigDecimal.valueOf(120), null, BigDecimal.valueOf(0.2));
      assertTrue(validator.isValid(dto, context));
    }

    @Test
    @DisplayName("Only vatAmount is set")
    void validWhenOnlyVatAmountSet() {
      final VatCalculationRequestDTO dto = new VatCalculationRequestDTO(
          null, null, BigDecimal.valueOf(20), BigDecimal.valueOf(0.2));
      assertTrue(validator.isValid(dto, context));
    }
  }

  @Nested
  @DisplayName("Invalid cases")
  class InvalidCases {

    @Test
    @DisplayName("All amount fields are null")
    void invalidWhenNoAmountSet() {
      final VatCalculationRequestDTO dto = new VatCalculationRequestDTO(
          null, null, null, BigDecimal.valueOf(0.2));
      assertFalse(validator.isValid(dto, context));
    }

    @Test
    @DisplayName("Two amount fields are set")
    void invalidWhenTwoFieldsSet() {
      final VatCalculationRequestDTO dto = new VatCalculationRequestDTO(
          BigDecimal.valueOf(100), BigDecimal.valueOf(120), null, BigDecimal.valueOf(0.2));
      assertFalse(validator.isValid(dto, context));
    }

    @Test
    @DisplayName("All three amount fields are set")
    void invalidWhenAllFieldsSet() {
      final VatCalculationRequestDTO dto = new VatCalculationRequestDTO(
          BigDecimal.valueOf(100), BigDecimal.valueOf(120), BigDecimal.valueOf(20), BigDecimal.valueOf(0.2));
      assertFalse(validator.isValid(dto, context));
    }
  }
}
