
import cn.swingz.entities.T1;
import cn.swingz.entities.T3;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.IgniteException;
import org.apache.ignite.Ignition;


public class PersonStoreServer {
    public static void main(String[] args) throws IgniteException {

        Ignite ignite = Ignition.start("ignite-service-config.xml");

        IgniteCache<Integer, T1> t1cache = ignite.getOrCreateCache("t1Cache");
        t1cache.loadCache(null);
        IgniteCache<String, T3> t3cache = ignite.getOrCreateCache("t3Cache");
        t3cache.loadCache(null);

    }
}