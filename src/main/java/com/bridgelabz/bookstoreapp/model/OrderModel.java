package com.bridgelabz.bookstoreapp.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "orders")
public class OrderModel {

	@Id
	@GeneratedValue
	private int orderId;
	@OneToOne
	@JoinColumn(name="user_id")
	private UserModel user;
	@ManyToOne
	@JoinColumn(name="book_id")
	private BookModel book;
	private int price;
	private int total;
	private int quantity;
	private String address;
	private boolean cancel;
	private LocalDate date = LocalDate.now();
		
	public OrderModel(UserModel user, BookModel book, int price, int total, int quantity, String address,
			boolean cancel, LocalDate date) {
		this.user = user;
		this.book = book;
		this.price = price;
		this.total = total;
		this.quantity = quantity;
		this.address = address;
		this.cancel = cancel;
		this.date = date;
	}
	
	
}
