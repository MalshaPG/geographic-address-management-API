package com.tmf.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@ControllerAdvice
public class AddressNotFoundExceptionHandler {
    @ExceptionHandler(value = AddressNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public @ResponseBody AddressNotFoundException handleAddressNotFoundException(AddressNotFoundException exception) {
        return new AddressNotFoundException("Resource Not Found" + exception.getMessage());
    }
}
