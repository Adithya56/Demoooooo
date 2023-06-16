package eStoreProduct.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import eStoreProduct.DAO.stockSummaryDAO;
import eStoreProduct.model.HSNCodeModel;
import eStoreProduct.model.productCategoryModel;
import eStoreProduct.model.productStockModel;
import eStoreProduct.model.productsModel;
import eStoreProduct.model.stockSummaryModel;

@Controller
public class adminStockController {
	private stockSummaryDAO ssd;
	private HSNCodeModel a;
	private productCategoryModel b;
	private productsModel c;
	private productStockModel d;

	@Autowired
	adminStockController(stockSummaryDAO stockdao, HSNCodeModel hsnm, productCategoryModel pcm, productsModel pm,
			productStockModel psm) {
		ssd = stockdao;
		a = hsnm;
		b = pcm;
		c = pm;
		d = psm;
	}

	@GetMapping("/listStock")
	@ResponseBody
	public String showStocks(Model model) {
		System.out.println("enter stock controller");
		List<stockSummaryModel> stocks = (List<stockSummaryModel>) ssd.getStocks();
		model.addAttribute("stocks", stocks);
		return generateStockSummary(stocks);
	}

	public String generateStockSummary(List<stockSummaryModel> stocks) {
		System.out.println("genereate stock content");
		StringBuilder htmlContent = new StringBuilder();
		htmlContent.append("<div class=\"container mt-5\">\n")
				.append("<table id=\"tableData\" class=\"table table-bordered table-hover\">\n")
				.append("<thead class=\"thead-dark\">\n").append("<tr>\n").append("<th>Product ID</th>\n")
				.append("<th>Product Title</th>\n").append("<th>Product Category ID</th>\n")
				.append("<th>Product GST Category ID</th>\n").append("<th>Product Brand</th>\n")
				.append("<th>Image URL</th>\n").append("<th>Product Description</th>\n")
				.append("<th>Reorder Level</th>\n").append("<th>Product Category Title</th>\n")
				.append("<th>SGST</th>\n").append("<th>IGST</th>\n").append("<th>CGST</th>\n").append("<th>GST</th>\n")
				.append("<th>Product Price</th>\n").append("<th>Product Stock</th>\n").append("<th>Product MRP</th>\n")
				.append("</tr>\n").append("</thead>\n").append("<tbody>\n");

		for (stockSummaryModel stock : stocks) {
			htmlContent.append("<tr>\n").append("<td>").append(stock.getProd_id()).append("</td>\n").append("<td>")
					.append(stock.getProd_title()).append("</td>\n").append("<td>").append(stock.getProd_prct_id())
					.append("</td>\n").append("<td>").append(stock.getProd_gstc_id()).append("</td>\n").append("<td>")
					.append(stock.getProd_brand()).append("</td>\n").append("<td>").append(stock.getImage_url())
					.append("</td>\n").append("<td>").append(stock.getProd_desc()).append("</td>\n").append("<td>")
					.append(stock.getReorderlevel()).append("</td>\n").append("<td>").append(stock.getPrct_title())
					.append("</td>\n").append("<td>").append(stock.getSgst()).append("</td>\n").append("<td>")
					.append(stock.getIgst()).append("</td>\n").append("<td>").append(stock.getCgst()).append("</td>\n")
					.append("<td>").append(stock.getGst()).append("</td>\n").append("<td>")
					.append(stock.getProd_price()).append("</td>\n").append("<td>").append(stock.getProd_stock())
					.append("</td>\n").append("<td>").append(stock.getProd_mrp()).append("</td>\n").append("</tr>\n");
		}

		htmlContent.append("</tbody>\n").append("</table>\n").append("</div>\n");

		return htmlContent.toString();

	}

}
