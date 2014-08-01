package cn.joy.demo.external.ftp;

import java.io.*;
import java.util.List;

import org.apache.commons.net.ftp.FTPFile;

public class TestFTP {
	public static void main(String[] args) throws Exception{
		FTPUtil ftp = new FTPUtil("a","a","192.168.1.235",21,"");
		ftp.connectServer();
		ftp.changeWorkingDirectory("ftpDir");
		List<FTPFile> files = ftp.listRemoteFiles("bb.txt");
		for(FTPFile file : files){
			System.out.println(file.getName());
		}

		//ftp.changeWorkingDirectory(ftp.getFiledir()+"/123");
		File file = new File("D:/ids.gif");
		//InputStream is = new FileInputStream(file);
		//ftp.uploadFile(is, "123.gif", 2);
		//ftp.changeWorkingDirectory(ftp.getFiledir()+"/1234/22");
		//File file2 = new File("D:/submit rule使用说明.doc");
		//ftp.uploadFile(new FileInputStream(file2), "222/333/使用说明2.doc", 2);
		//File file2 = new File("D:/aaaa.txt");
		//ftp.uploadFile(new FileInputStream(file2), "bbb2.txt", 2);
		//ftp.loadFile("Form需要隐掉的配置项.txt", "D:/配置项.txt");
		//ftp.deleteFile("哈哈/1234/22/使用说明34.doc");
		//ftp.closeConnect();
		//file.delete();
	}
}
