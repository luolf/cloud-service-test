package org.llf.up.orderservice.controller;

import org.llf.order.domain.Order;
import org.llf.up.orderservice.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {

    @Autowired
    OrderService orderService;

    @RequestMapping("/get")
    public Order findOrder(){
        return orderService.findOrder(new Order());
    }
}
