package com.project.shopapp.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.shopapp.dtos.OrderDetailDto;
import com.project.shopapp.model.OrderDetail;
import com.project.shopapp.responses.OrderDetailResponses;
import com.project.shopapp.services.OrderDetailService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("${api.prefix}/ordersDetail")
public class OrderDetailController {

	@Autowired
	private OrderDetailService orderDetailService;

	@PostMapping("")
	public ResponseEntity<?> createOrderDetail(@Valid @RequestBody OrderDetailDto orderDetailDtos) {
		try {
			OrderDetail newOrderDetail = orderDetailService.createOrderDetail(orderDetailDtos);
			return ResponseEntity.ok().body(OrderDetailResponses.fromOrderDetail(newOrderDetail));
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getOrderDetailById(@Valid @PathVariable("id") Long id) {
		try {
			OrderDetail orderDetail = orderDetailService.getOrderDetail(id);
			return ResponseEntity.ok().body(OrderDetailResponses.fromOrderDetail(orderDetail));
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@GetMapping("/order/{orderId}")
	public ResponseEntity<?> getOrderDetaiByOrderId(@Valid @PathVariable("orderId") Long orderId) {
		try {
			List<OrderDetail> orderDetails = orderDetailService.getOrderDetails(orderId);
			List<OrderDetailResponses> orderDetailResponses = orderDetails.stream()
					.map(OrderDetailResponses::fromOrderDetail).toList();
			return ResponseEntity.ok().body(orderDetailResponses);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> updateOrderDetail(@Valid @PathVariable("id") Long id,
			@Valid @RequestBody OrderDetailDto orderDetailDtos) {
		try {
			OrderDetail orderDetail = orderDetailService.updateDetail(id, orderDetailDtos);
			return ResponseEntity.ok().body(orderDetail);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteOrderDetail(@Valid @PathVariable("id") Long id) {
		orderDetailService.deleteOrderDetail(id);
		return ResponseEntity.ok().body("Delete Order detail with id: "+id);
	}
}
