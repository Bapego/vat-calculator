package hu.sonrisa.vatcalculator.exception;

import jakarta.validation.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown when the VAT rate is invalid.
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidVatRateException extends ValidationException {

  public InvalidVatRateException(final String message) {
    super(message);
  }
}
