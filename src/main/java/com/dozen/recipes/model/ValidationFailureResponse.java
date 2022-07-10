package com.dozen.recipes.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.LinkedList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ValidationFailureResponse {
    List<FieldErrorDto> error = new LinkedList<>();

    public void addFieldError(FieldErrorDto errorDto){
        error.add(errorDto);
    }
}
