package hu.sonrisa.vatcalculator.exception;

import jakarta.validation.ValidationException;

/**
 * Exception thrown when the VAT rate is invalid.
 */
public class InvalidVatRateException extends ValidationException {

  public InvalidVatRateException(final String message) {
    super(message);
  }
}
