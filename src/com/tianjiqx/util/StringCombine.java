package com.tianjiqx.util;

import java.util.ArrayList;
import java.util.Collections;

import javax.swing.text.html.MinimalHTMLWriter;

//#################################
//# 程序说明：
//# 功能：字符串组合
//# 作者：quxing
//# email: tianjiqx@126.com
//#################################

public class StringCombine {

	public static int maxOneceCobine = 15; // 一次最大的抽取n数
	public static int maxOneceCobine2 = 10; // 一次最大的抽取n数

	// 封装n选2
	public static ArrayList<String> twoStringConbine(ArrayList<String> srcList,
			String conSymbol) {

		System.out.println("twoStringConbine src size="+srcList.size());
		ArrayList<String> list = new ArrayList<String>();

		if (srcList.size() < 2) {
			return list;
		}

		// 限制产生的数量太大
		// 混洗
		Collections.shuffle(srcList);

		String str = "";

		for (int i = 0; i < srcList.size(); i++) {
			str += (char) ('a' + i);
		}

		for (int m = 0; m < Math.ceil(srcList.size() * 1.0 / maxOneceCobine); m++) {

			String subStr = str.substring(m * maxOneceCobine,
					Math.min((m + 1) * maxOneceCobine, srcList.size()));

			ArrayList<String> conbineList = twoConbine(subStr.toCharArray());

			for (int i = 0; i < conbineList.size(); i++) {
				String tmp = conbineList.get(i);
				list.add(srcList.get(tmp.charAt(0) - 'a') + conSymbol
						+ srcList.get(tmp.charAt(1) - 'a'));
			}
		}
		System.out.println("twoStringConbine num:" + list.size());
		return list;
	}

	// 封装n选3
	public static ArrayList<String> ThreeStringConbine(
			ArrayList<String> srcList, String conSymbol) {

		System.out.println("ThreeStringConbine src size="+srcList.size());
		ArrayList<String> list = new ArrayList<String>();

		if (srcList.size() < 3) {
			return list;
		}

		// 限制产生的数量太大
		// 混洗
		Collections.shuffle(srcList);

		String str = "";

		for (int i = 0; i < srcList.size(); i++) {
			str += (char) ('a' + i);
		}

		for (int m = 0; m < Math.ceil(srcList.size() * 1.0 / maxOneceCobine2); m++) {
			String subStr = str.substring(m * maxOneceCobine2,
					Math.min((m + 1) * maxOneceCobine2, srcList.size()));

			ArrayList<String> conbineList = threeConbine(subStr.toCharArray());

			for (int i = 0; i < conbineList.size(); i++) {
				String tmp = conbineList.get(i);
				list.add(srcList.get(tmp.charAt(0) - 'a') + conSymbol
						+ srcList.get(tmp.charAt(1) - 'a') + conSymbol
						+ srcList.get(tmp.charAt(2) - 'a'));
			}
		}

		System.out.println("ThreeStringConbine num:" + list.size());
		return list;
	}

	// n选2
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

	// n选3
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
