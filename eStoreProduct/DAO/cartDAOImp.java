package eStoreProduct.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import eStoreProduct.model.Product;

@Component
public class cartDAOImp implements cartDAO {

	private static final String JDBC_DRIVER = "org.postgresql.Driver";
	private static final String DB_URL = "jdbc:postgresql://192.168.110.48:5432/plf_training";
	private static final String USERNAME = "plf_training_admin";
	private static final String PASSWORD = "pff123";

	public int addToCart(int productId, int customerId) {
		try {
			System.out.println("entered dao add to cart");
			Connection connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
			String query = "INSERT INTO slam_cart (c_id,p_id) VALUES (?, ?)";
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setInt(1, customerId);
			statement.setInt(2, productId);
			int r = statement.executeUpdate();
			if (r > 0) {
				System.out.println("inserted into cart");
				return productId;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
		return -1;
	}

	public int removeFromCart(int productId, int customerId) {
		try {
			Connection connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
			String query = "DELETE FROM SLAM_CART WHERE c_id=? AND p_id=?";
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setInt(1, customerId);
			statement.setInt(2, productId);
			int r = statement.executeUpdate();
			if (r > 0) {
				System.out.println("deleted from  cart");
				return productId;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
		return -1;
	}

	public List<Product> getCartProds(int cust_id) {
		List<Product> products = new ArrayList<>();
		System.out.println(cust_id + " from model");
		try {
			Class.forName(JDBC_DRIVER);
			Connection connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
			String query = "SELECT pd.* FROM slam_Products pd, slam_cart sc WHERE sc.c_id = ? AND sc.p_id = pd.prod_id";
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setInt(1, cust_id);
			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				int prod_id = resultSet.getInt("Prod_id");
				String prod_title = resultSet.getString("prod_title");
				int prod_prct_id = resultSet.getInt("prod_prct_id");
				int prod_gstc_id = resultSet.getInt("prod_gstc_id");
				String prod_brand = resultSet.getString("prod_brand");
				String image_url = resultSet.getString("image_url");
				String prod_desc = resultSet.getString("prod_desc");
				int reorderLevel = resultSet.getInt("reorderLevel");
				Product product = new Product(prod_id, prod_title, prod_prct_id, prod_gstc_id, prod_brand, image_url,
						prod_desc, reorderLevel);
				products.add(product);
			}
			System.out.println(products.toString());
			resultSet.close();
			statement.close();
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return products;
	}

}