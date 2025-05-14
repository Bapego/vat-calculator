package hu.sonrisa.vatcalculator.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.experimental.UtilityClass;

/**
 * Helper class for handling valid Austrian VAT rates.
 */
@UtilityClass
public class VatRate {

  private static final Set<BigDecimal> VALID_VAT_RATES = Set.of(
      new BigDecimal("0.10"),
      new BigDecimal("0.13"),
      new BigDecimal("0.20")
  );

  /**
   * Checks whether the given VAT rate is valid in Austria.
   *
   * @param rate the VAT rate to check
   * @return true if the rate is valid; false otherwise
   */
  public static boolean isValid(final BigDecimal rate) {
    return rate != null && VALID_VAT_RATES.contains(rate.setScale(2, RoundingMode.HALF_UP));
  }

  /**
   * Returns a comma-separated string of all allowed VAT rates.
   *
   * @return a formatted string of valid VAT rates (e.g., "0.05, 0.1, 0.2")
   */
  public static String getAllowedVatRates() {
    return VALID_VAT_RATES.stream()
        .map(BigDecimal::toPlainString)
        .sorted()
        .collect(Collectors.joining(", "));
  }
}
