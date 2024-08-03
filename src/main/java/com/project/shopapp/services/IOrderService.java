package com.project.shopapp.services;

import java.util.List;

import com.project.shopapp.dtos.OrderDto;
import com.project.shopapp.exceptions.DataNotFoundException;
import com.project.shopapp.model.Order;
import com.project.shopapp.responses.OrderResponses;

public interface IOrderService {
	 Order createOrder(OrderDto orderDTO) throws Exception;

	Order getOrderById(Long id);

	List<Order> getAllCategory(Long userId);

	Order updateCategory(Long orderId, OrderDto orderDto) throws DataNotFoundException;

	void deleteOrder(long id);
}
