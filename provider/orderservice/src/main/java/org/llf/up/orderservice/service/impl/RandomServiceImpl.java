package org.llf.up.orderservice.service.impl;

import org.llf.up.orderservice.service.RandomService;
import org.springframework.stereotype.Component;

@Component
public class RandomServiceImpl implements RandomService {
    @Override
    public Long getRandomLong() {
        return -1L;
    }
}
