package eStoreProduct.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import eStoreProduct.DAO.ProductDAO;
import eStoreProduct.DAO.stockSummaryDAO;
import eStoreProduct.model.Product;
import eStoreProduct.model.stockSummaryModel;

@Controller
public class adminMasterEntryController {

	private stockSummaryDAO ssd;

	private final ProductDAO pdaoimp;

	@Autowired
	adminMasterEntryController(stockSummaryDAO stockdao, ProductDAO productdao) {
		ssd = stockdao;
		pdaoimp = productdao;

	}

	@GetMapping("/showEditableStocks")
	@ResponseBody
	public String showEditableStocks(Model model) {
		System.out.println("enter masterEntry controller");
		List<stockSummaryModel> stocks1 = (List<stockSummaryModel>) ssd.getStocks();
		model.addAttribute("stocks1", stocks1);
		return generateEditableStockSummary(stocks1);
	}

	public String generateEditableStockSummary(List<stockSummaryModel> stocks1) {
		System.out.println("genereate MasterEntry content");
		StringBuilder htmlContent = new StringBuilder();
		htmlContent.append("<table id=\"tableData\" class=\"table table-bordered table-hover\">\n")
				.append("<thead class=\"thead-dark\">\n").append("<tr>\n").append("<th>Product ID</th>\n")
				.append("<th>Product Title</th>\n").append("<th>Product Category ID</th>\n")
				.append("<th>Product GST Category ID</th>\n").append("<th>Product Brand</th>\n")
				.append("<th>Image URL</th>\n").append("<th>Product Description</th>\n")
				.append("<th>Reorder Level</th>\n").append("<th>Product Category Title</th>\n")
				.append("<th>SGST</th>\n").append("<th>IGST</th>\n").append("<th>CGST</th>\n").append("<th>GST</th>\n")
				.append("<th>Product Price</th>\n").append("<th>Product Stock</th>\n").append("<th>Product MRP</th>\n")
				.append("<th>EDIT</th>\n").append("</tr>\n").append("</thead>\n").append("<tbody>\n");

		for (stockSummaryModel stock : stocks1) {

			htmlContent.append("<tr>\n").append("<td>").append(stock.getProd_id()).append("</td>\n").append("<td>")
					.append(stock.getProd_title()).append("</td>\n").append("<td>").append(stock.getProd_prct_id())
					.append("</td>\n").append("<td>").append("<input type=\"text\" id=\"prodGstcId-input\" value=\"")
					.append(stock.getProd_gstc_id()).append("\">").append("</td>\n").append("<td>")
					.append(stock.getProd_brand()).append("</td>\n").append("<td>")
					.append("<input type=\"text\" id=\"imageUrl-input\" value=\"").append(stock.getImage_url())
					.append("\">").append("</td>\n").append("<td>")
					.append("<input type=\"text\" id=\"prodDesc-input\" value=\"").append(stock.getProd_desc())
					.append("\">").append("</td>\n").append("<td>")
					.append("<input type=\"text\" id=\"reorderlevel-input\" value=\"").append(stock.getReorderlevel())
					.append("\">").append("</td>\n").append("<td>").append(stock.getPrct_title()).append("</td>\n")
					.append("<td>").append(stock.getSgst()).append("</td>\n").append("<td>").append(stock.getIgst())
					.append("</td>\n").append("<td>").append(stock.getCgst()).append("</td>\n").append("<td>")
					.append(stock.getGst()).append("</td>\n").append("<td>").append(stock.getProd_price())
					.append("</td>\n").append("<td>").append("<input type=\"text\" id=\"prodStock-input\" value=\"")
					.append(stock.getProd_stock()).append("\">").append("</td>\n").append("<td>")
					.append("<input type=\"text\" id=\"prodMrp-input\" value=\"").append(stock.getProd_mrp())
					.append("\">").append("</td>\n").append("<td>")
					.append("<button id=\"edit-button\" class=\"btn btn-success\" data-gstc-id=\"")
					.append(stock.getProd_gstc_id()).append("\" data-imageurl-id=\"").append(stock.getImage_url())
					.append("\" data-prod-id=\"").append(stock.getProd_id()).append("\" data-reorderlevel-id=\"")
					.append(stock.getReorderlevel()).append("\" data-stock-id=\"").append(stock.getProd_stock())
					.append("\" data-mrp-id=\"").append(stock.getProd_mrp()).append("\">UPDATE</button>")
					.append("</td>\n").append("</tr>\n");

		}

		htmlContent.append("</tbody>\n").append("</table>\n").append("</div>\n");

		return htmlContent.toString();

	}

	@GetMapping("/updateMasterEntryTables")
	@ResponseBody
	public String showUpdatedEditableStocks(@RequestParam(value = "gstcid") int gstcid,
			@RequestParam(value = "imageurl") String imageurl, @RequestParam(value = "prodid") int prodid,
			@RequestParam(value = "reorderlevel") int reorderlevel, @RequestParam(value = "stock") int updatedstock,
			@RequestParam(value = "mrp") double mrp, Model model) {
		System.out.println("enter updated masterEntry controller");
		ssd.updateStocks(prodid, imageurl, gstcid, reorderlevel, updatedstock, mrp);
		List<stockSummaryModel> stocks1 = (List<stockSummaryModel>) ssd.getStocks();
		System.out.println("enter updated masterEntry controller23");
		model.addAttribute("stocks1", stocks1);
		return generateEditableStockSummary(stocks1);
	}

	@GetMapping("/addNewProductInTheMasterEntry")
	public String addNewProductInMasterEntry(Model model) {
		System.out.println("enter addNewProductController controller");

		return "addNewProduct";
	}

	@RequestMapping(value = "/createNewProduct", method = RequestMethod.POST)
	public String createProductNew(@Validated Product prod, Model model) {
		pdaoimp.createProduct(prod);
		return "AddedProduct";

	}
}