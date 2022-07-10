package com.dozen.recipes.service;

import com.dozen.recipes.model.IngredientDto;

import java.util.List;

public interface IngredientsService {

    List<IngredientDto> getAll();//pagination
    IngredientDto save(IngredientDto dto);
}
