package com.dozen.recipes.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "rcp_ingredient")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = "recipes")
public class RcpIngredient extends AbstractEntity{
    @Column
    private String name;
    @Column
    private Boolean isVeggie;
    @Column
    @ManyToMany(mappedBy = "ingredients")
    private Set<RcpRecipe> recipes;
}
