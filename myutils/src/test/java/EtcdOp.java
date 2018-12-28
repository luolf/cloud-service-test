import com.alibaba.fastjson.JSON;
import com.coreos.jetcd.api.AuthenticateRequest;
import com.coreos.jetcd.data.ByteSequence;
import com.coreos.jetcd.lock.LockResponse;
import gateway.backend.vo.*;
import gateway.frontend.vo.Frontend;
import gateway.frontend.vo.Route;
import gateway.vo.PathNode;
import gateway.vo.Provider;
import org.testng.annotations.Test;
import org.testng.log4testng.Logger;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Test
public class EtcdOp {

    private static Logger log=Logger.getLogger(EtcdOp.class);


    public static void printJsomLog(Object obj){
//        log.info(toJsonString(obj));
        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSSS").format(new Date())+":"+ toJsonString(obj));
    }

    public static String toJsonString(Object obj){
        return JSON.toJSONString(obj);
    }

    public void getEtcdKeyTest() throws InterruptedException {
        String  key="/traefik";
        printJsomLog(EtcdUtil.getWithPrefix(key));

    }


    public void putEtcdKeyTest() throws ExecutionException, InterruptedException {
        String key="/traefik/backends/backend1/circuitbreaker/expression";
        String value="NetworkErrorRatio() > 0.5";
        log.info("begin  putEtcdKey(key,value)");
        EtcdUtil.putEtcdKey(key,value);
        log.info("end putEtcdKey(key,value)");
        printJsomLog(EtcdUtil.getEtcdKey(key));
    }

    public void getEtcdKeyTest2() throws ExecutionException, InterruptedException {
        int sleepSec=1;
        for(int j=0;j<3;j++) {
            printJsomLog("开始");
            for (int i = 0; i < 10; i++) {
                String key = "/traefik/backends/backend1/circuitbreaker/expression" + i+(j+1)*10;
                String value = new Date().toString();
//                printJsomLog(i);
                EtcdUtil.putEtcdKey(key, value);
                printJsomLog(EtcdUtil.getEtcdKey(key));
            }
            sleepSec=60000*((j+1)*10);
            printJsomLog("休息"+(j+1)*10+"分钟");
            Thread.sleep(sleepSec);
        }


    }


    public PathNode createFrontendsNode(Map<String, Frontend> frontendsMap){

        if(frontendsMap==null && frontendsMap.isEmpty()){
            return null;
        }
        PathNode frontends=new PathNode("frontends");
        for(String key : frontendsMap.keySet()){
            if(key==null ||frontendsMap.get(key)==null ){
                continue;
            }
            Frontend frontend=frontendsMap.get(key);
            PathNode frontendNode=new PathNode(key);
            frontends.getSubNodes().add(frontendNode);

            frontendNode.getSubNodes().add(new PathNode("priority",frontend.getPriority().toString()));
            frontendNode.getSubNodes().add(new PathNode("backend",frontend.getBackend()));

            if(frontend.getEntryPoints()!=null && frontend.getEntryPoints().size()>0){
                String entrypoints=",";
                for(String ep:frontend.getEntryPoints()){
                    entrypoints= entrypoints+ep+",";
                }

                frontendNode.getSubNodes().add(new PathNode("entrypoints",entrypoints.substring(1,entrypoints.length()-1)));
            }

            Map<String, Route> routes=  frontend.getRoutes();
            if(routes!=null && !routes.isEmpty()){
                PathNode routesNode=new PathNode("routes");
                frontendNode.getSubNodes().add(routesNode);
                for( String routeKey:routes.keySet()){
                    if(routeKey==null ||routes.get(routeKey)==null ){
                        continue;
                    }
                    Route route=routes.get(routeKey);
                    PathNode routeNode=new PathNode(routeKey);
                    routesNode.getSubNodes().add(routeNode);
                    routeNode.getSubNodes().add(new PathNode("rule",route.getRule()));

                }
            }
        }

        return frontends;

    }

    public PathNode createTlsNode(){
        String keys="/traefik/tls/2/entrypoints";
        String certfileContent="/traefik/tls/2/certificate/certfile";
        String keyfileContent="/traefik/tls/2/certificate/keyfile";

        return null;
    }

    public PathNode createGlobalConfigNode(){
        return null;
    }
    public PathNode createBackendsNode(Map<String, Backend> backendsMap){

        if(backendsMap==null && backendsMap.isEmpty()){
            return null;
        }
        //backends
        PathNode backendsNode=new PathNode("backends");
        for(String key : backendsMap.keySet()) {
            if (key == null || backendsMap.get(key) == null) {
                continue;
            }
            //backend-x
            Backend backend = backendsMap.get(key);
            PathNode backendNode = new PathNode(key);
            backendsNode.getSubNodes().add(backendNode);


            //servers
            Map<String, Server> servers = backend.getServers();
            if (servers != null && !servers.isEmpty()) {
                PathNode serversNode = new PathNode("servers");
                backendNode.getSubNodes().add(serversNode);
                for (String serverKey : servers.keySet()) {
                    if (serverKey == null || servers.get(serverKey) == null) {
                        continue;
                    }
                    Server server = servers.get(serverKey);
                    PathNode serverNode = new PathNode(serverKey);
                    serversNode.getSubNodes().add(serverNode);
                    serverNode.getSubNodes().add(new PathNode("weight", server.getWeight().toString()));
                    serverNode.getSubNodes().add(new PathNode("url", server.getUrl() == null ? "http://127.0.0.1" : server.getUrl()));
                }
            }

            //loadBalancer
            LoadBalancer loadBalancer = backend.getLoadBalancer();
            if (loadBalancer != null) {
                PathNode lbNode = new PathNode("loadBalancer");
                backendNode.getSubNodes().add(lbNode);
                lbNode.getSubNodes().add(new PathNode("method", loadBalancer.getMethod() == null ? "wrr" : loadBalancer.getMethod()));

            }

            //maxConn
            MaxConn maxConnr = backend.getMaxConn();
            if (maxConnr != null) {
                PathNode maxConnrNode = new PathNode("maxConn");
                backendNode.getSubNodes().add(maxConnrNode);
                maxConnrNode.getSubNodes().add(new PathNode("extractorfunc", maxConnr.getExtractorfunc()));
                maxConnrNode.getSubNodes().add(new PathNode("amount", maxConnr.getAmount().toString()));
            }

            //circuitBreaker
            CircuitBreaker circuitBreaker = backend.getCircuitBreaker();
            if (circuitBreaker != null) {
                PathNode circuitBreakerNode = new PathNode("circuitBreaker");
                backendNode.getSubNodes().add(circuitBreakerNode);
                circuitBreakerNode.getSubNodes().add(new PathNode("expression", circuitBreaker.getExpression()));
            }
        }

        return backendsNode;

    }
    public PathNode createProviderNode(Provider provider,String prefix){

        PathNode root=new PathNode("traefik");
        Map<String, Frontend> frontendsMap=provider.getFrontends();
        Map<String, Backend> backendsMap=provider.getBackends();
        root.getSubNodes().add(createBackendsNode(backendsMap));
        root.getSubNodes().add(createFrontendsNode(frontendsMap));
        return root;
    }
    public void  putProvider() throws Exception {
        Provider provider=createProvider();
        String prefix="/traefik";
        AuthenticateRequest s;
        EtcdUtil.deleteEtcdKeyWithPrefix(prefix);
        PathNode node= createProviderNode(provider,prefix);
        HashMap<String,String> kvMap;
        kvMap = new LinkedHashMap<>() ;
        pathNode2Map(node,"",kvMap);
        printJsomLog("");

        for (String key : kvMap.keySet()) {
            EtcdUtil.putEtcdKey(key,kvMap.get(key));
        }
        printJsomLog(kvMap);



    }
    public void pathNode2Map(PathNode node,String key,HashMap<String,String> kv) throws Exception {
        String newKey=key;
        if (node==null || node.getName()==null){
            return ;
        }
        newKey=newKey+node.getName();
        if ( node.getValue()!=null) {
            kv.put(newKey,node.getValue());
        }
        if(newKey.split("/").length>50) {
            throw  new Exception("健值深度过大！");

        }
        for(PathNode subNode:node.getSubNodes()){
            pathNode2Map(subNode,  newKey,  kv);
        }

    }


    public Provider createProvider(){

        String provideJson="{\"backends\":{\"backend0\":{\"name\":\"backend0\",\"circuitBreaker\":{\"expression\":\"NetworkErrorRatio() > 0.5\"},\"maxConn\":{\"amount\":10,\"extractorfunc\":\"request.host\"},\"loadBalancer\":{\"method\":\"wcc\"},\"servers\":{\"server1\":{\"name\":\"server1\",\"url\":\"127.0.0.1:8080\",\"weight\":1},\"server2\":{\"name\":\"server2\",\"url\":\"127.0.0.1:80\",\"weight\":5}}},\"backend1\":{\"name\":\"backend0\",\"circuitBreaker\":{\"expression\":\"NetworkErrorRatio() > 0.5\"},\"maxConn\":{\"amount\":10,\"extractorfunc\":\"request.host\"},\"loadBalancer\":{\"method\":\"wcc\"},\"servers\":{\"server1\":{\"name\":\"server1\",\"url\":\"127.0.0.1:8080\",\"weight\":1},\"server2\":{\"name\":\"server2\",\"url\":\"127.0.0.1:80\",\"weight\":5}}}},\"frontends\":{\"frontend0\":{\"name\":\"frontend0\",\"priority\":0},\"frontend1\":{\"name\":\"frontend1\",\"priority\":10,\"entryPoints\":[\"http\"],\"backend\":\"frontend1\",\"routes\":{\"route1\":{\"name\":\"route1\",\"rule\":\"path:/test\"},\"route3\":{\"name\":\"route3\",\"rule\":\"path:/test2\"}}}}}";
        Provider provider= JSON.parseObject(provideJson,Provider.class);
        printJsomLog(provider);
        return  provider;
    }
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        String key="/myname";

        for(int i=0;i<3;i++) {
            final int sn=i;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Thread.currentThread().setName("慢兔兔"+sn);
                     Long leaseId;
                    try {
                        //租约不可共用
                        leaseId = EtcdUtil.getEtclClient().getLeaseClient().grant(5000L).get().getID();
                        printJsomLog("租约"+leaseId);
                        putKey(key, 5000L,leaseId);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                }
            }).start();
        }
    }

    //ETCD分布式锁
    public static void putKey(String key,long time,long leaseId) throws Exception {

        //加锁
           CompletableFuture<LockResponse> future=EtcdUtil.getEtclClient().getLockClient().lock(ByteSequence.fromString(key), leaseId);

        try {
             future.get().getKey().toStringUtf8();
            printJsomLog(Thread.currentThread().getName()+":加锁"+key);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        if("慢兔兔10".equals(Thread.currentThread().getName())){
            throw new Exception("test");
        }
        printJsomLog(Thread.currentThread().getName()+":get1="+EtcdUtil.get(key));
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        EtcdUtil.putEtcdKey(key,Thread.currentThread().getName());

        printJsomLog(Thread.currentThread().getName()+":get2="+EtcdUtil.get(key));
        //解锁
        EtcdUtil.getEtclClient().getLockClient().unlock(ByteSequence.fromString(future.get().getKey().toStringUtf8())).get();
        //错误用法，不可预期后果
//        EtcdUtil.getEtclClient().getLockClient().unlock(ByteSequence.fromString(key)).get();
        printJsomLog(Thread.currentThread().getName()+":释放锁"+key);
    }
}
