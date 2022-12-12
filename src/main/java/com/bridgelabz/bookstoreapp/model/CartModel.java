package com.bridgelabz.bookstoreapp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name="cart")
@NoArgsConstructor
public class CartModel {

	@Id
	@GeneratedValue
	@Column(name = "cartId")
	private int cartId;
	@ManyToOne
    @JoinColumn(name="user_id")
	private UserModel user;
	@ManyToOne
    @JoinColumn(name="book_id")
	private BookModel book;
	private int quantity;
	
	 public CartModel(UserModel user, BookModel book, int quantity) {
	        this.user = user;
	        this.book = book;
	        this.quantity = quantity;
	    }
}
