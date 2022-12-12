package com.bridgelabz.bookstoreapp.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.bookstoreapp.dto.OrderDTO;
import com.bridgelabz.bookstoreapp.dto.ResponseDTO;
import com.bridgelabz.bookstoreapp.model.OrderModel;
import com.bridgelabz.bookstoreapp.service.IOrderService;

@CrossOrigin(allowedHeaders = "*", origins = "*")
@RestController
@RequestMapping("/orders")
public class OrderController {

	@Autowired
	IOrderService iorderService;

	// Add Order Details and send email
	@PostMapping("/add/{userId}")
	public ResponseEntity<ResponseDTO> addOrderDetails(@PathVariable int userId) {
		OrderModel orderModel = iorderService.addOrderDetails(userId);
		ResponseDTO responseDTO = new ResponseDTO("Order Details Added", orderModel);
		return new ResponseEntity<>(responseDTO, HttpStatus.OK);
	}

	// GetAll Orders
	@GetMapping("/getall")
	public ResponseEntity<ResponseDTO> getAllOrders() {
		List<OrderModel> orderModel = iorderService.getAllOrders();
		ResponseDTO responseDTO = new ResponseDTO("All Records retrieved Successfully !", orderModel);
		return new ResponseEntity<>(responseDTO, HttpStatus.OK);
	}

	// Get Order details by order ID
	@GetMapping("/getby/{orderId}")
	public ResponseEntity<ResponseDTO> getOrderDataByOrderID(@PathVariable int orderId) {
		OrderModel orderModel = iorderService.getOrderDetailsByOrderId(orderId);
		ResponseDTO responseDTO = new ResponseDTO("Order Details with Order ID: " + orderId, orderModel);
		return new ResponseEntity<>(responseDTO, HttpStatus.OK);
	}

	// Update Order Details By OrderID
	@PutMapping("/update/{orderId}")
	public ResponseEntity<ResponseDTO> updateOrderById(@PathVariable int orderId,
			@Valid @RequestBody OrderDTO orderDTO) {
		String response = iorderService.editOrderByOrderId(orderId, orderDTO);
		ResponseDTO responseDTO = new ResponseDTO("Updated Order Details with Order ID: " + orderId, response);
		return new ResponseEntity<>(responseDTO, HttpStatus.OK);
	}

	// Delete Order By OrderID
	@DeleteMapping("/delete/{userId}/{orderId}")
	public ResponseEntity<ResponseDTO> updateDeleteById(@PathVariable int userId, @PathVariable int orderId) {
		String response = iorderService.deleteOrderByOrderId(userId, orderId);
		ResponseDTO responseDTO = new ResponseDTO("Status of order Id: " + orderId, response);
		return new ResponseEntity<>(responseDTO, HttpStatus.OK);
	}

	// Cancel Order
	@PutMapping("/cancelorder/{userId}/{orderId}")
	public ResponseEntity<ResponseDTO> cancelOrderById(@PathVariable int orderId, @PathVariable int userId) {
		String response = iorderService.cancelOrderById(orderId, userId);
		ResponseDTO responseDTO = new ResponseDTO("Order Cancelled Successfully !!", response);
		return new ResponseEntity<>(responseDTO, HttpStatus.ACCEPTED);
	}
}
