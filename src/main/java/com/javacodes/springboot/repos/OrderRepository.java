package com.javacodes.springboot.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.javacodes.springboot.models.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
	

}
