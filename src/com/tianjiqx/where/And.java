package com.tianjiqx.where;

import java.util.ArrayList;

import com.tianjiqx.util.StringCombine;

public class And {

	public static ArrayList<String> generateAnd(ArrayList<String> expr) {
		ArrayList<String> list = new ArrayList<String>();

		String tmp = "";
		for (int i = 0; i < expr.size() - 1; i++) {
			tmp += expr.get(i) + " and ";
		}
		tmp += expr.get(expr.size() - 1);
		list.add(tmp);

		return list;
	}

	public static ArrayList<String> generateTwoAnd(ArrayList<String> expr) {

		ArrayList<String> list = new ArrayList<String>();
		char[] chs = new char[expr.size()];

		for (int i = 0; i < expr.size(); i++) {
			chs[i] = (char) ('a' + i);
		}
		ArrayList<String> list1 = StringCombine.twoConbine(chs);
		String str = "", tmp = "( ";
		for (int i = 0; i < list1.size(); i++) {
			tmp = "( ";
			str = list1.get(i);
			for (int j = 0; j < str.length() - 1; j++) {
				tmp += expr.get(str.charAt(j) - 'a') + " and ";
			}
			tmp += expr.get(str.charAt(str.length() - 1) - 'a') + " )";
			list.add(tmp);
		}

		return list;

	}

}
