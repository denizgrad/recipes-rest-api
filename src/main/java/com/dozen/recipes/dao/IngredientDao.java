package com.dozen.recipes.dao;

import com.dozen.recipes.model.entity.RcpIngredient;
import com.dozen.recipes.model.entity.RcpUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IngredientDao extends CrudRepository<RcpIngredient, Long> {
	Optional<RcpIngredient> findByName(String name);
}