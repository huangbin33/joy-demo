package cn.joy.demo.external.office.excel;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import jxl.Workbook;
import jxl.write.WritableImage;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class ExcelExportJxl {

	public static void main(String[] args) throws Exception {
		OutputStream os = null;  
        try {  
        	File xlsFile = new File("D:\\testJxl11.xls");  
        	xlsFile.delete();
            String imgPath = "D:\\icon.gif";  
            os = new FileOutputStream(xlsFile);  
            WritableWorkbook wwb = Workbook.createWorkbook(os);  
            WritableSheet ws = wwb.createSheet("write img", 0);  
            
            File imgFile = new File(imgPath);  
  
            // WritableImage(col, row, width, height, imgFile);  
            Object[] imgInfo = getImageData(imgFile);
			if (imgInfo != null){
	            WritableImage image = new WritableImage(2, 1, 1, 1, (byte[])imgInfo[0]);  
	            ws.addImage(image);  
			}
            wwb.write();  
            wwb.close();  
  
        } catch (Exception e) {  
            System.out.println(e);  
        } finally {  
            if (null != os) {  
                os.close();  
            }  
        }  
	}

	private static Object[] getImageData(File imgFile) {
		try {
			ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();
			ImageInputStream iis = ImageIO.createImageInputStream(imgFile);
			Iterator<ImageReader> iter = ImageIO.getImageReaders(iis);
			if (!iter.hasNext())
				return null;
			ImageReader reader = iter.next();
			String imgType = reader.getFormatName();
			System.out.println("image type=" + imgType);
			reader.setInput(iis);
			BufferedImage bufferImg = reader.read(0);
			ImageIO.write(bufferImg, "png", byteArrayOut);
			iis.close();
			return new Object[] { byteArrayOut.toByteArray(), imgType };
		} catch (Exception exe) {
			exe.printStackTrace();
			return null;
		}
	}	
}