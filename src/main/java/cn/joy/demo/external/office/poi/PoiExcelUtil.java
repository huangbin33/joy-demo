package cn.joy.demo.external.office.poi;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFPictureData;

import cn.joy.framework.kits.BeanKit;
import cn.joy.framework.kits.StringKit;

public class PoiExcelUtil {
	private SXSSFWorkbook wb;

	private String defaultSheetName = "First";
	
	private static final int defaultCacheRowCount = 100;
	
	private File xlsFile;
	
	private String[] fieldLabels;
	
	private String[] fieldNames;
	
	private String[] fieldTypes;
	
	private CellStyle titleCs;
	
	private Map<String, Map<String, Object>> sheetInfos;
	
	public PoiExcelUtil(){
		this(defaultCacheRowCount);
	}
	
	public PoiExcelUtil(int CacheRowCount){
		wb = new SXSSFWorkbook(CacheRowCount);
		wb.setCompressTempFiles(true);
		this.sheetInfos = new HashMap();
	}
	
	public void initModel(File xlsFile, String[] fieldLabels, String[] fieldNames, String[] fieldTypes){
		this.xlsFile = xlsFile;
		this.fieldLabels = fieldLabels;
		this.fieldNames = fieldNames;
		this.fieldTypes = fieldTypes;
	}
	
	public void initTitleCellStyle(){
		this.titleCs = wb.createCellStyle();
		this.titleCs.setFillForegroundColor(IndexedColors.LIME.getIndex());
        //cs.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        Font f = wb.createFont();
        f.setBoldweight(Font.BOLDWEIGHT_BOLD);
        f.setFontHeightInPoints((short) 10);
        this.titleCs.setFont(f);
	}

	public void buildExcel(String sheetName, List items, List<Map<String, byte[]>> imgDatas) throws Exception {
		if (this.xlsFile == null)
			return;
		long t1 = System.currentTimeMillis();
		//System.out.println("before export, "+(Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory())+", time="+new Date());
		if (StringKit.isEmpty(sheetName))
			sheetName = defaultSheetName;
		//Sheet ws = wb.createSheet(sheetName);
		Sheet ws = wb.getSheet(sheetName);
		Map<String, Object> sheetInfo = null;
		Drawing patriarch = null;
		if(ws==null){
			ws = wb.createSheet(sheetName);
			sheetInfo = new HashMap();
			// 声明一个画图的顶级管理器
			patriarch = ws.createDrawingPatriarch();
			sheetInfo.put("patriarch", patriarch);
			sheetInfos.put(sheetName, sheetInfo);
			
			// 标题栏
	        Row tilteRow = ws.createRow(0);
	        Cell titleCell = null;
			if (fieldLabels != null) {
				for (int i = 0; i < fieldLabels.length; i++) {
					titleCell = tilteRow.createCell(i);
					titleCell.setCellValue(fieldLabels[i]);
					titleCell.setCellStyle(this.titleCs);
				}
			}
		}else{
			sheetInfo = sheetInfos.get(sheetName);
			patriarch = (Drawing)sheetInfo.get("patriarch");
		}
		// 内容
		int row = ws.getLastRowNum()+1;
		int startRow = row;
		Row contentRow = null;
		Cell contentCell = null;
		if (items != null && fieldNames != null && fieldTypes != null) {
			for (int i = 0; i < items.size(); i++) {
				Object instance = items.get(i);
				Map<String, byte[]> imgData = null;
				if (imgDatas != null && imgDatas.size() > 0)
					imgData = imgDatas.get(i);
				contentRow = ws.createRow(row);
				for (int col = 0; col < fieldNames.length; col++) {
					String filedName = fieldNames[col];
					String filedType = fieldTypes[col];
					if ("91".equals(fieldTypes[col])) {
						if (imgData != null) {
							byte[] imgInfo = imgData.get(filedName);
							if (imgInfo != null) {
								//insertImage(wb, patriarch, (byte[]) imgInfo[0], 0, 4, 1);
								XSSFClientAnchor anchor = new XSSFClientAnchor(0, 0, 1023, 255, (short) col, row, (short) col+1, row+1);
								anchor.setAnchorType(2);
								patriarch.createPicture(anchor, wb.addPicture(imgInfo, HSSFWorkbook.PICTURE_TYPE_PNG));//.resize(1);
							}
						}
					} else {
						String value = BeanKit.getSimpleProperty(instance, filedName);
						//System.out.println(filedName + ": " + value);
						contentCell = contentRow.createCell(col);
						if ("2".equals(fieldTypes[col])||"5".equals(fieldTypes[col])||"11".equals(fieldTypes[col])) {
							contentCell.setCellValue(Double.valueOf(value).doubleValue());
						} else {
							contentCell.setCellValue(value);
						}
					}
				}
				row++;
			}
		}
		long t2 = System.currentTimeMillis();
		System.out.println("创建行"+startRow+"-"+(row-1)+"，耗时"+(t2-t1)+"毫秒！");
		List<XSSFPictureData> allPic = (List<XSSFPictureData>)wb.getAllPictures();
		for(XSSFPictureData p:allPic){
			//((MemoryPackagePart)p.getPackagePart()).clear();
		}
		System.out.println(allPic.size());
		//System.out.println("after export, "+(Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory())+", time="+new Date());
	}
	
	public void write() throws IOException{
		// 输出到文件
		FileOutputStream fout = null;
		try {
			fout = new FileOutputStream(xlsFile);
			wb.write(fout);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(fout!=null)
				fout.close();
			this.wb = null;
			this.fieldLabels = null;
			this.fieldNames = null;
			this.fieldTypes = null;
			this.titleCs = null;
			this.xlsFile = null;
			this.sheetInfos = null;
		}
	}

}