package com.javacodes.springboot.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.javacodes.springboot.models.Order;
import com.javacodes.springboot.repos.OrderRepository;

@RestController
@EnableJpaRepositories("com.javacodes.springboot.OrderRepository")
public class OrderController {
	
	@Autowired
	private OrderRepository orderRepository;
	
	@PostMapping("/create")
    public Order createOrder(@Valid @RequestBody Order order) {
        return orderRepository.save(order);
    }
     
	@GetMapping("/order")
    public List < Order > geOrders() {
        return orderRepository.findAll();
    }
	
	@GetMapping("/orders/{order_id}")
	public ResponseEntity < Order > getOrderById(
	        @PathVariable(value = "order_id") Long orderId) throws ResourceNotFoundException {
	        Order order = orderRepository.findById(orderId)
	            .orElseThrow(() -> new ResourceNotFoundException("Order not found :: " + orderId));
	        return ResponseEntity.ok().body(order);
	    }
	
	   @PutMapping("/orders/{order_Id}")
       public Order updateOrder(@PathVariable(value = "order_Id") Long orderId, @Valid @RequestBody Order orderRequest) throws ResourceNotFoundException {
		return orderRepository.findById(orderId).map(order -> {
			//order.setOrderDate(orderRequest.getOrderDate());
			order.setOrderStatus(orderRequest.getOrderStatus());
			return orderRepository.save(order);
		}).orElseThrow(() -> new ResourceNotFoundException("OrderId " + orderId + " not found"));
    	   
       }
	   
	   @DeleteMapping("/orders/{orderId}")
	    public ResponseEntity<?> deleteOrder(@PathVariable Long orderId) throws ResourceNotFoundException {
	        return orderRepository.findById(orderId).map(order -> {
	        	orderRepository.delete(order);
	            return ResponseEntity.ok().build();
	        }).orElseThrow(() -> new ResourceNotFoundException("OrderId " + orderId + " not found"));
	    }
	   
}
