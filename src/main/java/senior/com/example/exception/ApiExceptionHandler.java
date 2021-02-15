package senior.com.example.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.*;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(
            MissingServletRequestParameterException ex, HttpHeaders headers,
            HttpStatus status, WebRequest request) {
        String error = (" parametro " + ex.getParameterName() +  " está faltando");

        ExceptionMessage exceptionMessage = new ExceptionMessage(new Date(), error, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<Object>(exceptionMessage, new HttpHeaders(), exceptionMessage.getStatus());
    }

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(
            HttpRequestMethodNotSupportedException ex, HttpHeaders headers,
            HttpStatus status, WebRequest request) {
        String error = (" método " + ex.getMethod() +  " não suportado");

        ExceptionMessage exceptionMessage = new ExceptionMessage(new Date(), error, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<Object>(exceptionMessage, new HttpHeaders(), exceptionMessage.getStatus());
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(
            NoHandlerFoundException ex, HttpHeaders headers,
            HttpStatus status, WebRequest request) {
        String error = ex.getLocalizedMessage();

        ExceptionMessage exceptionMessage = new ExceptionMessage(new Date(), error, HttpStatus.NOT_FOUND);
        return new ResponseEntity<Object>(exceptionMessage, new HttpHeaders(), exceptionMessage.getStatus());
    }

    @ExceptionHandler({ ConstraintViolationException.class })
    public ResponseEntity<Object> handleConstraintViolation(
            ConstraintViolationException ex, WebRequest request) {
        List<String> errors = new ArrayList<>();
        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            errors.add(violation.getRootBeanClass().getName() + " " +
                    violation.getPropertyPath() + ": " + violation.getMessage());
        }

        ExceptionMessage exceptionMessage = new ExceptionMessage(new Date(), errors.toString(), HttpStatus.BAD_REQUEST);
        return new ResponseEntity<Object>(exceptionMessage, new HttpHeaders(), exceptionMessage.getStatus());
    }

    @ExceptionHandler({ MethodArgumentTypeMismatchException.class })
    public ResponseEntity<Object> handleMethodArgumentTypeMismatch(
            MethodArgumentTypeMismatchException ex, WebRequest request) {
        String error =
                ex.getName() + " should be of type " + ex.getRequiredType().getName();

        ExceptionMessage exceptionMessage = new ExceptionMessage(new Date(), error, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<Object>(exceptionMessage, new HttpHeaders(), exceptionMessage.getStatus());
    }

    @ExceptionHandler({ProdServicoNotFound.class})
    public ResponseEntity<Object> handleProdServicoNotFound(ProdServicoNotFound ex, WebRequest request) {
        ExceptionMessage exceptionMessage = new ExceptionMessage(new Date(), ex.getMessage(), HttpStatus.NOT_FOUND);
        return new ResponseEntity<Object>(exceptionMessage, new HttpHeaders(), exceptionMessage.getStatus());
    }

    @ExceptionHandler({PedidoNotFound.class})
    public ResponseEntity<Object> handlePedidoNotFound(PedidoNotFound ex, WebRequest request) {
        ExceptionMessage exceptionMessage = new ExceptionMessage(new Date(), ex.getMessage(), HttpStatus.NOT_FOUND);
        return new ResponseEntity<Object>(exceptionMessage, new HttpHeaders(), exceptionMessage.getStatus());
    }

    @ExceptionHandler({ProdServicoEmPedidoException.class})
    public ResponseEntity<Object> handleProdutoEmPedidoException(ProdServicoEmPedidoException ex, WebRequest request) {
        ExceptionMessage exceptionMessage = new ExceptionMessage(new Date(), ex.getMessage(), HttpStatus.CONFLICT);
        return new ResponseEntity<Object>(exceptionMessage, new HttpHeaders(), exceptionMessage.getStatus());
    }

    @ExceptionHandler({ProdutoDesativadoException.class})
    public ResponseEntity<Object> handleProdutoDesativadoException(ProdutoDesativadoException ex, WebRequest request) {
        ExceptionMessage exceptionMessage = new ExceptionMessage(new Date(), ex.getMessage(), HttpStatus.CONFLICT);
        return new ResponseEntity<Object>(exceptionMessage, new HttpHeaders(), exceptionMessage.getStatus());
    }

    @ExceptionHandler({PedidoFechadoException.class})
    public ResponseEntity<Object> handlePedidoFechadoException(PedidoFechadoException ex, WebRequest request) {
        ExceptionMessage exceptionMessage = new ExceptionMessage(new Date(), ex.getMessage(), HttpStatus.CONFLICT);
        return new ResponseEntity<Object>(exceptionMessage, new HttpHeaders(), exceptionMessage.getStatus());
    }

}
