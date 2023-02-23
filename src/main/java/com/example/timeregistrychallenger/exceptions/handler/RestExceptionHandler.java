package com.example.timeregistrychallenger.exceptions.handler;


import com.example.timeregistrychallenger.exceptions.CannotAddAlocation;
import com.example.timeregistrychallenger.exceptions.ExistsHoursException;
import com.example.timeregistrychallenger.exceptions.MaximumHoursException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
public class RestExceptionHandler {

    @ResponseStatus(FORBIDDEN)
    @ExceptionHandler(MaximumHoursException.class)
    public ExceptionFilters illegalArgumentException(MaximumHoursException ex) {
        return ExceptionFilters.builder()
                .mensagem(ex.getMessage())
                .build();
    }

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(CannotAddAlocation.class)
    public ExceptionFilters illegalArgumentException(CannotAddAlocation ex) {
        return ExceptionFilters.builder()
                .mensagem(ex.getMessage())
                .build();
    }

    @ResponseStatus(CONFLICT)
    @ExceptionHandler(ExistsHoursException.class)
    public ExceptionFilters illegalArgumentException(ExistsHoursException ex) {
        return ExceptionFilters.builder()
                .mensagem(ex.getMessage())
                .build();
    }

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ExceptionFilters illegalArgumentException() {
        return ExceptionFilters.builder()
                .mensagem("Data e hora em formato inválido")
                .build();
    }
}

