package com.sparta.outsourcing_team_project.config.exception;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.nio.file.AccessDeniedException;
import java.util.Objects;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AuthException.class)
    public ResponseEntity<String> authException(AuthException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(HttpStatus.BAD_REQUEST + " :  " + ex.getMessage());
    }

    // 잘못된 요청 예외
    @ExceptionHandler(InvalidRequestException.class)
    public ResponseEntity<String> invalidRequestException(InvalidRequestException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(HttpStatus.BAD_REQUEST + " :  " + ex.getMessage());
    }

    // 잘못된 인수 예외
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> illegalArgumentException(IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(HttpStatus.BAD_REQUEST + " :  " + ex.getMessage());
    }

    // 액세스거부예외
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<String> accessDeniedException(AccessDeniedException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(HttpStatus.FORBIDDEN + " :  " + ex.getMessage());
    }

    // 중복키예외
    @ExceptionHandler(DuplicateKeyException.class)
    public ResponseEntity<String> duplicateKeyException(DuplicateKeyException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(HttpStatus.BAD_REQUEST + " :  " + ex.getMessage());
    }

    // @Valid 할때 유효성 검사 실패
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> validationExceptions(MethodArgumentNotValidException ex) {
        StringBuilder errorMessage = new StringBuilder();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            errorMessage.append(fieldName).append(": ").append(message).append(". ");
        });
        return new ResponseEntity<>(errorMessage.toString(), HttpStatus.BAD_REQUEST);
    }

    //입력으로 다른 타입 입력했을 때 에러 (ex . @PathVariable 이 Long 타입인데 String 타입 입력했을때)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<String> idTypeMismatch(MethodArgumentTypeMismatchException ex) {
        String errorMessage = ex.getName()
                + " 의 입력된 값은 잘못된 입력 입니다. "
                + Objects.requireNonNull(ex.getRequiredType()).getSimpleName()
                + " 타입으로 정확히 입력해주세요."
                + " 현재 입력값은 "
                + ex.getValue()
                + " 입니다.";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(HttpStatus.BAD_REQUEST + " :  " + errorMessage);
    }
}
