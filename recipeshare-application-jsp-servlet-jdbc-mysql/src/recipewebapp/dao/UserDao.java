package recipewebapp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import recipewebapp.model.User;
import recipewebapp.utils.JDBCUtils;

public class UserDao {

	public int registerUser(User user) throws ClassNotFoundException {
		String INSERT_USERS_SQL = "INSERT INTO users"
				+ "  (username, password) VALUES "
				+ " (?, ?);";

		int result = 0;
		try (Connection connection = JDBCUtils.getConnection();
				// Step 2:Create a statement using connection object
				PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USERS_SQL)) {
			preparedStatement.setString(1, user.getUsername());
			preparedStatement.setString(2, user.getPassword());
			
			// Step 3: Execute the query or update query
			result = preparedStatement.executeUpdate();

		} catch (SQLException e) {
			// process sql exception
			JDBCUtils.printSQLException(e);
		}
		return result;
	}

}