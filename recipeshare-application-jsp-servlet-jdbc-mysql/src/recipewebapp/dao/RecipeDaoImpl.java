package recipewebapp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import recipewebapp.model.Recipe;
import recipewebapp.utils.JDBCUtils;

/**
 * This DAO class provides CRUD database operations for the table recipes in the
 * database.
 *
 */

public class RecipeDaoImpl implements RecipeDao {

	private static final String INSERT_RECIPES_SQL = "INSERT INTO recipes"
			+ "  (title, owner, filename, description,  publication_date, likes) VALUES " + " (?, ?, ?, ?, ?, ?);";

	private static final String SELECT_RECIPE_BY_ID = "select id,title,owner,filename,description,publication_date,likes from recipes where id =?";
	private static final String SELECT_ALL_RECIPES = "select * from recipes";
	private static final String DELETE_RECIPE_BY_ID = "delete from recipes where id = ?;";
	private static final String UPDATE_RECIPE = "update recipes set title = ?, owner= ?, filename =?, description =?, publication_date = ?, likes = ? where id = ?;";
	private static final String SELECT_MOST_POPULAR_RECIPE = "SELECT id,MAX(likes) FROM recipes GROUP BY id;";
	
	public RecipeDaoImpl() {}
	
	@Override
	public void insertRecipe(Recipe recipe) throws SQLException {
		// try-with-resource statement will auto close the connection.
		try (Connection connection = JDBCUtils.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(INSERT_RECIPES_SQL)) {
			preparedStatement.setString(1, recipe.getTitle());
			preparedStatement.setString(2, recipe.getOwner());
			preparedStatement.setString(3, recipe.getFilename());
			preparedStatement.setString(4, recipe.getDescription());
			preparedStatement.setDate(5, JDBCUtils.getSQLDate(recipe.getPublicationDate()));
			preparedStatement.setInt(6, recipe.getLikes());
			System.out.println(preparedStatement);
			preparedStatement.executeUpdate();
		} catch (SQLException exception) {
			JDBCUtils.printSQLException(exception);
		}
	}

	@Override
	public Recipe getRecipeById(int recipeId) {
		Recipe recipe = null;
		// Step 1: Establishing a Connection
		try (Connection connection = JDBCUtils.getConnection();
				// Step 2:Create a statement using connection object
				PreparedStatement preparedStatement = connection.prepareStatement(SELECT_RECIPE_BY_ID);) {
			preparedStatement.setInt(1, recipeId);
			System.out.println(preparedStatement);
			// Step 3: Execute the query or update query
			ResultSet rs = preparedStatement.executeQuery();

			// Step 4: Process the ResultSet object.
			while (rs.next()) {
				int id = rs.getInt("id");
				String title = rs.getString("title");
				String owner = rs.getString("owner");
				String filename = rs.getString("filename");
				String description = rs.getString("description");
				LocalDate publicationDate = rs.getDate("publication_date").toLocalDate();
				int likes = rs.getInt("likes");

				recipe = new Recipe(id, title, owner, filename, description, publicationDate, likes);
			}
		} catch (SQLException exception) {
			JDBCUtils.printSQLException(exception);
		}
		return recipe;
	}
	
	@Override
	public Recipe getMostPopularRecipe() {
		List<Recipe> recipes = getAllRecipes();
		
		return recipes.stream().max(Comparator.comparing(Recipe::getLikes)).get();
	}

	@Override
	public List<Recipe> getAllRecipes() {

		// using try-with-resources to avoid closing resources (boiler plate code)
		List<Recipe> recipes = new ArrayList<>();

		// Step 1: Establishing a Connection
		try (Connection connection = JDBCUtils.getConnection();

				// Step 2:Create a statement using connection object
				PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_RECIPES);) {
			System.out.println(preparedStatement);
			// Step 3: Execute the query or update query
			ResultSet rs = preparedStatement.executeQuery();

			// Step 4: Process the ResultSet object.
			while (rs.next()) {
				int id = rs.getInt("id");
				String title = rs.getString("title");
				String owner = rs.getString("owner");
				String filename = rs.getString("filename");
				String description = rs.getString("description");
				LocalDate publicationDate = rs.getDate("publication_date").toLocalDate();
				int likes = rs.getInt("likes");
				recipes.add(new Recipe(id, title, owner, filename, description, publicationDate, likes));
			}
		} catch (SQLException exception) {
			JDBCUtils.printSQLException(exception);
		}
		return recipes;
	}

	@Override
	public boolean deleteRecipe(int id) throws SQLException {
		boolean rowDeleted;
		try (Connection connection = JDBCUtils.getConnection();
				PreparedStatement statement = connection.prepareStatement(DELETE_RECIPE_BY_ID);) {
			statement.setInt(1, id);
			rowDeleted = statement.executeUpdate() > 0;
		}
		return rowDeleted;
	}

	@Override
	public boolean updateRecipe(Recipe recipe) throws SQLException {
		boolean rowUpdated;
		try (Connection connection = JDBCUtils.getConnection();
				PreparedStatement statement = connection.prepareStatement(UPDATE_RECIPE);) {
			statement.setString(1, recipe.getTitle());
			statement.setString(2, recipe.getOwner());
			statement.setString(3, recipe.getFilename());
			statement.setString(4, recipe.getDescription());
			statement.setInt(5, recipe.getLikes());
			statement.setDate(6, JDBCUtils.getSQLDate(recipe.getPublicationDate()));
			statement.setInt(7, recipe.getId());
			rowUpdated = statement.executeUpdate() > 0;
		}
		return rowUpdated;
	}
	
	public void incrementRecipeLikes(final int recipeId) throws SQLException {
		final Recipe recipeToUpdate = getRecipeById(recipeId);
		try (Connection connection = JDBCUtils.getConnection();
			PreparedStatement statement = connection.prepareStatement(UPDATE_RECIPE);) {
			statement.setString(1, recipeToUpdate.getTitle());
			statement.setString(2, recipeToUpdate.getOwner());
			statement.setString(3, recipeToUpdate.getFilename());
			statement.setString(4, recipeToUpdate.getDescription());
			statement.setDate(5, JDBCUtils.getSQLDate(recipeToUpdate.getPublicationDate()));
			statement.setInt(6, recipeToUpdate.getLikes()+1);
			statement.setInt(7, recipeToUpdate.getId());
			statement.executeUpdate();
		}
	}
}