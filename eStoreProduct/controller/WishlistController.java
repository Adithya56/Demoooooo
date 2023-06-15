package eStoreProduct.controller;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import eStoreProduct.DAO.WishlistDAO;
import eStoreProduct.model.Product;

@Controller
public class WishlistController {

	WishlistDAO wishlistdao;

	@Autowired
	public WishlistController(WishlistDAO wishlistimp) {
		wishlistdao = wishlistimp;
	}

	@GetMapping("/addToWishlist")
	@ResponseBody
	public String addToWishlist(@RequestParam(value = "productId", required = true) int productId, Model model)
			throws NumberFormatException, SQLException {
		System.out.println("add to wishlist called1");
		// ProductDAO pdao = new ProductDAO();
		// System.out.println("add to wishlist called2");
		return wishlistdao.addToWishlist(productId, 1) + " Added to wishlist";

	}

	@GetMapping("/removeFromWishlist")
	@ResponseBody
	public String removeFromWishlist(@RequestParam(value = "productId", required = true) int productId, Model model)
			throws NumberFormatException, SQLException {
		System.out.println("remove from wishlist called1");
		// ProductDAO pdao = new ProductDAO();
		System.out.println("remove from wishlist called2");
		return wishlistdao.removeFromWishlist(productId, 1) + " remove from wishlist";

	}

	@GetMapping("/wishlistItems")
	// @ResponseBody
	public String userWishlistItems(@RequestParam(value = "userId", required = true) int cust_id, Model model)
			throws NumberFormatException, SQLException {
		System.out.println("wishlist called1");
		List<Product> products = wishlistdao.getWishlistProds(cust_id);

		// Set the products attribute in the model
		model.addAttribute("products", products);

		// Forward to the cart.jsp view
		return "wishlistCatalog";
	}

}