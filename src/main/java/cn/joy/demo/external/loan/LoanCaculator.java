package cn.joy.demo.external.loan;


public class LoanCaculator {
	public static double caculateJieShengLiXi(double chushibenjin, double nianlilv, int zongqishu, int yihuanqishu, double tiqianhuankuanshuj){
		double A = chushibenjin;
	    double b = nianlilv/12;
	    double m = zongqishu;
	    
	    double X = A*b*Math.pow((1+b), m)/(Math.pow((1+b), m)-1);
	    //System.out.println(X);
	    
	    double bjA12 = A*Math.pow((1+b), yihuanqishu)-X*(Math.pow((1+b), yihuanqishu)-1)/b;
	    double yhA12 = X*yihuanqishu;
	    double yhlxA12 = yhA12 - (A - bjA12);
	    //System.out.println(bjA12);
	    //System.out.println(yhA12);
	    //System.out.println(yhlxA12);
	    
	    //System.out.println("lx:"+(X*m-A));
	    
	    double tqh = tiqianhuankuanshuj;
	    
	    double sybj = bjA12*(1)-(tqh);
	    System.out.println("剩余本金:"+sybj);
	    double symts= (Math.log(X)-Math.log(X-sybj*b))/Math.log(1+b);
	    
	    System.out.println("剩余期数:"+(int)symts);
	    
	    int sym = (int)symts;
	    double oldX = X;
	    X = sybj*b*Math.pow((1+b), sym)/(Math.pow((1+b), sym)-1);
	    //System.out.println(X);
	    
	    System.out.println("节省利息:"+(oldX*m-yhA12-(tqh)-X*sym));
	    return oldX*m-yhA12-(tqh)-X*sym;
	}
	
	public static void main(String[] args) throws Exception{
		double lx1 = caculateJieShengLiXi(1360000, 6.55/100, 20*12, 12, 300000);
		
		double lx2 = caculateJieShengLiXi(1360000, 6.55/100, 20*12, 12, 150000);
		double lx3 = caculateJieShengLiXi(1175910.2525367185, 6.55/100, 182, 3, 150000);
		System.out.println("15+15==>"+(lx1-lx2-lx3));
		
		lx2 = caculateJieShengLiXi(1360000, 6.55/100, 20*12, 12, 160000);
		lx3 = caculateJieShengLiXi(1165910.2525367185, 6.55/100, 180, 3, 140000);
		System.out.println("16+14==>"+(lx1-lx2-lx3));
		
		lx2 = caculateJieShengLiXi(1360000, 6.55/100, 20*12, 12, 170000);
		lx3 = caculateJieShengLiXi(1155910.2525367185, 6.55/100, 177, 3, 130000);
		System.out.println("17+13==>"+(lx1-lx2-lx3));
		
		lx2 = caculateJieShengLiXi(1360000, 6.55/100, 20*12, 12, 180000);
		lx3 = caculateJieShengLiXi(1145910.2525367185, 6.55/100, 175, 3, 120000);
		System.out.println("18+12==>"+(lx1-lx2-lx3));
		
		lx2 = caculateJieShengLiXi(1360000, 6.55/100, 20*12, 12, 190000);
		lx3 = caculateJieShengLiXi(1135910.2525367185, 6.55/100, 172, 3, 110000);
		System.out.println("19+11==>"+(lx1-lx2-lx3));
		
		lx2 = caculateJieShengLiXi(1360000, 6.55/100, 20*12, 12, 200000);
		lx3 = caculateJieShengLiXi(1125910.2525367185, 6.55/100, 170, 3, 100000);
		System.out.println("20+10==>"+(lx1-lx2-lx3));
		

		lx2 = caculateJieShengLiXi(1360000, 6.55/100, 20*12, 12, 210000);
		lx3 = caculateJieShengLiXi(1115910.2525367185, 6.55/100, 167, 3, 90000);
		System.out.println("21+9==>"+(lx1-lx2-lx3));
		
		lx2 = caculateJieShengLiXi(1360000, 6.55/100, 20*12, 12, 240000);
		lx3 = caculateJieShengLiXi(1085910.2525367185, 6.55/100, 160, 3, 60000);
		System.out.println("24+6==>"+(lx1-lx2-lx3));
		
		lx2 = caculateJieShengLiXi(1360000, 6.55/100, 20*12, 12, 250000);
		lx3 = caculateJieShengLiXi(1075910.2525367185, 6.55/100, 158, 3, 50000);
		System.out.println("25+5==>"+(lx1-lx2-lx3));
		
		lx2 = caculateJieShengLiXi(1360000, 6.55/100, 20*12, 12, 260000);
		lx3 = caculateJieShengLiXi(1065910.2525367185, 6.55/100, 155, 3, 40000);
		System.out.println("26+4==>"+(lx1-lx2-lx3));
		
		lx2 = caculateJieShengLiXi(1360000, 6.55/100, 20*12, 12, 270000);
		lx3 = caculateJieShengLiXi(1055910.2525367185, 6.55/100, 153, 3, 30000);
		System.out.println("27+3==>"+(lx1-lx2-lx3));
		
		lx2 = caculateJieShengLiXi(1360000, 6.55/100, 20*12, 12, 280000);
		lx3 = caculateJieShengLiXi(1045910.2525367185, 6.55/100, 151, 3, 20000);
		System.out.println("28+2==>"+(lx1-lx2-lx3));
	}
}
