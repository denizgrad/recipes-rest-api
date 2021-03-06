package com.dozen.recipes;

import com.dozen.recipes.dao.IngredientDao;
import com.dozen.recipes.model.entity.RcpIngredient;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.solr.repository.config.EnableSolrRepositories;

import java.util.Arrays;

@AllArgsConstructor
@SpringBootApplication
@EnableSolrRepositories(basePackages = "com.dozen.recipes.dao.solr")
public class SpringRecipesApplication implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(SpringRecipesApplication.class, args);
    }

    private final IngredientDao ingredientDao;

    @Override
    public void run(String... args) throws Exception {
		createVeggieIngredientsIfNotExists(
        		"Banana",
        		"Bread",
        		"Olive Oil",
        		"Rice",
        		"Onion"
		);
		createNonVeggieIngredientsIfNotExists(
				"Red Meat",
				"Butter"
		);
    }
	private void createVeggieIngredientsIfNotExists(String... args) {
		createDefaultIngredientsIfNotExists(true, args);

	}
	private void createNonVeggieIngredientsIfNotExists(String... args) {
		createDefaultIngredientsIfNotExists(false, args);

	}

	private void createDefaultIngredientsIfNotExists(boolean isVeggie, String... args) {
		Arrays.stream(args)
				.filter( name ->  !ingredientDao.findByName(name).isPresent())
				.forEach(name -> ingredientDao.save(new RcpIngredient(name, isVeggie, null)));
	}
}