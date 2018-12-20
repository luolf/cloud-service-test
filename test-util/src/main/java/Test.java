import com.coreos.jetcd.Client;
import com.coreos.jetcd.KV;
import com.coreos.jetcd.data.ByteSequence;
import com.coreos.jetcd.kv.GetResponse;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class Test {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
//        Client client = Client.builder().endpoints("http://192.168.56.20:2379").build();
//        KV kvClient = client.getKVClient();
//
//        ByteSequence key = ByteSequence.fromString("test_key");
//        ByteSequence value = ByteSequence.fromString("test_value");
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
//// delete the key
//        kvClient.delete(key).get();
        EtcdUtil.ClientInit();
        EtcdUtil.putEtcdKey("name","llf");
    }
}
