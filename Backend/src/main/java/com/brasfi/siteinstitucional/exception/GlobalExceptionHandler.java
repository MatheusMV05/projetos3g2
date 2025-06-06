package com.brasfi.siteinstitucional.exception;

import lombok.extern.slf4j.Slf4j; // Adicionar Slf4j
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@Slf4j // Adicionar para logging
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleResourceNotFoundException(ResourceNotFoundException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("mensagem", ex.getMessage());
        log.warn("Resource not found: {}", ex.getMessage()); // Logar a exceção
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        log.warn("Validation error: {}", errors, ex); // Logar erros de validação
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    // Adicionar um handler genérico para outras exceções não tratadas
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleGenericException(Exception ex) {
        Map<String, String> error = new HashMap<>();
        error.put("mensagem", "Ocorreu um erro inesperado no servidor.");
        error.put("detalhe", ex.getMessage()); // Cuidado ao expor detalhes da exceção em produção
        log.error("Unexpected server error: {}", ex.getMessage(), ex); // Logar com stack trace
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}