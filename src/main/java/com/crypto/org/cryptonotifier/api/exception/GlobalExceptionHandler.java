package com.crypto.org.cryptonotifier.api.exception;

import com.crypto.org.cryptonotifier.api.responses.ApiException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(WebClientResponseException.Unauthorized.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    public ApiException handleUnauthorizedException(WebClientResponseException.Unauthorized exception){
        return ApiException.unauthorized(exception.getMessage());
    }

    @ExceptionHandler(WebClientResponseException.NotFound.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ApiException handleNotFound(WebClientResponseException.NotFound exception){
        return ApiException.notFound(exception.getMessage());
    }

    @ExceptionHandler(WebClientResponseException.BadRequest.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ApiException handleNotFound(WebClientResponseException.BadRequest exception){
        return ApiException.badRequest(exception.getMessage());
    }

}
