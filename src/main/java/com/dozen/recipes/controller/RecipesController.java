package com.dozen.recipes.controller;

import com.dozen.recipes.exception.RecipesException;
import com.dozen.recipes.model.RecipeDto;
import com.dozen.recipes.service.RecipesService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("v1/recipes")
@AllArgsConstructor
public class RecipesController extends AbstractController{

    private final RecipesService service;

    @GetMapping // might be from jax-rs
    public ResponseEntity<?> getAll(){
        return ResponseEntity.ok(service.getAll());
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody @Valid final RecipeDto dto){
        if(!StringUtils.isEmpty(dto.getId())){
            throw new RecipesException(RecipesException.UNEXPECTED_PARAM, "id in Recipe");
        }
        return ResponseEntity.ok(service.save(dto));
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable final Long id, @RequestBody @Valid final RecipeDto dto){
        dto.setId(id);
        return ResponseEntity.ok(service.update(dto));
    }
}
