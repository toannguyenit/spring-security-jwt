package vn.edu.likelion.app.security_jwt.exception;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = RuntimeException.class)
    ResponseEntity handlingRuntimeException(RuntimeException exception) {
        String message = exception.getMessage();
        return ResponseEntity.badRequest().body(message);
    }


    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    ResponseEntity handlingValidation(MethodArgumentNotValidException exception) {
        return ResponseEntity.badRequest().body(exception.getBindingResult().getAllErrors().get(0).getDefaultMessage());
    }
}
