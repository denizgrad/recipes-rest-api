package com.dozen.recipes.controller;

import com.dozen.recipes.model.IngredientDto;
import com.dozen.recipes.service.IngredientsService;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("v1/ingredients")
@AllArgsConstructor
public class IngredientController extends AbstractController{

    private final IngredientsService service;

    @GetMapping // might be from jax-rs
    public ResponseEntity<?> getAll(){
        return ResponseEntity.ok(service.getAll());
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody @Valid final IngredientDto dto){
        return ResponseEntity.ok(service.save(dto));
    }
}
