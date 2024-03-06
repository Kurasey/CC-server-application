package me.t.kaurami.service.bookReader;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
public class NotValidWorkbookException extends RuntimeException {

    public NotValidWorkbookException(String message) {
        super(message);
    }
}
