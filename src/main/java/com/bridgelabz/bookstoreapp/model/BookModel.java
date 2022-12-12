package com.bridgelabz.bookstoreapp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.bridgelabz.bookstoreapp.dto.BookDTO;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "book")
public class BookModel {
	
	@Id
	@GeneratedValue
	@Column(name = "BookId")
	private int BookId;
	@Column(name = "name")
	private String bookName;
	private String authorName;
	private String bookDescription;
	private String bookImage;
	private int price;
	private int quantity;
	 
	public BookModel(BookDTO bookdto){
		this.bookName = bookdto.getBookName();
	    this.authorName = bookdto.getAuthorName();
	    this.bookDescription = bookdto.getBookDescription();
	    this.bookImage = bookdto.getBookImage();
	    this.price = bookdto.getPrice();
	    this.quantity = bookdto.getQuantity();
	}
}
