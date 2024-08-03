package com.project.shopapp.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.shopapp.dtos.OrderDto;
import com.project.shopapp.model.Order;
import com.project.shopapp.responses.OrderResponses;
import com.project.shopapp.services.OrderService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("${api.prefix}/orders")
public class OrderController {

	@Autowired
	private OrderService orderService;

	@PostMapping("")
	public ResponseEntity<?> createOrder(@Valid @RequestBody OrderDto orderDTO, BindingResult result) {
		try {
			if (result.hasErrors()) {
				List<String> errorMessages = result.getFieldErrors().stream().map(FieldError::getDefaultMessage)
						.toList();
				return ResponseEntity.badRequest().body(errorMessages);
			}
			Order orderResponse = orderService.createOrder(orderDTO);
			return ResponseEntity.ok(orderResponse);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@GetMapping("/user/{user_id}")
	public ResponseEntity<?> getOrderByUserId(@Valid @PathVariable("user_id") long userId) {
		try {
			List<Order> getOrders = orderService.getAllCategory(userId);
			return ResponseEntity.ok(getOrders);

		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getOrder(@Valid @PathVariable("id") long id) {
		try {
			Order existOrder = orderService.getOrderById(id);
			return ResponseEntity.ok(existOrder);

		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> updateOrder(@Valid @PathVariable("id") long id, @Valid @RequestBody OrderDto orderDtos) {
		try {
			Order order = orderService.updateCategory(id, orderDtos);
			return ResponseEntity.ok(order);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteOrder(@Valid @PathVariable long id) {
		orderService.deleteOrder(id);
		return ResponseEntity.ok("Orsders deleted with UserId=" + id);
	}
}
