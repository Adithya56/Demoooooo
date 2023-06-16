package eStoreProduct.DAO;

import java.util.List;

import eStoreProduct.model.Category;
import eStoreProduct.model.Product;

public interface ProductDAO {

	public boolean createProduct(Product p);

	public List<String> getAllProductCategories();

	public List<Product> getProductsByCategory(Integer category);

	public List<Product> getAllProducts();

	public List<Category> getAllCategories();

	public Product getProductById(Integer productId);

}