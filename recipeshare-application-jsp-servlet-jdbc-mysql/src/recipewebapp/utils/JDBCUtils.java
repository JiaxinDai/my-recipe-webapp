package recipewebapp.utils;

import java.sql.Connection;

import java.sql.PreparedStatement;

import java.sql.ResultSet;

import java.sql.ResultSetMetaData;

import java.sql.SQLException;

import java.util.HashMap;

import java.util.Iterator;

import java.util.Map;



import javax.sql.DataSource;



public class JdbcUtils {



	private DataSource dataSource;

	private int fetchSize = 1000;



	public JdbcUtils(DataSource dataSource) {

		this.dataSource = dataSource;

	}



	public JdbcUtils(DataSource dataSource, int fetchSize) {

		this.dataSource = dataSource;

		this.fetchSize = fetchSize;

	}



	public void exec(final String sql, final Iterator<Object[]> paramsIterator, final Handler handler)

			throws Exception {

		Connection connection = dataSource.getConnection();

		PreparedStatement statement = null;

		ResultSet resultSet = null;

		try {

			statement = connection.prepareStatement(sql);

			statement.setFetchSize(fetchSize);



			if (paramsIterator != null) {

				int count = 0;

				while (paramsIterator.hasNext()) {

					Object[] params = paramsIterator.next();

					for (int i = 0; i < params.length; i++) {

						statement.setObject(i + 1, params[i]);

					}

					statement.addBatch();

					if (count++ % fetchSize == 0) {

						statement.executeBatch();

					}

				}

			}

			statement.executeBatch();

			connection.commit();

		} catch (SQLException e) {

			connection.rollback();

			throw e;

		} finally {

			if (resultSet != null) {

				try {

					resultSet.close();

				} catch (SQLException e) {

				}

			}

			if (statement != null) {

				try {

					statement.close();

				} catch (SQLException e) {

				}

			}

			if (connection != null) {

				try {

					connection.close();

				} catch (SQLException e) {

				}

			}

		}



	}



	public void exec(final String sql, final Object[] params, final Handler handler) throws Exception {

		Connection connection = dataSource.getConnection();

		PreparedStatement statement = null;

		ResultSet resultSet = null;

		try {

			statement = connection.prepareStatement(sql);

			statement.setFetchSize(fetchSize);

			if (params != null) {

				for (int i = 0; i < params.length; i++) {

					statement.setObject(i + 1, params[i]);

				}

			}



			resultSet = statement.executeQuery();

			handler.handle(resultSet);

		} catch (SQLException e) {

			throw e;

		} finally {

			if (resultSet != null) {

				try {

					resultSet.close();

				} catch (SQLException e) {

				}

			}

			if (statement != null) {

				try {

					statement.close();

				} catch (SQLException e) {

				}

			}

			if (connection != null) {

				try {

					connection.close();

				} catch (SQLException e) {

				}

			}

		}



	}



	public void exec(final String sql, final Object[] params, final DataHandler handler) throws Exception {

		exec(sql, params, new Handler() {



			@Override

			public void handle(ResultSet resultSet) throws Exception {

				ResultSetMetaData rsm = resultSet.getMetaData();

				final int columnCount = rsm.getColumnCount();

				String[] columnName = new String[columnCount];

				for (int i = 0; i < columnCount; i++) {

					columnName[i] = rsm.getColumnName(i + 1);

				}

				int row = 1;

				Map<String, Object> data = null;

				while (resultSet.next()) {

					data = new HashMap<>(columnCount);

					for (int i = 0; i < columnCount; i++) {

						data.put(columnName[i], resultSet.getObject(i + 1));

					}

					handler.handle(row++, data);

				}

			}



		});



	}



	public static interface Handler {

		public void handle(ResultSet resultSet) throws Exception;

	}



	public static interface DataHandler {

		public void handle(int row, Map<String, Object> data) throws Exception;

	}

}
