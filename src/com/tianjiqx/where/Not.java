package com.tianjiqx.where;

import java.util.ArrayList;

//#################################
//# ģ��˵����
//# ���ܣ����ɣ����ʽ
//# ���ߣ�quxing
//# email: tianjiqx@126.com
//#################################
public class Not implements Expr{

	public static ArrayList<String> generateNot(ArrayList<String> expr) {
		ArrayList<String> list = new ArrayList<String>();


		for (int i = 0; i < expr.size() - 1; i++) {
			list.add("not "+expr.get(i));
		}
		

		return list;
	}
}
