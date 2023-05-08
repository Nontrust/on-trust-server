package com.ontrustserver.global.error;


import com.ontrustserver.domain.post.exception.PostNotFound;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResponse invalidHandler(MethodArgumentNotValidException e){
        ErrorResponse errorResponse = ErrorResponse.builder().code("400").message("잘못된 요청입니다.").build();

        for(FieldError fe : e.getFieldErrors()){
            errorResponse.validation().put(fe.getField(), fe.getDefaultMessage());
        }

        return errorResponse;
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(PostNotFound.class)
    public ErrorResponse postNotFoundHandler(PostNotFound e) {
        ErrorResponse errorResponse = ErrorResponse.builder().code("400").message("잘못된 요청입니다.").build();
        errorResponse.validation().put("parameter", e.getMessage());
        return errorResponse;
    }
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(TypeMismatchException.class)
    public ErrorResponse typeMismatchExceptionHandler(TypeMismatchException e){
        ErrorResponse errorResponse = ErrorResponse.builder()
                .code("400")
                .message("잘못된 요청입니다.")
                .build();

        String fieldName = e.getPropertyName();
        String requiredType = String.valueOf(e.getRequiredType());
        String message = String.format("'%s'에 유효한 값이 아닙니다. '%s' 타입에 해당하는 값을 넣어주세요", fieldName,requiredType);

        errorResponse.validation().put(fieldName, message);
        return errorResponse;

    }
}
