package com.tianjiqx.util;

import java.util.ArrayList;

//#################################
//# ����˵����
//# ���ܣ��ַ������
//# ���ߣ�quxing
//# email: tianjiqx@126.com
//#################################

public class StringCombine {

	// �������
	public static ArrayList<String> twoConbine(char[] chs) {

		ArrayList<String> list = new ArrayList<String>();
		String str = "";
		for (int i = 0; i < chs.length - 1; i++) {
			for (int j = i + 1; j < chs.length; j++) {
				str = chs[i] + "" + chs[j];
				list.add(str);
			}
		}
		return list;
	}

	// nѡ3
	public static ArrayList<String> threeConbine(char[] chs) {
		ArrayList<String> list = new ArrayList<String>();
		String str = "";
		String str2 = "";
		ArrayList<String> list2 = twoConbine(chs);

		for (int i = 0; i < list2.size(); i++) {
			str2 = list2.get(i);

			for (int j = 0; j < chs.length; j++) {
				if (!str2.contains(chs[j] + "")) {
					str = str2 + chs[j];
					list.add(str);
				}
			}
		}

		return list;
	}

}
