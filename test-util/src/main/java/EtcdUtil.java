
import com.coreos.jetcd.Client;
import com.coreos.jetcd.KV;
import com.coreos.jetcd.Watch;
import com.coreos.jetcd.data.ByteSequence;
import com.coreos.jetcd.data.KeyValue;
import com.coreos.jetcd.kv.GetResponse;
import com.coreos.jetcd.kv.PutResponse;
import com.coreos.jetcd.lease.LeaseGrantResponse;
import com.coreos.jetcd.options.DeleteOption;
import com.coreos.jetcd.options.GetOption;
import com.coreos.jetcd.options.PutOption;
import com.coreos.jetcd.options.WatchOption;
import io.netty.handler.ssl.SslContext;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;


public class EtcdUtil {
    private static Client client = null;

    /**
     * init EtcdClient 初始化Etcd客户端
     *
     * @return EtcdClient instance
     */
    public static Client getEtclClient() {
        ClientInit();
        return client;
    }

    /**
     * init EtcdClient 初始化Etcd客户端
     */
    public static synchronized void ClientInit() {
        if (null == client) {
            //read current ip;
            //String machineIp = getMachineIp();
            //create client;
            //client = Client.builder().endpoints("http://" + machineIp + port).build();
            try {
                client = Client.builder().endpoints("http://" + "192.168.56.20:2379").build();

//                System.out.println( client.getClusterClient().);
//                Client.builder().lazyInitialization(true)

            }catch(Exception e){
                e.printStackTrace();
            }

        }
    }

    /**
     * get single etcdKey from etcd; 从Etcd获取单个key
     *
     * @param key etcdKey
     * @return etcdKey and value 's instance
     */
    public static KeyValue getEtcdKey(String key) {
        KeyValue keyValue = null;
        try {
            KV kv =EtcdUtil.getEtclClient().getKVClient();
            ByteSequence bs=ByteSequence.fromString(key);
            CompletableFuture<GetResponse> tt=kv.get(bs);
            GetResponse response =tt.get();
            response =tt.get();
            response =tt.get();


            if (response.getKvs().isEmpty()) {
                // key does not exist
                return null;
            }
            keyValue = response.getKvs().get(0);


        } catch (Exception e) {
//            e.printStackTrace();
            System.out.println("连接ETCD失败"+e.getMessage());
        }
        return keyValue;
    }
   public static String get(String key){
       KeyValue kv=getEtcdKey(key);
       return kv==null?null:kv.getValue().toStringUtf8();
   }
    public static Map<String ,String > getWithPrefix(String key){
        List<KeyValue> keyValues=getEtcdKeyWithPrefix(key);
        HashMap hashMap = new HashMap();
        for (KeyValue kv:keyValues){
            hashMap.put(kv.getKey().toStringUtf8(),kv.getValue().toStringUtf8());
        }
        return hashMap;
    }
    /**
     * get all etcdKey with this prefix 从Etcd获取满足前缀的所有key
     *
     * @param prefix etcdKey's prefix
     * @return all etcdKey with this prefix
     */
    public static List<KeyValue> getEtcdKeyWithPrefix(String prefix) {
        List<KeyValue> keyValues = new ArrayList<>();
        GetOption getOption = GetOption.newBuilder().withPrefix(ByteSequence.fromString(prefix)).build();
        try {
            keyValues = EtcdUtil.getEtclClient().getKVClient().get(ByteSequence.fromString(prefix), getOption).get().getKvs();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return keyValues;
    }

    /**
     * put single etcdKey 将单个key放入Etcd中
     *
     * @param key   single etcdKey
     * @param value etcdKey's value
     */
    public static void putEtcdKey(String key, String value)   {
        try {
            EtcdUtil.getEtclClient().getKVClient().put(ByteSequence.fromString(key), ByteSequence.fromString(value)).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    /**
     * put single etcdKey with a expire time (by etcd lease) 将一个有过期时间的key放入Etcd，通过lease机制
     *
     * @param key        single etcdKey
     * @param value      etcdKey's value
     * @param expireTime expire time (s) 过期时间，单位秒
     * @return lease id 租约id
     */
    public static long putEtcdKeyWithExpireTime(String key, String value, long expireTime) {
        CompletableFuture<LeaseGrantResponse> leaseGrantResponse = client.getLeaseClient().grant(expireTime);
        PutOption putOption;
        try {
            putOption = PutOption.newBuilder().withLeaseId(leaseGrantResponse.get().getID()).build();
            client.getKVClient().put(ByteSequence.fromString(key), ByteSequence.fromString(value), putOption);
            return leaseGrantResponse.get().getID();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0L;
    }

    /**
     * put single etcdKey with a lease id 将一个key绑定指定的租约放入到Etcd。
     *
     * @param key     single etcdKey
     * @param value   etcdKey's value
     * @param leaseId lease id 租约id
     * @return revision id if exception return 0L
     */
    public static long putEtcdKeyWithLeaseId(String key, String value, long leaseId) throws Exception {
        PutOption putOption = PutOption.newBuilder().withLeaseId(leaseId).build();
        CompletableFuture<PutResponse> putResponse = client.getKVClient().put(ByteSequence.fromString(key), ByteSequence.fromString(value), putOption);
        try {
            return putResponse.get().getHeader().getRevision();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0L;
    }

    /**
     * keep alive for a single lease
     *
     * @param leaseId lease id 租约Id
     */
    public static void keepAliveEtcdSingleLease(long leaseId) {
        EtcdUtil.getEtclClient().getLeaseClient().keepAlive(leaseId);
    }

    /**
     * delete single etcdKey 从Etcd中删除单个key
     *
     * @param key etcdKey
     */
    public static void deleteEtcdKey(String key) throws ExecutionException, InterruptedException {
        EtcdUtil.getEtclClient().getKVClient().delete(ByteSequence.fromString(key)).get();
    }

    /**
     * delete all key with prefix 从Etcd中删除所有满足前缀匹配的key
     *
     * @param prefix etcdKey's prefix
     */
    public static void deleteEtcdKeyWithPrefix(String prefix) throws ExecutionException, InterruptedException {
        DeleteOption deleteOption = DeleteOption.newBuilder().withPrefix(ByteSequence.fromString(prefix)).build();
        EtcdUtil.getEtclClient().getKVClient().delete(ByteSequence.fromString(prefix), deleteOption).get();
    }

    /**
     * get single etcdKey's custom watcher 得到一个单个key的自定义观察者
     *
     * @param key etcdKey
     * @return single etcdKey's custom watcher
     */
    public static Watch.Watcher getCustomWatcherForSingleKey(String key) {
        return EtcdUtil.getEtclClient().getWatchClient().watch(ByteSequence.fromString(key));
    }

    /**
     * get a watcher who watch all etcdKeys with prefix 得到一个满足所有前缀匹配的key集合的自定义观察者
     *
     * @param prefix etcdKey's prefix
     * @return a watcher who watch all etcdKeys with prefix
     */
    public static Watch.Watcher getCustomWatcherForPrefix(String prefix) {
        WatchOption watchOption = WatchOption.newBuilder().withPrefix(ByteSequence.fromString(prefix)).build();
        return EtcdUtil.getEtclClient().getWatchClient().watch(ByteSequence.fromString(prefix), watchOption);
    }
}


