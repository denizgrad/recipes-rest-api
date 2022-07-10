package com.dozen.recipes.service;

import com.dozen.recipes.model.RecipeDto;
import com.dozen.recipes.model.entity.RcpRecipe;

import java.util.List;

public interface RecipesService {

    List<RecipeDto> getAll();//pagination
    RecipeDto save(RecipeDto dto);
    RecipeDto update(RecipeDto dto);
}
