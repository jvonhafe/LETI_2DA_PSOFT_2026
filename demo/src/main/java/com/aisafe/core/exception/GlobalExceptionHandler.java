package com.aisafe.core.exception;

import org.springframework.http.HttpStatus;
<<<<<<< HEAD
import org.springframework.security.access.AccessDeniedException; // NOVO IMPORT PARA O JWT
=======
>>>>>>> 987052eedb031394fba250f4e0e571285ef997aa
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        return errors;
    }

<<<<<<< HEAD
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public Map<String, String> handleIllegalArgumentException(IllegalArgumentException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", ex.getMessage());
        return error;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ResourceNotFoundException.class)
    public Map<String, String> handleResourceNotFoundException(ResourceNotFoundException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", ex.getMessage());
        return error;
    }

    // --- NOVO 1: Erro 409 (Conflito) ---
    // Imprescindível para tratar duplicados (ex: Aeroporto com mesmo IATA)
    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(DuplicateResourceException.class) // Mudámos para a exceção genérica
    public Map<String, String> handleDuplicateException(DuplicateResourceException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", ex.getMessage());
        return error;
    }

    // --- NOVO 2: Erro 403 (Forbidden - Segurança JWT) ---
    // Impede que o Spring devolva um HTML feio de erro quando a Role não tem permissão
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(AccessDeniedException.class)
    public Map<String, String> handleAccessDeniedException(AccessDeniedException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", "Acesso Negado: Não tem permissões suficientes para executar esta ação.");
        return error;
    }
=======

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public Map<String, String> handleIllegalArgumentException(
            IllegalArgumentException ex) {

        Map<String, String> error = new HashMap<>();
        error.put("error", ex.getMessage());

        return error;
    }


    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ResourceNotFoundException.class)
    public Map<String, String> handleResourceNotFoundException(
            ResourceNotFoundException ex) {

        Map<String, String> error = new HashMap<>();
        error.put("error", ex.getMessage());

        return error;
    }

>>>>>>> 987052eedb031394fba250f4e0e571285ef997aa

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public Map<String, String> handleGenericException(Exception ex) {
<<<<<<< HEAD
        Map<String, String> error = new HashMap<>();
        error.put("error", ex.getMessage());
        return error;
    }
=======

        Map<String, String> error = new HashMap<>();
        error.put("error", ex.getMessage());

        return error;
    }

>>>>>>> 987052eedb031394fba250f4e0e571285ef997aa
}