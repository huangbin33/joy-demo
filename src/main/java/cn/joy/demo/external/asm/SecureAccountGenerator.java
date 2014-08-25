package cn.joy.demo.external.asm;

import java.io.IOException;

import org.objectweb.asm.ClassAdapter;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;

public class SecureAccountGenerator {
	private static class AccountGeneratorClassLoader extends ClassLoader {
		public Class defineClassFromClassFile(String className, byte[] classFile) throws ClassFormatError {
			return defineClass("asm.Account$EnhancedByASM", classFile, 0, classFile.length);
		}
	}
	
	private static AccountGeneratorClassLoader classLoader = new AccountGeneratorClassLoader();
	private static Class secureAccountClass;

	public static Account generateSecureAccount() throws IOException, ClassFormatError, InstantiationException, IllegalAccessException {
		if (null == secureAccountClass) {
			ClassReader cr = new ClassReader("asm.Account");
			ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
			ClassAdapter classAdapter = new AddSecurityCheckClassAdapter(cw);
			cr.accept(classAdapter, ClassReader.SKIP_DEBUG);
			byte[] data = cw.toByteArray();
			secureAccountClass = classLoader.defineClassFromClassFile("asm.Account$EnhancedByASM", data);
		}
		return (Account) secureAccountClass.newInstance();
	}

}
