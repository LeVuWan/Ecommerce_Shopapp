package com.project.shopapp.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.shopapp.dtos.OrderDetailDto;
import com.project.shopapp.dtos.OrderDto;
import com.project.shopapp.exceptions.DataNotFoundException;
import com.project.shopapp.model.Order;
import com.project.shopapp.model.OrderDetail;
import com.project.shopapp.model.Product;
import com.project.shopapp.repositories.OrderDetailRepository;
import com.project.shopapp.repositories.OrderRepository;
import com.project.shopapp.repositories.ProductRepository;

@Service
public class OrderDetailService implements IOrderDetailService {

	@Autowired
	private OrderRepository orderRepository;
	@Autowired
	private OrderDetailRepository orderDetailRepository;
	@Autowired
	private ProductRepository productRepository;

	@Override
	public OrderDetail createOrderDetail(OrderDetailDto newOrderDetail) throws DataNotFoundException {
		Order existOrder = orderRepository.findById(newOrderDetail.getOrderId()).orElseThrow(
				() -> new DataNotFoundException("Can't find order with id: " + newOrderDetail.getOrderId()));

		Product existProduct = productRepository.findById(newOrderDetail.getProductId()).orElseThrow(
				() -> new DataNotFoundException("Can't find product with id: " + newOrderDetail.getProductId()));

		OrderDetail orderDetail = new OrderDetail();
		orderDetail.setColor(newOrderDetail.getColor());
		orderDetail.setNumberOfProducts(newOrderDetail.getNumberOfProduct());
		orderDetail.setTotalMoney(newOrderDetail.getTotalMoney());
		orderDetail.setProduct(existProduct);
		orderDetail.setOrder(existOrder);
		orderDetail.setPrice(newOrderDetail.getPrice());
		return orderDetailRepository.save(orderDetail);
	}

	@Override
	public OrderDetail getOrderDetail(Long id) throws DataNotFoundException {
		OrderDetail orderDetail = orderDetailRepository.findById(id)
				.orElseThrow(() -> new DataNotFoundException("Can't find order detail with id: " + id));
		return orderDetail;
	}

	@Override
	public OrderDetail updateDetail(Long id, OrderDetailDto newOrderDetail) throws DataNotFoundException {
		OrderDetail existOrderDetail = orderDetailRepository.findById(id)
				.orElseThrow(() -> new DataNotFoundException("Can't find orderdetail with id: " + id));

		Order existOrder = orderRepository.findById(newOrderDetail.getOrderId()).orElseThrow(
				() -> new DataNotFoundException("Can't find order with id: " + newOrderDetail.getOrderId()));

		Product existProduct = productRepository.findById(newOrderDetail.getProductId()).orElseThrow(
				() -> new DataNotFoundException("Can't find product with id: " + newOrderDetail.getProductId()));

		existOrderDetail.setPrice(newOrderDetail.getPrice());
		existOrderDetail.setOrder(existOrder);
		existOrderDetail.setProduct(existProduct);
		existOrderDetail.setTotalMoney(newOrderDetail.getTotalMoney());
		existOrderDetail.setColor(newOrderDetail.getColor());
		existOrderDetail.setNumberOfProducts(newOrderDetail.getNumberOfProduct());
		return orderDetailRepository.save(existOrderDetail);
	}

	@Override
	public void deleteOrderDetail(Long id) {
		orderDetailRepository.deleteById(id);
	}

	@Override
	public List<OrderDetail> getOrderDetails(Long orderId) {
		List<OrderDetail> orderDetails = orderDetailRepository.findByOrderId(orderId);
		return orderDetails;
	}

}
