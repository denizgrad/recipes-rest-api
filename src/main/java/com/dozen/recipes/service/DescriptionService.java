package com.dozen.recipes.service;

import com.dozen.recipes.model.entity.solr.RcpRecipeDescription;

import java.util.Set;

public interface DescriptionService {

    RcpRecipeDescription create(Long recipeId, Long userId, String description);
    Set<Long> getRecipeIdsByUserIdAndDescriptionQuote(Long userId, String description);
}
