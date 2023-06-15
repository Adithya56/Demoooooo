package eStoreProduct.ProductsService;

import java.util.List;

import eStoreProduct.DAO.CategoryDAO;

public class CategoryService {
	private CategoryDAO categoryDAO;

	public CategoryService(CategoryDAO categoryDAO) {
		this.categoryDAO = categoryDAO;
	}

	public CategoryService() {
		// TODO Auto-generated constructor stub
	}

	public List<String> getAllCategories() {
		return categoryDAO.getAllCategories();
	}
}