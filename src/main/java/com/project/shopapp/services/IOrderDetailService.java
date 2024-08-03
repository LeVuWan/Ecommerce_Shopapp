package com.project.shopapp.services;

import java.util.List;

import com.project.shopapp.dtos.OrderDetailDto;
import com.project.shopapp.exceptions.DataNotFoundException;
import com.project.shopapp.model.OrderDetail;

public interface IOrderDetailService {
	OrderDetail createOrderDetail(OrderDetailDto newOrderDetail) throws DataNotFoundException;

	OrderDetail getOrderDetail(Long id) throws DataNotFoundException;

	OrderDetail updateDetail(Long id, OrderDetailDto newOrderDetail) throws DataNotFoundException;

	void deleteOrderDetail(Long id);

	List<OrderDetail> getOrderDetails(Long orderId);
}
