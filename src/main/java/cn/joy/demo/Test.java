package cn.joy.demo;

import java.security.SecureRandom;
import java.util.Random;

public class Test {
	public static void main(String[] args) throws Exception {
		B b = new B();
		b.ps();
	}

	public static int bytesToInt2(byte[] src, int offset) {
		int value;
		value = (int) (((src[offset] & 0xFF) << 24) | ((src[offset + 1] & 0xFF) << 16)
				| ((src[offset + 2] & 0xFF) << 8) | (src[offset + 3] & 0xFF));
		return value;
	}
}

class A {
	protected String s = "aaaa";

	protected void ps() {
		System.out.println(s);
	}
}

class B extends A {
	
	protected void ps() {
		System.out.println(s);
	}
}
