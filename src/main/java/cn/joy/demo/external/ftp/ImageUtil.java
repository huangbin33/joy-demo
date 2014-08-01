package cn.joy.demo.external.ftp;

import java.io.*; 
import javax.imageio.*; 
import java.util.*; 
import javax.imageio.stream.*; 
public class ImageUtil{ 
  public static void main(String[] args) { 
        File f = new File("D:/新建 文本文档.txt"); 
        if (f.exists()) { 
            System.out.println(getFormatInFile(f)); 
        } 
    } 
    // Returns the format of the image in the file 'f'. 
    // Returns null if the format is not known. 
    public static String getFormatInFile(File f) { 
        return getFormatName(f); 
    } 
    
    // Returns the format name of the image in the object 'o'. 
    // Returns null if the format is not known. 
    private static String getFormatName(Object o) { 
        try { 
            // Create an image input stream on the image 
            ImageInputStream iis = ImageIO.createImageInputStream(o); 
    
            // Find all image readers that recognize the image format 
            Iterator iter = ImageIO.getImageReaders(iis); 
            if (!iter.hasNext()) { 
                // No readers found 
                return null; 
            } 
    
            // Use the first reader 
            ImageReader reader = (ImageReader)iter.next(); 
    
            // Close stream 
            iis.close(); 
    
            // Return the format name 
            return reader.getFormatName(); 
        } catch (IOException e) { 
            // 
        } 
        
        // The image could not be read 
        return null; 
    } 
} 
