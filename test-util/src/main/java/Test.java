import com.coreos.jetcd.Client;
import com.coreos.jetcd.KV;
import com.coreos.jetcd.data.ByteSequence;
import com.coreos.jetcd.data.KeyValue;
import com.coreos.jetcd.kv.GetResponse;
import sun.rmi.runtime.Log;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Test {
    public static Logger  log=Logger.getLogger("luolf");
    public static void main(String[] args) throws ExecutionException, InterruptedException {
//        Client client = Client.builder().endpoints("http://192.168.56.20:2379").build();
//        KV kvClient = client.getKVClient();
//
//        ByteSequence key = ByteSequence.fromString("/test_key");
//        ByteSequence value = ByteSequence.fromString("test_value");
//
//
//// put the key-value
//        kvClient.put(key, value).get();
//
//// get the CompletableFuture
//        CompletableFuture<GetResponse> getFuture = kvClient.get(key);
//
//// get the value from CompletableFuture
//        GetResponse response = getFuture.get();
//
// delete the key
//        kvClient.delete(key).get();

        String key2="/china/fujian/fuhzou/mawei";
         key2="/china/fujian/fuhzou";

        log.log(Level.INFO,"begin init!!");
        EtcdUtil.ClientInit();

        log.log(Level.INFO,"begin put!!");
//        EtcdUtil.putEtcdKey(key2,"gao2");
        log.log(Level.INFO,key2+": put OVER!!");
//        EtcdUtil.putEtcdKeyWithExpireTime("/china/fujian/fuhzou/mawei/hha","gao",60);
//        log.log(Level.INFO,key2+": put putEtcdKeyWithExpireTime OVER!!");
//        EtcdUtil.putEtcdKey("/china/fujian/fuhzou/mawei/hha2","gao8");
//        EtcdUtil.putEtcdKey("/china/fujian/fuhzou/mawei/hha3","gao8");
//        EtcdUtil.putEtcdKey("/china/fujian/fuhzou/mawei/hha4","gao8");
//        EtcdUtil.putEtcdKey("/china/fujian/fuhzou/mawei/hha5/GOGO","gao8");
        log.log(Level.INFO, ": put OVER!!");
        KeyValue keyValue= EtcdUtil.getEtcdKey(key2);
        keyValue= EtcdUtil.getEtcdKey("/china/fujian/fuhzou/mawei/hha2");
        log.log(Level.INFO, ": getEtcdKey OVER!!");
       if(keyValue==null){
           System.out.println("key does not exist!!");
           return;
       }
        System.out.println(keyValue.getKey().toStringUtf8());
        System.out.println(keyValue.getValue().toStringUtf8());
        log.log(Level.INFO, ": END !!");
    }
}
