package eStoreProduct.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import eStoreProduct.DAO.customerDAO;
import eStoreProduct.model.custCredModel;

@Controller
public class CustomerController {
	customerDAO cdao;

	@Autowired
	public CustomerController(customerDAO customerdao) {
		cdao = customerdao;
		// cartdao1 = cartdao;
	}

	@RequestMapping(value = "/profilePage")
	public String sendProfilePage(Model model, HttpSession session) {
		custCredModel cust = (custCredModel) session.getAttribute("cust`");
		System.out.println(cust.getCustId());
		model.addAttribute("cust", cust);
		return "profile";
	}

	// on clicking update Profile in profile page
	@RequestMapping(value = "/updateProfile", method = RequestMethod.POST)
	public String userupdate(@ModelAttribute("Customer") custCredModel cust, Model model, HttpSession session) {
		cdao.updatecustomer(cust);
		custCredModel custt = cdao.getCustomerById(cust.getCustId());
		System.out.print(custt.getCustLocation());
		if (custt != null) {
			model.addAttribute("cust", custt);
		}
		return "profile";
	}

	@RequestMapping(value = "/payment", method = RequestMethod.POST)
	public String showPaymentOptions(Model model, HttpSession session) {
		// custCredModel cust = (custCredModel) session.getAttribute("customer");
		// System.out.println(cust.getCustId());
		// model.addAttribute("cust", cust);
		return "payment";
	}
}