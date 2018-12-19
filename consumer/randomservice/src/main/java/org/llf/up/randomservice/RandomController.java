package org.llf.up.randomservice;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tools.JSONChange;


import javax.servlet.http.HttpServletRequest;
import java.util.Random;

@RestController
//@RequestMapping("/randomservice")
public class RandomController {

    @Value("${server.port}")
    int port;
    @Value("${spring.application.name}")
    String serviceName;
    @RequestMapping(value="/get", method = RequestMethod.GET)
    public Long getRandomNum(HttpServletRequest request){
        try {
            System.out.println("my name:"+serviceName+":"+"request:"+JSONChange.obj2Json(request.getContextPath()));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return new Random().nextLong();
    }

    @RequestMapping(value="/hit", method = RequestMethod.GET)
    public String Hi(@RequestParam String name){
        return "HI "+name+",I\'M "+serviceName+":"+port;
    }

}
