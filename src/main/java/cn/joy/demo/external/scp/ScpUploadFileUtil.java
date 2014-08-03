package cn.joy.demo.external.scp;

import java.io.File;

import cn.joy.framework.kits.FileKit;
import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.SCPClient;
import ch.ethz.ssh2.SCPOutputStream;

public class ScpUploadFileUtil {
	public static String uploadFile(String remoteFile, byte[] content) throws Exception {
		System.out.println("==============开始上传====" + remoteFile + "==============");
		try {
			// scp文件目录
			String scpPath = "/home";
			// scp ip地址
			String scpAddress = "";
																	// //
			// scp 端口号
			String scpPort = "22";
			// scp 用户名
			String username = "";
			// scp 密码
			String password = "";

			System.out.println("加入SCP实现远程传输文件");
			// 加入SCP实现远程传输文件
			Connection con = new Connection(scpAddress, Integer.parseInt(scpPort));
			System.out.println("开始连接");
			// 连接
			con.connect();
			System.out.println("登陆远程服务器" + username + "," + password);
			// 登陆远程服务器的用户名密码
			boolean isAuthed = con.authenticateWithPassword(username, password);
			// 登陆失败
			if (!isAuthed) {
				System.out.println("登陆远程服务器失败");
				return "506";
			}
			System.out.println("建立scp客户端");

			// 建立SCP客户端
			SCPClient scpClient = con.createSCPClient();
			System.out.println("开始上传文件到服务器");

			// 0755是指权限编号
			SCPOutputStream sos = scpClient.put(remoteFile, content.length, scpPath, "0755");
			sos.write(content);
			sos.close();
			System.out.println("关闭连接");
			con.close();
			System.out.println("上传完成");
			return "200";
		} catch (Exception e) {
			e.printStackTrace();
			return "500";
		}

//      
//   SFTPv3Client sftpClient = new SFTPv3Client(con);   
//   sftpClient.mkdir("newRemoteDir", 6);    //远程新建目录   
//   sftpClient.rmdir("");                   //远程删除目录   
//      
//   sftpClient.createFile("newRemoteFile"); //远程新建文件   
//   sftpClient.openFileRW("remoteFile");    //远程打开文件，可进行读写   
      
//   Session session = con.openSession();   
//   session.execCommand("uname -a && date && uptime && who");   //远程执行命令   
	}
	
	public static void main(String[] args) throws Exception{
		uploadFile("abc.txt", FileKit.readFileBytes("D:\\索引.txt"));
	}
}
