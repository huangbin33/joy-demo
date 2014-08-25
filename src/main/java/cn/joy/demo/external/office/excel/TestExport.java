package cn.joy.demo.external.office.excel;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import cn.joy.demo.external.office.jxl.JxlExcelUtil;
import cn.joy.demo.external.office.poi.PoiExcelUtil;

public class TestExport {
	public static boolean testImg = true;

	public static void main(String[] args) {
		try {
			long t1 = System.currentTimeMillis();
			testPoiExport();
			long t2 = System.currentTimeMillis();
			System.out.println("poi导出成功，耗时"+(t2-t1)+"毫秒！");
			//testJxlExport();
			long t3 = System.currentTimeMillis();
			System.out.println("jxl导出成功，耗时"+(t3-t2)+"毫秒！");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}
	}
	
	public static void testPoiExport() throws Exception{
		File xlsFile = new File("D:\\testPoi.xlsx");
		xlsFile.delete();
		
		String[] fieldLabels = new String[] { "名称", "描述", "价格", "图片", "产地" };
		String[] fieldNames = new String[] { "mingcheng", "miaoshu", "price", "pic1", "chandi" };
		String[] fieldTypes = new String[] { "1", "1", "11", "91", "1" };

		List<Atzproduct> items = initListDatas();
		List<Map<String, byte[]>> imgDatas = initImgDatas(items);
		
		PoiExcelUtil util = new PoiExcelUtil(50);
		util.initModel(xlsFile, fieldLabels, fieldNames, fieldTypes);
		util.initTitleCellStyle();
		util.buildExcel("", items, imgDatas);
		
		for(int i=0;i<1;i++){
			items = initListDatas();
			imgDatas = initImgDatas(items);
			util.buildExcel("", items, imgDatas);
			for(Map<String, byte[]> m:imgDatas){
				m.clear();
			}
			imgDatas.clear();
			//System.out.println("before system.gc() in form:" + Runtime.getRuntime().freeMemory());
			//System.gc();
			//System.out.println("after system.gc() in form:" + Runtime.getRuntime().freeMemory());
		}
		
		util.write();
		//PoiExcelUtil.exportExcel(xlsFile, sheetName, fieldLabels, fieldNames, fieldTypes, items, imgDatas);
	}
	
	public static void testJxlExport() throws Exception{
		File xlsFile = new File("D:\\testJxl.xls");
		xlsFile.delete();

		String[] fieldLabels = new String[] { "名称", "描述", "价格", "图片", "产地" };
		String[] fieldNames = new String[] { "mingcheng", "miaoshu", "price", "pic", "chandi" };
		String[] fieldTypes = new String[] { "1", "1", "11", "91", "1" };

		List<Atzproduct> items = initListDatas();
		List<Map<String, byte[]>> imgDatas = initImgDatas(items);
		
		JxlExcelUtil util = new JxlExcelUtil(xlsFile);
		util.initModel(fieldLabels, fieldNames, fieldTypes);
		util.initTitleCellStyle();
		util.buildExcel("", items, imgDatas);
		System.gc();
		items = initListDatas();
		imgDatas = initImgDatas(items);
		util.buildExcel("Second", items, imgDatas);
		System.gc();
		items = initListDatas();
		imgDatas = initImgDatas(items);
		util.buildExcel("Third", items, imgDatas);
		
		util.write();
	}
	
	private static List<Atzproduct> initListDatas(){
		List<Atzproduct> items = new ArrayList();
		Atzproduct item = null;
		for(int i=0;i<2500;i++){
			item = new Atzproduct();
			item.setMingcheng("台式机");
			item.setMiaoshu("台式机，是一种独立相分离的计算机，完完全全跟其它部件无联系，相对于笔记本和上网本体积较大，主机、显示器等设备一般都是相对独立的，一般需要放置在电脑桌或者专门的工作台上。因此命名为台式机。");
			item.setPrice(8888.8);
			if(testImg)
				item.setPic("D:\\taishiji.jpg");
			item.setChandi("上海");
			items.add(item);

			item = new Atzproduct();
			item.setMingcheng("笔记本");
			item.setMiaoshu("笔记本有两种含义，第一种是指用来记录文字的纸制本子，第二种是指笔记本电脑。而笔记本电脑又被称为“便携式电脑”，其最大的特点就是机身小巧，相比PC携带方便。虽然笔记本的机身十分轻便，但完全不用怀疑其应用性，在日常操作和基本商务、娱乐操作中，笔记本电脑完全可以胜任。目前，在全球市场上有多种品牌，排名前列的有联想、华硕、戴尔（DELL）、ThinkPad、惠普（HP）、苹果（Apple）、宏基（Acer）、索尼、东芝、三星等。");
			item.setPrice(9888d);
			if(testImg)
				item.setPic("D:\\bijiben.png");
			item.setChandi("北京");
			items.add(item);

			item = new Atzproduct();
			item.setMingcheng("一体机");
			item.setMiaoshu("一体机，有影印一体机和电脑一体机。影印一体机简单而言就是集传真、打印与复印等功能为一体的机器。电脑一体机即一体台式机，一体台式机这一概念最先由联想集团提出，是指将传统分体台式机的主机集成到显示器中，从而形成一体台式机。");
			item.setPrice(3234.8);
			if(testImg)
				item.setPic("D:\\yitiji.png");
			item.setChandi("杭州");
			items.add(item);

			item = new Atzproduct();
			item.setMingcheng("服务器");
			item.setMiaoshu("服务器指一个管理资源并为用户提供服务的计算机软件，通常分为文件服务器、数据库服务器和应用程序服务器。运行以上软件的计算机或计算机系统也被称为服务器。相对于普通PC来说，服务器在稳定性、安全性、性能等方面都要求更高，因此CPU、芯片组、内存、磁盘系统、网络等硬件和普通PC有所不同。");
			item.setPrice(28888.8);
			if(testImg)
				item.setPic("D:\\fuwuqi.png");
			item.setChandi("南京");
			items.add(item);
		}
		return items;
	}
	
	public static List<Map<String, byte[]>> initImgDatas(List<Atzproduct> items){
		long t1 = System.currentTimeMillis();
		List<Map<String, byte[]>> imgDatas = new ArrayList();
		for (Atzproduct p : items) {
			Map<String, byte[]> imgData = new HashMap();
			String fileUrl = p.getPic();
			if(fileUrl==null)
				continue;
			imgData.put("pic", getImageData(new File(fileUrl)));
			imgDatas.add(imgData);
		}
		long t2 = System.currentTimeMillis();
		System.out.println("读取图片"+imgDatas.size()+"张，耗时"+(t2-t1)+"毫秒");
		return imgDatas;
	}
	
	private static byte[] getImageData(File imgFile) {
		try {
			ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();
			ImageInputStream iis = ImageIO.createImageInputStream(imgFile);
			Iterator<ImageReader> iter = ImageIO.getImageReaders(iis);
			if (!iter.hasNext())
				return null;
			ImageReader reader = iter.next();
			String imgType = reader.getFormatName();
			//System.out.println("image type=" + imgType);
			if("gif".equals(imgType))
				imgType = "png";  //这种文件读取方式，gif导出有问题
			reader.setInput(iis);
			BufferedImage bufferImg = reader.read(0);
			ImageIO.write(bufferImg, imgType, byteArrayOut);
			iis.close();
			return byteArrayOut.toByteArray();
		} catch (Exception exe) {
			exe.printStackTrace();
			return null;
		}
	}
	
	protected static class Atzproduct {
		private String mingcheng;
		private String miaoshu;
		private Double price;
		private String pic;
		private String chandi;

		public String getMingcheng() {
			return mingcheng;
		}

		public void setMingcheng(String mingcheng) {
			this.mingcheng = mingcheng;
		}

		public String getMiaoshu() {
			return miaoshu;
		}

		public void setMiaoshu(String miaoshu) {
			this.miaoshu = miaoshu;
		}

		public Double getPrice() {
			return price;
		}

		public void setPrice(Double price) {
			this.price = price;
		}

		public String getPic() {
			return pic;
		}

		public void setPic(String pic) {
			this.pic = pic;
		}
		
		public String getChandi() {
			return chandi;
		}

		public void setChandi(String chandi) {
			this.chandi = chandi;
		}
	}
}
