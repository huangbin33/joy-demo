package cn.joy.demo.external.office.excel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.eventusermodel.HSSFListener;
import org.apache.poi.hssf.record.BOFRecord;
import org.apache.poi.hssf.record.BlankRecord;
import org.apache.poi.hssf.record.BoolErrRecord;
import org.apache.poi.hssf.record.BoundSheetRecord;
import org.apache.poi.hssf.record.FormulaRecord;
import org.apache.poi.hssf.record.LabelSSTRecord;
import org.apache.poi.hssf.record.NumberRecord;
import org.apache.poi.hssf.record.Record;
import org.apache.poi.hssf.record.RowRecord;
import org.apache.poi.hssf.record.SSTRecord;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;

public class HSSFExcelParseListener implements HSSFListener {
	private Logger log = Logger.getLogger(HSSFExcelParseListener.class);

	private List<Map<String, String>> rowList = new ArrayList<Map<String, String>>();

	private Map<String, String> rowMap = new HashMap<String, String>();

	private Integer rowSize = 0;

	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	private Map<Short, String> titleMap = new HashMap<Short, String>();

	public List<Map<String, String>> getRowList() {
		return rowList;
	}

	private void parseRecord(int row, short column, String value) {
		if (value == null || value.trim().length() == 0) {
			return;
		}
		value = value.trim();
		if (row == 0) {
			log.debug("title = " + value);
			titleMap.put(column, value);
		} else if (titleMap.containsKey(column)) {
			// log.debug("row column = " + column );
			if (rowSize < row) {
				log.debug("row size column = " + column + ", rowSize =" + rowSize);
				rowSize = row;
				rowMap = new HashMap<String, String>();
				rowList.add(rowMap);
			}
			System.out.println("@@" + value);
			rowMap.put(titleMap.get(column), value);
		}
	}

	private SSTRecord strRec;

	public void processRecord(Record record) {
		switch (record.getSid()) {
		case BOFRecord.sid:
			BOFRecord br = (BOFRecord) record;
			switch (br.getType()) {
			case BOFRecord.TYPE_WORKBOOK: //
				log.debug("workbook");
				break;
			case BOFRecord.TYPE_WORKSHEET://
				log.debug("worksheet");
				break;
			}
			break;
		case BoundSheetRecord.sid: 
			BoundSheetRecord bsr = (BoundSheetRecord) record;
			log.debug("sheetName" + bsr.getSheetname());
			break;
		case SSTRecord.sid: 
			strRec = (SSTRecord) record;
			break;
		case RowRecord.sid: 
			RowRecord rr = (RowRecord) record;
			break;
		case NumberRecord.sid: 
			NumberRecord nr = (NumberRecord) record;
			if (HSSFDateUtil.isInternalDateFormat(nr.getXFIndex())) {
				String dateStr = sdf.format(HSSFDateUtil.getJavaDate(nr.getValue()));
				parseRecord(nr.getRow(), nr.getColumn(), dateStr);
			} else {
				parseRecord(nr.getRow(), nr.getColumn(), String.valueOf(nr.getValue()));
			}
			break;
		case LabelSSTRecord.sid: 
			LabelSSTRecord lsr = (LabelSSTRecord) record;
			String value = String.valueOf(strRec.getString(lsr.getSSTIndex()));
			parseRecord(lsr.getRow(), lsr.getColumn(), value);
			break;
		case BoolErrRecord.sid: // boolean or error
			BoolErrRecord ber = (BoolErrRecord) record;
			if (ber.isBoolean()) {
				String booleanvalue = String.valueOf(ber.getBooleanValue());
				parseRecord(ber.getRow(), ber.getColumn(), booleanvalue);
			}
			if (ber.isError()) {
			}
			break;
		case BlankRecord.sid:
			BlankRecord br1 = (BlankRecord) record;
			break;
		case FormulaRecord.sid: 
			FormulaRecord fr = (FormulaRecord) record;
			break;
		}
	}

}
