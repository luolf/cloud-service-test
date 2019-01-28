import com.coreos.jetcd.Watch;
import com.coreos.jetcd.data.ByteSequence;
import com.coreos.jetcd.data.KeyValue;
import com.coreos.jetcd.watch.WatchEvent;

import java.util.List;

public class WatchTest {
    public static void main(String[] args) {
            List<String> s=null;
            for (String i:s){

            }

    }


    public static void testWatch() throws InterruptedException {
        Watch.Watcher watcher = EtcdUtil.getCustomWatcherForSingleKey("abc");
        while(true)
        {
            for (WatchEvent event : watcher.listen().getEvents()) {
                KeyValue kv = event.getKeyValue();
                System.out.println(event.getEventType());
                System.out.println(kv.getKey().toStringUtf8());
                System.out.println(kv.getValue().toStringUtf8());
            }
        }
    }
}
