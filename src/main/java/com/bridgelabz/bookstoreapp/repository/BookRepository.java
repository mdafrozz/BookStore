package com.bridgelabz.bookstoreapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bridgelabz.bookstoreapp.model.BookModel;

@Repository
public interface BookRepository extends JpaRepository<BookModel, Integer> {
    //Using custom query
	@Query(value = "SELECT * FROM book WHERE name LIKE %:bookName%", nativeQuery = true)
    List<BookModel> findByBookName(String bookName);
    
}