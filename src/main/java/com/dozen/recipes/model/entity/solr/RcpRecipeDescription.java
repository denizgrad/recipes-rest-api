package com.dozen.recipes.model.entity.solr;

import lombok.Data;
import org.springframework.data.solr.core.mapping.Indexed;
import org.springframework.data.solr.core.mapping.SolrDocument;
import org.apache.solr.client.solrj.beans.Field;
import org.springframework.data.annotation.Id;

@SolrDocument(collection = "RcpRecipeDescription")
@Data
public class RcpRecipeDescription {
    @Id
    @Field
    private String id;

    @Field
    private Long recipeId;

    @Field
    @Indexed
    private Long userId;

    @Field
    @Indexed
    private String description;
}
