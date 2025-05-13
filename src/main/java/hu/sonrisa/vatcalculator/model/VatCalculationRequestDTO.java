package hu.sonrisa.vatcalculator.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import hu.sonrisa.vatcalculator.validation.ExactlyOneAmountField;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * Request DTO representing the input sent by the client for VAT calculation. The client must
 * provide exactly one amount (net, gross, or VAT) and a valid Austrian VAT rate.
 */
@ExactlyOneAmountField
public record VatCalculationRequestDTO(

    @DecimalMin(value = "0.00", inclusive = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "0.00")
    BigDecimal netAmount,

    @DecimalMin(value = "0.00", inclusive = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "0.00")
    BigDecimal grossAmount,

    @DecimalMin(value = "0.00", inclusive = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "0.00")
    BigDecimal vatAmount,

    @NotNull
    @DecimalMin(value = "0.00", inclusive = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "0.00")
    BigDecimal vatRate
) {

}
