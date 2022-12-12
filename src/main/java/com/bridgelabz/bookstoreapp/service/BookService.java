package com.bridgelabz.bookstoreapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.bridgelabz.bookstoreapp.dto.BookDTO;
import com.bridgelabz.bookstoreapp.exception.BookStoreException;
import com.bridgelabz.bookstoreapp.model.BookModel;
import com.bridgelabz.bookstoreapp.repository.BookRepository;
import com.bridgelabz.bookstoreapp.util.TokenUtil;

@Service
public class BookService implements IBookService {

	@Autowired
	BookRepository bookRepo;

	@Autowired
	TokenUtil tokenUtil;

	// Add book details
	public String addBook(BookDTO bookDTO) {
		BookModel bookModel = new BookModel(bookDTO);
		bookRepo.save(bookModel);
		return bookModel.getBookName() + " added successfully!!";
	}

	// get all book details
	public List<BookModel> getAllBooks(BookDTO bookDTO) {
		List<BookModel> bookList = bookRepo.findAll();
		if (bookList.isEmpty()) {
			throw new BookStoreException("No Books Added yet!");
		} else
			return bookList;
	}

	// get book details by id
	public BookModel getBookById(int id) {
		BookModel bookModel = bookRepo.findById(id).orElse(null);
		if (bookModel != null) {
			return bookModel;
		} else
			throw new BookStoreException("Invalid BookID");
	}

	// delete by id
	public String deleteBook(int id) {
		BookModel bookModel = bookRepo.findById(id).orElse(null);
		if (bookModel != null) {
			bookRepo.deleteById(id);
		} else
			throw new BookStoreException("Invalid BookId");
		return bookModel.getBookName();
	}

	// Get Book Data by Book Name
	public List<BookModel> searchbyBookName(String bookName) {
		List<BookModel> bookModel = bookRepo.findAll();
		if (bookModel != null) {
			return bookRepo.findByBookName(bookName);
		} else
			throw new BookStoreException("Book Name: " + bookName + " is not available");
	}

	// update book data by email address
	public BookModel updateBookbyId(BookDTO bookDTO, int id) {
		BookModel bookModel = bookRepo.findById(id).orElse(null);
		if (bookModel != null) {
			bookModel.setBookName(bookDTO.getBookName());
			bookModel.setAuthorName(bookDTO.getAuthorName());
			bookModel.setBookDescription(bookDTO.getBookDescription());
			bookModel.setBookImage(bookModel.getBookImage());
			bookModel.setPrice(bookDTO.getPrice());
			bookModel.setQuantity(bookDTO.getQuantity());
			return bookRepo.save(bookModel);
		} else
			throw new BookStoreException("Invalid BookID: " + id);
	}

	// Sorting : Ascending
	public List<BookModel> sortAscending() {
		List<BookModel> bookList = bookRepo.findAll();
		if (bookList.isEmpty()) {
			throw new BookStoreException("No Books available!!!");
		} else
			return bookRepo.findAll(Sort.by(Sort.Direction.ASC, "price"));
	}

	// Sorting : Descending
	public List<BookModel> sortDescending() {
		List<BookModel> bookList = bookRepo.findAll();
		if (bookList.isEmpty()) {
			throw new BookStoreException("No Books available!!!");
		} else
			return bookRepo.findAll(Sort.by(Sort.Direction.DESC, "price"));
	}

	// update Quantity by ID
	public BookModel updateQuantity(int id, int qty) {
		BookModel bookModel = bookRepo.findById(id).orElse(null);
		if (bookModel != null) {
			bookModel.setQuantity(qty);
			bookRepo.save(bookModel);
			return bookModel;
		} else
			throw new BookStoreException("Invalid BookID");
	}

}