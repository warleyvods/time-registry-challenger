package com.example.timeregistrychallenger.exceptions.handler;


import com.example.timeregistrychallenger.exceptions.CannotAddAlocation;
import com.example.timeregistrychallenger.exceptions.ExistsHoursException;
import com.example.timeregistrychallenger.exceptions.MaximumHoursException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.example.timeregistrychallenger.exceptions.handler.ExceptionFilters.*;
import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
public class RestExceptionHandler {

    @ResponseStatus(FORBIDDEN)
    @ExceptionHandler(MaximumHoursException.class)
    public ExceptionFilters maximumHoursException(final MaximumHoursException ex) {
        return builder().mensagem(ex.getMessage()).build();
    }

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(CannotAddAlocation.class)
    public ExceptionFilters cannotAddAlocation(final CannotAddAlocation ex) {
        return builder().mensagem(ex.getMessage()).build();
    }

    @ResponseStatus(CONFLICT)
    @ExceptionHandler(ExistsHoursException.class)
    public ExceptionFilters existsHoursException(final ExistsHoursException ex) {
        return builder().mensagem(ex.getMessage()).build();
    }

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ExceptionFilters invalidTypeException() {
        return builder().mensagem("Data e hora em formato inválido").build();
    }
}

