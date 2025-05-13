package hu.sonrisa.vatcalculator.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.math.BigDecimal;

/**
 * DTO representing the result of the VAT calculation. Contains all calculated values: net amount,
 * gross amount, VAT amount.
 */
public record VatCalculationResponseDTO(
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "0.00")
    BigDecimal netAmount,

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "0.00")
    BigDecimal grossAmount,

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "0.00")
    BigDecimal vatAmount

) {

}
