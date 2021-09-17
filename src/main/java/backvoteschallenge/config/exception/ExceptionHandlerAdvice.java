package backvoteschallenge.config.exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ExceptionHandlerAdvice {

    @Autowired
    private MessageSource messageSource;

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<StandardError> handleValidationError(MethodArgumentNotValidException e, HttpServletRequest request) {
        Map<String, String> errorsTest = new HashMap<>();

        e.getBindingResult().getFieldErrors().forEach(fieldError -> {
            String message = messageSource.getMessage(fieldError, LocaleContextHolder.getLocale());
            errorsTest.put(fieldError.getField(), message);
        });

        String error = "Validation Error";
        HttpStatus status = HttpStatus.BAD_REQUEST;
        StandardError standardError = new StandardError(Instant.now(), status.value(), error, "Required Fields", request.getRequestURI());
        standardError.setErrors(errorsTest);
        return ResponseEntity.status(status).body(standardError);
    }

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<StandardError> handleOrderNotFoundException(OrderNotFoundException e, HttpServletRequest request) {

        String error = "Resource Not Found";
        HttpStatus status = HttpStatus.BAD_REQUEST;
        StandardError standardError = new StandardError(Instant.now(), status.value(), error, "Invalid Order Id", request.getRequestURI());
        Map<String, String> errorsTest = new HashMap<>();
        errorsTest.put("orderId", e.getMessage());
        standardError.setErrors(errorsTest);

        return ResponseEntity.status(status).body(standardError);
    }

}
