package eStoreProduct.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import eStoreProduct.DAO.adminDAO;
import eStoreProduct.model.adminModel;

@Controller
public class adminController {
	adminDAO adao;

	@Autowired
	public adminController(adminDAO admindao) {
		adao = admindao;
	}

	@RequestMapping(value = "/adminLogin", method = RequestMethod.GET)
	public String getAdminLogin(Model model) {
		System.out.println("Admin Page");

		// call the view
		return "adminSignIn";
	}

	@RequestMapping(value = "/adminSignOk", method = RequestMethod.GET)
	public String getHomeFinal(@RequestParam("em") String email, @RequestParam("ps") String psd, Model model,
			HttpSession session) {
		System.out.println("checking admin sign in");

		adminModel ad = adao.getAdmin(email, psd);

		model.addAttribute("admin", ad);
		System.out.println("checking sign in 1");

		if (ad != null) {
			session.setAttribute("admin", ad); // Store customer object in the session

		}
		return "admin";
	}

}