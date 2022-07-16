package com.dozen.recipes.dao.solr;

import com.dozen.recipes.model.entity.solr.RcpRecipeDescription;
import org.springframework.data.solr.repository.SolrCrudRepository;

import java.util.List;

public interface RecipeDescriptionDao extends SolrCrudRepository<RcpRecipeDescription, String> {

    public List<RcpRecipeDescription> findByUserIdAndDescriptionContains(Long userId, String descriptionQuote);

}
