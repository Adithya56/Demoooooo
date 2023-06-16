package eStoreProduct.DAO;

import java.util.List;

import eStoreProduct.model.Product;

public interface cartDAO {
	public int addToCart(int productId, int customerId);

	public int removeFromCart(int productId, int customerId);

	public List<Product> getCartProds(int cust_id);

}