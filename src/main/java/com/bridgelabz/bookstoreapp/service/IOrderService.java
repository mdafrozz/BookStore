package com.bridgelabz.bookstoreapp.service;

import java.util.List;

import com.bridgelabz.bookstoreapp.dto.OrderDTO;
import com.bridgelabz.bookstoreapp.model.OrderModel;

public interface IOrderService {
	public OrderModel addOrderDetails(int userId);
	public OrderModel getOrderDetailsByOrderId(int orderId);
	public List<OrderModel> getAllOrders();
	public String editOrderByOrderId(int orderId, OrderDTO orderDTO);
	public String deleteOrderByOrderId(int userId, int orderId);
	public String cancelOrderById(int orderId, int userId);
}
