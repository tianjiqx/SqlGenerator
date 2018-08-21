package com.tianjiqx.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

//#################################
//# 模块说明：
//# 功能：随机抽取list中的字符串
//# 作者：quxing
//# email: tianjiqx@126.com
//#################################

public class RandSelect {

	public static ArrayList<String> rangSelect(ArrayList<String> list, int num) {
		
		
		if (num >= list.size())
		{
			return list;
		}
		
		ArrayList<String> r= new ArrayList<String>();
		Random random= new Random();
		random.setSeed(new Date().getTime());
		int max=list.size();
		for (int i=0;i<num;i++)
		{
			r.add(list.get(random.nextInt(max)));
		}
		
		return r;
	}
	
	//间隔采样
	public static ArrayList<String> deltSelect(ArrayList<String> list, int num) {
		
		if (num >= list.size())
		{
			return list;
		}
		
		ArrayList<String> r= new ArrayList<String>();
		
		int delt= (int) Math.ceil(list.size()*1.0/num);
		for (int i=0;i<list.size()/delt;i++)
		{
			r.add(list.get(i*delt));
		}
		
		return r;
	}
	
	public static ArrayList<SelectGroupOrderRel> rangSelect2(ArrayList<SelectGroupOrderRel> list, int num) {
		
		
		if (num >= list.size())
		{
			return list;
		}
		
		ArrayList<SelectGroupOrderRel> r= new ArrayList<SelectGroupOrderRel>();
		Random random= new Random();
		random.setSeed(new Date().getTime());
		int max=list.size();
		for (int i=0;i<num;i++)
		{
			r.add(list.get(random.nextInt(max)));
		}
		
		return r;
	}
	
	//间隔采样
	public static ArrayList<SelectGroupOrderRel> deltSelect2(ArrayList<SelectGroupOrderRel> list, int num) {
		
		if (num >= list.size())
		{
			return list;
		}
		
		ArrayList<SelectGroupOrderRel> r= new ArrayList<SelectGroupOrderRel>();
		
		int delt= (int) Math.ceil(list.size()*1.0/num);
		for (int i=0;i<list.size()/delt;i++)
		{
			r.add(list.get(i*delt));
		}
		
		return r;
	}
	
	
	
	
	
	
}
