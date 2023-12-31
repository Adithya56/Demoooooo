package eStoreProduct.DAO;

import java.util.Collections;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import eStoreProduct.model.Product;

@Component
public class cartDAOImp implements cartDAO {
	JdbcTemplate jdbcTemplate;
	private String insert_slam_cart = "INSERT INTO slam_cart (c_id,p_id) VALUES (?, ?)";
	private String delete_slam_cart = "DELETE FROM SLAM_CART WHERE c_id=? AND p_id=?";
	private String select_cart_products = "SELECT pd.* FROM slam_Products pd, slam_cart sc WHERE sc.c_id = ? AND sc.p_id = pd.prod_id";

	public int addToCart(int productId, int customerId) {
		int r = jdbcTemplate.update(insert_slam_cart, customerId, productId);
		if (r > 0) {
			System.out.println("inserted into cart");
			return productId;

		} else {
			return -1;
		}
	}

	public int removeFromCart(int productId, int customerId) {
		int r = jdbcTemplate.update(delete_slam_cart, customerId, productId);
		if (r > 0) {
			System.out.println("deleted from  cart");
			return productId;
		} else {
			return -1;
		}
	}

	public List<Product> getCartProds(int cust_id) {
		System.out.println(cust_id + " from model");
		try {
			List<Product> cproducts = jdbcTemplate.query(select_cart_products, new CartProductRowMapper(), cust_id);
			System.out.println(cproducts.toString());
			return cproducts;
		} catch (Exception e) {
			e.printStackTrace();
			return Collections.emptyList(); // or throw an exception if required
		}
	}

}