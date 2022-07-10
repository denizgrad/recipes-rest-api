package com.dozen.recipes.service.impl;

import com.dozen.recipes.dao.IngredientDao;
import com.dozen.recipes.exception.RecipesException;
import com.dozen.recipes.model.IngredientDto;
import com.dozen.recipes.model.entity.RcpIngredient;
import com.dozen.recipes.service.IngredientsService;
import com.dozen.recipes.service.NamingService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@AllArgsConstructor
public class IngredientsServiceImpl implements IngredientsService {

    private final IngredientDao dao;
    private final NamingService namingService;

    @Override
    public List<IngredientDto> getAll() {
        Iterable<RcpIngredient> all = dao.findAll();
        return StreamSupport.stream(all.spliterator(), false)
                .map(IngredientDto::mapFromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public IngredientDto save(IngredientDto dto) {
        RcpIngredient entity = new RcpIngredient();
        final String name = namingService.capitalizeAllFirstLetters(dto.getName()); // some business and maybe plural check, typo fixer etc
        if (dao.findByName(name).isPresent() ) {
            throw new RecipesException(RecipesException.ALREADY_EXISTS, "Ingredient");
        }
        entity.setName(name);
        entity.setIsVeggie(dto.getIsVeggie());
        return IngredientDto.mapFromEntity(dao.save(entity));
    }
}
