package com.bridgelabz.bookstoreapp.service;

import java.util.List;

import com.bridgelabz.bookstoreapp.dto.BookDTO;
import com.bridgelabz.bookstoreapp.model.BookModel;

public interface IBookService {
	public String addBook(BookDTO bookDTO);
	public List<BookModel> getAllBooks(BookDTO bookDTO);
	public BookModel getBookById(int id);
	public String deleteBook(int id);
	public List<BookModel> searchbyBookName(String bookName);
	public BookModel updateBookbyId(BookDTO bookDTO, int id);
    public List<BookModel> sortAscending();
    public List<BookModel> sortDescending();
    public BookModel updateQuantity(int id,int qty);
}
