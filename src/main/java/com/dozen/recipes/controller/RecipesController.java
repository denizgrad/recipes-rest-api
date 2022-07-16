package com.dozen.recipes.controller;

import com.dozen.recipes.exception.RecipesException;
import com.dozen.recipes.model.RecipeDto;
import com.dozen.recipes.service.RecipesService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("v1/recipes")
@AllArgsConstructor
public class RecipesController extends AbstractController{

    private final RecipesService service;

    @GetMapping // might be from jax-rs
    public ResponseEntity<List<RecipeDto>> search(
            @RequestParam(required=false, value = "descriptionQuote") String descriptionQuote,//pattern validaiton for security
            @RequestParam(required=false, value = "includeIngredients") Long includes,
            @RequestParam(required=false, value = "excludeIngredients") Long excludes,
            @RequestParam(required=false, value = "servings") Integer servings,
            @RequestParam(required=false, value = "isVeggie") Boolean isVeggie
    ) {
        return ResponseEntity.ok(service.filter(descriptionQuote, includes, excludes, servings, isVeggie));
    }

    @PostMapping
    public ResponseEntity<RecipeDto> save(@RequestBody @Valid final RecipeDto dto){
        if(!StringUtils.isEmpty(dto.getId())){
            throw new RecipesException(RecipesException.UNEXPECTED_PARAM, "id in Recipe");
        }
        return ResponseEntity.ok(service.save(dto));
    }
    @PutMapping("/{id}")
    public ResponseEntity<RecipeDto> update(@PathVariable final Long id, @RequestBody @Valid final RecipeDto dto){
        dto.setId(id);
        return ResponseEntity.ok(service.update(dto));
    }

}
