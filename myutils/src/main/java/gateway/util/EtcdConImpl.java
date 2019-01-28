package gateway.util;

import com.coreos.jetcd.Client;
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
import com.newland.paas.log.Log;
import com.newland.paas.log.LogFactory;
import com.newland.paas.paastool.etcdtool.utils.SslUtil;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;

import javax.net.ssl.SSLException;
import java.io.ByteArrayInputStream;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: luolifeng
 * Date: 2018-12-20
 * Time: 15:55
 * @author Administrator
 */
public class EtcdConImpl implements EtcdConnection {
    private Client client = null;
    private String[] endpoints;
    private String privateKey;
    private String keyCertChain;
    private static final Log log = LogFactory.getLogger(EtcdConImpl.class);

    public EtcdConImpl(String endps) {
        this.endpoints=endps.split(ToolDict.SPLIT);
         clientInit();
    }
    public EtcdConImpl(String endps,String privateKey,String keyCertChain) {
        this.endpoints=endps.split(ToolDict.SPLIT);
        this.keyCertChain=keyCertChain;
        this.privateKey=privateKey;
        clientInit();
    }
    /**
     * init EtcdClient 初始化Etcd客户端
     *
     * @return EtcdClient instance
     */
    @Override
   public Client getEtcdClient() {
          if (null == client) {
              clientInit();
          }
        return client;
    }

    /**
     * init EtcdClient 初始化Etcd客户端
     */
   private synchronized void clientInit() {
        if (null == client) {
            if(privateKey!=null && keyCertChain!=null){
                PrivateKey pk = SslUtil.toPrivateKeyFromPKCS1(new ByteArrayInputStream(privateKey.getBytes()));
                try {
                    X509Certificate[] kcc = SslUtil.toX509Certificates(keyCertChain);
                    SslContext sslContext = SslContextBuilder.forClient().enableOcsp(false).keyManager(pk, kcc).build();
                    client = Client.builder().sslContext(sslContext).endpoints(endpoints).build();
                } catch (SSLException e) {
                    throw new RuntimeException(e);
                } catch (Exception e) {
                    throw new IllegalArgumentException("Input String not contain valid certificates.", e);
                }

            }else{
                client = Client.builder().endpoints(endpoints).build();
            }
        }
    }

    /**
     * get single etcdKey from etcd; 从Etcd获取单个key
     *
     * @param key etcdKey
     * @return etcdKey and value 's instance
     */
    @Override
    public KeyValue getEtcdKey(String key) throws ExecutionException, InterruptedException {

            GetResponse response =this.getEtcdClient().getKVClient().get(ByteSequence.fromString(key)).get();
            if (response==null||response.getKvs().isEmpty()) {
                // key does not exist
                return null;
            }
            return response.getKvs().get(0);

    }
    /**
     * @Created with IntelliJ IDEA.
     * @Description:
     * @return: 通过Key直接获取对应的value,
     * @params:
     * @author: luolifeng
     * @Date: 2018/12/21
     */
    @Override
    public String get(String key) throws ExecutionException, InterruptedException {
        KeyValue kv=getEtcdKey(key);
        if(kv==null){
            return null;
        }
        return kv.getValue().toStringUtf8();
    }
    /**
     * @Created with IntelliJ IDEA.
     * @Description:
     * @return: 通过Key直接获取对应的value,
     * @params:
     * @author: luolifeng
     * @Date: 2018/12/21
     */
    @Override
    public Map<String,String> getWithPrefix(String prefix) throws ExecutionException, InterruptedException {
        List<KeyValue> kvList=getEtcdKeyWithPrefix( prefix);
        HashMap<String,String> rst=new HashMap<>();
        if(kvList==null ){
            return null;
        }
        for(KeyValue kv:kvList){
            rst.put(kv.getKey().toStringUtf8(),kv.getValue().toStringUtf8());
        }
        return rst;
    }
    /**
     * get all etcdKey with this prefix 从Etcd获取满足前缀的所有key
     *
     * @param prefix etcdKey's prefix
     * @return all etcdKey with this prefix
     */
    @Override
    public   List<KeyValue> getEtcdKeyWithPrefix(String prefix) throws ExecutionException, InterruptedException {

        GetOption getOption = GetOption.newBuilder().withPrefix(ByteSequence.fromString(prefix)).build();

        return this.getEtcdClient().getKVClient().get(ByteSequence.fromString(prefix), getOption).get().getKvs();

    }

    /**
     * put single etcdKey 将单个key放入Etcd中
     *
     * @param key   single etcdKey
     * @param value etcdKey's value
     */
    @Override
    public   void putEtcdKey(String key, String value) throws ExecutionException, InterruptedException {
        this.getEtcdClient().getKVClient().put(ByteSequence.fromString(key), ByteSequence.fromString(value)).get();
     }

    /**
     * put single etcdKey with a expire time (by etcd lease) 将一个有过期时间的key放入Etcd，通过lease机制
     *
     * @param key        single etcdKey
     * @param value      etcdKey's value
     * @param expireTime expire time (s) 过期时间，单位秒
     * @return lease id 租约id
     */
    @Override
    public   long putEtcdKeyWithExpireTime(String key, String value, long expireTime) throws ExecutionException, InterruptedException {
        CompletableFuture<LeaseGrantResponse> leaseGrantResponse = client.getLeaseClient().grant(expireTime);
        PutOption putOption= PutOption.newBuilder().withLeaseId(leaseGrantResponse.get().getID()).build();
            this.getEtcdClient().getKVClient().put(ByteSequence.fromString(key), ByteSequence.fromString(value), putOption);
            return leaseGrantResponse.get().getID();

    }

    /**
     * put single etcdKey with a lease id 将一个key绑定指定的租约放入到Etcd。
     *
     * @param key     single etcdKey
     * @param value   etcdKey's value
     * @param leaseId lease id 租约id
     * @return revision id if exception return 0L
     */
    @Override
    public   long putEtcdKeyWithLeaseId(String key, String value, long leaseId) throws ExecutionException, InterruptedException {
        PutOption putOption = PutOption.newBuilder().withLeaseId(leaseId).build();
        CompletableFuture<PutResponse> putResponse = this.getEtcdClient().getKVClient().put(ByteSequence.fromString(key), ByteSequence.fromString(value), putOption);
        return putResponse.get().getHeader().getRevision();
    }

    /**
     * keep alive for a single lease
     *
     * @param leaseId lease id 租约Id
     */
    @Override
    public   void keepAliveEtcdSingleLease(long leaseId) {
        this.getEtcdClient().getLeaseClient().keepAlive(leaseId);
    }

    /**
     * delete single etcdKey 从Etcd中删除单个key
     *
     * @param key etcdKey
     */
    @Override
    public   void deleteEtcdKey(String key) throws ExecutionException, InterruptedException {
        this.getEtcdClient().getKVClient().delete(ByteSequence.fromString(key)).get();

    }

    /**
     * delete all key with prefix 从Etcd中删除所有满足前缀匹配的key
     *
     * @param prefix etcdKey's prefix
     */
    @Override
    public   void deleteEtcdKeyWithPrefix(String prefix) throws ExecutionException, InterruptedException{
        DeleteOption deleteOption = DeleteOption.newBuilder().withPrefix(ByteSequence.fromString(prefix)).build();
        this.getEtcdClient().getKVClient().delete(ByteSequence.fromString(prefix), deleteOption).get();

    }

    /**
     * get single etcdKey's custom watcher 得到一个单个key的自定义观察者
     *
     * @param key etcdKey
     * @return single etcdKey's custom watcher
     */
    @Override
    public   Watch.Watcher getCustomWatcherForSingleKey(String key) {
        return this.getEtcdClient().getWatchClient().watch(ByteSequence.fromString(key));
    }

    /**
     * get a watcher who watch all etcdKeys with prefix 得到一个满足所有前缀匹配的key集合的自定义观察者
     *
     * @param prefix etcdKey's prefix
     * @return a watcher who watch all etcdKeys with prefix
     */
    @Override
    public Watch.Watcher getCustomWatcherForPrefix(String prefix) {
        WatchOption watchOption = WatchOption.newBuilder().withPrefix(ByteSequence.fromString(prefix)).build();
        return this.getEtcdClient().getWatchClient().watch(ByteSequence.fromString(prefix), watchOption);
    }
}