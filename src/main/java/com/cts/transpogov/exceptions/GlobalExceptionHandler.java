package com.cts.transpogov.exceptions;



import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
	

	@ExceptionHandler(ProgramNotFoundException.class)
	public ResponseEntity<ExceptionResponse> handleProgramNotFoundException(ProgramNotFoundException e){
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ExceptionResponse(e.getMessage(),HttpStatus.NOT_FOUND.value()));
	}

	@ExceptionHandler(IllegalStateException.class)
	public ResponseEntity<ExceptionResponse> handleIllegalStateException(IllegalStateException e){
		return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new ExceptionResponse(e.getMessage(),HttpStatus.NOT_ACCEPTABLE.value()));
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
