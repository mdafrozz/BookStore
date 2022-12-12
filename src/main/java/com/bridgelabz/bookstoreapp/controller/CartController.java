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

import com.bridgelabz.bookstoreapp.dto.CartDTO;
import com.bridgelabz.bookstoreapp.dto.ResponseDTO;
import com.bridgelabz.bookstoreapp.model.CartModel;
import com.bridgelabz.bookstoreapp.service.ICartService;

@CrossOrigin(allowedHeaders = "*", origins = "*")
@RestController
@RequestMapping("/cart")
public class CartController {

	@Autowired
	ICartService icartService;

	// Add Cart Details to the UserID
	@PostMapping("/insert")
	public ResponseEntity<ResponseDTO> addToCart(@Valid @RequestBody CartDTO cartDTO) {
		CartModel cartModel = icartService.addCart(cartDTO);
		ResponseDTO responseDTO = new ResponseDTO("Cart Details Added", cartModel);
		return new ResponseEntity<>(responseDTO, HttpStatus.OK);
	}

	// Get All Cart Details
	@GetMapping("/getall")
	public ResponseEntity<ResponseDTO> getAllCart() {
		List<CartModel> cartList = icartService.allCartList();
		ResponseDTO responseDTO = new ResponseDTO("Total Books in cart: " + cartList.size(), cartList);
		return new ResponseEntity<>(responseDTO, HttpStatus.OK);
	}

	// Get Cart Data by CartId
	@GetMapping("/getby/{cartId}")
	public ResponseEntity<ResponseDTO> getCartDataByID(@PathVariable int cartId) {
		CartModel cartModel = icartService.getCartDetailsByCartId(cartId);
		ResponseDTO responseDTO = new ResponseDTO("Cart Details with Cart ID: " + cartId, cartModel);
		return new ResponseEntity<>(responseDTO, HttpStatus.OK);
	}

	// Update Cart Details(Book and Quantity) By CartId
	@PutMapping("/updateby/{cartId}")
	public ResponseEntity<ResponseDTO> updateCartById(@PathVariable int cartId, @Valid @RequestBody CartDTO cartDTO) {
		CartModel response = icartService.editCartByCartId(cartId, cartDTO);
		ResponseDTO responseDTO = new ResponseDTO("Updated Cart Data with Cart ID: " + cartId, response);
		return new ResponseEntity<>(responseDTO, HttpStatus.OK);
	}

	// Delete Cart by Cart ID
	@DeleteMapping("/delete/{cartId}")
	public ResponseEntity<ResponseDTO> deleteCartById(@PathVariable int cartId) {
		String response = icartService.deleteCartByCartId(cartId);
		ResponseDTO responseDTO = new ResponseDTO("Cart Deleted Successfully", response);
		return new ResponseEntity<>(responseDTO, HttpStatus.OK);
	}
}
