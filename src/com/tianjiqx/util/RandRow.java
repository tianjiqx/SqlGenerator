package com.tianjiqx.util;

import java.util.Date;
import java.util.Random;

//#################################
//# 模块说明：
//# 功能：随机抽取产生行数据
//# 作者：quxing
//# email: tianjiqx@126.com
//#################################

public class RandRow {

	public static int delt = 0;
	
	public static int randInt(int min, int max) {
		Random random = new Random();
		random.setSeed(new Date().getTime()+delt);
		int ret = min;
		ret = min + random.nextInt(max);
		return ret;

	}

	public static String randBool() {
		Random random = new Random();
		random.setSeed(new Date().getTime()+delt);
		String[] r = { "'1'", "'2'" ,"'3'","'4'"};
		String ret = "false";
		ret = r[random.nextInt(10) % r.length];
		return ret;

	}

	public static double randDouble(double min, double max) {
		Random random = new Random();
		random.setSeed(new Date().getTime()+delt);
		double ret = min;
		ret = min + random.nextDouble() * (max - min);
		// cbase 与db2 double 禁毒位数保留不一致
//		String tmp=String.format("%.2f", ret);
//		ret= Double.valueOf(tmp);
		
		int  tmp = (int) ret;
		ret = tmp + 0.83;
		
		return ret;

	}

	public static String randVarChar(int varcharlength) {
		Random random = new Random();
		random.setSeed(new Date().getTime()+delt);
		String tmpString = "";

		for (int i = 0; i < varcharlength; i++) {
			tmpString +=   (char)( 'a' + random.nextInt(26));
		}

		String ret = "'"+tmpString+"'";
		return ret;
	}

	public static String randDate(String b, String e) {
		Random random = new Random();
		random.setSeed(new Date().getTime()+delt);
		String tmpString = "";

		String [] bd = b.split("-");
		String [] ed = e.split("-");
		
		for (int i = 0; i < bd.length; i++) {

			int tb= (Integer.parseInt(bd[i]));  
			int te= (Integer.parseInt(ed[i]));  
			
			int t= tb + random.nextInt(te-tb);
			
			String tStr= String.valueOf(t);
			if (tStr.length()==1)
			{
				tStr="0"+tStr;
			}
			
			
			tmpString += tStr+"-";
			
		}

		String ret = "'"+tmpString.substring(0,tmpString.length()-1)+"'";
		return ret;
	}
	public static String randTime(String b, String e) {
		Random random = new Random();
		random.setSeed(new Date().getTime()+delt);
		String tmpString = "";

		String[] bd = b.split(":");
		String[] ed = e.split(":");
		for (int i = 0; i < bd.length; i++) {
			int tb= (Integer.parseInt(bd[i]));  
			int te= (Integer.parseInt(ed[i]));  
			
			int t= tb + random.nextInt(te-tb);
			
			String tStr= String.valueOf(t);
			if (tStr.length()==1)
			{
				tStr="0"+tStr;
			}
			
			tmpString +=tStr +":";
			
		}

		String ret = "'"+tmpString.substring(0,tmpString.length()-1)+"'";
		return ret;
	}
	
	public static String randDecimal(int min, int max) {
		Random random = new Random();
		random.setSeed(new Date().getTime()+delt);
		int tmp =min;
		String ret = "";
		tmp = min + random.nextInt(max);
		ret="'"+String.valueOf(tmp)+"'";
		
		return ret;

	}
	

}
