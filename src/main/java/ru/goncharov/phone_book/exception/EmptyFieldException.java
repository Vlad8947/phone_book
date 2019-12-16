package ru.goncharov.phone_book.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Empty field")
public class EmptyFieldException extends RuntimeException {
}
