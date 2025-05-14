package hu.sonrisa.vatcalculator.model;

import hu.sonrisa.vatcalculator.validation.ExactlyOneAmountField;
import hu.sonrisa.vatcalculator.validation.ValidVatInput;
import jakarta.validation.constraints.NotBlank;

/**
 * Request DTO representing the input sent by the client for VAT calculation. The client must
 * provide exactly one amount (net, gross, or VAT) and a valid Austrian VAT rate.
 */
@ExactlyOneAmountField
public record VatCalculationRequestDTO(

    @ValidVatInput
    String netAmount,

    @ValidVatInput
    String grossAmount,

    @ValidVatInput
    String vatAmount,

    @NotBlank
    @ValidVatInput
    String vatRate
) {

}
