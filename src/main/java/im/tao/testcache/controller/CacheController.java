package im.tao.testcache.controller;

import im.tao.testcache.bean.Data;
import im.tao.testcache.utils.Cache;
import org.springframework.web.bind.annotation.*;

@RestController
public class CacheController {

    @GetMapping("/get/{key}")
    public String get(@PathVariable("key") String key) {
        return Cache.get(key);
    }

    /**
     *
     * @param key
     * @param value
     * @param expiration
     * @return the size of the cache
     */
    @PostMapping("/set/{key}/{value}/{expiration}")
    public int put(@PathVariable("key") String key,
                      @PathVariable("value") String value,
                      @PathVariable("expiration") Long expiration) {
        return Cache.put(key, value, expiration);
    }
}



