package com.project.exception.handler;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.project.exception.ApiException;
import com.project.exception.CovidReportException;

@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
public class RestExceptionHandler {

	private static final Logger LOG = LoggerFactory.getLogger(RestExceptionHandler.class);

	@ExceptionHandler(value = { HttpMessageNotReadableException.class })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
	protected ResponseEntity<Object> handleHttpMessageNotReadable(Exception ex) {
		LOG.info("found bad request error");
		String error = "Malformed JSON request";
		return this.buildResponseEntity(new ApiException(HttpStatus.BAD_REQUEST, error, ex));
	}

	@ExceptionHandler(value = { NoHandlerFoundException.class })
    @ResponseStatus(HttpStatus.NOT_FOUND)
	protected ResponseEntity<Object> handleNotFoundException(Exception ex) {
		LOG.info("URLNotFoundException : got invalid url");
		String error = "Invalid URL";
		return this.buildResponseEntity(new ApiException(HttpStatus.NOT_FOUND, error, ex));
	}
	
	private ResponseEntity<Object> buildResponseEntity(ApiException exception) {
		LOG.info(exception.getMessage());
		if(Objects.nonNull(exception.getDebugMessage()))
			LOG.info(exception.getDebugMessage());
		return new ResponseEntity<Object>(exception, exception.getStatus());
	}

	@ExceptionHandler
	protected ResponseEntity<Object> handleResourceNotFoundException(CovidReportException ex) {
		LOG.info("Custom ResourceNotFoundException");
		ApiException ApiException = new ApiException(HttpStatus.NOT_FOUND);
		ApiException.setMessage(ex.getMessage());
		return buildResponseEntity(ApiException);
	}
	
	@ExceptionHandler(value = { Exception.class })
	public final ResponseEntity<Object> handleUnknownException(Exception ex) {
		LOG.info("handleAllExceptions by default");
		HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
		ApiException apiException = null;
		if (ex instanceof NullPointerException 
				|| ex instanceof java.lang.NumberFormatException
				|| ex instanceof IllegalArgumentException
				|| ex.getLocalizedMessage().contains("Failed to convert value of type")) {
			httpStatus = HttpStatus.BAD_REQUEST;
			apiException = new ApiException(httpStatus);
			apiException.setMessage("Invalid URL Or Request Data");
			apiException.setDebugMessage(ex.getLocalizedMessage());
        } else {
        	apiException = new ApiException(httpStatus, ex);
        }
		return buildResponseEntity(apiException);
	}
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String, String> handleBeanValidationExceptions(
	  MethodArgumentNotValidException ex) {
	    Map<String, String> errors = new HashMap<>();
	    ex.getBindingResult().getAllErrors().forEach((error) -> {
	        String fieldName = ((FieldError) error).getField();
	        String errorMessage = error.getDefaultMessage();
	        errors.put(fieldName, errorMessage);
	    });
	    return errors;
	}
}