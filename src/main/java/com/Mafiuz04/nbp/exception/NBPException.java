package com.Mafiuz04.nbp.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public class NBPException extends RuntimeException {

    private final String message;
    private final HttpStatus status;
}

