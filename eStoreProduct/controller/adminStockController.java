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
/*
	@GetMapping("/listStocks")
	@ResponseBody
	public String showOrders(Model model) {
		System.out.println("xyz");
		List<Object[]> stocks = ssd.fetchStockSummary();
		model.addAttribute("stocks", stocks);
		return generateStockListTable(stocks);
	}

	public String generateStockListTable(List<Object[]> stocks) {
		System.out.println("genereate stock content");
		StringBuilder htmlContent = new StringBuilder();
		htmlContent.append("<div class=\"container mt-5\">\n")
				.append("<table id=\"tableData\" class=\"table table-bordered table-hover\">\n")
				.append("<thead class=\"thead-dark\">\n").append("<tr>\n").append("<th>Product ID</th>\n")
				.append("<th>Product Title</th>\n").append("<th>Product Category</th>\n")
				.append("<th>Product Brand</th>\n").append("<th>Image URL</th>\n")
				.append("<th>Product Description</th>\n").append("<th>Reorder Level</th>\n").append("<th>SGST</th>\n")
				.append("<th>IGST</th>\n").append("<th>CGST</th>\n").append("<th>GST</th>\n")
				.append("<th>Product Price</th>\n").append("<th>Product Stock</th>\n").append("<th>Product MRP</th>\n")
				.append("</tr>\n").append("</thead>\n").append("<tbody>\n");
		System.out.println("genereate stock content2" + htmlContent);

		for (Object[] result : stocks) {
			productsModel product = (productsModel) result[0];
			String categoryTitle = (String) result[1];
			double sgst = (double) result[2];
			double igst = (double) result[3];
			double cgst = (double) result[4];
			double gst = (double) result[5];
			double price = (double) result[6];
			Integer stock = (Integer) result[7];
			Integer mrp = (Integer) result[8];

			htmlContent.append("<tr>\n").append("<td>").append(product.getId()).append("</td>\n").append("<td>")
					.append(product.getTitle()).append("</td>\n").append("<td>").append(categoryTitle).append("</td>\n")
					.append("<td>").append(product.getBrand()).append("</td>\n").append("<td>")
					.append(product.getImageUrl()).append("</td>\n").append("<td>").append(product.getDescription())
					.append("</td>\n").append("<td>").append(product.getReorderLevel()).append("</td>\n").append("<td>")
					.append(sgst).append("</td>\n").append("<td>").append(igst).append("</td>\n").append("<td>")
					.append(cgst).append("</td>\n").append("<td>").append(gst).append("</td>\n").append("<td>")
					.append(price).append("</td>\n").append("<td>").append(stock).append("</td>\n").append("<td>")
					.append(mrp).append("</td>\n").append("</tr>\n");
		}

		htmlContent.append("</tbody>\n").append("</table>\n").append("</div>\n");

		return htmlContent.toString();

	}
	
	*/
}
