package com.piter.videoapi.config.handlers;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.piter.videoapi.mapper.requests.ResponseModel;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GenericHandler {

	@ExceptionHandler(Exception.class)
	@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
	public ResponseModel<Object> handleGenericException(Exception e) {
		ResponseModel<Object> response = new ResponseModel<>("FALHA");
		response.setMessage(e.getMessage());

		log.error(e.getMessage(), e);
		
		return response;
	}
	
	@ExceptionHandler(InvalidDataAccessResourceUsageException.class)
	@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
	public ResponseModel<Object> handlerSQLSyntaxErrorException(InvalidDataAccessResourceUsageException e) {
		ResponseModel<Object> response = new ResponseModel<>("FALHA");
		
		response.setMessage(e.getCause().getCause().getMessage());

		log.error(e.getCause().getCause().getMessage(), e);
		
		return response;
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	public ResponseModel<Object> handleValidationExceptions(MethodArgumentNotValidException e) {
		Map<Object, Object> errors = new HashMap<>();
	    e.getBindingResult().getAllErrors().forEach(error -> {
	        String fieldName = ((FieldError) error).getField();
	        String errorMessage = error.getDefaultMessage();
			errors.put(fieldName, errorMessage);
		});

		ResponseModel<Object> response = new ResponseModel<>("FALHA");
		response.setMessage("Os campos correspondentes não atendem as regras de validação");
		response.setErrors(errors);

		log.error(e.getMessage(), e);
		
		return response;
	}

	@ExceptionHandler(TransactionSystemException.class)
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	public ResponseModel<String> handleConstraintViolation(Exception ex) {
		ResponseModel<String> error = new ResponseModel<>("FALHA");

		Throwable cause = ((TransactionSystemException) ex).getRootCause();
		if (cause instanceof ConstraintViolationException) {
			Set<ConstraintViolation<?>> constraintViolations = ((ConstraintViolationException) cause).getConstraintViolations();
			error.setErrors(constraintViolations.stream().collect(Collectors.toMap(ConstraintViolation::getPropertyPath, constraint -> constraint.getMessageTemplate())));
		}

		error.setMessage("Erro ao tentar inserir/atualizar valores no banco de dados");
		return error;
	}

	@ExceptionHandler(ConstraintViolationException.class)
	@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
	public ResponseModel<String> handleConstraintViolation(ConstraintViolationException ex) {
		ResponseModel<String> error = new ResponseModel<>("FALHA");

		Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();
		error.setErrors(constraintViolations.stream().collect(Collectors.toMap(ConstraintViolation::getPropertyPath, constraint -> constraint.getMessageTemplate())));

		error.setMessage("Erro ao tentar inserir/atualizar valores no banco de dados");
		return error;
	}

	@ExceptionHandler(DataIntegrityViolationException.class)
	@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
	public ResponseModel<String> handleConstraintViolation(DataIntegrityViolationException ex) {
		ResponseModel<String> error = new ResponseModel<>("FALHA");

		error.setErrors(Map.of("integridade", ex.getMostSpecificCause().getLocalizedMessage()));
		error.setMessage("Erro ao tentar inserir/atualizar valores no banco de dados");
		return error;
	}
}
