package org.llf.up.orderservice.controller;

import org.llf.order.domain.Order;
import org.llf.up.orderservice.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
//@RequestMapping("/orderservice")
public class OrderController {

    @Autowired
    OrderService orderService;
    @Value("${server.port}")
    int port;
    @Value("${spring.application.name}")
    String serviceName;
    @RequestMapping("/get")
    public Order findOrder(){
        return orderService.findOrder(new Order());
    }
    @RequestMapping(value="/hit", method = RequestMethod.GET)
    public String Hi(@RequestParam String name){

        Map<String, String> map = System.getenv();
        return "HI "+name+",I\'M "+map.get("COMPUTERNAME")+serviceName+":"+port;
    }
    @RequestMapping(value="/getServiceInfo", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, String> getServiceInfo(){
        Map<String, String> map = System.getenv();
        return map;
    }
}
