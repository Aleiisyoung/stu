package com.jt.order.service;

import java.util.Date;
import java.util.List;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import com.jt.dubbo.pojo.Order;
import com.jt.dubbo.pojo.OrderItem;
import com.jt.dubbo.pojo.OrderShipping;
import com.jt.dubbo.service.DubboOrderService;
import com.jt.order.mapper.OrderItemMapper;
import com.jt.order.mapper.OrderMapper;
import com.jt.order.mapper.OrderShippingMapper;
//已有bean标签，不用注解
public class OrderServiceImpl implements DubboOrderService{
	@Autowired
	private OrderItemMapper orderItemMapper;
	@Autowired
	private OrderShippingMapper orderShippingMapper;
	@Autowired
	private OrderMapper orderMapper;
	
	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	@Override
	public String saveOrder(Order order) {
		/*Date date = new Date();
		String orderId=
				order.getUserId()+""+System.currentTimeMillis();
		order.setStatus(1);//状态
		order.setOrderId(orderId);
		order.setCreated(date);
		order.setUpdated(date);
		orderMapper.insert(order);
		System.out.println("订单入库成功！");
		
		OrderShipping orderShipping = 
				order.getOrderShipping();
		orderShipping.setOrderId(orderId);
		orderShipping.setCreated(date);
		orderShipping.setUpdated(date);
		orderShippingMapper.insert(orderShipping);
		System.out.println("订单物流入库成功！");
		
		List<OrderItem> orderItems = 
				order.getOrderItems();
		for (OrderItem orderItem : orderItems) {
			orderItem.setOrderId(orderId);
			orderItem.setCreated(date);
			orderItem.setUpdated(date);
			orderItemMapper.insert(orderItem);
		}
		System.out.println("订单入库操作成功");
		
		return orderId;*/
		
		
		
		String orderId=
				order.getUserId()+""+System.currentTimeMillis();
		order.setOrderId(orderId);
		//将order信息写入消息队列
		rabbitTemplate.convertAndSend("save.order", order);
		
		return orderId;
		
		
	}

	@Override
	public Order findOrderById(String id) {
		
		
		return orderMapper.findOrderById(id);
	}

}
