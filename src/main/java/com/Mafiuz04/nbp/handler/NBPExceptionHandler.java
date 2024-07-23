package com.Mafiuz04.nbp.handler;

import com.Mafiuz04.nbp.exception.NBPException;
import com.Mafiuz04.nbp.model.ErrorMessageDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class NBPExceptionHandler {


       @ExceptionHandler(NBPException.class)
        public ResponseEntity<ErrorMessageDto> NBPException(NBPException ex) {
            return ResponseEntity.status(ex.getStatus()).body(new ErrorMessageDto(ex.getStatus(), ex.getMessage()));
        }

        @ExceptionHandler(RuntimeException.class)
        @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
        public String anyExceptionResponse() {
            return "Unknown error";
        }
}
