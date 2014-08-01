package cn.joy.demo.external.cache.memcached;

import com.alisoft.xplatform.asf.cache.ICacheManager;
import com.alisoft.xplatform.asf.cache.IMemcachedCache;
import com.alisoft.xplatform.asf.cache.memcached.CacheUtil;
import com.alisoft.xplatform.asf.cache.memcached.MemcachedCacheManager;

public class MemTest {
	public static void main(String[] args) {  
        ICacheManager<IMemcachedCache> manager;  
        manager = CacheUtil.getCacheManager(IMemcachedCache.class,  
                MemcachedCacheManager.class.getName());  
        manager.setConfigFile("memcached.xml");  
        manager.start();  
        try {  
            IMemcachedCache cache = manager.getCache("mclient_0");  
            cache.put("name", "actiz");  
            System.out.println(cache.get("name"));  
        } finally {  
            manager.stop();  
        }  
    }  
}
