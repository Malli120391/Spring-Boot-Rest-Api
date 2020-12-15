package com.javacodes.springboot.repos;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import com.javacodes.springboot.models.Item;
import com.javacodes.springboot.models.Order;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long>{

	Optional<Order> findById(Long itemId, Long orderId);
	

	Optional<Order> findByIdAndOrderId(Long itemId, Long orderId);
	
	//Optional<Item> deleteById(Long itemId, Long orderId);
	//void deleteById(Order items);


	//void deleteById(Order items);

	void delete(Order item);

	

}
