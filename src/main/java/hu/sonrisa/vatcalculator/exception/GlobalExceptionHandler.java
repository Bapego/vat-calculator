package hu.sonrisa.vatcalculator.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Global exception handler to catch and handle specific exceptions across the application.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

  private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

  /**
   * Handles {@link InvalidVatRateException} thrown when an unsupported VAT rate is provided.
   *
   * @param ex the thrown exception
   * @return a 400 Bad Request response with the exception message
   */
  @ExceptionHandler(InvalidVatRateException.class)
  public ResponseEntity<String> handleValidationException(final InvalidVatRateException ex) {
    log.error(ex.getMessage());
    return ResponseEntity.badRequest().body(ex.getMessage());
  }
}
