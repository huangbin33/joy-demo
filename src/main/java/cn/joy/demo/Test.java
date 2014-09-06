package cn.joy.demo;

import java.security.SecureRandom;
import java.util.Random;


public class Test {
	public static void main(String[] args) throws Exception{
		//SecureRandom random = new SecureRandom();
		SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
		random.setSeed(123123123123L);
		//byte bytes[] = new byte[20];
		for(int i=0;i<100;i++){
			//int r = random.nextInt(200);
			int r = new Random().nextInt(200);
			System.out.println(r);
		}
		}
		
	
	public static int bytesToInt2(byte[] src, int offset) {  
	    int value;    
	    value = (int) ( ((src[offset] & 0xFF)<<24)  
	            |((src[offset+1] & 0xFF)<<16)  
	            |((src[offset+2] & 0xFF)<<8)  
	            |(src[offset+3] & 0xFF));  
	    return value;  
	}  
}
