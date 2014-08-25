package cn.joy.demo.external.office.excel;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.validator.GenericValidator;
import org.apache.log4j.Logger;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.model.SharedStringsTable;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

public class XSSFParser {
	private static Logger log = Logger.getLogger(XSSFParser.class);

	private List<Map<String, String>> rowList = new ArrayList<Map<String, String>>();

	public List<Map<String, String>> parseBatchImport(String filepath) {
		log.debug("parse excel by xssf start");
		try {
			processAllSheets(filepath);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("parse xlsx error...", e);
		}
		log.debug("row list = " + rowList.size());
		return rowList;
	}

	public void processOneSheet(String filename) throws Exception {
		OPCPackage pkg = OPCPackage.open(filename);
		XSSFReader r = new XSSFReader(pkg);
		SharedStringsTable sst = r.getSharedStringsTable();

		XMLReader parser = fetchSheetParser(sst);

		// InputStream sheet2 = r.getSheet("rId2");
		InputStream sheet2 = r.getSheet("rId1");
		InputSource sheetSource = new InputSource(sheet2);
		parser.parse(sheetSource);
		sheet2.close();
	}

	public void processAllSheets(String filename) throws Exception {
		OPCPackage pkg = OPCPackage.open(filename);
		XSSFReader r = new XSSFReader(pkg);
		SharedStringsTable sst = r.getSharedStringsTable();
		XMLReader parser = fetchSheetParser(sst);
		Iterator<InputStream> sheets = r.getSheetsData();
		while (sheets.hasNext()) {
			log.debug("Processing new sheet:\n");
			InputStream sheet = sheets.next();
			InputSource sheetSource = new InputSource(sheet);
			parser.parse(sheetSource);
			sheet.close();
			SheetHandler shandler = (SheetHandler) parser.getContentHandler();
			log.debug(" sheet row list = " + shandler.getSheetRowList().size());
			rowList.addAll(shandler.getSheetRowList());
			shandler.getSheetRowList().clear();
			log.debug("proess sheet end---");
		}
	}

	public XMLReader fetchSheetParser(SharedStringsTable sst) throws SAXException {
		XMLReader parser = XMLReaderFactory.createXMLReader("org.apache.xerces.parsers.SAXParser");
		ContentHandler handler = new SheetHandler(sst);
		parser.setContentHandler(handler);
		return parser;
	}

	/**
	 * See org.xml.sax.helpers.DefaultHandler javadocs
	 */
	private static class SheetHandler extends DefaultHandler {
		private SharedStringsTable sst;
		private String lastContents;
		private boolean nextIsString;
		private boolean isContinue = false;
		private boolean isTitle = false;
		private String titleColumn = "";
		private Integer rowSize = 1;
		private List<Map<String, String>> sheetRowList = new ArrayList<Map<String, String>>();
		private Map<String, String> titleMap = new HashMap<String, String>();
		private Map<String, String> rowMap = new HashMap<String, String>();

		private SheetHandler(SharedStringsTable sst) {
			this.sst = sst;
		}

		public List<Map<String, String>> getSheetRowList() {
			return sheetRowList;
		}

		public void startElement(String uri, String localName, String name, Attributes attributes) throws SAXException {
			// c => cell
			/*
			 * for( int i = 0; i < attributes.getLength(); i++ ){ log.debug(
			 * attributes.getLocalName(i) + "=" + attributes.getValue(i) ); }
			 */
			if (name.equals("c")) {
				isContinue = false;
				isTitle = false;
				String index = attributes.getValue("r");
				String col = index.replaceAll("[0-9]", "");
				String rownum = index.replaceAll("[A-Z]", "");
				if (index != null) {
					// if ( index.length() == 2 && index.endsWith("1") ){
					if (rownum.equals("1")) {
						isTitle = true;
						titleColumn = col;
					} else if (titleMap.containsKey(col)) {
						isContinue = true;
						titleColumn = col;
						// String rownum = index.substring(1);
						if (GenericValidator.isInt(rownum)) {
							Integer row = Integer.parseInt(rownum);
							if (rowSize < row) {
								log.debug("rowSize = " + rowSize + ", row = " + row);
								rowSize = row;
								rowMap = new HashMap<String, String>();
								sheetRowList.add(rowMap);
							}
						}
					}
				}
				// log.debug( index + " - isTitle = " + isTitle +
				// " - isContinue = " + isContinue + " - titleColumn = " +
				// titleColumn);
				String cellType = attributes.getValue("t");
				if (cellType != null && cellType.equals("s")) {
					nextIsString = true;
				} else {
					nextIsString = false;
				}
			}
			lastContents = "";
		}

		public void endElement(String uri, String localName, String name) throws SAXException {

			if (nextIsString) {
				if (GenericValidator.isInt(lastContents)) {
					int idx = Integer.parseInt(lastContents);
					lastContents = new XSSFRichTextString(sst.getEntryAt(idx)).toString();
				}
			}
			if (name.equals("v") && lastContents != null) {
				// log.debug("isTitle = " + isTitle + ", isContinue = " +
				// isContinue );
				if (isTitle) {
					log.debug("title = " + lastContents + ", titleColumn=" + titleColumn);
					titleMap.put(titleColumn, lastContents);
				} else if (isContinue) {
					rowMap.put(titleMap.get(titleColumn), lastContents);
					log.debug(titleMap.get(titleColumn) + "=" + lastContents);
				}
				// log.debug(lastContents);
			}
		}

		public void characters(char[] ch, int start, int length) throws SAXException {
			lastContents += new String(ch, start, length);
		}
	}

}
