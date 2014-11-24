package cn.joy.demo.external.office.excel;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import cn.joy.framework.kits.StringKit;

public class CompanyExcelParser{
	
	private static int rowCount = -1;
	private static int extraRowCount = 0;
	private static int rc = 0;
	private static int fileNum = 1;
	private static FileOutputStream fos;
	private static FileOutputStream fosExtra;

	public static void main(String[] args) throws Exception{
		System.out.println();
		long end, start = System.currentTimeMillis();
		Path file = Paths.get("D:/jiangsu20140107.xlsx");
		
		fosExtra = new FileOutputStream("D:/company_sql_extra.sql");
		UtilPoi.read(file, new RowMapper(){
			@Override
			void mapRow(int sheetIndex, int rowIndex, Object[] row){
				rowCount++;
				if(rowCount==0)
					return;
				
				rc++;
				try{
					if(fos==null){
						fos = new FileOutputStream("D:/company_sql_"+fileNum+".sql");
					}
					
					fos.write(getInsertSql(row, rowCount).getBytes());
					if(extraRowCount<20000 && random.nextInt(4)==0){
						extraRowCount++;
						fosExtra.write(getInsertSql(row, extraRowCount).getBytes());
					}
					
					if(rc==20000){
						rc = 0;
						fileNum++;
						try{
							fos.flush();
							fos.close();
						} catch(IOException e){
							e.printStackTrace();
						}
						fos = new FileOutputStream("D:/company_sql_"+fileNum+".sql");
					}
				} catch(IOException e){
					throw new RuntimeException(e);
				}
					
			}
		});
		
		try{
			fosExtra.flush();
			fosExtra.close();
		} catch(IOException e){
			e.printStackTrace();
		}
		
		end = System.currentTimeMillis();
		System.out.println("耗时：" + (end - start) / 1000f + "s");
		System.out.println("sheet 大小：" + rowCount);
	}
	
	private static String getIndustry(String companyName){
		if(companyName.indexOf("IT")!=-1
				|| companyName.indexOf("通信")!=-1
				|| companyName.indexOf("通讯")!=-1
				|| companyName.indexOf("电子")!=-1
				|| companyName.indexOf("信息")!=-1
				|| companyName.indexOf("互联网")!=-1
				|| companyName.indexOf("软件")!=-1
				|| companyName.indexOf("数据")!=-1
				|| companyName.indexOf("半导体")!=-1
				|| companyName.indexOf("电路")!=-1
				|| companyName.indexOf("硬件")!=-1
				|| companyName.indexOf("网络")!=-1
				|| companyName.indexOf("电信")!=-1
				|| companyName.indexOf("科技")!=-1)
			return "IT/通信/电子/互联网";
		else if(companyName.indexOf("金融")!=-1
				|| companyName.indexOf("银行")!=-1
				|| companyName.indexOf("贷款")!=-1
				|| companyName.indexOf("理财")!=-1
				|| companyName.indexOf("基金")!=-1
				|| companyName.indexOf("保险")!=-1
				|| companyName.indexOf("投资")!=-1)
			return "金融业";
		else if(companyName.indexOf("房地产")!=-1
				|| companyName.indexOf("建造")!=-1
				|| companyName.indexOf("建材")!=-1
				|| companyName.indexOf("家居")!=-1
				|| companyName.indexOf("室内")!=-1
				|| companyName.indexOf("装饰")!=-1
				|| companyName.indexOf("装潢")!=-1
				|| companyName.indexOf("物业")!=-1
				|| companyName.indexOf("建筑")!=-1)
			return "房地产/建筑业";
		else if(companyName.indexOf("咨询")!=-1
				|| companyName.indexOf("广告")!=-1
				|| companyName.indexOf("会展")!=-1
				|| companyName.indexOf("公关")!=-1
				|| companyName.indexOf("中介")!=-1
				|| companyName.indexOf("检验")!=-1
				|| companyName.indexOf("检测")!=-1
				|| companyName.indexOf("认证")!=-1
				|| companyName.indexOf("家政")!=-1
				|| companyName.indexOf("租赁")!=-1
				|| companyName.indexOf("摄影")!=-1
				|| companyName.indexOf("修理")!=-1
				|| companyName.indexOf("维修")!=-1
				|| companyName.indexOf("审计")!=-1)
			return "商业服务";
		else if(companyName.indexOf("造纸")!=-1
				|| companyName.indexOf("纸制")!=-1
				|| companyName.indexOf("印刷")!=-1)
			return "造纸及纸制品/印刷业";
		else if(companyName.indexOf("批发")!=-1
				|| companyName.indexOf("零售")!=-1
				|| companyName.indexOf("食品")!=-1
				|| companyName.indexOf("糕点")!=-1
				|| companyName.indexOf("饮料")!=-1
				|| companyName.indexOf("服装")!=-1
				|| companyName.indexOf("进出口")!=-1
				|| companyName.indexOf("用品")!=-1
				|| companyName.indexOf("乐器")!=-1
				|| companyName.indexOf("眼镜")!=-1
				|| companyName.indexOf("材料")!=-1
				|| companyName.indexOf("贸易")!=-1)
			return "贸易/批发/零售业";
		else if(companyName.indexOf("礼品")!=-1
				|| companyName.indexOf("玩具")!=-1
				|| companyName.indexOf("工艺")!=-1
				|| companyName.indexOf("美术")!=-1)
			return "文教体育/工艺美术";
		else if(companyName.indexOf("加工")!=-1
				|| companyName.indexOf("制造")!=-1
				|| companyName.indexOf("仪表")!=-1
				|| companyName.indexOf("仪器")!=-1
				|| companyName.indexOf("原料")!=-1
				|| companyName.indexOf("自动化")!=-1
				|| companyName.indexOf("环保")!=-1
				|| companyName.indexOf("航空")!=-1
				|| companyName.indexOf("航天")!=-1
				|| companyName.indexOf("机械")!=-1
				|| companyName.indexOf("控制")!=-1
				|| companyName.indexOf("模具")!=-1
				|| companyName.indexOf("机床")!=-1
				|| companyName.indexOf("泡沫")!=-1
				|| companyName.indexOf("五金")!=-1
				|| companyName.indexOf("涂料")!=-1
				|| companyName.indexOf("电控")!=-1
				|| companyName.indexOf("线缆")!=-1
				|| companyName.indexOf("机器")!=-1
				|| companyName.indexOf("机电")!=-1
				|| companyName.indexOf("设备")!=-1)
			return "加工制造/仪表设备";
		else if(companyName.indexOf("交通")!=-1
				|| companyName.indexOf("运输")!=-1
				|| companyName.indexOf("仓储")!=-1
				|| companyName.indexOf("物流")!=-1
				|| companyName.indexOf("快递")!=-1
				|| companyName.indexOf("搬家")!=-1
				|| companyName.indexOf("搬场")!=-1)
			return "交通运输仓储";
		else if(companyName.indexOf("制药")!=-1
				|| companyName.indexOf("医疗")!=-1
				|| companyName.indexOf("生物")!=-1
				|| companyName.indexOf("卫生")!=-1
				|| companyName.indexOf("保健")!=-1
				|| companyName.indexOf("医药")!=-1
				|| companyName.indexOf("护理")!=-1
				|| companyName.indexOf("美容")!=-1)
			return "制药医疗/生物/卫生保健";
		else if(companyName.indexOf("酒店")!=-1
				|| companyName.indexOf("餐饮")!=-1
				|| companyName.indexOf("旅游")!=-1
				|| companyName.indexOf("度假")!=-1)
			return "酒店/餐饮/旅游";
		else if(companyName.indexOf("文化")!=-1
				|| companyName.indexOf("体育")!=-1
				|| companyName.indexOf("娱乐")!=-1
				|| companyName.indexOf("媒体")!=-1
				|| companyName.indexOf("出版")!=-1
				|| companyName.indexOf("影视")!=-1
				|| companyName.indexOf("休闲")!=-1)
			return "文化/体育/娱乐业";
		else if(companyName.indexOf("石油")!=-1
				|| companyName.indexOf("石化")!=-1
				|| companyName.indexOf("化工")!=-1
				|| companyName.indexOf("橡胶")!=-1
				|| companyName.indexOf("水泥")!=-1
				|| companyName.indexOf("能源")!=-1
				|| companyName.indexOf("矿产")!=-1
				|| companyName.indexOf("冶炼")!=-1
				|| companyName.indexOf("电气")!=-1
				|| companyName.indexOf("电力")!=-1
				|| companyName.indexOf("水利")!=-1
				|| companyName.indexOf("采掘")!=-1
				|| companyName.indexOf("地质")!=-1)
			return "能源/电气/采掘/地质/石油";
		else if(companyName.indexOf("学术")!=-1
				|| companyName.indexOf("科研")!=-1
				|| companyName.indexOf("政府")!=-1
				|| companyName.indexOf("公共")!=-1
				|| companyName.indexOf("公益")!=-1
				|| companyName.indexOf("非盈利")!=-1
				|| companyName.indexOf("慈善")!=-1)
			return "政府/非盈利机构";
		else if(companyName.indexOf("农")!=-1
				|| companyName.indexOf("牧")!=-1
				|| companyName.indexOf("渔")!=-1
				|| companyName.indexOf("养殖")!=-1)
			return "农林牧渔/其他";
		else if(companyName.indexOf("教育")!=-1
				|| companyName.indexOf("培训")!=-1
				|| companyName.indexOf("院校")!=-1)
			return "教育";
		
		return "其他";
	}
	
	private static String getInsertSql(Object[] row, int rowCount){
		String companyName = StringKit.getString(row[0], "UNKNOWN");
		String name = StringKit.getString(row[1], "UNKNOWN");
		String mobile = StringKit.getString(row[3]);
		String phone = StringKit.getString(row[2], mobile);
		String address = StringKit.getString(row[4]);
		String email = StringKit.getString(row[5]);
		
		StringBuilder sb = new StringBuilder();
		if((rowCount-1)%25==0)
			sb.append("insert into company_temp(chuangjiansj,telno,nicheng,companyname,adress,xingming,loginname,"
					+ "email,qyescode,simple_code,mainindustry,invite_permission,enable_status,usertype,accounttype,ustatus) values\n");
		String qyescode = getQyescode();
		sb.append(" ('"+getRandomTime()+"','"+phone+"','"+name+"','"+companyName+"','"+address+"','"+name+"','"+getLoginId()+"','"
					+email+"','"+qyescode+"','"+qyescode+"','"+getIndustry(companyName)+"','y','1','1','1','1')");
		if((rowCount)%25==0)
			sb.append(";\n");
		else
			sb.append(",\n");
		return sb.toString();
	}
	
	private static String getQyescode(){
		long no = (long)(Math.random()*Math.pow(10, 9));
		return "9"+String.format("%09d", no);
	}
	
	private static String getLoginId(){
		long no1 = (long)(Math.random()*1000);
		long no2 = (long)(Math.random()*1000);
		return "ucoi"+String.format("%03d", no1)+"qc"+String.format("%03d", no2);
	}
	
	private static Integer[] years = {114, 113, 114};
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static Random random = new Random();
	private static String getRandomTime(){
		int y = years[random.nextInt(3)];
		int m = 0;
		if(y==114)
			m = random.nextInt(10);
		else
			m = random.nextInt(12);
		int d = random.nextInt(28)+1;
		int h = random.nextInt(15)+8;
		int mi = random.nextInt(60)+1;
		int s = random.nextInt(60)+1;
		
		Date date = new Date(y,m,d,h,mi,s);
		return sdf.format(date);
	}
}

