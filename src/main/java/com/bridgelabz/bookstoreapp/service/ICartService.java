package com.bridgelabz.bookstoreapp.service;

import java.util.List;

import com.bridgelabz.bookstoreapp.dto.CartDTO;
import com.bridgelabz.bookstoreapp.model.CartModel;

public interface ICartService {
	public CartModel addCart(CartDTO cartDTO);
	public List<CartModel> allCartList();
	public CartModel getCartDetailsByCartId(int cartId);
	public CartModel editCartByCartId(int cartId,CartDTO cartDTO);
	public String deleteCartByCartId(int cartId);
	public List<CartModel> getUserbyId(int userId);
}
