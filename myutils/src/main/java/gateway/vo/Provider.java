package gateway.vo;

import com.newland.paas.paasservice.frwmgr.gateway.backend.vo.Backend;
import com.newland.paas.paasservice.frwmgr.gateway.frontend.vo.Frontend;

import java.util.Map;

public class Provider {
    private Map<String, Backend> backends;
    private Map<String, Frontend> frontends;

    public Map<String, Backend> getBackends() {
        return backends;
    }

    public void setBackends(Map<String, Backend> backends) {
        this.backends = backends;
    }

    public Map<String, Frontend> getFrontends() {
        return frontends;
    }

    public void setFrontends(Map<String, Frontend> frontends) {
        this.frontends = frontends;
    }

}
