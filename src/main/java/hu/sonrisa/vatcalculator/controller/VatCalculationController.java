package hu.sonrisa.vatcalculator.controller;

import hu.sonrisa.vatcalculator.model.VatCalculationRequestDTO;
import hu.sonrisa.vatcalculator.model.VatCalculationResponseDTO;
import hu.sonrisa.vatcalculator.service.VatCalculationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller class that handles incoming HTTP requests for VAT calculations.
 */
@RestController
@RequestMapping("/api/vat")
@RequiredArgsConstructor
public class VatCalculationController {

  private final VatCalculationService vatCalculationService;

  /**
   * Endpoint which calculates the missing VAT amounts based on the provided input. It accepts
   * exactly one amount data (net, gross or VAT) and with VAT rate it returns the missing values in
   * the response.
   *
   * @param request containing net/gross/vat amount and VAT rate
   * @return ResponseEntity containing the calculated VAT values
   */
  @PostMapping("/calculate")
  public ResponseEntity<VatCalculationResponseDTO> calculateVat(
      @RequestBody @Valid final VatCalculationRequestDTO request) {
    return ResponseEntity.ok(vatCalculationService.calculate(request));
  }
}
