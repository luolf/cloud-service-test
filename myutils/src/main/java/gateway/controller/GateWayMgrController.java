package gateway.controller;

import com.newland.paas.advice.audit.AuditObject;
import com.newland.paas.advice.audit.AuditOperate;
import com.newland.paas.advice.request.RequestContext;
import com.newland.paas.common.util.DateUtils;
import com.newland.paas.log.Log;
import com.newland.paas.log.LogFactory;
import com.newland.paas.paasservice.controllerapi.alartmgr.AlarmMgrService;
import com.newland.paas.paasservice.controllerapi.alartmgr.common.vo.AlertAnnotationVO;
import com.newland.paas.paasservice.controllerapi.alartmgr.common.vo.AlertVO;
import com.newland.paas.paasservice.controllerapi.alartmgr.common.vo.Alerts;
import com.newland.paas.paasservice.frwmgr.gateway.service.impl.TraefikService;
import com.newland.paas.paasservice.frwmgr.gateway.vo.Provider;
import com.newland.paas.sbcommon.common.ApplicationException;
import com.newland.paas.sbcommon.config.AlarmConfig;
import com.newland.paas.sbcommon.utils.NanjinRestfulRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * 
 * @author SongDi
 * @date 2018/12/19
 */
@RestController
@RequestMapping("v1/gateway")
@AuditObject("网关")
public class GateWayMgrController {

    private static final Log LOG = LogFactory.getLogger(GateWayMgrController.class);

    @Autowired
    private TraefikService traefikService;
    @Autowired
    private AlarmConfig alarmConfig;

    @AuditOperate(value = "providers-eureka", name = "获取eureka默认路由")
    @GetMapping(value = "/providers/eureka", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public Provider getEurekaProvider() throws ApplicationException {

        // log.info(LogProperty.LOGTYPE_DETAIL, MessageFormat.format("getframeworkDetail frameworkId:{0}",
        // frameworkId));
        String providerName = "eureka";
        Provider provider = traefikService.getEurekaProvider(providerName);
        return provider;
    }

    @AuditOperate(value = "providers-etcd", name = "获取路由配置")
    @GetMapping(value = "/providers/etcd", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public Provider getEtcdProvider() throws ApplicationException {

        // log.info(LogProperty.LOGTYPE_DETAIL, MessageFormat.format("getframeworkDetail frameworkId:{0}",
        // frameworkId));
        String providerName = "etcd";
        Provider provider = traefikService.getEurekaProvider(providerName);
        return provider;

    }

    @AuditOperate(value = "providers-etcd", name = "保存路由配置")
    @PutMapping(value = "/providers/etcd", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public Provider saveEtcdProvider(@RequestBody Provider provider) throws ApplicationException {

        // 保存配置到etcd
        return provider;

    }

    @GetMapping(value = "/providers/ss", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public void ss() throws ApplicationException {

        AlarmMgrService alarmMgrService =
            NanjinRestfulRequestUtil.getInstance(AlarmMgrService.class, alarmConfig.getUrl());
        Alerts alerts = new Alerts();

        AlertVO alertVO = new AlertVO();
        AlertAnnotationVO alertAnnotationVO = new AlertAnnotationVO();
        alertAnnotationVO.setDescription("aaa");
        alertAnnotationVO.setSummary("aaa");
        alertVO.setAnnotations(alertAnnotationVO);
        alertVO.setStartsAt(DateUtils.formatDate(new Date(), DateUtils.DATE_FULL_FORMAT));
        alertVO.addLabels(AlertVO.LABELS_ALERT_LEVEL, AlertVO.ALERT_LEVEL_FATAL);
        alertVO.addLabels(AlertVO.LABELS_TENANT, RequestContext.getTenantId().toString());
        // alertVO.addLabels(AlertVO.LABELS_MSG_GROUP, "");
        alerts.addAlerts(alertVO);
        alarmMgrService.createAlerts(alerts);
    }

}
