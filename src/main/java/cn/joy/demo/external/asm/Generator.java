package cn.joy.demo.external.asm;

import java.io.File;
import java.io.FileOutputStream;
import org.objectweb.asm.*;

public class Generator {
	public static void main(String[] args) throws Exception {
		ClassReader cr = new ClassReader("asm.Account");
		ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
		ClassAdapter classAdapter = new AddSecurityCheckClassAdapter(cw);
		cr.accept(classAdapter, ClassReader.SKIP_DEBUG);
		byte[] data = cw.toByteArray();
		System.out.println(new String(data));
		File file = new File("bin/asm/Account.class");
		FileOutputStream fout = new FileOutputStream(file);
		fout.write(data);
		fout.close();
	}
}