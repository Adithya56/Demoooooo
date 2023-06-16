package eStoreProduct.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import eStoreProduct.DAO.ProductDAO;
import eStoreProduct.DAO.cartDAO;
import eStoreProduct.model.Product;
import eStoreProduct.model.custCredModel;

@Controller
public class CartController {
	cartDAO cartimp;
	private final ProductDAO pdaoimp;
	List<Product> alist = new ArrayList<>();

	@Autowired
	public CartController(cartDAO cartdao, ProductDAO productdao) {
		cartimp = cartdao;
		pdaoimp = productdao;
	}

	@GetMapping("/addToCart")
	@ResponseBody
	public String addToCart(@RequestParam(value = "productId", required = true) int productId, Model model,
			HttpSession session) throws NumberFormatException, SQLException {
		custCredModel cust1 = (custCredModel) session.getAttribute("customer");
		if (cust1 != null) {
			System.out.println("add to cart called1");
			// ProductDAO pdao = new ProductDAO();
			System.out.println("add to cart called2");
			return cartimp.addToCart(productId, cust1.getCustId()) + " Added to cart";
		} else {
			Product product = pdaoimp.getProductById(productId);
			System.out.println("added to cart " + product.getProd_id());
			System.out.println(product);
			alist.add(product);
			return "added to cart";

		}
	}

	@RequestMapping(value = "/cartDisplay", method = RequestMethod.GET)
	public String getSignUpPage(Model model) {
		System.out.println("product description Page");

		// call the view
		return "cartItems";
	}

	@GetMapping("/removeFromCart")
	@ResponseBody
	public String removeFromCart(@RequestParam(value = "productId", required = true) int productId, Model model,
			HttpSession session) throws NumberFormatException, SQLException {
		System.out.println("pid remove from cart  " + productId);

		custCredModel cust1 = (custCredModel) session.getAttribute("customer");
		if (cust1 != null) {
			System.out.println("remove from cart login");
			return cartimp.removeFromCart(productId, 1) + " remove from cart";
		} else {
			// Product product=pdaoimp.getProductById(productId);
			System.out.println("remove from cart nonlogin");
			for (Product p : alist) {
				if (p.getProd_id() == productId)

					alist.remove(p);
			}

			return "remove from cart";
		}

	}

	@GetMapping("/cartItems")
	public String userCartItems(@RequestParam(value = "userId", required = true) int cust_id, Model model,
			HttpSession session) throws NumberFormatException, SQLException {
		System.out.println("carts called1");
		// ProductDAO pdao = new ProductDAO();
		custCredModel cust1 = (custCredModel) session.getAttribute("customer");
		if (cust1 != null) {
			List<Product> products = cartimp.getCartProds(cust_id);

			// Set the products attribute in the model
			model.addAttribute("products", products);

			// Forward to the cart.jsp view
			return "cart";
		} else {
			// Set the products attribute in the model
			model.addAttribute("products", alist);
			for (Product p : alist) {
				System.out.println(p);
			}
			// Forward to the cart.jsp view
			return "cart";

		}
	}

}
