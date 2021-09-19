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
        Map<String, String> errors = new HashMap<>();

        e.getBindingResult().getFieldErrors().forEach(fieldError -> {
            String message = messageSource.getMessage(fieldError, LocaleContextHolder.getLocale());
            errors.put(fieldError.getField(), message);
        });

        String error = "Validation Error";
        HttpStatus status = HttpStatus.BAD_REQUEST;
        StandardError standardError = new StandardError(Instant.now(), status.value(), error, "Required Fields", request.getRequestURI());
        standardError.setErrors(errors);
        return ResponseEntity.status(status).body(standardError);
    }

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<StandardError> handleOrderNotFoundException(OrderNotFoundException e, HttpServletRequest request) {

        String error = "Resource Not Found";
        HttpStatus status = HttpStatus.NOT_FOUND;
        StandardError standardError = new StandardError(Instant.now(), status.value(), error, "Invalid Order Id", request.getRequestURI());
        Map<String, String> errors = new HashMap<>();
        errors.put("orderId", e.getMessage());
        standardError.setErrors(errors);

        return ResponseEntity.status(status).body(standardError);
    }

    @ExceptionHandler(SessionNotFoundException.class)
    public ResponseEntity<StandardError> handleSessionNotFoundException(SessionNotFoundException e, HttpServletRequest request) {

        String error = "Resource Not Found";
        HttpStatus status = HttpStatus.NOT_FOUND;
        StandardError standardError = new StandardError(Instant.now(), status.value(), error, "Invalid Session Id", request.getRequestURI());
        Map<String, String> errors = new HashMap<>();
        errors.put("sessionId", e.getMessage());
        standardError.setErrors(errors);

        return ResponseEntity.status(status).body(standardError);
    }

    @ExceptionHandler(AssociateNotFoundException.class)
    public ResponseEntity<StandardError> handleAssociateNotFoundException(AssociateNotFoundException e, HttpServletRequest request) {

        String error = "Resource Not Found";
        HttpStatus status = HttpStatus.NOT_FOUND;
        StandardError standardError = new StandardError(Instant.now(), status.value(), error, "Invalid Associate Id", request.getRequestURI());
        Map<String, String> errors = new HashMap<>();
        errors.put("associateId", e.getMessage());
        standardError.setErrors(errors);

        return ResponseEntity.status(status).body(standardError);
    }

    @ExceptionHandler(SessionIsClosedException.class)
    public ResponseEntity<StandardError> handleSessionIsClosedException(SessionIsClosedException e, HttpServletRequest request) {

        String error = "Resource Not Acessible";
        HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
        StandardError standardError = new StandardError(Instant.now(), status.value(), error, "Session is Closed", request.getRequestURI());
        Map<String, String> errors = new HashMap<>();
        errors.put("sessionId", e.getMessage());
        standardError.setErrors(errors);

        return ResponseEntity.status(status).body(standardError);
    }

    @ExceptionHandler(AssociateAlreadyVotedException.class)
    public ResponseEntity<StandardError> handleAssociateAlreadyVotedException(AssociateAlreadyVotedException e, HttpServletRequest request) {

        String error = "Operation invalid";
        HttpStatus status = HttpStatus.BAD_REQUEST;
        StandardError standardError = new StandardError(Instant.now(), status.value(), error, "Associate Already Voted In this Order", request.getRequestURI());
        Map<String, String> errors = new HashMap<>();
        errors.put("associateId", e.getMessage());
        standardError.setErrors(errors);

        return ResponseEntity.status(status).body(standardError);
    }

    @ExceptionHandler(AssociateUnableToVoteException.class)
    public ResponseEntity<StandardError> handleAssociateUnableToVoteException(AssociateUnableToVoteException e, HttpServletRequest request) {

        String error = "Operation invalid";
        HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
        StandardError standardError = new StandardError(Instant.now(), status.value(), error, "Associate don't Able to vote", request.getRequestURI());
        Map<String, String> errors = new HashMap<>();
        errors.put("associateId", e.getMessage());
        standardError.setErrors(errors);

        return ResponseEntity.status(status).body(standardError);
    }

    @ExceptionHandler(AssociateDocumentAlreadyExistsException.class)
    public ResponseEntity<StandardError> handleAssociateDocumentAlreadyExistsException(AssociateDocumentAlreadyExistsException e, HttpServletRequest request) {

        String error = "Associate with Document Already Exists";
        HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
        StandardError standardError = new StandardError(Instant.now(), status.value(), error, "", request.getRequestURI());
        Map<String, String> errors = new HashMap<>();
        errors.put("document", e.getMessage());
        standardError.setErrors(errors);

        return ResponseEntity.status(status).body(standardError);
    }

}
