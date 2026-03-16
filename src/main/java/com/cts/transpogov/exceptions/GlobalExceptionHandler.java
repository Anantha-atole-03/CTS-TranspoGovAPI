package com.cts.transpogov.exceptions;



import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
//	
//	@ExceptionHandler(MethodArgumentNotValidException.class)
//	public ResponseEntity<?> handleInvalidInputException(MethodArgumentNotValidException e) {
//	
//		Map<String, String> errorMap = e.getFieldErrors().stream()
//				.collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));
//		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//				.body(new ExceptionResponse(errorMap.get("name"),HttpStatus.NOT_FOUND.value()));
//	}

	@ExceptionHandler(ComplianceNotFoundException.class)
	public ResponseEntity<ExceptionResponse> handleException(ComplianceNotFoundException e){
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ExceptionResponse(e.getMessage(),HttpStatus.BAD_REQUEST.value()));
	}
	
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<String> handleValidationException(MethodArgumentNotValidException ex) {
		StringBuilder errors = new StringBuilder();
		ex.getBindingResult().getFieldErrors().forEach(
				error -> errors.append(error.getField()).append(": ").append(error.getDefaultMessage()).append("; "));
		return new ResponseEntity<>("Validation error(s): " + errors.toString(), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ExceptionResponse> handleException(Exception e){
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ExceptionResponse(e.getMessage(),HttpStatus.BAD_REQUEST.value()));
	}


}
