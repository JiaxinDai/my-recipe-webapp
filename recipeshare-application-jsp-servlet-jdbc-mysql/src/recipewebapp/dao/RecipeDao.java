package recipewebapp.dao;

import java.sql.SQLException;
import java.util.List;

import recipewebapp.model.Recipe;

public interface RecipeDao {

	void insertRecipe(Recipe recipe) throws SQLException;

	Recipe getRecipeById(int recipeId);

	List<Recipe> getAllRecipes();

	boolean deleteRecipe(int id) throws SQLException;

	boolean updateRecipe(Recipe recipe) throws SQLException;

}