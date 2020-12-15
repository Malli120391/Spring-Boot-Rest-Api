package com.javacodes.springboot.controllers;

import java.util.List;
import java.util.Optional;

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


import com.javacodes.springboot.models.Item;
import com.javacodes.springboot.repos.ItemRepository;
import com.javacodes.springboot.repos.OrderRepository;

@RestController
public class ItemController {
	
	
	@Autowired
	private ItemRepository itemRepository;
	
	
	@Autowired
	private OrderRepository orderRepository;
	
	@PostMapping("/orders/{order_id}/items")
    public Item createItem(@PathVariable (value = "order_id") Long orderId,
                                 @Valid @RequestBody Item item) throws ResourceNotFoundException  {
        return orderRepository.findById(orderId).map(order -> {
        	item.setOrder(order);
            return itemRepository.save(item);
        }).orElseThrow(() -> new ResourceNotFoundException("OrderId " + orderId + " not found"));
    }
	
	@GetMapping("/orders/{orderId}/items")
    public Optional<Item> getAllItemById(@PathVariable (value = "orderId") Long orderId) {
		return itemRepository.findById(orderId);
		
    }
	
	@GetMapping("/items")
    public List < Item > getItems() {
        return itemRepository.findAll();
    }
	
	@PutMapping("/orders/{orderId}/items/{itemId}")
    public Item updateItem(@PathVariable (value = "orderId") Long orderId,
                                 @PathVariable (value = "itemId") Long itemId,
                                 @Valid @RequestBody Item itemRequest) throws ResourceNotFoundException {
        if(!orderRepository.existsById(orderId)) {
            throw new ResourceNotFoundException("OrderId " + orderId + " not found");
        }

        return itemRepository.findById(itemId).map(item -> {
        	item.setItemName(itemRequest.getItemName());
        	item.setItemUnitPrice(itemRequest.getItemUnitPrice());
        	item.setItemQuantity(itemRequest.getItemQuantity());
        	
            return itemRepository.save(item);
        }).orElseThrow(() -> new ResourceNotFoundException("ItemId " + itemId + "not found"));
    }
	
	 @DeleteMapping("/orders/{orderId}/items/{itemId}")
	    public ResponseEntity < ? > deleteItem(@PathVariable(value = "orderId") Long orderId,
	        @PathVariable(value = "itemId") Long itemId) throws ResourceNotFoundException {
	        return itemRepository.findByIdAndOrderId(itemId, orderId).map(item -> {
	        	itemRepository.delete(item);
	            return ResponseEntity.ok().build();
	        }).orElseThrow(() -> new ResourceNotFoundException(
	            "Item not found with id " + itemId + " and OrderId " + orderId));
	    }
   
	/*
	 * @DeleteMapping("/orders/{orderId}/items/{itemId}") public ResponseEntity<?>
	 * deleteItem(@PathVariable (value = "orderId") Long orderId,
	 * 
	 * @PathVariable (value = "itemId") Long itemId) { return
	 * itemRepository.findById(itemId, orderId).map(items -> {
	 * itemRepository.deleteById(items); return ResponseEntity.ok().build();
	 * }).orElseThrow(() -> new ResourceNotFoundException("Item not found with id "
	 * + itemId + " and OrderId " + orderId)); }
	 */
}
