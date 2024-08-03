package com.project.shopapp.services;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.shopapp.dtos.OrderDto;
import com.project.shopapp.exceptions.DataNotFoundException;
import com.project.shopapp.model.Order;
import com.project.shopapp.model.OrderStatus;
import com.project.shopapp.model.User;
import com.project.shopapp.repositories.OrderRepository;
import com.project.shopapp.repositories.UserRepository;
import com.project.shopapp.responses.OrderResponses;

@Service
public class OrderService implements IOrderService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public Order createOrder(OrderDto orderDTO) throws Exception {
		// tìm xem user'id có tồn tại ko
		User user = userRepository.findById(orderDTO.getUserId())
				.orElseThrow(() -> new DataNotFoundException("Cannot find user with id: " + orderDTO.getUserId()));
		// convert orderDTO => Order
		// dùng thư viện Model Mapper
		// Tạo một luồng bảng ánh xạ riêng để kiểm soát việc ánh xạ
		modelMapper.typeMap(OrderDto.class, Order.class).addMappings(mapper -> mapper.skip(Order::setId));
		// Cập nhật các trường của đơn hàng từ orderDTO
		Order order = new Order();
		modelMapper.map(orderDTO, order);
		order.setUser(user);
		order.setOrderDate(new Date());// lấy thời điểm hiện tại
		order.setStatus(OrderStatus.PENDING);
		// Kiểm tra shipping date phải >= ngày hôm nay
		LocalDate shippingDate = orderDTO.getShippingDate() == null ? LocalDate.now() : orderDTO.getShippingDate();
		if (shippingDate.isBefore(LocalDate.now())) {
			throw new DataNotFoundException("Date must be at least today !");
		}
		order.setShippingDate(shippingDate);
		order.setActive(true);
		orderRepository.save(order);
		return order;
	}

	@Override
	public Order getOrderById(Long id) {
		return orderRepository.findById(id).orElse(null);
	}

	@Override
	public List<Order> getAllCategory(Long userId) {
		return orderRepository.findByUserId(userId);
	}

	@Override
	public Order updateCategory(Long orderId, OrderDto orderDto) throws DataNotFoundException {
		Order existOrder = orderRepository.findById(orderId)
				.orElseThrow(() -> new DataNotFoundException("Can't find order with id: " + orderId));
		User existUser = userRepository.findById(orderDto.getUserId())
				.orElseThrow(() -> new DataNotFoundException("Can't find order with id: " + orderId));

		modelMapper.typeMap(OrderDto.class, Order.class).addMappings(mapper -> mapper.skip(Order::setId));
		modelMapper.map(orderDto, existOrder);
		existOrder.setUser(existUser);

		// Lưu đơn hàng đã được cập nhật vào cơ sở dữ liệu
		return orderRepository.save(existOrder);
	}

	@Override
	public void deleteOrder(long id) {
		Order order = orderRepository.findById(id).orElse(null);
		if(order != null) {
			order.setActive(false);
			orderRepository.save(order);
		}
	}

}
