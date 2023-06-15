package com.kimtaehoonki.task.utils;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

public class ErrorMessageBinder {
    public static void throwErrorWithErrorBinding(BindingResult bindingResult) {
        StringBuilder errorMessage = new StringBuilder();
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            errorMessage.append(
                    "[" + fieldError.getField() + "] 필드: " + fieldError.getDefaultMessage())
                .append("; ");
        }
        throw new IllegalArgumentException(errorMessage.toString());
    }
}
