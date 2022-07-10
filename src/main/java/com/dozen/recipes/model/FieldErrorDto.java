package com.dozen.recipes.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class FieldErrorDto {
    String field;
    String message;
}
