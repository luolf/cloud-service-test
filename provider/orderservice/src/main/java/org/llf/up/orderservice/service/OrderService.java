package org.llf.up.orderservice.service;
import org.llf.order.domain.Order;

public interface OrderService {
    public void createOrder(Order order);
    public Order findOrder(Order order);

}
