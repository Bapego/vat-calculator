package hu.sonrisa.vatcalculator.exception;

import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
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

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Map<String, String>> handleValidationExceptions(
      final MethodArgumentNotValidException ex) {
    final Map<String, String> errors = new HashMap<>();

    ex.getBindingResult().getFieldErrors().forEach(error ->
        errors.put(error.getField(), error.getDefaultMessage())
    );

    ex.getBindingResult().getGlobalErrors().forEach(error ->
        errors.put("Global error", error.getDefaultMessage())
    );

    log.error(errors.toString());

    return ResponseEntity.badRequest().body(errors);
  }
}
