package eStoreProduct.controller;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import eStoreProduct.DAO.OrderDAO;
import eStoreProduct.model.orderModel;

@Controller
public class adminOrderController {

	private OrderDAO od;
	private orderModel om;

	@Autowired
	adminOrderController(OrderDAO ord, orderModel omd) {
		od = ord;
		om = omd;
	}

	@GetMapping("/listOrders")
	@ResponseBody
	public String showOrders(Model model) {
		List<orderModel> orders = od.getAllOrders();
		model.addAttribute("orders", orders);
		return generateOrderListTable(orders);
	}

	public String generateOrderListTable(List<orderModel> orders) {
		StringBuilder htmlContent = new StringBuilder();
		htmlContent.append("<div class=\"container mt-5\">\n").append("<h2>Order List</h2>\n")
				.append("<div class=\"filters\">\n")
				.append("<label for=\"dateRangeFilter\">Date Range Filter:</label>\n")
				.append("<select id=\"dateRangeFilter\" onchange=\"changeByDate()\">\n")
				.append("<option value=\"All\">All</option>\n")
				.append("<option id=\"daily\" value=\"daily\">Daily</option>\n")
				.append("<option id=\"weekly\" value=\"weekly\">Weekly</option>\n")
				.append("<option id=\"monthly\" value=\"monthly\">Monthly</option>\n").append("</select>\n")
				.append("<label for=\"processedStatusFilter\">Processed Status Filter:</label>\n")
				.append("<select id=\"processedStatusFilter\" onchange=\"processedStatusFilter()\">\n")
				.append("<option value=\"\">All</option>\n")
				.append("<option id=\"processed\" value=\"processed\">Processed</option>\n")
				.append("<option id=\"unprocessed\" value=\"unprocessed\">Unprocessed</option>\n").append("</select>\n")
				.append("</div>\n");
		htmlContent.append("<div class=\"container mt-5\">\n")
				.append("<table id=\"tableData\" class=\"table table-bordered table-hover\">\n")
				.append("<thead class=\"thead-dark\">\n").append("<tr>\n").append("<th>Order ID</th>\n")
				.append("<th>Customer ID</th>\n").append("<th>Bill Number</th>\n").append("<th>Order Date</th>\n")
				.append("<th>Total</th>\n").append("<th>GST</th>\n").append("<th>Payment Reference</th>\n")
				.append("<th>Payment Mode</th>\n").append("<th>Payment Status</th>\n")
				.append("<th>Shipping Address</th>\n").append("<th>Shipping Pincode</th>\n")
				.append("<th>Shipment Status</th>\n").append("<th>Shipment Date</th>\n")
				.append("<th>Processed By</th>\n").append("</tr>\n").append("</thead>\n").append("<tbody>\n");

		for (orderModel order : orders) {
			String processedBy = "" + order.getOrdr_processedby();
			String buttonColor = (processedBy == "NULL") ? "btn-danger" : "btn-success";

			htmlContent.append("<tr>\n").append("<td>").append(order.getId()).append("</td>\n").append("<td>")
					.append(order.getOrdr_cust_id()).append("</td>\n").append("<td>").append(order.getBillNumber())
					.append("</td>\n").append("<td>").append(order.getOrderDate()).append("</td>\n").append("<td>")
					.append(order.getTotal()).append("</td>\n").append("<td>").append(order.getGst()).append("</td>\n")
					.append("<td>").append(order.getPaymentReference()).append("</td>\n").append("<td>")
					.append(order.getPaymentMode()).append("</td>\n").append("<td>").append(order.getPaymentStatus())
					.append("</td>\n").append("<td>").append(order.getShippingAddress()).append("</td>\n")
					.append("<td>").append(order.getShippingPincode()).append("</td>\n").append("<td>")
					.append(order.getShipmentStatus()).append("</td>\n").append("<td>").append(order.getShipmentDate())
					.append("</td>\n").append("<td>");

			if (order.getOrdr_processedby() == null) {
				htmlContent.append("<button  id=\"red-button\" class=\"btn btn-danger\" data-order-id=\"")
						.append(order.getId()).append("\">Unprocessed</button>");

			} else {
				htmlContent.append("<button class=\"btn ").append(buttonColor).append("\" data-order-id=\"")
						.append(order.getId()).append("\">").append(order.getOrdr_processedby()).append("</button>");
			}

			htmlContent.append("</td>\n").append("</tr>\n");
		}

		htmlContent.append("</tbody>\n").append("</table>\n").append("</div>\n");

		return htmlContent.toString();
	}

	@GetMapping("/processOrders")
	@ResponseBody
	public String processOrders(@RequestParam(value = "orderId") long orderId,
			@RequestParam(value = "adminId") int adminId, Model model) {
		System.out.println("procvessing");
		System.out.println(orderId + "" + adminId);
		od.updateOrderProcessedBy(orderId, adminId);
		List<orderModel> orders = od.getAllOrders();
		model.addAttribute("orders", orders);
		return generateOrderListTable(orders);
	}

	@GetMapping("/loadOrdersByDate")
	@ResponseBody
	public String loadOrders(@RequestParam(value = "selectDateFilter") String selectDateFilter, Model model) {
		LocalDateTime currentDate = LocalDateTime.now();
		LocalDateTime startDate = null;
		LocalDateTime endDate = null;

		if (selectDateFilter.equals("daily")) {
			// Set the start and end date for daily filter
			startDate = currentDate.withHour(0).withMinute(0).withSecond(0);
			endDate = currentDate.withHour(23).withMinute(59).withSecond(59);
		} else if (selectDateFilter.equals("weekly")) {
			// Set the start and end date for weekly filter (assuming a week starts on Monday)
			startDate = currentDate.withHour(0).withMinute(0).withSecond(0)
					.minusDays(currentDate.getDayOfWeek().getValue() - 1);
			endDate = startDate.plusDays(6).withHour(23).withMinute(59).withSecond(59);
		} else if (selectDateFilter.equals("monthly")) {
			// Set the start and end date for monthly filter
			startDate = currentDate.withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0);
			endDate = startDate.plusMonths(1).minusDays(1).withHour(23).withMinute(59).withSecond(59);
		} else {
			// No filter selected, load all orders
			List<orderModel> orders = od.getAllOrders();
			model.addAttribute("orders", orders);
			return generateOrderListTable(orders);
		}

		List<orderModel> orders = od.loadOrdersByDate(Timestamp.valueOf(startDate), Timestamp.valueOf(endDate));
		model.addAttribute("orders", orders);
		return generateOrderDateListTable(orders);
	}

	public String generateOrderDateListTable(List<orderModel> orders) {
		StringBuilder htmlContent = new StringBuilder();
		htmlContent.append("<div class=\"container mt-5\">\n")
				.append("<table id=\"tableData\" class=\"table table-bordered table-hover\">\n")
				.append("<thead class=\"thead-dark\">\n").append("<tr>\n").append("<th>Order ID</th>\n")
				.append("<th>Customer ID</th>\n").append("<th>Bill Number</th>\n").append("<th>Order Date</th>\n")
				.append("<th>Total</th>\n").append("<th>GST</th>\n").append("<th>Payment Reference</th>\n")
				.append("<th>Payment Mode</th>\n").append("<th>Payment Status</th>\n")
				.append("<th>Shipping Address</th>\n").append("<th>Shipping Pincode</th>\n")
				.append("<th>Shipment Status</th>\n").append("<th>Shipment Date</th>\n")
				.append("<th>Processed By</th>\n").append("</tr>\n").append("</thead>\n").append("<tbody>\n");

		for (orderModel order : orders) {
			String processedBy = "" + order.getOrdr_processedby();
			String buttonColor = (processedBy == "NULL") ? "btn-danger" : "btn-success";

			htmlContent.append("<tr>\n").append("<td>").append(order.getId()).append("</td>\n").append("<td>")
					.append(order.getOrdr_cust_id()).append("</td>\n").append("<td>").append(order.getBillNumber())
					.append("</td>\n").append("<td>").append(order.getOrderDate()).append("</td>\n").append("<td>")
					.append(order.getTotal()).append("</td>\n").append("<td>").append(order.getGst()).append("</td>\n")
					.append("<td>").append(order.getPaymentReference()).append("</td>\n").append("<td>")
					.append(order.getPaymentMode()).append("</td>\n").append("<td>").append(order.getPaymentStatus())
					.append("</td>\n").append("<td>").append(order.getShippingAddress()).append("</td>\n")
					.append("<td>").append(order.getShippingPincode()).append("</td>\n").append("<td>")
					.append(order.getShipmentStatus()).append("</td>\n").append("<td>").append(order.getShipmentDate())
					.append("</td>\n").append("<td>");

			if (order.getOrdr_processedby() == null) {
				htmlContent.append("<button  id=\"red-button\" class=\"btn btn-danger\" data-order-id=\"")
						.append(order.getId()).append("\">Unprocessed</button>");

			} else {
				htmlContent.append("<button class=\"btn ").append(buttonColor).append("\" data-order-id=\"")
						.append(order.getId()).append("\">").append(order.getOrdr_processedby()).append("</button>");
			}

			htmlContent.append("</td>\n").append("</tr>\n");
		}

		htmlContent.append("</tbody>\n").append("</table>\n").append("</div>\n");

		return htmlContent.toString();
	}

}