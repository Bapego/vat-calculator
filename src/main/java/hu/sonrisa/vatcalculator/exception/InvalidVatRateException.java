package hu.sonrisa.vatcalculator.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown when the VAT rate is invalid.
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidVatRateException extends RuntimeException {

  public InvalidVatRateException(final String message) {
    super(message);
  }
}
