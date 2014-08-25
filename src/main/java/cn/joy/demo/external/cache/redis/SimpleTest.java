package cn.joy.demo.external.cache.redis;

import redis.clients.jedis.Jedis;

public class SimpleTest {
	public static void main(String[] args) {
		Jedis jedis = new Jedis("192.168.1.95");  
		
		String keys = "name";  
		
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
