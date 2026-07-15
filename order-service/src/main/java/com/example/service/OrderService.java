package com.example.service;


import com.example.dto.OrderRequestDto;
import com.example.entity.PurchaseOrder;
import com.example.event.OrderStatus;
import com.example.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderStatusPublisher orderStatusPublisher;

    @Transactional
    public PurchaseOrder createOrder(OrderRequestDto orderRequestDto) {
         PurchaseOrder order = orderRepository.save(convertDtoToEntity(orderRequestDto));
         orderRequestDto.setOrderId(order.getId());
         //produce kafka event with status ORDER_CREATED
        orderStatusPublisher.publishOrderEvent(orderRequestDto, OrderStatus.ORDER_CREATED);
        return order;
    }

    public List<PurchaseOrder> getAllOrders() {
        return orderRepository.findAll();
    }

    private PurchaseOrder convertDtoToEntity(OrderRequestDto orderRequestDto) {
        PurchaseOrder purchaseOrder = new PurchaseOrder();
        purchaseOrder.setUserId(orderRequestDto.getUserId());
        purchaseOrder.setProductId(orderRequestDto.getProductId());
        purchaseOrder.setOrderStatus(OrderStatus.ORDER_CREATED);
        purchaseOrder.setPrice(orderRequestDto.getAmount());
        return purchaseOrder;
    }
}
