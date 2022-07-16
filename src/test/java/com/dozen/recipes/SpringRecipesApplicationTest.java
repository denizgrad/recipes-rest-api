package com.dozen.recipes;

import com.dozen.recipes.controller.RecipesController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.Assert.assertNotNull;


@SpringBootTest
public class SpringRecipesApplicationTest  {
	@Autowired
	private RecipesController controller;
	@Test
	public void contextLoads() {
		assertNotNull(controller);
	}
}