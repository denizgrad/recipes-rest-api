package com.dozen.recipes.model;

import com.dozen.recipes.model.entity.RcpIngredient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IngredientDto {

    private Long id;
    @NotNull
    private String name;
    @NotNull
    private Boolean isVeggie;

    public static IngredientDto mapFromEntity(RcpIngredient entity){ // mapper class using interfaces is better
        return new IngredientDto(entity.getId() ,entity.getName(), entity.getIsVeggie());
    }
}
