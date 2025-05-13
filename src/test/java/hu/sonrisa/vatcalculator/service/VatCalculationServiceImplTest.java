package hu.sonrisa.vatcalculator.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import hu.sonrisa.vatcalculator.exception.InvalidVatRateException;
import hu.sonrisa.vatcalculator.model.VatCalculationRequestDTO;
import hu.sonrisa.vatcalculator.model.VatCalculationResponseDTO;
import java.math.BigDecimal;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class VatCalculationServiceImplTest {

  @InjectMocks
  private VatCalculationServiceImpl vatCalculationService;

  @Nested
  @DisplayName("Valid VAT calculation scenarios")
  class ValidCases {

    @Test
    void shouldCalculateFromNetAmount() {
      final VatCalculationRequestDTO request = new VatCalculationRequestDTO(
          new BigDecimal("100.00"), null, null, new BigDecimal("0.20"));

      final VatCalculationResponseDTO response = vatCalculationService.calculate(request);

      assertEquals(new BigDecimal("100.00"), response.netAmount());
      assertEquals(new BigDecimal("120.00"), response.grossAmount());
      assertEquals(new BigDecimal("20.00"), response.vatAmount());
    }

    @Test
    void shouldCalculateFromGrossAmount() {
      final VatCalculationRequestDTO request = new VatCalculationRequestDTO(
          null, new BigDecimal("120.00"), null, new BigDecimal("0.20"));

      final VatCalculationResponseDTO response = vatCalculationService.calculate(request);

      assertEquals(new BigDecimal("100.00"), response.netAmount());
      assertEquals(new BigDecimal("120.00"), response.grossAmount());
      assertEquals(new BigDecimal("20.00"), response.vatAmount());
    }

    @Test
    void shouldCalculateFromVatAmount() {
      final VatCalculationRequestDTO request = new VatCalculationRequestDTO(
          null, null, new BigDecimal("20.00"), new BigDecimal("0.20"));

      final VatCalculationResponseDTO response = vatCalculationService.calculate(request);

      assertEquals(new BigDecimal("100.00"), response.netAmount());
      assertEquals(new BigDecimal("120.00"), response.grossAmount());
      assertEquals(new BigDecimal("20.00"), response.vatAmount());
    }
  }

  @Nested
  @DisplayName("Invalid VAT rate handling")
  class InvalidVatRateCases {

    @Test
    void shouldThrowExceptionForInvalidVatRate() {
      final VatCalculationRequestDTO request = new VatCalculationRequestDTO(
          new BigDecimal("100.00"), null, null, new BigDecimal("0.15"));

      assertThrows(InvalidVatRateException.class, () -> vatCalculationService.calculate(request));
    }
  }
}

