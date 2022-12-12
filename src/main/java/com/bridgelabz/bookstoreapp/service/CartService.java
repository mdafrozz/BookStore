package com.bridgelabz.bookstoreapp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgelabz.bookstoreapp.dto.CartDTO;
import com.bridgelabz.bookstoreapp.exception.BookStoreException;
import com.bridgelabz.bookstoreapp.model.BookModel;
import com.bridgelabz.bookstoreapp.model.CartModel;
import com.bridgelabz.bookstoreapp.model.UserModel;
import com.bridgelabz.bookstoreapp.repository.BookRepository;
import com.bridgelabz.bookstoreapp.repository.CartRepository;
import com.bridgelabz.bookstoreapp.repository.UserRepository;
import com.bridgelabz.bookstoreapp.util.TokenUtil;

@Service
public class CartService implements ICartService{

	@Autowired
	CartRepository cartRepo;

	@Autowired
	TokenUtil tokenUtil;

	@Autowired
	UserRepository userRepo;

	@Autowired
	BookRepository bookRepo;

	// Adding Cart Data with userId
	public CartModel addCart(CartDTO cartDTO) {
		Optional<UserModel> userModel = userRepo.findById(cartDTO.getUser_id());
		Optional<BookModel> bookModel = bookRepo.findById(cartDTO.getBook_id());
		if (userModel.isPresent() && bookModel.isPresent()) {
			if (cartDTO.getQuantity() <= bookModel.get().getQuantity()) {
				CartModel cartModel = new CartModel(userModel.get(), bookModel.get(), cartDTO.getQuantity());
				return cartRepo.save(cartModel);
			} else
				throw new BookStoreException(
						"Quantity Exceeds, Available Book Quantity: " + bookModel.get().getQuantity());
		} else
			throw new BookStoreException("Invalid UserId/ BookId");
	}

	// Get All cart List
	public List<CartModel> allCartList() {
		List<CartModel> cartList = cartRepo.findAll();
		if (cartList.isEmpty()) {
			throw new BookStoreException("No Items in your cart!!");
		} else
			return cartList;
	}

	// Get Cart Data By cart ID
	public CartModel getCartDetailsByCartId(int cartId) {
		Optional<CartModel> cartModel = cartRepo.findById(cartId);
		if (cartModel.isPresent()) {
			return cartModel.get();
		} else
			throw new BookStoreException("Invalid CartID");
	}

	// Edit Cart details with Cart ID
	public CartModel editCartByCartId(int cartId, CartDTO cartDTO) {
		Optional<CartModel> cartModel = cartRepo.findById(cartId);
		Optional<BookModel> bookModel = bookRepo.findById(cartDTO.getBook_id());
		Optional<UserModel> userModel = userRepo.findById(cartDTO.getUser_id());
		if (cartModel.isPresent()) {
			if (bookModel.isPresent() && userModel.isPresent()) {
				if (cartDTO.getQuantity() <= bookModel.get().getQuantity()) {
					//CartModel cart = new CartModel(userModel.get(), bookModel.get(), cartDTO.getQuantity());
					cartModel.get().setQuantity(cartDTO.getQuantity());
					return cartRepo.save(cartModel.get());
				} else
					throw new BookStoreException(
							"Quantity Exceeds, Available Book Quantity: " + bookModel.get().getQuantity());
			} else
				throw new BookStoreException("Invalid BookId/ UserId");
		} else
			throw new BookStoreException("Invalid Cart ID");
	}

	// Delete by Cart ID
	public String deleteCartByCartId(int cartId) {
		Optional<CartModel> cartModel = cartRepo.findById(cartId);
		if (cartModel.isEmpty()) {
			throw new BookStoreException("Invalid CartID");
		} else {
			cartRepo.deleteById(cartId);
			return "Deleted CartID: " + cartId;
		}
	}
	
	// Get the User Details by Email Address
		public List<CartModel> getUserbyId(int userId) {
			List<CartModel> cartList = cartRepo.getUserbyId(userId);
			if (cartList.isEmpty()) {
				throw new BookStoreException("user does not exist");
			} else
				return cartList;
		}
}
