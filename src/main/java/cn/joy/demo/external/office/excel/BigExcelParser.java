package cn.joy.demo.external.office.excel;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.model.SharedStringsTable;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

public class BigExcelParser{
	public static void main1(String[] args) throws Exception{
		System.out.println();
		long end, start = System.currentTimeMillis();
		// Path file = Paths.get("/home/skzrorg/tmp/2007.xlsx");
		Path file = Paths.get("/home/skzrorg/tmp/xlsx/IPTABLE.xlsx");
		SheetDatasHandler handler = UtilPoi.read(file);
		end = System.currentTimeMillis();
		System.out.println("耗时：" + (end - start) / 1000f + "s");
		List<Object[]> sheet = handler.getSheetData(0);
		System.out.println("sheet 大小：" + sheet.size());
		System.out.println("sheet[0]" + sheet.get(0));
		int i = new Random().nextInt(sheet.size());
		System.out.println("sheet[" + i + "]" + sheet.get(i));
		System.out.println("sheet[" + (sheet.size() - 1) + "]" + sheet.get(sheet.size() - 1));
	}

	private static int rowCount;

	public static void main(String[] args) throws Exception{
		System.out.println();
		long end, start = System.currentTimeMillis();
		Path file = Paths.get("/home/skzrorg/tmp/xlsx/IPTABLE.xlsx");
		UtilPoi.read(file, new RowMapper(){
			@Override
			void mapRow(int sheetIndex, int rowIndex, Object[] row){
				rowCount++;
			}
		});
		end = System.currentTimeMillis();
		System.out.println("耗时：" + (end - start) / 1000f + "s");
		System.out.println("sheet 大小：" + rowCount);
	}
}

class UtilPoi{
	public static SheetDatasHandler read(Path file) throws Exception{
		SheetDatasHandler handler = new SheetDatasHandler((int)(Files.size(file) / 50));
		read(file, handler);
		return handler;
	}

	public static void read(Path file, RowMapper mapper) throws Exception{
		final long size = Files.size(file);
		try(InputStream in = new BufferedInputStream(new FileInputStream(file.toFile()),
				size > Integer.MAX_VALUE ? 1024 * 1024 * 10 : (int)size)){
			read(in, mapper);
		}
	}

	public static void read(InputStream in, RowMapper mapper) throws Exception{
		XSSFReader reader = new XSSFReader(OPCPackage.open(in));
		XMLReader parser = XMLReaderFactory.createXMLReader("org.apache.xerces.parsers.SAXParser");

		mapper.setSharedStringsTable(reader.getSharedStringsTable());
		parser.setContentHandler(mapper);

		for(Iterator<InputStream> iter = reader.getSheetsData(); iter.hasNext();){
			try(InputStream sheetIn = iter.next()){
				parser.parse(new InputSource(sheetIn));
			}
		}
	}
}

class SheetDatasHandler extends RowMapper{
	private int bufRowSize, curSheetIndex = -1;
	private List<List<Object[]>> sheetDatas = new ArrayList<>();
	private List<Object[]> sheetData;

	public List<List<Object[]>> getSheetDatas(){
		return sheetDatas;
	}

	public List<Object[]> getSheetData(int sheetIndex){
		return sheetDatas.get(sheetIndex);
	}

	SheetDatasHandler(int bufRowSize){
		this.bufRowSize = bufRowSize;
	}

	@Override
	void mapRow(int sheetIndex, int rowIndex, Object[] row){
		if(curSheetIndex != sheetIndex){
			sheetData = new ArrayList<>(sheetIndex == 0 ? bufRowSize : sheetData.size() / 2);
			sheetDatas.add(sheetData);
			curSheetIndex = sheetIndex;
		}

		sheetData.add(row);
	}
}

abstract class RowMapper extends DefaultHandler{
	private SharedStringsTable sst;
	private Map<Integer, String> strMap;
	private int sheetIndex = -1, rowIndex = -1, colIndex = -1, maxColNum = 26;
	private Object[] row;
	private String cellS;
	private String cellType;
	private boolean valueFlag;
	private StringBuilder value;

	public void setSharedStringsTable(SharedStringsTable sst){
		this.sst = sst;
		strMap = new HashMap<>(sst.getCount());
	}

	private void clearSheet(){
		sst = null;
		strMap = null;
		row = null;
		cellS = null;
		cellType = null;
		value = null;
		rowIndex = 0;
		colIndex = -1;
	}

	private Object convertCellValue(){
		String tmp = value.toString();
		Object result = tmp;

		if("s".equals(cellType)){ // 字符串
			Integer key = Integer.parseInt(tmp);
			result = strMap.get(key);
			if(result == null)
				strMap.put(key, (String)(result = new XSSFRichTextString(sst.getEntryAt(key)).toString()));
		} else if("n".equals(cellType)){
			if("2".equals(cellS)){ // 日期
				result = HSSFDateUtil.getJavaDate(Double.valueOf(tmp));
			}
		}
		return result;
	}

	@Override
	public void startElement(String uri, String localName, String name, Attributes attributes) throws SAXException{
		if("sheetData".equals(name)){
			sheetIndex++;
		} else if("row".equals(name)){
			rowIndex++;
			row = new Object[maxColNum];
		} else if("c".equals(name)){
			cellS = attributes.getValue("s");
			cellType = attributes.getValue("t");
			String r = attributes.getValue("r");
			colIndex = r.codePointAt(0) - 65;
		} else if("v".equals(name)){
			valueFlag = true;
			value = new StringBuilder();
		}
	}

	@Override
	public void endElement(String uri, String localName, String name) throws SAXException{
		if("sheetData".equals(name)){
			clearSheet();
		} else if("row".equals(name)){
			mapRow(sheetIndex, rowIndex, row);
		} else if("v".equals(name)){
			row[colIndex] = convertCellValue();
			valueFlag = false;
		}
	}

	@Override
	public void characters(char[] ch, int start, int length) throws SAXException{
		if(valueFlag)
			value.append(ch, start, length);
	}

	abstract void mapRow(int sheetIndex, int rowIndex, Object[] row);
}