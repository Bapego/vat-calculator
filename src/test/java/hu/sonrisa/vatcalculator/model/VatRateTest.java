package hu.sonrisa.vatcalculator.model;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class VatRateTest {
  @Nested
  @DisplayName("isValid() tests")
  class IsValidTests {

    @Test
    @DisplayName("Should return true for valid VAT rates")
    void shouldReturnTrueForValidRates() {
      final List<BigDecimal> validRates = List.of(
          new BigDecimal("0.10"),
          new BigDecimal("0.13"),
          new BigDecimal("0.20")
      );

      for (final BigDecimal rate : validRates) {
        assertThat(VatRate.isValid(rate)).isTrue();
      }
    }

    @Test
    @DisplayName("Should return false for invalid VAT rates")
    void shouldReturnFalseForInvalidRates() {
      final List<BigDecimal> invalidRates = new ArrayList<>();
      invalidRates.add(new BigDecimal("0.00"));
      invalidRates.add(new BigDecimal("0.05"));
      invalidRates.add(new BigDecimal("0.19"));
      invalidRates.add(new BigDecimal("0.25"));
      invalidRates.add(null);

      for (final BigDecimal rate : invalidRates) {
        assertThat(VatRate.isValid(rate)).isFalse();
      }
    }
  }

  @Test
  @DisplayName("getAllowedVatRates() should return formatted and sorted rate list")
  void shouldReturnFormattedVatRateString() {
    final String result = VatRate.getAllowedVatRates();

    assertThat(result)
        .isEqualTo("0.10%, 0.13%, 0.20%")
        .contains("0.10%")
        .contains("0.13%")
        .contains("0.20%");
  }
}
