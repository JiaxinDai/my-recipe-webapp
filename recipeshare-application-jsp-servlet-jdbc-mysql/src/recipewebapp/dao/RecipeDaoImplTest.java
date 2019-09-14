package com.recipeapi.dao;

import static org.junit.Assert.*;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.mockito.Mockito;

import com.recipeapi.api.Customer;
import com.recipeapi.api.Rating;
import com.recipeapi.api.Recipe;

public class RecipeDaoImplTest {
	
	private RecipeDaoImpl dataStore;

	@Test
	public void testCreation() throws IOException, ParseException {
		dataStore = new RecipeDaoImpl();
		assertEquals(10, dataStore.recipes.size());
	}
	
	@Test
	public void testGetMaxId() throws IOException, ParseException {
		dataStore = new RecipeDaoImpl();
		assertEquals(10L, dataStore.getLastUsedId());
	}
	
	@Test
	public void testAddRating() throws IOException, ParseException {
		dataStore = new RecipeDaoImpl();
		Rating rating = new Rating(1l, 4, new Customer(), "Nice");
		assertEquals(1, dataStore.addRating(1l, rating));
		
		rating = new Rating(1l, 0, new Customer(), "Nice");
		assertEquals(0, dataStore.addRating(1l, rating));
		
		rating = new Rating(1l, 5, new Customer(), "Nice");
		assertEquals(1, dataStore.addRating(1l, rating));
	}
	
	@Test
	public void testAddRecipe() throws IOException, ParseException {
		dataStore = new RecipeDaoImpl();
		Recipe recipe = Mockito.mock(Recipe.class);
		dataStore.addRecipe(recipe);
		assertEquals(11, dataStore.getLastUsedId());
	}
	
	@Test
	public void testUpdateRecipe() throws IOException, ParseException {
		dataStore = new RecipeDaoImpl();
		Recipe recipe = Mockito.mock(Recipe.class);
		assertEquals(false, dataStore.updateRecipe(23l, recipe));
		assertEquals(true, dataStore.updateRecipe(2l, recipe));
	}
	
	@Test
	public void testPaginate() throws IOException, ParseException {
		dataStore = new RecipeDaoImpl();
		
		List<Recipe> list = null;
		dataStore.paginate(list);
		
		Recipe recipe1 = Mockito.mock(Recipe.class);
		Recipe recipe2 = Mockito.mock(Recipe.class);
		Recipe recipe3 = Mockito.mock(Recipe.class);
		Recipe recipe4 = Mockito.mock(Recipe.class);
		
		list = new ArrayList<Recipe>();
		list.add(recipe1);
		assertEquals(1, dataStore.paginate(list).size());
		list.add(recipe2);
		assertEquals(1, dataStore.paginate(list).size());
		list.add(recipe3);
		assertEquals(2, dataStore.paginate(list).size());
		list.add(recipe4);
		assertEquals(2, dataStore.paginate(list).size());
	}
	
	@Test
	public void testGetAllRecipes() throws IOException, ParseException {
		dataStore = new RecipeDaoImpl();
		assertEquals(2, dataStore.getAllRecipes("british", 1).size());
		assertEquals(2, dataStore.getAllRecipes("british", 2).size());
		assertEquals(2, dataStore.getAllRecipes("british", 3).size());
		assertEquals(1, dataStore.getAllRecipes("mexican", 1).size());
		assertEquals(1, dataStore.getAllRecipes("mexican", 3).size());
		assertEquals(2, dataStore.getAllRecipes(null, 1).size());
	}

}