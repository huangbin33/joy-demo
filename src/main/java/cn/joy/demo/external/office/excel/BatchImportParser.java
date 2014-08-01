package cn.joy.demo.external.office.excel;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

public class BatchImportParser {
	static Logger log = Logger.getLogger(BatchImportParser.class);

	public static List<Map<String, String>> parseBatchImport(Logger logger, String filepath) {
		logger.debug("start to parse excel...");
		List<Map<String, String>> entList = new ArrayList<Map<String, String>>();
		if (filepath == null || filepath.length() == 0) {
			return entList;
		}
		if (filepath.endsWith("xls")) {
			logger.debug("parse xls start...");
			entList.addAll(HSSFParser.parseBatchImport(logger, filepath));
			logger.debug("parse xls end...");
		} else if (filepath.endsWith("xlsx")) {
			logger.debug("parse xlsx start...");
			entList.addAll(new XSSFParser().parseBatchImport(filepath));
			logger.debug("build xlsx end...");
		} else {
			logger.warn("it is not a excel...");
		}
		logger.debug("excel row size = " + entList.size());
		return entList;
	}

	public static void main(String[] args) {
		MemoryMXBean mem = ManagementFactory.getMemoryMXBean();
		MemoryUsage heap = mem.getHeapMemoryUsage();
		String commit = (int) (heap.getCommitted() / (1024 * 1024)) + "MB";
		String init = (int) (heap.getInit() / (1024 * 1024)) + "MB";
		String max = (int) (heap.getMax() / (1024 * 1024)) + "MB";
		String used = (int) (heap.getUsed() / (1024 * 1024)) + "MB";
		System.out.println("Heap committed=" + commit + " init=" + init + " max=" + max + " used=" + used);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");
		System.out.println("---START---" + sdf.format(new Date()));
		// List<Map<String, String>> rowList = parseBatchImport( log,
		// "E:/temp/������.xls");
		List<Map<String, String>> rowList = BatchImportParser.parseBatchImport(log, "D:/ffffffff.xlsx");
		// List<Map<String, String>> rowList = parseBatchImport( log,
		// "E:/temp/������Ʊ.xlsx");
		log.debug(" row size = " + rowList.size());
		System.out.println("---END1---" + sdf.format(new Date()));
		for (Map<String, String> rowMap : rowList) {
			for (String key : rowMap.keySet()) {
				log.debug(key + " = " + rowMap.get(key));
			}
		}
		System.out.println("---END2---" + sdf.format(new Date()));
	}

}
