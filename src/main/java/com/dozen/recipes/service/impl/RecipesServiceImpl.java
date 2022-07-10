package com.dozen.recipes.service.impl;

import com.dozen.recipes.dao.IngredientDao;
import com.dozen.recipes.dao.RecipeDao;
import com.dozen.recipes.exception.RecipesException;
import com.dozen.recipes.model.IngredientDto;
import com.dozen.recipes.model.RecipeDto;
import com.dozen.recipes.model.entity.RcpIngredient;
import com.dozen.recipes.model.entity.RcpRecipe;
import com.dozen.recipes.model.entity.RcpUser;
import com.dozen.recipes.service.NamingService;
import com.dozen.recipes.service.RecipesService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashSet;
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

    private RecipeDto doCommonPartsForSave(RecipeDto dto, RcpRecipe rcpRecipe) {
        rcpRecipe.setName(dto.getName());
        rcpRecipe.setServings(dto.getServings());
        setIngredientsByIds(rcpRecipe, dto.getIngredientIds());
        rcpRecipe.setIsVeggie(isVeggie(rcpRecipe.getIngredients()));

        RcpRecipe saved = dao.save(rcpRecipe);
        //Description is not in postgres

        return RecipeDto.mapFromEntity(saved);
    }

    private Boolean isVeggie(Set<RcpIngredient> ingredients) {
        return ingredients.stream().anyMatch(ingredient -> ingredient.getIsVeggie());
    }

    private void setIngredientsByIds(RcpRecipe rcpRecipe, List<Long> ingredientIds) {
        final Iterable<RcpIngredient> allById = ingredientDao.findAllById(ingredientIds);
        Set<RcpIngredient> ingredentSet = StreamSupport.stream(allById.spliterator(), false)
                .collect(Collectors.toSet());
        rcpRecipe.setIngredients(ingredentSet);
        ingredentSet.forEach(ing -> ing.getRecipes().add(rcpRecipe));
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
