//
//import com.coreos.jetcd.Client;
//import com.coreos.jetcd.data.ByteSequence;
//import com.coreos.jetcd.data.KeyValue;
//
//import java.util.List;
//
//public class EtcdUtil {
//    public static String endPoint="http://192.168.56.20:2379";
//    //etcl客户端链接
//    private static Client client = null;
//    //链接初始化
//    public static synchronized Client getEtclClient(){
//        if(null == client){
//
//            client = Client.builder().endpoints(endPoint).build();
//        }
//        return client;
//    }
//
//    /**
//     * 根据指定的配置名称获取对应的value
//     * @param key 配置项
//     * @return
//     * @throws Exception
//     */
//    public static String getEtcdValueByKey(String key) throws Exception {
//        List<KeyValue> kvs = EtcdUtil.getEtclClient().getKVClient().get(ByteSequence.fromString(key)).get().getKvs();
//        if(kvs.size()>0){
//            String value = kvs.get(0).getValue().toStringUtf8();
//            return value;
//        }
//        else {
//            return null;
//        }
//    }
//
//    /**
//     * 新增或者修改指定的配置
//     * @param key
//     * @param value
//     * @return
//     */
//    public static void putEtcdValueByKey(String key,String value) throws Exception{
//        EtcdUtil.getEtclClient().getKVClient().put(ByteSequence.fromString(key),ByteSequence.fromBytes(value.getBytes("utf-8")));
//
//    }
//
//    /**
//     * 删除指定的配置
//     * @param key
//     * @return
//     */
//    public static void deleteEtcdValueByKey(String key){
//        EtcdUtil.getEtclClient().getKVClient().delete(ByteSequence.fromString(key));
//
//    }
//
//    //V3 api配置初始化和监听
//    public void init(){
//        try {
//            //加载配置
//            getConfig(EtcdUtil.getEtclClient().getKVClient().get(ByteSequence.fromString(ETCD_CONFIG_FILE_NAME)).get().getKvs());
////启动监听线程
//            new Thread(() -> {
////对某一个配置进行监听
//                Watch.Watcher watcher = EtcdUtil.getEtclClient().getWatchClient().watch(ByteSequence.fromString("etcd_key"));
//                try {
//                    while(true) {
//                        watcher.listen().getEvents().stream().forEach(watchEvent -> {
//                            KeyValue kv = watchEvent.getKeyValue();
// //获取事件变化类型
// System.out.println(watchEvent.getEventType());
//               //获取发生变化的key
//                            System.out.println(kv.getKey().toStringUtf8());
// //获取变化后的value
//    String afterChangeValue = kv.getValue().toStringUtf8();
//
//                        });
//                    }
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//
//                }
//            }).start();
//        } catch (Exception e) {
//            e.printStackTrace();
//
//        }
//
//    }
//    private String getConfig(List<KeyValue> kvs){
//        if(kvs.size()>0){
//            String config = kvs.get(0).getValue().toStringUtf8();
//            System.out.println("etcd 's config 's configValue is :"+config);
//            return config;
//        }
//        else {
//            return null;
//        }
//    }
//    public static void  main(String[] args){
//
//    }
//}
//
//
