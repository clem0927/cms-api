package com.min.cms.global.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice(basePackages = "com.min.cms")
public class GlobalExceptionHandler {

    // 1. @Valid
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidation(MethodArgumentNotValidException e) {

        Map<String, String> errors = new HashMap<>();

        for (FieldError error : e.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }

        return ResponseEntity
                .badRequest()
                .body(Map.of(
                        "message", "유효성 검사 실패",
                        "errors", errors
                ));
    }

    // 2. 로그인 실패
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Map<String, String>> handleLogin(BadCredentialsException e) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(Map.of(
                        "message", "아이디 또는 비밀번호가 잘못되었습니다."
                ));
    }

    // 3. 커스텀 예외
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<Map<String, String>> handleCustom(CustomException e) {
        return ResponseEntity
                .status(e.getStatus())
                .body(Map.of(
                        "message", e.getMessage()
                ));
    }
}