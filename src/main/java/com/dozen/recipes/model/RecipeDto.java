package com.dozen.recipes.model;

import com.dozen.recipes.model.entity.RcpIngredient;
import com.dozen.recipes.model.entity.RcpRecipe;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecipeDto {

    private Long id;
    @NotNull
    private String name;
    @NotEmpty
    private List<Long> ingredientIds;
    @Null
    private Boolean isVeggie;
    @NotNull
    private Integer servings;
    @NotNull
    private String description;

    public static RecipeDto mapFromEntity(RcpRecipe entity){ // mapper class using interfaces is better
        return new RecipeDto(
                entity.getId(),
                entity.getName(),
                entity.getIngredients().stream().map(RcpIngredient::getId).collect(Collectors.toList()),
                entity.getIsVeggie(),
                entity.getServings(),
                null);
    }
}
