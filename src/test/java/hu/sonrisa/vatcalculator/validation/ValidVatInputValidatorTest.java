package hu.sonrisa.vatcalculator.validation;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ValidVatInputValidatorTest {

  @InjectMocks
  private ValidVatInputValidator validator;

  @Mock
  private ConstraintValidatorContext context;

  @BeforeEach
  void setUp() {
    validator = new ValidVatInputValidator();
  }


  @Nested
  @DisplayName("Valid cases")
  class ValidCases {

    @Test
    @DisplayName("Should accept null value")
    void shouldAcceptNull() {
      assertTrue(validator.isValid(null, context));
    }

    @Test
    @DisplayName("Should accept valid number greater than zero")
    void shouldAcceptPositiveNumber() {
      assertTrue(validator.isValid("123.45", context));
    }

    @Test
    @DisplayName("Should accept small positive number")
    void shouldAcceptSmallPositiveNumber() {
      assertTrue(validator.isValid("0.01", context));
    }
  }

  @Nested
  @DisplayName("Invalid cases")
  class InvalidCases {

    @Test
    @DisplayName("Should reject zero")
    void shouldRejectZero() {
      assertFalse(validator.isValid("0", context));
    }

    @Test
    @DisplayName("Should reject negative number")
    void shouldRejectNegativeNumber() {
      assertFalse(validator.isValid("-1", context));
    }

    @Test
    @DisplayName("Should reject non-numeric string")
    void shouldRejectNonNumeric() {
      assertFalse(validator.isValid("abc", context));
    }

    @Test
    @DisplayName("Should reject mixed alphanumeric string")
    void shouldRejectAlphaNumeric() {
      assertFalse(validator.isValid("12.3abc", context));
    }

    @Test
    @DisplayName("Should reject empty string")
    void shouldRejectEmptyString() {
      assertFalse(validator.isValid("", context));
    }

    @Test
    @DisplayName("Should reject string with only spaces")
    void shouldRejectWhitespaceOnly() {
      assertFalse(validator.isValid("   ", context));
    }
  }

}
