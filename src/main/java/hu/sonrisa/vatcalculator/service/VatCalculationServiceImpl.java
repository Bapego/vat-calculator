package hu.sonrisa.vatcalculator.service;

import hu.sonrisa.vatcalculator.exception.InvalidVatRateException;
import hu.sonrisa.vatcalculator.model.VatCalculationRequestDTO;
import hu.sonrisa.vatcalculator.model.VatCalculationResponseDTO;
import hu.sonrisa.vatcalculator.model.VatRate;
import java.math.BigDecimal;
import java.math.RoundingMode;
import org.springframework.stereotype.Service;

/**
 * This service class defines all implementation of operations related to VAT calculation.
 */
@Service
public class VatCalculationServiceImpl implements VatCalculationService {

  /**
   * {@inheritDoc}
   */
  public VatCalculationResponseDTO calculate(final VatCalculationRequestDTO request) {
    final BigDecimal vatRate = convertStringToBigDecimal(request.vatRate());

    if (!VatRate.isValid(vatRate)) {
      throw new InvalidVatRateException(
          "Invalid VAT rate. Supported rates are " + VatRate.getAllowedVatRates());
    }

    if (request.netAmount() != null) {

      final BigDecimal netAmount = convertStringToBigDecimal(request.netAmount());
      return calculateFromNet(netAmount, vatRate);
    } else if (request.grossAmount() != null) {

      final BigDecimal grossAmount = convertStringToBigDecimal(request.grossAmount());
      return calculateFromGross(grossAmount, vatRate);
    } else {

      final BigDecimal vatAmount = convertStringToBigDecimal(request.vatAmount());
      return calculateFromVat(vatAmount, vatRate);
    }
  }

  private VatCalculationResponseDTO calculateFromNet(final BigDecimal netAmount,
      final BigDecimal vatRate) {
    final BigDecimal vatAmount = netAmount.multiply(vatRate).setScale(2, RoundingMode.HALF_UP);
    final BigDecimal grossAmount = netAmount.add(vatAmount);
    return buildVatCalculationResponseDTO(netAmount, grossAmount, vatAmount);
  }

  private VatCalculationResponseDTO calculateFromGross(final BigDecimal grossAmount,
      final BigDecimal vatRate) {
    final BigDecimal netAmount = grossAmount.divide(BigDecimal.ONE.add(vatRate), 2,
        RoundingMode.HALF_UP);
    final BigDecimal vatAmount = grossAmount.subtract(netAmount);
    return buildVatCalculationResponseDTO(netAmount, grossAmount, vatAmount);
  }

  private VatCalculationResponseDTO calculateFromVat(final BigDecimal vatAmount,
      final BigDecimal vatRate) {
    final BigDecimal netAmount = vatAmount.divide(vatRate, 2, RoundingMode.HALF_UP);
    final BigDecimal grossAmount = netAmount.add(vatAmount);
    return buildVatCalculationResponseDTO(netAmount, grossAmount, vatAmount);
  }

  private VatCalculationResponseDTO buildVatCalculationResponseDTO(final BigDecimal netAmount,
      final BigDecimal grossAmount, final BigDecimal vatAmount) {
    return new VatCalculationResponseDTO(netAmount.setScale(2, RoundingMode.HALF_UP),
        grossAmount.setScale(2, RoundingMode.HALF_UP),
        vatAmount.setScale(2, RoundingMode.HALF_UP));
  }

  private BigDecimal convertStringToBigDecimal(final String input) {
    return new BigDecimal(input).setScale(2, RoundingMode.HALF_UP);
  }
}
