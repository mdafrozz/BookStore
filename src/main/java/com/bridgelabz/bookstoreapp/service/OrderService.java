package com.bridgelabz.bookstoreapp.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgelabz.bookstoreapp.dto.OrderDTO;
import com.bridgelabz.bookstoreapp.exception.BookStoreException;
import com.bridgelabz.bookstoreapp.model.BookModel;
import com.bridgelabz.bookstoreapp.model.CartModel;
import com.bridgelabz.bookstoreapp.model.OrderModel;
import com.bridgelabz.bookstoreapp.model.UserModel;
import com.bridgelabz.bookstoreapp.repository.BookRepository;
import com.bridgelabz.bookstoreapp.repository.CartRepository;
import com.bridgelabz.bookstoreapp.repository.OrderRepository;
import com.bridgelabz.bookstoreapp.repository.UserRepository;
import com.bridgelabz.bookstoreapp.util.EmailSenderService;
import com.bridgelabz.bookstoreapp.util.TokenUtil;

@Service
public class OrderService implements IOrderService{

	@Autowired
	UserRepository userRepo;

	@Autowired
	BookRepository bookRepo;
	
	@Autowired
	CartRepository cartRepo;

	@Autowired
	OrderRepository orderRepo;

	@Autowired
	TokenUtil tokenUtil;

	@Autowired
	EmailSenderService emailSender;

	// Insert Order
	public OrderModel addOrderDetails(int userId) {
		Optional<UserModel> userModel = userRepo.findById(userId);
		List<CartModel> cartModel = cartRepo.getUserbyId(userId);
		OrderModel model = new OrderModel();
        for (int i=0; i<cartModel.size();i++) 
        {
        	OrderModel orderModel = new OrderModel();
        		orderModel.setAddress(userModel.get().getAddress());
        		orderModel.setBook(cartModel.get(i).getBook());
        		orderModel.setCancel(false);
        		orderModel.setDate(LocalDate.now());
        		orderModel.setPrice(cartModel.get(i).getBook().getPrice());
        		orderModel.setTotal(cartModel.get(i).getQuantity()*cartModel.get(i).getBook().getPrice());
        		orderModel.setQuantity(cartModel.get(i).getQuantity());
        		orderModel.setUser(cartModel.get(i).getUser());
				orderRepo.save(orderModel);
				model=orderModel;
        		cartRepo.deleteById(cartModel.get(i).getCartId());
        }
	
				// sending email
				//String msg = "Hi " + orderModel.getUser().getFirstName() + ",\n\nYour OrderId: "
						//+ orderModel.getOrderId() + " is confirmed.\n\n"
					//	+ "Below are your Order Details:\n\uD83D\uDED2 OrderID: " + orderModel.getOrderId()
					//	+ "\nBook Name: " + orderModel.getBook().getBookName() + "\nAuthor: "
					//	+ orderModel.getBook().getAuthorName() + "\nPrice: " + orderModel.getBook().getPrice()
					//	+ " \u20B9\nQuantity: " + cartModel.iterator().next().getQuantity() + "\nTotal Amount: " + totalAmount
					//	+ " \u20B9\n\nThank You for Placing Order\n\nRegards,\nBookStore Team";
				//emailSender.sendEmail(userModel.get().getEmail(), "Order Placed..!!", msg);
				return model;
			
	}

	// Get Order Details by Order ID
	public OrderModel getOrderDetailsByOrderId(int orderId) {
		Optional<OrderModel> orderModel = orderRepo.findById(orderId);
		if (orderModel.isPresent()) {
			return orderModel.get();
		} else
			throw new BookStoreException("Invalid orderID");
	}

	// Get all Order Details
	public List<OrderModel> getAllOrders() {
		List<OrderModel> ordersList = orderRepo.findAll();
		if (ordersList.isEmpty()) {
			throw new BookStoreException("Order List is Empty!");
		} else
			return ordersList;
	}

	// Edit Order Details by Order ID
	public String editOrderByOrderId(int orderId, OrderDTO orderDTO) {
		Optional<UserModel> userModel = userRepo.findById(orderDTO.getUser_id());
		Optional<OrderModel> orderModel = orderRepo.findById(orderId);
		Optional<BookModel> bookModel = bookRepo.findById(orderDTO.getBook_id());
		if (orderModel.isPresent() && bookModel.isPresent() && userModel.get().equals(orderModel.get().getUser())) {
			if (orderDTO.getQuantity() <= bookModel.get().getQuantity()) {

				orderModel.get().setQuantity(orderDTO.getQuantity());
				int totalAmount = bookModel.get().getPrice() * orderDTO.getQuantity();
				orderModel.get().setPrice(totalAmount);
				orderRepo.save(orderModel.get());

				// sending email
				String msg = "Hi " + orderModel.get().getUser().getFirstName() + ",\n\nYour OrderId: "
						+ orderModel.get().getOrderId() + " is updated successfully.\n\n"
						+ "Below are your updated order Details:\n\uD83D\uDED2 OrderID: "
						+ orderModel.get().getOrderId() + "\nBook Name: " + orderModel.get().getBook().getBookName()
						+ "\n  Author: " + orderModel.get().getBook().getAuthorName() + "\nPrice: "
						+ orderModel.get().getBook().getPrice() + " \u20B9\nQuantity: " + orderDTO.getQuantity()
						+ "\nTotal Amount: " + totalAmount
						+ " \u20B9\n\nThank You for Placing Order\n\nRegards,\nBookStore Team";

				//emailSender.sendEmail(orderModel.get().getUser().getEmail(), "Order Updated", msg);
				return "Order Details Updated! with Book ID: " + orderDTO.getBook_id() + ", Quantity: "
						+ orderDTO.getQuantity();
			} else
				throw new BookStoreException(
						"Quantity Exceeds, Available Book Quantity: " + bookModel.get().getQuantity());
		} else
			throw new BookStoreException("User ID | order ID | Book ID is invalid");
	}

	// delete by orderId
	public String deleteOrderByOrderId(int userId, int orderId) {
		Optional<UserModel> userModel = userRepo.findById(userId);
		Optional<OrderModel> orderModel = orderRepo.findById(orderId);
		Optional<BookModel> bookModel = bookRepo.findById(orderModel.get().getQuantity());
		if (orderModel.isPresent() && userModel.get().equals(orderModel.get().getUser())) {
			int qty = bookModel.get().getQuantity()+orderModel.get().getQuantity();
			bookModel.get().setQuantity(qty);
			bookRepo.save(bookModel.get());
			orderRepo.deleteById(orderId);
			// sending email
			//emailSender.sendEmail(orderModel.get().getUser().getEmail(), "Order Deleted!!", "Dear "
					//+ orderModel.get().getUser().getFirstName()
					//+ "\n\nYour Order has been deleted successfully from the Book Store App!!\n\nRegards,\nBookStore Team");
			return "Order Deleted successfully";
		} else
			throw new BookStoreException("Invalid Order ID | User ID");
	}

	// Cancel Order
	public String cancelOrderById(int orderId, int userId) {
		Optional<OrderModel> orderModel = orderRepo.findById(orderId);
		Optional<UserModel> userModel = userRepo.findById(userId);
		Optional<BookModel> bookModel = bookRepo.findById(orderModel.get().getBook().getBookId());
		if (orderModel.isPresent() && userModel.isPresent()) {
			// Set cancel to true
			orderModel.get().setCancel(true);
			orderRepo.save(orderModel.get());
			// restore bookqty in book table
			int quantity = bookModel.get().getQuantity() + orderModel.get().getQuantity();
			bookModel.get().setQuantity(quantity);
			bookRepo.save(bookModel.get());
			// Sending Email
			String msg = "Dear " + orderModel.get().getUser().getFirstName() + ",\n\nYour order with OrderId: "
					+ orderModel.get().getOrderId()
					+ "has been successfully cancelled.\n\n If you have changed your mind or don't wish to cancel this order please contact BookStore Team ";
			//emailSender.sendEmail(orderModel.get().getUser().getEmail(), "Order Cancelled", msg);
			return "OrderId :" + orderId;
		} else {
			throw new BookStoreException("No Orders found!!");
		}
	}
}