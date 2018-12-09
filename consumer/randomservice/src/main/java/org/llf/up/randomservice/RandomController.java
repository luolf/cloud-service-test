package org.llf.up.randomservice;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import tools.JSONChange;


import javax.servlet.http.HttpServletRequest;
import java.util.Random;

@RestController
@RequestMapping("/num")
public class RandomController {

    @Value("${server.port}")
    int port;
    @RequestMapping(value="/longObject", method = RequestMethod.GET)
    public Long getRandomNum(HttpServletRequest request){
        try {
            System.out.println("my port:"+port+":"+"request:"+JSONChange.obj2Json(request.getContextPath()));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return new Random().nextLong();
    }
}
