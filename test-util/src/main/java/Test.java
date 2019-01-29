import com.coreos.jetcd.Client;
import com.coreos.jetcd.KV;
import com.coreos.jetcd.data.ByteSequence;
import com.coreos.jetcd.data.KeyValue;
import com.coreos.jetcd.kv.GetResponse;
import sun.rmi.runtime.Log;

import java.util.List;
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
        EtcdUtil.ClientInit();

        log.log(Level.INFO,"begin init0!!");
        for(int i=0;i<10;i++) {
            if(EtcdUtil.putEtcdKey("/test"+i,"/test"+i)){
                log.log(Level.INFO,"done"+i+1);
            }
            Thread.sleep(10000);
            log.log(Level.INFO,"begin init!!"+i+1);
        }
        String key2="/china/fujian/fuhzou/mawei";
         key2="/china/fujian/fuhzou";

        log.log(Level.INFO,"begin init!!");


        log.log(Level.INFO,"begin put!!");
//        if(!EtcdUtil.putEtcdKey(key2,"gao2")){
//            log.log(Level.INFO,key2+": putEtcdKey 失败!!");
//        }
        log.log(Level.INFO,key2+": put OVER!!");
//        EtcdUtil.putEtcdKeyWithExpireTime("/china/fujian/fuhzou/mawei/hha","gaohaha",60);
        for(int i=0;i<10;i++){
            if(!EtcdUtil.putEtcdKey("/china/fujian/fuhzou/mawei/"+i,"gao"+i)){
                log.log(Level.INFO,key2+": putEtcdKey 失败!!"+i);
            }
            Thread.sleep(10);
        }
//        for(int i=0;i<10;i++){
//            if(!EtcdUtil.deleteEtcdKey("/china/fujian/fuhzou/mawei/"+i)){
//                log.log(Level.INFO,key2+": deleteEtcdKey 失败!!"+i);
//            }
//            Thread.sleep(100);
//        }

//        if(!EtcdUtil.deleteEtcdKeyWithPrefix("/china/fujian/fuhzou/mawei")){
//            log.log(Level.INFO,key2+": deleteEtcdKey 失败!!");
//        }
//        log.log(Level.INFO,"批量开始读");
//          List<String> list=EtcdUtil.getWithPrefix("/china");
//        log.log(Level.INFO,"批量读结束"+list.size());
//        for(int i=0;i<10;i++){
//            if(!EtcdUtil.putEtcdKey("/china/fujian/fuhzou/mawei/"+i,"2gao"+i)){
//                log.log(Level.INFO,key2+": putEtcdKey 失败!!"+i);
//            }
//            Thread.sleep(10);
//        }
//        log.log(Level.INFO,"批量开始读2");
//        List<String> list2=EtcdUtil.getWithPrefix("/china");
//        log.log(Level.INFO,"批量读结束2"+list2.size());
//        EtcdUtil.putEtcdKey("/china/fujian/fuhzou/mawei/hha5/GOGO","gao8");
//        log.log(Level.INFO, ": put OVER!!");
//        KeyValue keyValue= EtcdUtil.getEtcdKey(key2);
//        keyValue= EtcdUtil.getEtcdKey("/china/fujian/fuhzou/mawei/hha2");
//        log.log(Level.INFO, ": getEtcdKey OVER!!");
//       if(keyValue==null){
//           System.out.println("key does not exist!!");
//           return;
//       }
//        System.out.println(keyValue.getKey().toStringUtf8());
//        System.out.println(keyValue.getValue().toStringUtf8());
        log.log(Level.INFO, ": END !!");
    }
}
