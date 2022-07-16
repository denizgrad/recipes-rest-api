package com.dozen.recipes.dao;

import com.dozen.recipes.model.entity.RcpIngredient;
import com.dozen.recipes.model.entity.RcpRecipe;
import com.dozen.recipes.model.entity.RcpUser;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RecipeDao extends CrudRepository<RcpRecipe, Long> {
    Optional<RcpRecipe> findByName(String name);
    List<RcpRecipe> findByUser(RcpUser user);
    List<RcpRecipe> findRcpRecipesByUserId(Long userId);

    @Query("SELECT rec FROM RcpRecipe rec " +
            "LEFT JOIN rec.ingredients ing " +
            "WHERE rec.user.id = :userId " +
            "AND (:servings is null or rec.servings = :servings) " +
            "AND (:isVeggie is null or rec.isVeggie = :isVeggie) " +
            "AND (:includes is null or ing.id in (:includes)) "
    )
    List<RcpRecipe> findRcpRecipesDetailed(Long userId, Long includes, Boolean isVeggie, Integer servings);

    @Query("SELECT rec FROM RcpRecipe rec " +
            "WHERE rec.user.id = :userId " +
            "AND (:servings is null or rec.servings = :servings) " +
            "AND (:isVeggie is null or rec.isVeggie = :isVeggie) "
    )
    List<RcpRecipe> findRcpRecipesByParams(Long userId, Boolean isVeggie, Integer servings);


}