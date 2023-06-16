package eStoreProduct.DAO;

import java.util.List;

import eStoreProduct.model.Product;

public interface WishlistDAO {

	public int addToWishlist(int productId, int customerId);

	public int removeFromWishlist(int productId, int customerId);

	public List<Product> getWishlistProds(int cust_id);

}