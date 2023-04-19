package com.ontrustserver.exception;


import com.ontrustserver.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@ControllerAdvice
public class ExceptionController {

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
}
