package com.example.service;

import com.example.dto.OrderRequestDto;
import com.example.event.OrderEvent;
import com.example.event.OrderStatus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Sinks;

@Service
public class OrderStatusPublisher {

    private Sinks.Many<OrderEvent> orderSinks;

    public void publishOrderEvent(OrderRequestDto orderRequestDto, OrderStatus orderStatus) {
        OrderEvent orderEvent = new OrderEvent(orderRequestDto, orderStatus);
        orderSinks.tryEmitNext(orderEvent);
    }
}
