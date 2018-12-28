//package com.newland.paas.paastool.etcdtool.utils;
//
//import com.coreos.jetcd.Client;
//import com.coreos.jetcd.data.ByteSequence;
//import com.coreos.jetcd.data.KeyValue;
//import com.coreos.jetcd.kv.GetResponse;
//import com.coreos.jetcd.lock.LockResponse;
//import com.coreos.jetcd.options.GetOption;
//import com.newland.paas.paastool.etcdtool.EtcdConnection;
//import com.newland.paas.paastool.etcdtool.ToolDict;
//import com.newland.paas.paastool.etcdtool.impl.EtcdConImpl;
//import com.newland.paas.paastool.vo.Provider;
//
//import io.grpc.netty.GrpcSslContexts;
//import io.netty.handler.ssl.SslContext;
//import io.netty.handler.ssl.SslContextBuilder;
//import org.testng.annotations.Test;
//
//import javax.net.ssl.SSLException;
//import java.io.File;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.List;
//import java.util.concurrent.CompletableFuture;
//import java.util.concurrent.ExecutionException;
//
//
///**
// * Created with IntelliJ IDEA.
// * Description:
// * User: luolifeng
// * Date: 2018-12-25
// * Time: 11:25
// */
//public class ProviderOperTest {
//
//    @Test
//    public void testGetEtcdKey2Provider() {
//    }
//
//    @Test
//    public void testPutProvider() {
//    }
////    public static void printJsomLog(Object obj){
//////        log.info(toJsonString(obj));
////        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSSS").format(new Date())+":"+ JSON.toJSONString(obj));
////    }
//
//
////    @Test
////    public void tOther() throws SSLException, ExecutionException, InterruptedException {
////        File cert = new File("F:\\newland\\yjy\\tools\\etcdkeeper\\etcd\\etcd.pem");
////        File ca = new File("F:\\newland\\yjy\\tools\\etcdkeeper\\etcd\\ca.pem");
////        File pkcs8Key = new File("F:\\newland\\yjy\\tools\\etcdkeeper\\etcd\\pkcs8-key.pem");
////
////        SslContextBuilder sslContextBuilder=GrpcSslContexts.forClient();
////        sslContextBuilder=sslContextBuilder.trustManager(ca);
////        sslContextBuilder=sslContextBuilder.keyManager(cert, pkcs8Key);
////        SslContext s=sslContextBuilder.build();
//////        SslContext s= sslContextBuilder.trustManager(cert)
//////                .keyManager(cert, pkcs8Key).build();
////
////
////        Client client = Client.builder()
////                .endpoints("https://192.168.11.63:2379")
//////                .sslContext()
////                .build();
////        getWithKey(client,"/traefik");
////
////    }
//    public void getWithKey(Client client,String prefix) throws ExecutionException, InterruptedException {
//        GetOption getOption = GetOption.newBuilder().withPrefix(ByteSequence.fromString(prefix)).build();
//
//         List<KeyValue> rsts=client.getKVClient().get(ByteSequence.fromString(prefix), getOption).get().getKvs();
//        rsts.size();
//    }
//
//    public void getKey(Client client,String key) throws ExecutionException, InterruptedException {
//        CompletableFuture<GetResponse> getResponseCompletableFuture = client.getKVClient().get(ByteSequence.fromString(key));
//        GetResponse getResponse = getResponseCompletableFuture.get();
//        List<KeyValue> kvs = getResponse.getKvs();
//        for (KeyValue kv : kvs) {
//            System.out.println("key=" + kv.getKey().toStringUtf8());
//            System.out.println("value=" + kv.getValue().toStringUtf8());
//        }
//    }
//
//    public static void main(String[] args) throws ExecutionException, InterruptedException {
//
//        Long frwId=11L;
//        String traefikPrefix = "/traefik-"+frwId;
//        String alias=traefikPrefix+"/alias";
//        String traefikConf = traefikPrefix+"-conf";
//        ByteSequence shareKey=null;
//        Integer version =0;
//        alias="/test";
//        EtcdConnection connection = EtcdFactory.getEtcdConnection("http://192.168.11.63:2379");
//        connection.deleteEtcdKeyWithPrefix(alias);
////        connection.deleteEtcdKey(alias);
////        connection.putEtcdKey(alias,traefikConf+"/0");
////        Long leaseId = connection.getEtcdClient().getLeaseClient().grant(1L).get().getID();
////        connection.getEtcdClient().getLockClient().unlock(ByteSequence.fromString(alias)).get();
////        connection.getEtcdClient().getLockClient().unlock(ByteSequence.fromString(alias)).get();
////        /test/694d67e87c1d631a
////                /test/694d67e87c1d66e6
////                /test/694d67e87c1d631a
//
////        CompletableFuture<LockResponse> future = connection.getEtcdClient().getLockClient().lock(ByteSequence.fromString(alias), leaseId);
////        System.out.println("return1");
////        shareKey=future.get().getKey();
////        System.out.println("return2");
//
////
////
////        connection.putEtcdKey(alias,traefikConf+"/1");
////        connection.getEtcdClient().getLockClient().unlock(ByteSequence.fromString(alias)).get();
//        if(shareKey==null || 1==1){
//            System.out.println("return");
//            return;
//        }
//
//
//        for(int i=0;i<30;i++) {
//            final int sn=i;
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    Thread.currentThread().setName("编号："+sn);
//                    try {
//                        tt();
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    } catch (ExecutionException e) {
//                        e.printStackTrace();
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//
//
//                }
//            }).start();
//        }
//    }
//    @Test
//    public static void tt() throws ExecutionException, InterruptedException {
//        Long frwId=11L;
//        String traefikPrefix = "/traefik-"+frwId;
//        String alias=traefikPrefix+"/alias";
//        String traefikConf = traefikPrefix+"-conf";
//        ByteSequence shareKey=null;
//        Integer version =0;
//
//        EtcdConnection connection = EtcdFactory.getEtcdConnection("http://192.168.11.63:2379");
//        String s = connection.get(alias);
//        if (s == null) {
//            return;
//        }
//         try {
//             Long leaseId = connection.getEtcdClient().getLeaseClient().grant(5000L).get().getID();
//            CompletableFuture<LockResponse> future = connection.getEtcdClient().getLockClient().lock(ByteSequence.fromString(alias), leaseId);
//            shareKey=future.get().getKey();
//
//             s = connection.get(alias);
//             System.out.println(Thread.currentThread().getName()+"加锁alias="+s);
//             String[] pathKeys = s.split(ToolDict.PATH_SLASH);
//             if (pathKeys.length != 3) {
//                 System.out.println(Thread.currentThread().getName()+"异常解锁currentVersion="+pathKeys[2]);
//                 connection.getEtcdClient().getLockClient().unlock(shareKey).get();
//                 return;
//             }
//             version = Integer.parseInt(pathKeys[2]) + 1;
//
//
//            String newPrefix = traefikConf + ToolDict.PATH_SLASH + version;
//            ProviderOper providerOper = new ProviderOper(connection);
//            Provider provider = providerOper.getEtcdKey2Provider("/traefik");
//            provider.getFrontends().get("frontend0").setPriority(version);
//
////            providerOper.putProvider(provider, newPrefix);
////            providerOper.getEtcdKey2Provider(newPrefix);
////             connection.putEtcdKey(alias,newPrefix);
//
//
//        }catch(Exception e){
//
//        }finally {
//             System.out.println(Thread.currentThread().getName()+"解锁");
//            connection.getEtcdClient().getLockClient().unlock(shareKey).get();
//        }
//    }
//    @Test
//    public  void getConnection(){
//        EtcdConnection connection=new EtcdConImpl("http://192.168.11.63:2379");
//
////        EtcdConnection connection=EtcdFactory.getEtcdConnection("http://192.168.11.63:2379");
//    }
//}