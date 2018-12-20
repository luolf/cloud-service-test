import com.coreos.jetcd.Client;
import com.coreos.jetcd.data.ByteSequence;
import com.coreos.jetcd.data.KeyValue;
import com.coreos.jetcd.kv.GetResponse;

public class MyUtil {
    private static Client client = null;

    /**
     * init EtcdClient 初始化Etcd客户端
     *
     * @return EtcdClient instance
     */
    public static Client getEtclClient() {
        String endpoints="http://" + "127.0.0.1:2379";
        ClientInit(endpoints);
        return client;
    }
    /**
     * init EtcdClient 初始化Etcd客户端
     *
     * @return EtcdClient instance
     */
    public static Client getEtclClient(String endpoints) {
        return client;
    }
    /**
     * init EtcdClient 初始化Etcd客户端
     */
    private static synchronized void ClientInit(String endpoints) {
        if (null == client) {
            client = Client.builder().endpoints(endpoints.split(",")).build();
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
            keyValue =   client.getKVClient().get(ByteSequence.fromString(key)).get().getKvs().get(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return keyValue;
    }
}
