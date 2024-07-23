package com.Mafiuz04.nbp.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public class ErrorMessageDto {
    private final HttpStatus status;
    private final String message;
}
