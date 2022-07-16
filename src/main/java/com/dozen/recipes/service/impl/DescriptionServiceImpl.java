package com.dozen.recipes.service.impl;

import com.dozen.recipes.dao.solr.RecipeDescriptionDao;
import com.dozen.recipes.model.entity.solr.RcpRecipeDescription;
import com.dozen.recipes.service.DescriptionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class DescriptionServiceImpl implements DescriptionService {
    private final RecipeDescriptionDao descriptionDao;


    @Override
    public RcpRecipeDescription create(Long recipeId, Long userId, String description) {
        RcpRecipeDescription rcpRecipeDescription = new RcpRecipeDescription();//make async with thread or queue (kafka?)
        rcpRecipeDescription.setRecipeId(recipeId);
        rcpRecipeDescription.setUserId(userId);
        rcpRecipeDescription.setDescription(description);
        return descriptionDao.save(rcpRecipeDescription);
    }

    @Override
    public Set<Long> getRecipeIdsByUserIdAndDescriptionQuote(Long userId, String descriptionQuote) {
        return descriptionDao.findByUserIdAndDescriptionContains(userId, descriptionQuote).stream() //dao can directly return this?
                .map(rcpRecipeDescription -> rcpRecipeDescription.getRecipeId())
                .collect(Collectors.toSet());
    }
}
