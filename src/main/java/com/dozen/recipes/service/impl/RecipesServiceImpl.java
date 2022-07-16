package com.dozen.recipes.service.impl;

import com.dozen.recipes.dao.IngredientDao;
import com.dozen.recipes.dao.RecipeDao;
import com.dozen.recipes.exception.RecipesException;
import com.dozen.recipes.model.RecipeDto;
import com.dozen.recipes.model.entity.RcpIngredient;
import com.dozen.recipes.model.entity.RcpRecipe;
import com.dozen.recipes.model.entity.RcpUser;
import com.dozen.recipes.service.DescriptionService;
import com.dozen.recipes.service.RecipesService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@AllArgsConstructor
public class RecipesServiceImpl implements RecipesService {

    private final JwtUserDetailsService userDetailsService;
    private final RecipeDao dao;
    private final DescriptionService descriptionService;
    private final IngredientDao ingredientDao;

    @Override
    public List<RecipeDto> getAll() {
        return dao.findByUser(getUser()).stream()
                .map(RecipeDto::mapFromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public RecipeDto save(RecipeDto dto) {
        if (dao.findByName(dto.getName()).isPresent()) {
            throw new RecipesException(RecipesException.ALREADY_EXISTS, "Recipe");
        }
        RcpRecipe rcpRecipe = getRcpRecipeWithUser();

        return doCommonPartsForSave(dto, rcpRecipe);
    }

    @Override
    public RecipeDto update(RecipeDto dto) {
        Optional<RcpRecipe> byId = dao.findById(dto.getId());
        if (!byId.isPresent()){
            throw new RecipesException(RecipesException.NOT_FOUND, "Recipe");
        }
        RcpRecipe rcpRecipe = byId.get();

        return doCommonPartsForSave(dto, rcpRecipe);
    }

    @Override
    public List<RecipeDto> filter(final String descriptionQuote,
                                  final Long includes,
                                  final Long excludes,
                                  final Integer servings,
                                  final Boolean isVeggie) {
        long userId = getUser().getId();
        List<RcpRecipe> returnList = null;
        if(
                includes == null &&
                excludes == null &&
                servings == null &&
                isVeggie == null &&
                descriptionQuote != null // only solr param is not null, only go to solr
        ) {
            Set<Long> recipeIdsbyUserIdAndDescriptionQuote = descriptionService.getRecipeIdsByUserIdAndDescriptionQuote(getUser().getId(), descriptionQuote);
            final Iterable<RcpRecipe> allById = dao.findAllById(recipeIdsbyUserIdAndDescriptionQuote);

            returnList = StreamSupport.stream(allById.spliterator(), false)
                    .collect(Collectors.toList());
        } else if (descriptionQuote != null){
            Set<Long> recipeIdsByUserIdAndDescriptionQuote = descriptionService.getRecipeIdsByUserIdAndDescriptionQuote(getUser().getId(), descriptionQuote);
            if (includes == null ) {
                returnList = dao.findRcpRecipesByParams(userId, isVeggie, servings).stream()
                        .filter(recipe -> recipeIdsByUserIdAndDescriptionQuote.contains(recipe.getId()))
                        .filter(recipe -> isExcluded(excludes, recipe))
                        .collect(Collectors.toList());
            } else {
                returnList = dao.findRcpRecipesDetailed(userId, includes, isVeggie, servings).stream()
                        .filter(recipe -> recipeIdsByUserIdAndDescriptionQuote.contains(recipe.getId()))
                        .filter(recipe -> isExcluded(excludes, recipe))
                        .collect(Collectors.toList());
            }
        } else {//only go to rdb
            if (includes == null && // no join needed
                excludes == null ) {
                returnList = dao.findRcpRecipesByParams(userId, isVeggie, servings);
            } else {
                returnList = dao.findRcpRecipesDetailed(userId, includes, isVeggie, servings).stream()
                        .filter(recipe -> isExcluded(excludes, recipe))
                        .collect(Collectors.toList());
            }
        }
        return returnList.stream()
                .map(rcrRecipe -> createResponseDto(rcrRecipe.getDescription(), rcrRecipe))
                .collect(Collectors.toList());
    }

    //exclusion should be delegated to db level..
    private boolean isExcluded(Long excludes, RcpRecipe recipe) {
        return excludes == null || !recipe.getIngredients().contains(ingredientDao.findById(excludes).get());
    }

    private RecipeDto doCommonPartsForSave(RecipeDto dto, RcpRecipe rcpRecipe) {
        rcpRecipe.setName(dto.getName());
        rcpRecipe.setServings(dto.getServings());
        setIngredientsByIds(rcpRecipe, dto.getIngredientIds());
        rcpRecipe.setIsVeggie(isVeggie(rcpRecipe.getIngredients()));
        rcpRecipe.setDescription(dto.getDescription());

        RcpRecipe saved = dao.save(rcpRecipe);
        //Description sent to lucene
        descriptionService.create(rcpRecipe.getId(), rcpRecipe.getUser().getId(), dto.getDescription());

        return createResponseDto(dto.getDescription(), saved);
    }

    private RecipeDto createResponseDto(String description, RcpRecipe saved) {//extract to mapper class
        RecipeDto recipeDto = RecipeDto.mapFromEntity(saved);
        recipeDto.setDescription(description);
        return recipeDto;
    }

    private Boolean isVeggie(Set<RcpIngredient> ingredients) {
        return ingredients.stream().anyMatch(ingredient -> ingredient.getIsVeggie());
    }

    private void setIngredientsByIds(RcpRecipe rcpRecipe, List<Long> ingredientIds) {
        final Iterable<RcpIngredient> allById = ingredientDao.findAllById(ingredientIds);
        Set<RcpIngredient> ingredentSet = StreamSupport.stream(allById.spliterator(), false)
                .collect(Collectors.toSet());
        if(ingredentSet.size() != ingredientIds.size()){
            throw new RecipesException(RecipesException.NOT_FOUND, "Ingredient");
        }
        rcpRecipe.setIngredients(ingredentSet);
    }

    private RcpRecipe getRcpRecipeWithUser() {
        final RcpUser rcpUser = getUser();
        RcpRecipe rcpRecipe = new RcpRecipe();
        rcpRecipe.setUser(rcpUser);
        return rcpRecipe;
    }

    private RcpUser getUser() {
        UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        final RcpUser rcpUser = userDetailsService.getRcpUserByUsername(userDetails.getUsername());
        return rcpUser;
    }

}
