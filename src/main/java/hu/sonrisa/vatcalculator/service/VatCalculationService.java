package hu.sonrisa.vatcalculator.service;

import hu.sonrisa.vatcalculator.model.VatCalculationRequestDTO;
import hu.sonrisa.vatcalculator.model.VatCalculationResponseDTO;

/**
 * This interface defines all operations related to VAT calculation.
 */
public interface VatCalculationService {

  /**
   * Calculate the missing values (net amount, gross amount, and VAT) based on the provided input.
   *
   * @param request the request containing the input data (one of the amounts and VAT rate)
   * @return a response containing the calculated values (net, gross, and VAT)
   */
  VatCalculationResponseDTO calculate(final VatCalculationRequestDTO request);

}
