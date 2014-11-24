package cn.joy.demo.external.office.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.eventusermodel.HSSFEventFactory;
import org.apache.poi.hssf.eventusermodel.HSSFRequest;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

public class HSSFParser {
	public static List<Map<String, String>> parseBatchImport(Logger logger, String filepath) {
		List<Map<String, String>> rowList = new ArrayList<Map<String, String>>();
		File file = new File(filepath);
		FileInputStream fis;
		try {
			fis = new FileInputStream(file);
			POIFSFileSystem pfs;
			pfs = new POIFSFileSystem(fis);
			InputStream is;
			is = pfs.createDocumentInputStream("Workbook");

			HSSFRequest request = new HSSFRequest();
			HSSFExcelParseListener hepl = new HSSFExcelParseListener();
			request.addListenerForAllRecords(hepl);

			HSSFEventFactory factory = new HSSFEventFactory();
			factory.processEvents(request, is);

			fis.close();
			is.close();
			rowList.addAll(hepl.getRowList());
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("parse excel by hssf error...", e);
		}
		return rowList;
	}

}
