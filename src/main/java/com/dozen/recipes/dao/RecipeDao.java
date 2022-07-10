package com.dozen.recipes.dao;

import com.dozen.recipes.model.entity.RcpRecipe;
import com.dozen.recipes.model.entity.RcpUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RecipeDao extends CrudRepository<RcpRecipe, Long> {
    Optional<RcpRecipe> findByName(String name);
    List<RcpRecipe> findByUser(RcpUser user);
}