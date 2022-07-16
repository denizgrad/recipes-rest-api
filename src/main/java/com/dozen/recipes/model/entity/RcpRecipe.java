package com.dozen.recipes.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

@EqualsAndHashCode(exclude = "ingredients")
@Entity
@Table(name = "rcp_recipe")
@Data
@NoArgsConstructor
public class RcpRecipe extends AbstractEntity{

    @Column
    @NotNull
    private String name;

    @OneToOne
    @NotNull
    private RcpUser user;

    @Column
    @ManyToMany
    @JoinTable(
            name = "rcp_x_ingredient_recipe",
            joinColumns = @JoinColumn(name = "recipe_id"),
            inverseJoinColumns = @JoinColumn(name = "ingredient_id"))
    @NotEmpty
    private Set<RcpIngredient> ingredients;

    @Column
    @NotNull
    private Boolean isVeggie;

    @Column
    @NotNull
    private Integer servings;

    @Column(length = 1000)
    @NotNull
    private String description;
}
