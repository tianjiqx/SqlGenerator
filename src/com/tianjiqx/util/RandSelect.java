package com.tianjiqx.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

//#################################
//# ģ��˵����
//# ���ܣ������ȡlist�е��ַ���
//# ���ߣ�quxing
//# email: tianjiqx@126.com
//#################################

public class RandSelect {

	public static ArrayList<String> rangSelect(ArrayList<String> list, int num) {
		
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
	
	
}
