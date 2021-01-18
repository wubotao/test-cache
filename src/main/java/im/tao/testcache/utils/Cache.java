package im.tao.testcache.utils;

import im.tao.testcache.bean.Data;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Cache {

    private final static Map<String, Data> cache = new HashMap<>();
    private final static ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
    public static final long DEFAULT_EXPIRATION = 10_000;

    public synchronized static void put(String key, String value) {
        Cache.put(key, value, DEFAULT_EXPIRATION);
    }

    public synchronized static int put(String key, String value, Long expiration) {
        Cache.remove(key);
        if (value == null) {
            return cache.size();
        }
        if (expiration == null || expiration <= 0) {
            expiration = DEFAULT_EXPIRATION;
        }

        Future future = service.schedule(() -> {
            synchronized (Cache.class) {
                cache.remove(key);
            }
        }, expiration, TimeUnit.MILLISECONDS);
        cache.put(key, new Data(value, future));

        return cache.size();
    }

    public synchronized static String get(String key) {
        Data data = cache.get(key);
        if (data == null) {
            return null;
        }
        return data.getValue();
    }

    public synchronized static String remove(String key) {
        Data data = cache.remove(key);
        if (data == null) {
            return null;
        }
        Future future = data.getFuture();
        if (future != null) {
            future.cancel(true);
        }
        return data.getValue();
    }

    public synchronized static int size() {
        return cache.size();
    }

}
