package gateway.util;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: luolifeng
 * Date: 2018-12-24
 * Time: 15:55
 */
public class EtcdFactory {
    public static final Map<String, EtcdConnection> connectionPools = new HashMap<>();
    public static EtcdConnection getEtcdConnection(String endpoints){
        String eps=stringAnalyze(endpoints);
        synchronized (connectionPools) {
            if (null == connectionPools.get(eps)) {
//                connectionPools.put(eps, new EtcdConImpl(eps));
            }
        }
        return connectionPools.get(eps);
    }
    public static EtcdConnection getEtcdConnectionWithSSL(String endpoints,String privateKey,String keyCertChain){
        String eps=stringAnalyze(endpoints);
        synchronized (connectionPools) {
            if (null == connectionPools.get(eps)) {
//                connectionPools.put(eps, new EtcdConImpl(eps, privateKey, keyCertChain));
            }
        }
        return connectionPools.get(eps);
    }

    //去空格
    private static String stringAnalyze(String s){
        if(s==null || s.isEmpty()){
            return null;
        }
        StringBuilder sb=new StringBuilder();
        String[] spt=s.split(ToolDict.SPLIT);
        for(int i=0;i<spt.length;i++){
            sb.append(spt[i].trim());
            if(spt.length-1==i && i>0){
                sb.append(ToolDict.SPLIT);
            }
        }
        return sb.toString();
    }

}

