package com.dozen.recipes.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class RecipesException extends RuntimeException{
    public static String ALREADY_EXISTS = "Already exists: %s"; // enum OR we could extend class having diffrent tpyes
    public static String NOT_FOUND = "Not found: %s"; // enum OR we could extend class having diffrent tpyes
    public static String UNEXPECTED_PARAM = "Parameter is unexpected: %s"; // enum OR we could extend class having diffrent tpyes

    public RecipesException(String prefix, String type){
        this.message = String.format(prefix, type);
    }
    String message;
}
