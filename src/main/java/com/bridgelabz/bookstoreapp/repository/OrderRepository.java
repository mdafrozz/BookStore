package com.bridgelabz.bookstoreapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bridgelabz.bookstoreapp.model.OrderModel;

public interface OrderRepository extends JpaRepository<OrderModel, Integer>  {

}
