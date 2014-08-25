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
		if (row == 0) {// ��ͷ
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

	// ��¼��4�ַ��
	private SSTRecord strRec;

	public void processRecord(Record record) {
		switch (record.getSid()) {
		case BOFRecord.sid:
			BOFRecord br = (BOFRecord) record;
			switch (br.getType()) {
			case BOFRecord.TYPE_WORKBOOK: // ˳������µ�Workbook
				log.debug("��workbook");
				break;
			case BOFRecord.TYPE_WORKSHEET:// ˳������µ�Worksheet����ΪEvent
											// API�����Excel�ļ����������ݽṹ����j��4���������һ��Ҫ��¼���ڽ���ڼ���sheet�ˡ�
				log.debug("��worksheet");
				break;
			}
			break;
		case BoundSheetRecord.sid: // ��¼sheet����������е�sheet��˳���ӡ��4������ж��sheet�Ļ�������˳����뵽һ��List��
			BoundSheetRecord bsr = (BoundSheetRecord) record;
			log.debug("sheetName��" + bsr.getSheetname());
			break;
		case SSTRecord.sid: // ��¼�ַ��
			strRec = (SSTRecord) record;
			// log.debug("�ַ��");
			break;
		case RowRecord.sid: // ��ӡ�У�����ô�����
			RowRecord rr = (RowRecord) record;
			// log.debug("�У�"+rr.getRowNumber()+"�� �_ʼ��:"+rr.getFirstCol()+", �K���У�"+rr.getLastCol());
			break;
		case NumberRecord.sid: // �����������͵�cell����Ϊ���ֺ����ڶ���������ʽ����������һ��Ҫ�ж��ǲ������ڸ�ʽ������Ĭ�ϵ�����Ҳ�ᱻ��Ϊ���ڸ�ʽ��������������ֵĻ���һ��Ҫ��ȷָ����ʽ��������������
			NumberRecord nr = (NumberRecord) record;
			if (HSSFDateUtil.isInternalDateFormat(nr.getXFIndex())) {
				String dateStr = sdf.format(HSSFDateUtil.getJavaDate(nr.getValue()));
				parseRecord(nr.getRow(), nr.getColumn(), dateStr);
				// log.debug("���ڣ�"+ dateStr +", ��:"+nr.getRow()+
				// ", �У�"+nr.getColumn()+"����"+nr.getXFIndex()+"��");
			} else {
				parseRecord(nr.getRow(), nr.getColumn(), String.valueOf(nr.getValue()));
				// log.debug("���֣�"+nr.getValue()+", ��:"+nr.getRow()+
				// ", �У�"+nr.getColumn()+"����"+nr.getXFIndex()+"��");
			}
			break;
		case LabelSSTRecord.sid: // �����ַ����ͣ����Ҫȡ�ַ��ֵ�Ļ��������indexȥ�ַ�����ȡ
			LabelSSTRecord lsr = (LabelSSTRecord) record;
			String value = String.valueOf(strRec.getString(lsr.getSSTIndex()));
			parseRecord(lsr.getRow(), lsr.getColumn(), value);
			// log.debug("������:"+value+",���У�"+lsr.getRow()+", �У�"+lsr.getColumn());
			break;
		case BoolErrRecord.sid: // boolean or error
			BoolErrRecord ber = (BoolErrRecord) record;
			if (ber.isBoolean()) {
				String booleanvalue = String.valueOf(ber.getBooleanValue());
				parseRecord(ber.getRow(), ber.getColumn(), booleanvalue);
				// log.debug("Boolean:"+booleanvalue+", �У�"+ber.getRow()+", �У�"+ber.getColumn());
			}
			if (ber.isError()) {
				// log.debug("Error:"+ber.getErrorValue()+", �У�"+ber.getRow()+", �У�"+ber.getColumn());
			}
			break;
		case BlankRecord.sid:
			BlankRecord br1 = (BlankRecord) record;
			// log.debug("�ա����У�"+br1.getRow()+", �У�"+br1.getColumn());
			break;
		case FormulaRecord.sid: // ��ʽ
			FormulaRecord fr = (FormulaRecord) record;
			break;
		}
	}

}
