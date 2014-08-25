package cn.joy.demo.external.cache.redis;

import java.util.ResourceBundle;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class PoolTest {
	private static JedisPool pool;  
	static {  
	    ResourceBundle bundle = ResourceBundle.getBundle("redis");  
	    if (bundle == null) {  
	        throw new IllegalArgumentException(  
	                "[redis.properties] is not found!");  
	    }  
	    JedisPoolConfig config = new JedisPoolConfig();  
	    config.setMaxTotal(Integer.valueOf(bundle  
	            .getString("redis.pool.maxActive")));
	    config.setMaxIdle(Integer.valueOf(bundle  
	            .getString("redis.pool.maxIdle")));  
	    config.setMaxWaitMillis(Long.valueOf(bundle.getString("redis.pool.maxWait")));  
	    config.setTestOnBorrow(Boolean.valueOf(bundle  
	            .getString("redis.pool.testOnBorrow")));  
	    config.setTestOnReturn(Boolean.valueOf(bundle  
	            .getString("redis.pool.testOnReturn")));  
	    pool = new JedisPool(config, bundle.getString("redis.ip"),  
	            Integer.valueOf(bundle.getString("redis.port")));  
	    
	}  
	
	public static void main(String[] args) {
		Jedis jedis = pool.getResource(); 
		jedis.select(0);
		
		String keys = "aaa";  
		
		Long t1 = System.currentTimeMillis();
		String value = jedis.get(keys);  
		System.out.println(value);  
		Long t2 = System.currentTimeMillis();
		System.out.println(t2-t1);  
		  
		jedis.set(keys, "lee11232");  
		Long t3 = System.currentTimeMillis();
		System.out.println(t3-t2);  
		
		value = jedis.get(keys);  
		System.out.println(value);  
		
		//jedis.del(keys);  
	}
}
