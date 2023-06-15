package eStoreProduct.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CategoryDAO {
	private static final String JDBC_DRIVER = "org.postgresql.Driver";
	private static final String DB_URL = "jdbc:postgresql://192.168.110.48:5432/plf_training";
	private static final String USERNAME = "plf_training_admin";
	private static final String PASSWORD = "pff123";

	public List<String> getAllCategories() {
		List<String> categories = new ArrayList<>();

		try {
			// Load the JDBC driver
			Class.forName(JDBC_DRIVER);

			// Establish the database connection
			Connection connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);

			// Prepare the SQL query
			String query = "SELECT prct_title FROM slam_productCategories";
			PreparedStatement statement = connection.prepareStatement(query);

			// Execute the query
			ResultSet resultSet = statement.executeQuery();

			// Process the result set
			while (resultSet.next()) {
				// Retrieve category information from the result set
				String category = resultSet.getString("prct_title");

				// Add the category to the list
				categories.add(category);
			}

			// Close the result set, statement, and connection
			resultSet.close();
			statement.close();
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return categories;
	}
}