package org.llf.up.orderservice.service.impl;
import org.llf.order.domain.Order;
import org.llf.up.orderservice.service.OrderService;
import org.llf.up.orderservice.service.RandomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    RandomService randomService;
    @Override
    public void createOrder(Order order) {

    }

    @Override
    public Order findOrder(Order order) {

        Order order2=new Order();
        order2.setId(randomService.getRandomLong());
        return order2;
    }
}
