package cn.joy.demo.external.asm;

public class AsmTest {
	public static void main(String[] args) throws Exception {
		Account account = new Account();
	    account.operation();
	    
	    SecureAccountGenerator.generateSecureAccount().operation();
	}
}
