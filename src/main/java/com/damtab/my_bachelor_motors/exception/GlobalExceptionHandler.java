package com.damtab.my_bachelor_motors.exception;

import com.damtab.my_bachelor_motors.service.AlertService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    private final AlertService alertService;

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> handleResourceNotFound(
            ResourceNotFoundException ex,
            HttpServletRequest request) {

        log.warn("Ressource introuvable : {} |URI : {}",
                ex.getMessage(), request.getRequestURI());
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ex.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgument(
            IllegalArgumentException ex,
            HttpServletRequest request) {

        log.warn("Argument invalide : {} | URI : {}",
                ex.getMessage(), request.getRequestURI());

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(
            Exception ex,
            HttpServletRequest request) {
        alertService.sendAlert(
                "EXCEPTION",
                ex.getClass().getSimpleName(),
                "Une exception non gérée est survenue.\n" +
                        "Message : " + ex.getMessage()
        );
        log.error("Une erreur inattendue est survenue | URI : {}",
                request.getRequestURI(), ex);
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Une erreur interne est survenue");
    }

}
