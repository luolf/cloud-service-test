//import com.coreos.jetcd.Client;
////import com.newland.paas.paastool.etcdtool.utils.SslUtil;
//import io.netty.handler.ssl.SslContext;
//import io.netty.handler.ssl.SslContextBuilder;
//
//import javax.net.ssl.SSLException;
//import java.io.File;
//import java.io.IOException;
//import java.security.PrivateKey;
//import java.security.cert.X509Certificate;
//
//public class EtcdClient {
//
//    public static void main(String[] args) throws IOException {
//
//
//
//        File key = new File("D:\\reacher\\etcd-v3.3.10-windows-amd64\\client-key.pem");
//        PrivateKey privateKey = SslUtil.toPrivateKeyFromPKCS1(key);
//
//        File pem = new File("D:\\reacher\\etcd-v3.3.10-windows-amd64\\client.pem");
//        X509Certificate[] keyCertChain;
//        try {
//            keyCertChain = SslUtil.toX509Certificates(pem);
//        } catch (Exception e) {
//            throw new IllegalArgumentException("Input stream not contain valid certificates.", e);
//        }
//
//        Client client = null;
//        try {
//            SslContext sslContext = SslContextBuilder.forClient().enableOcsp(false).keyManager(privateKey, keyCertChain).build();
//            client = Client.builder().sslContext(sslContext).endpoints("https://127.0.0.1:2379").build();
//        }catch (SSLException e) {
//            e.printStackTrace();
//        }finally {
//            if (null != client) {
//                client.close();
//            }
//        }
//    }
//}
//
