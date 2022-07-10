package com.dozen.recipes.controller;

import com.dozen.recipes.exception.RecipesException;
import com.dozen.recipes.model.FieldErrorDto;
import com.dozen.recipes.model.RestErrorResponse;
import com.dozen.recipes.model.ValidationFailureResponse;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;


public class AbstractController {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ValidationFailureResponse validationError(MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();
        final List<FieldError> fieldErrors = result.getFieldErrors();
        return processFieldErrors(fieldErrors);
    }

    @ExceptionHandler(RecipesException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public RestErrorResponse applicationException(RecipesException ex) {
        return new RestErrorResponse(ex.getMessage());
    }

    private ValidationFailureResponse processFieldErrors(List<FieldError> fieldErrors) {
        ValidationFailureResponse dto = new ValidationFailureResponse();
        for (FieldError fieldError : fieldErrors) {
            dto.addFieldError(new FieldErrorDto(fieldError.getField(),fieldError.getDefaultMessage()));
        }
        return dto;
    }

}
