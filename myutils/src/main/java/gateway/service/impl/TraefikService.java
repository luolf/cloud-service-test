package gateway.service.impl;

import com.newland.paas.paasservice.frwmgr.gateway.service.TraefikApi;
import com.newland.paas.paasservice.frwmgr.gateway.vo.Provider;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class TraefikService {
    private RestTemplate restTemplate = new RestTemplate();

    public Provider getEurekaProvider(String provider) {
        String host = "http://10.1.8.33:25160";

        Provider providerObject =
            restTemplate.getForObject(host + TraefikApi.GET_PROVIDER_BY_NAME, Provider.class, provider);

        return providerObject;
    }

}
