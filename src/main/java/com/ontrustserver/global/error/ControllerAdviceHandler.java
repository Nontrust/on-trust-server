package com.ontrustserver.global.error;


import com.ontrustserver.domain.account.exception.AccountDomainException;
import com.ontrustserver.domain.post.exception.PostDomainException;
import com.ontrustserver.global.exception.AspectGlobalException;
import com.ontrustserver.global.exception.AuthorizedGlobalException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@ControllerAdvice
public class ControllerAdviceHandler {
    private static final String MESSAGE_BAD_REQUEST = "잘못된 요청입니다.";
    private static final String MESSAGE_UNAUTHORIZED = "인증되지 않은 사용자 입니다.";

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResponse invalidHandler(MethodArgumentNotValidException e){
        ErrorResponse errorResponse = ErrorResponse.builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .message(MESSAGE_BAD_REQUEST)
                .build();

        for(FieldError fe : e.getFieldErrors()){
            errorResponse.validation().put(fe.getField(), fe.getDefaultMessage());
        }

        return errorResponse;
    }

    @ResponseBody
    @ExceptionHandler(AspectGlobalException.class)
    public ResponseEntity<ErrorResponse> AspectExceptionHandler(AspectGlobalException e) {
        HttpStatus httpStatus = e.statusCode();

        ErrorResponse errorResponse = ErrorResponse.builder()
                .code(httpStatus.value())
                .message(MESSAGE_BAD_REQUEST)
                .build();
        errorResponse.validation().put("parameter", e.getMessage());

        return ResponseEntity.status(httpStatus).body(errorResponse);
    }

    @ResponseBody
    @ExceptionHandler(AccountDomainException.class)
    public ResponseEntity<ErrorResponse> accountDomainHandler(AccountDomainException e) {
        HttpStatus httpStatus = e.statusCode();

        ErrorResponse errorResponse = ErrorResponse.builder()
                .code(httpStatus.value())
                .message(MESSAGE_BAD_REQUEST)
                .build();
        errorResponse.validation().put("parameter", e.getMessage());

        return ResponseEntity.status(httpStatus).body(errorResponse);
    }

    @ResponseBody
    @ExceptionHandler(PostDomainException.class)
    public ResponseEntity<ErrorResponse> postDomainHandler(PostDomainException e) {
        HttpStatus httpStatus = e.statusCode();

        ErrorResponse errorResponse = ErrorResponse.builder()
                .code(httpStatus.value())
                .message(MESSAGE_BAD_REQUEST)
                .build();
        errorResponse.validation().put("parameter", e.getMessage());

        return ResponseEntity.status(httpStatus).body(errorResponse);
    }

    @ResponseBody
    @ExceptionHandler(AuthorizedGlobalException.class)
    public ResponseEntity<ErrorResponse> authGlobalHandler(AuthorizedGlobalException e) {
        HttpStatus httpStatus = e.statusCode();

        ErrorResponse errorResponse = ErrorResponse.builder()
                .code(httpStatus.value())
                .message(MESSAGE_UNAUTHORIZED)
                .build();
        errorResponse.validation().put("parameter", e.getMessage());

        return ResponseEntity.status(httpStatus).body(errorResponse);
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(TypeMismatchException.class)
    public ErrorResponse typeMismatchExceptionHandler(TypeMismatchException e){
        ErrorResponse errorResponse = ErrorResponse.builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .message(MESSAGE_BAD_REQUEST)
                .build();

        String fieldName = e.getPropertyName();
        String requiredType = String.valueOf(e.getRequiredType());
        String message = String.format("'%s'에 유효한 값이 아닙니다. '%s' 타입에 해당하는 값을 넣어주세요", fieldName,requiredType);

        errorResponse.validation().put(fieldName, message);
        return errorResponse;
    }
}
