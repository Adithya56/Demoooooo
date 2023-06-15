package eStoreProduct.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import eStoreProduct.model.ProdStock;

public class prodStockImp implements ProdStockDAO {
	private static final String JDBC_DRIVER = "org.postgresql.Driver";
	private static final String DB_URL = "jdbc:postgresql://192.168.110.48:5432/plf_training";
	private static final String USERNAME = "plf_training_admin";
	private static final String PASSWORD = "pff123";

	@Override
	public List<ProdStock> getAllProdStocks() {
		List<ProdStock> prodStocks = new ArrayList<>();

		try {
			Class.forName(JDBC_DRIVER);
			Connection connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);

			String query = "SELECT * FROM slam_productstock";
			PreparedStatement statement = connection.prepareStatement(query);
			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				int prodId = resultSet.getInt("prod_id");
				double prodPrice = resultSet.getDouble("prod_price");
				int prodStock = resultSet.getInt("prod_stock");
				double prodMrp = resultSet.getDouble("prod_mrp");

				ProdStock prodStockObj = new ProdStock(prodId, prodPrice, prodStock, prodMrp);
				prodStocks.add(prodStockObj);
			}

			resultSet.close();
			statement.close();
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return prodStocks;
	}

	@Override
	public ProdStock getProdStockById(int prodId) {
		ProdStock prodStock = null;

		try {
			Class.forName(JDBC_DRIVER);
			Connection connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);

			String query = "SELECT * FROM slam_productstock WHERE prod_id = ?";
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setInt(1, prodId);
			ResultSet resultSet = statement.executeQuery();

			if (resultSet.next()) {
				double prodPrice = resultSet.getDouble("prod_price");
				int prodStockValue = resultSet.getInt("prod_stock");
				double prodMrp = resultSet.getDouble("prod_mrp");

				prodStock = new ProdStock(prodId, prodPrice, prodStockValue, prodMrp);
			}

			resultSet.close();
			statement.close();
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return prodStock;
	}

	public double getProdPriceById(int prodId) {
		double prodPrice = 0.0;

		try {
			Class.forName(JDBC_DRIVER);
			Connection connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);

			String query = "SELECT prod_price FROM slam_productstock WHERE prod_id = ?";
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setInt(1, prodId);
			ResultSet resultSet = statement.executeQuery();

			if (resultSet.next()) {
				prodPrice = resultSet.getDouble("prod_price");
			}

			resultSet.close();
			statement.close();
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return prodPrice;
	}

	@Override
	public double getProdMrpById(int prodId) {
		double prodMrp = 0.0;

		try {
			Class.forName(JDBC_DRIVER);
			Connection connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);

			String query = "SELECT prod_mrp FROM slam_productstock WHERE prod_id = ?";
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setInt(1, prodId);
			ResultSet resultSet = statement.executeQuery();

			if (resultSet.next()) {
				prodMrp = resultSet.getDouble("prod_mrp");
			}

			resultSet.close();
			statement.close();
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return prodMrp;

	}
}
