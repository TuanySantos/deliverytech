package com.deliverytech.delivery_api.exception;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.transaction.TransactionSystemException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErroResponse> handleBusiness(BusinessException ex) {
            HttpStatus status = switch (ex.getTipo()) {
                case CLIENTE_NAO_ENCONTRADO, RESTAURANTE_NAO_ENCONTRADO, PRODUTO_NAO_ENCONTRADO -> HttpStatus.NOT_FOUND;
                case EMAIL_JA_CADASTRADO -> HttpStatus.CONFLICT;
                case PEDIDO_INVALIDO -> HttpStatus.BAD_REQUEST;
                case ESTOQUE_INSUFICIENTE -> HttpStatus.BAD_REQUEST;
                default -> HttpStatus.BAD_REQUEST;
            };

        return ResponseEntity.status(status)
            .body(new ErroResponse(ex.getTipo().name(), ex.getMessage()));
    }


    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErroResponse> handleEntityNotFound(EntityNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(new ErroResponse("ENTITY_NOT_FOUND", ex.getMessage()));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErroResponse> handleConstraintViolation(ConstraintViolationException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(new ErroResponse("VALIDATION_ERROR", ex.getMessage()));
    }

    @ExceptionHandler(TransactionSystemException.class)
    public ResponseEntity<ErroResponse> handleTransactionSystem(TransactionSystemException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(new ErroResponse("TRANSACTION_ERROR", ex.getMessage()));
    }
}
