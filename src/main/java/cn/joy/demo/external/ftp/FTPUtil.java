package cn.joy.demo.external.ftp;

import java.io.BufferedInputStream;   
import java.io.BufferedOutputStream;   
import java.io.FileOutputStream;   
import java.io.IOException;   
import java.io.InputStream;   
import java.text.SimpleDateFormat;   
import java.util.ArrayList;   
import java.util.List;   
import java.util.Properties;   
  
import org.apache.commons.net.ftp.FTPClient;   
import org.apache.commons.net.ftp.FTPClientConfig;   
import org.apache.commons.net.ftp.FTPFile;   
import org.apache.commons.net.ftp.FTPReply;   
  
public class FTPUtil {   
    private String username;   
  
    private String password;   
  
    private String ip;   
  
    private int port;   
  
    private Properties property = null;// 配置   
  
    private String configFile;// 配置文件的路径名   
  
    private FTPClient ftpClient = null;   
  
    private String filedir = "";// FTP文件路径   
  
    private final String[] FILE_TYPES = { "文件", "目录", "符号链接", "未知类型" };   
       
    private static SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd hh:mm");   
  
    public FTPUtil(String username, String password, String ip, int port, String filedir) {
		this.username = username;
		this.password = password;
		this.ip = ip;
		this.port = port;
		this.filedir = filedir;
	}

	/**  
     * 设置参数  
     *   
     * @param configFile  
     *            --参数的配置文件  
     */  
    private void setArg(String configFile) {   
        property = new Properties();   
        BufferedInputStream inBuff = null;   
        try {   
            inBuff = new BufferedInputStream(this.getClass().getResourceAsStream(configFile));   
            property.load(inBuff);   
            username = property.getProperty("username");   
            password = property.getProperty("password");   
            ip = property.getProperty("ip");   
            port = Integer.parseInt(property.getProperty("port"));   
            filedir = property.getProperty("filedir");   
        } catch (Exception e) {   
            e.printStackTrace();   
        } finally {   
            try {   
                if (inBuff != null)   
                    inBuff.close();   
            } catch (Exception e) {   
                e.printStackTrace();   
            }   
        }   
    }   
  
    /**  
     * 设置FTP客服端的配置--一般可以不设置  
     *   
     * @return  
     */  
    private FTPClientConfig getFtpConfig() {   
        FTPClientConfig ftpConfig = new FTPClientConfig(   
                FTPClientConfig.SYST_UNIX);   
        //ftpConfig.setServerLanguageCode(FTP.DEFAULT_CONTROL_ENCODING);   
        ftpConfig.setServerLanguageCode("zh");   
        return ftpConfig;   
    }   
  
    /**  
     * 连接到服务器  
     */  
    public void connectServer() {   
        if (ftpClient == null) {   
            int reply;   
            try {   
                //setArg(configFile);   
                ftpClient = new FTPClient();   
                ftpClient.setControlEncoding("GBK");
            	//FTPClientConfig conf = new FTPClientConfig(FTPClientConfig.SYST_NT); 
            	//conf.setServerLanguageCode("zh"); 
            	//ftpClient.configure(conf); 
                ftpClient.configure(getFtpConfig());   
                ftpClient.connect(ip);   
                ftpClient.login(username, password);   
                ftpClient.setDefaultPort(port);   
                System.out.print(ftpClient.getReplyString());   
                reply = ftpClient.getReplyCode();   
  
                if (!FTPReply.isPositiveCompletion(reply)) {   
                    ftpClient.disconnect();   
                    System.err.println("FTP server refused connection.");   
                }   
                ftpClient.enterLocalPassiveMode();
            } catch (Exception e) {   
                System.err.println("登录ftp服务器【" + ip + "】失败");   
                e.printStackTrace();   
            }   
        }   
    }   
  
    /**  
     * 进入到服务器的某个目录下  
     *   
     * @param directory  
     */  
    public void changeWorkingDirectory() {   
        try {   
        	ftpClient.changeWorkingDirectory("/");
        	makeDirs(filedir);  
        } catch (IOException ioe) {   
            ioe.printStackTrace();   
        }   
    } 
    
    public void changeWorkingDirectory(String dir) {   
        try {   
        	ftpClient.changeWorkingDirectory("/");
        	makeDirs(dir);  
        } catch (IOException ioe) {   
            ioe.printStackTrace();   
        }   
    }   
  
    public void makeDirs(String path) {   
    	try {
    		path = path.replaceAll("\\\\", "/").replaceAll("/+", "/");
			String[] dirs = path.split("/");
			for(String dir:dirs){
				ftpClient.makeDirectory(dir);
				ftpClient.changeWorkingDirectory(dir);   
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    /**  
     * 上传文件  
     *   
     * @param inputStream--文件输入流  
     * @param newFileName--新的文件名  
     */  
    public void uploadFile(InputStream inputStream, String newFileName){
    	uploadFile(inputStream, newFileName, 0);
    }
    
    public void uploadFile(InputStream inputStream, String newFileName, int fileType) {   
        //changeWorkingDirectory();// 进入文件夹   
        // 上传文件   
        BufferedInputStream buffIn = null;   
        try {   
            buffIn = new BufferedInputStream(inputStream);
            if(fileType>=0)
            	ftpClient.setFileType(fileType);
            ftpClient.storeFile(newFileName, buffIn);   
            
        } catch (Exception e) {   
            e.printStackTrace();   
        } finally {   
            try {   
                if (buffIn != null)   
                    buffIn.close();   
            } catch (Exception e) {   
                e.printStackTrace();   
            }   
        }   
    }   
  
    /**  
     * 列出服务器上文件和目录  
     *   
     * @param regStr  
     *            --匹配的正则表达式  
     */  
    @SuppressWarnings("unchecked")   
    public List listRemoteFiles(String regStr) {   
        List list = new ArrayList();   
        try {   
            FTPFile[] files = ftpClient.listFiles(regStr);   
            if (files == null || files.length == 0) {   
                System.out.println("There has not any file!");   
                return null;   
            } else {   
                for (FTPFile file : files) {   
                    if (file != null) {   
                    	if(!file.getName().equals(".")&&!file.getName().equals(".."))
                    		list.add(file);
                       /* Map map = new HashMap();   
                        String filename = file.getName();   
                        int filenamelen = filename.length();   
                        if(filenamelen>4){   
                            String filetype = filename.substring(filenamelen-3);   
                            if("txt".equals(filetype)){   
                                String name = file.getName();   
                                name = name.substring(0,name.length()-4);   
                                map.put("filename", name);   
                                map.put("filesize", FileUtils.byteCountToDisplaySize(file.getSize()));   
                                map.put("scsj", dateFormat.format(file.getTimestamp().getTime()));   
                                list.add(map);   
                            }   
                        } */  
                    }   
                }
            }   
        } catch (Exception e) {   
            e.printStackTrace();   
        }   
        return list;   
    }   
    /**  
     * 下载文件  
     * @param remoteFileName --服务器上的文件名  
     * @param localFileName--本地文件名  
     */  
    public void loadFile(String remoteFileName,String localFileName){   
        //下载文件   
        BufferedOutputStream buffOut=null;   
        try{   
            buffOut=new BufferedOutputStream(new FileOutputStream(localFileName));   
            ftpClient.retrieveFile(remoteFileName, buffOut);   
        }catch(Exception e){   
            e.printStackTrace();   
        }finally{   
            try{   
                if(buffOut!=null)   
                    buffOut.close();   
            }catch(Exception e){   
                e.printStackTrace();   
            }   
        }   
    }   
    /**  
     * 删除文件  
     */  
    public void deleteFile(String filename){   
        try{   
            ftpClient.deleteFile(filename);   
        }catch(IOException ioe){   
            ioe.printStackTrace();   
        }   
    }   
    /**  
     * 关闭连接  
     */  
    public void closeConnect(){   
        try{   
            if(ftpClient!=null){   
                ftpClient.logout();   
                ftpClient.disconnect();   
                System.out.println("Ftp have closed");   
            }   
        }catch(Exception e){   
            e.printStackTrace();   
        }   
    }   
    
    /** 
    * 远程文件路径编码(上传到ftp上的文件路径) 
    * 
    * @param remoteFilePath 
    * @return 
    */ 
    //protected String enCodingRemoteFilePath(String remoteFilePath) { 
    //	return StringUtils.gbkToIso8859EnCoding(remoteFilePath); 
    //} 

    
    public String getConfigFile() {   
        return configFile;   
    }   
  
    public void setConfigFile(String configFile) {   
        this.configFile = configFile;   
    }   
  
    public String[] getFILE_TYPES() {   
        return FILE_TYPES;   
    }   
  
    public FTPClient getFtpClient() {   
        return ftpClient;   
    }   
  
    public void setFtpClient(FTPClient ftpClient) {   
        this.ftpClient = ftpClient;   
    }   
  
    public String getIp() {   
        return ip;   
    }   
  
    public void setIp(String ip) {   
        this.ip = ip;   
    }   
  
    public String getPassword() {   
        return password;   
    }   
  
    public void setPassword(String password) {   
        this.password = password;   
    }   
  
    public int getPort() {   
        return port;   
    }   
  
    public void setPort(int port) {   
        this.port = port;   
    }   
  
    public Properties getProperty() {   
        return property;   
    }   
  
    public void setProperty(Properties property) {   
        this.property = property;   
    }   
  
    public String getUsername() {   
        return username;   
    }   
  
    public void setUsername(String username) {   
        this.username = username;   
    }   
  
    public String getFiledir() {   
        return filedir;   
    }   
  
    public void setFiledir(String filedir) {   
        this.filedir = filedir;   
    }   
}  
