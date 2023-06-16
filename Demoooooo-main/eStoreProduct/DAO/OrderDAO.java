package eStoreProduct.DAO;

import java.sql.Timestamp;
import java.util.List;

import eStoreProduct.model.orderModel;

public interface OrderDAO {
	void insertOrder(orderModel order);

	List<orderModel> getAllOrders();

	List<orderModel> loadOrdersByDate(Timestamp startDate, Timestamp endDate);

	void updateOrderProcessedBy(Long orderId, Integer processedBy);
}