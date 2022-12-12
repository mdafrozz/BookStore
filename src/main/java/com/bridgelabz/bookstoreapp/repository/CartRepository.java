package com.bridgelabz.bookstoreapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.bridgelabz.bookstoreapp.model.CartModel;

public interface CartRepository extends JpaRepository<CartModel, Integer>  {
	//Using custom query
		@Query(value = "SELECT * FROM cart WHERE user_id = :userId", nativeQuery = true)
		public List<CartModel> getUserbyId(int userId);
}
