package org.llf.up.orderservice.service;


import org.llf.up.orderservice.service.impl.RandomServiceImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "randomservice",fallback = RandomServiceImpl.class)
public interface RandomService {
    @RequestMapping(value = "/get",method = RequestMethod.GET)
    Long getRandomLong();
}
