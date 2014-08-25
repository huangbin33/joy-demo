package cn.joy.demo.external.office.jxl;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import jxl.Workbook;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableImage;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import cn.joy.framework.kits.BeanKit;
import cn.joy.framework.kits.StringKit;

public class JxlExcelUtil {

	private WritableWorkbook wb;

	private String defaultSheetName = "First";
	
	private File xlsFile;
	
	private String[] fieldLabels;
	
	private String[] fieldNames;
	
	private String[] fieldTypes;
	
	private WritableCellFormat titleCs;
	
	public JxlExcelUtil(File xlsFile){
		try {
			if(xlsFile!=null){
				this.xlsFile = xlsFile;
				wb = Workbook.createWorkbook(xlsFile);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void initModel(String[] fieldLabels, String[] fieldNames, String[] fieldTypes){
		this.fieldLabels = fieldLabels;
		this.fieldNames = fieldNames;
		this.fieldTypes = fieldTypes;
	}
	
	public void initTitleCellStyle(){
		WritableFont xlsFont = new WritableFont(WritableFont.COURIER, 10, WritableFont.NO_BOLD);
		this.titleCs = new WritableCellFormat(xlsFont);
	}

	public void buildExcel(String sheetName, List items, List<Map<String, byte[]>> imgDatas) throws Exception {
		if (this.wb == null)
			return;
		//System.out.println("before export, "+(Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory()));
		if (StringKit.isEmpty(sheetName))
			sheetName = defaultSheetName;
		WritableSheet ws = wb.createSheet(sheetName, wb.getSheets().length);

		// 标题栏
		if (fieldLabels != null) {
			for (int i = 0; i < fieldLabels.length; i++) {
				Label label = new Label(i, 0, fieldLabels[i], this.titleCs);
				ws.addCell(label);
			}
		}
		// 内容
		int row = 1;
		if (items != null && fieldNames != null && fieldTypes != null) {
			for (int i = 0; i < items.size(); i++) {
				Object instance = items.get(i);
				Map<String, byte[]> imgData = null;
				if (imgDatas != null && imgDatas.size() > 0)
					imgData = imgDatas.get(i);
				for (int col = 0; col < fieldNames.length; col++) {
					String filedName = fieldNames[col];
					String filedType = fieldTypes[col];
					if ("91".equals(fieldTypes[col])) {
						if (imgData != null) {
							byte[] imgInfo = imgData.get(filedName);
							if (imgInfo != null) {
								WritableImage image = new WritableImage(col, row, 1, 1, imgInfo);
								ws.addImage(image);
							}
						}
					} else {
						String value = BeanKit.getSimpleProperty(instance, filedName);
						//System.out.println(filedName + ": " + value);
						if ("2".equals(fieldTypes[col])||"5".equals(fieldTypes[col])||"11".equals(fieldTypes[col])) {
							//NumberFormat fivedps = new NumberFormat("#.##");
							//WritableCellFormat fivedpsFormat = new WritableCellFormat(fivedps);
							//jxl.write.Number doubleNum = new jxl.write.Number(col, row, Double.valueOf(value).doubleValue(), getCellStyle(fivedpsFormat, 1));
							jxl.write.Number doubleNum = new jxl.write.Number(col, row, Double.valueOf(value).doubleValue());
							ws.addCell(doubleNum);
						} else {
							Label label = new Label(col, row, value);
							ws.addCell(label);
						}
					}
				}
				row++;
			}
		}
		//System.out.println("after export, "+(Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory()));
	}
	
	public void write(){
		// 输出到文件
		try {
			if (this.wb != null){
				wb.write();
				wb.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.wb = null;
			this.fieldLabels = null;
			this.fieldNames = null;
			this.fieldTypes = null;
			this.titleCs = null;
			this.xlsFile = null;
		}
	}

	public WritableCellFormat getCellStyle(WritableCellFormat wcf, int flag) throws WriteException {
		//wcf.setBorder(Border.ALL, BorderLineStyle.THIN);
		// 设置单元格内容对齐方式
		if (flag == 0) {
			wcf.setAlignment(jxl.format.Alignment.LEFT);
		} else if (flag == 1) {
			wcf.setAlignment(jxl.format.Alignment.RIGHT);
		} else {
			wcf.setAlignment(jxl.format.Alignment.CENTRE);
		}
		// 设置单元格内容为垂直居中
		wcf.setVerticalAlignment(VerticalAlignment.CENTRE);
		return wcf;
	}

}