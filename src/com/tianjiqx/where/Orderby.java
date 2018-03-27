package com.tianjiqx.where;

import java.util.ArrayList;

import com.tianjiqx.table.Column;
import com.tianjiqx.table.Table;
import com.tianjiqx.util.StringCombine;

public class Orderby {

	public static ArrayList<String> generateOrderby(Table [] tables) {
		ArrayList<String> list = new ArrayList<String>();

		ArrayList<ArrayList<String>> ll = new ArrayList<ArrayList<String>>();

		for (int i = 0; i < tables.length; i++) {
			ArrayList<String> singlelist = generateSingleTableOrderby(tables[i]);
			ll.add(singlelist);
			list.addAll(singlelist);
		}

		char[] chs = new char[tables.length];

		for (int i = 0; i < tables.length; i++) {
			chs[i] = (char) ('a' + i);
		}

		ArrayList<String> list1 = StringCombine.twoConbine(chs);
		String str = "";
		if (tables.length > 1) {
			for (int i = 0; i < list1.size(); i++) {
				str = list1.get(i);
				ArrayList<String> l1 = ll.get(str.charAt(0) - 'a');
				ArrayList<String> l2 = ll.get(str.charAt(1) - 'a');
				for (int j = 0; j < l1.size(); j++) {
					for (int k = 0; k < l2.size(); k++) {
						str = l1.get(j) + "," + l2.get(k);
						list.add(str);
					}
				}
			}
		}
		// 3±í
		if (tables.length > 2) {
			ArrayList<String> list2 = StringCombine.threeConbine(chs);
			for (int i = 0; i < list2.size(); i++) {
				str = list2.get(i);
				ArrayList<String> l1 = ll.get(str.charAt(0) - 'a');
				ArrayList<String> l2 = ll.get(str.charAt(1) - 'a');
				ArrayList<String> l3 = ll.get(str.charAt(2) - 'a');
				for (int j = 0; j < l1.size(); j++) {
					for (int k = 0; k < l2.size(); k++) {
						for (int l = 0; l < l3.size(); l++) {
							str = l1.get(j) + "," + l2.get(k) + l3.get(l);
							list.add(str);
						}

					}
				}
			}
		}
		// all table
		ArrayList<String> list3 = new ArrayList<String>();
		list3.add("");
		if (tables.length > 3) {
			for (int i = 0; i < tables.length; i++) {
				ArrayList<String> tmp = ll.get(i);
				ArrayList<String> tl = new ArrayList<String>();
				String tstr = "";
				for (int j = 0; j < list3.size(); j++) {
					tstr = list3.get(j);
					for (int k = 0; k < tmp.size(); k++) {
						tl.add(tstr + tmp.get(k) + ",");
					}
				}
				list3 = tl;
			}
			for (int j=0;j<list3.size();j++)
			{
				list.add(list3.get(j).substring(0,list3.get(j).length()-1));
			}
			list.addAll(list3);
		}
		ArrayList<String> tmp = new ArrayList<>();
		for (int j=0;j<list.size();j++)
		{
			tmp.add("Order by "+ list.get(j)+" ");
		}
		list=tmp;

		return list;
	}
	
	public static ArrayList<String> generateAllColOrderby(ArrayList<String> cols){
		ArrayList<String> list= new ArrayList<String>();
		String str=cols.get(0);
		for (int i = 1; i < cols.size(); i++) {
			str+=","+cols.get(i);
		}
		list.add(str);
		
		return list;
	}

	public static ArrayList<String> generateSingleTableOrderby(Table table) {
		ArrayList<String> list = new ArrayList<String>();
		ArrayList<String> cols = new ArrayList<String>();
		//String allcolstr="";
		for (int i = 0; i < table.orderbyCols.length; i++) {
			switch (table.orderbyCols[i].type) {
			case Column.CINT:
			case Column.CVARCHAR:
			case Column.CDATE:
			case Column.CDECIMAL:
				//allcolstr+=table.aliasName + "." + table.groupbyCols[i].colName+",";
				cols.add(table.aliasName + "." + table.orderbyCols[i].colName);
				list.add(table.aliasName + "." + table.orderbyCols[i].colName);
			default:
				break;
			}
		}
		
		list.addAll(generateTwoColOrderby(cols));
		list.addAll(generateThreeColOrderby(cols));
		//list.add(allcolstr.substring(0, allcolstr.length()-1));
		list.addAll(generateAllColOrderby(cols));
		
		return list;
	}
	
	public static ArrayList<String> generateTwoColOrderby(ArrayList<String> cols) {
		ArrayList<String> list = new ArrayList<String>();
		char[] chs = new char[cols.size()];

		for (int i = 0; i < cols.size(); i++) {
			chs[i] = (char) ('a' + i);
		}

		ArrayList<String> list1 = StringCombine.twoConbine(chs);
		String str = "", tmp = "";
		for (int i = 0; i < list1.size(); i++) {
			str = list1.get(i);
			tmp = "";
			for (int j = 0; j < str.length() - 1; j++) {
				tmp += cols.get(str.charAt(j) - 'a') + ",";
			}
			tmp += cols.get(str.charAt(str.length() - 1) - 'a') ;
			list.add(tmp);
		}

		return list;
	}
	
	public static ArrayList<String> generateThreeColOrderby(ArrayList<String> cols) {
		ArrayList<String> list = new ArrayList<String>();
		
		char[] chs = new char[cols.size()];

		for (int i = 0; i < cols.size(); i++) {
			chs[i] = (char) ('a' + i);
		}
		ArrayList<String> list1 = StringCombine.threeConbine(chs);
		String str = "", tmp = "";
		for (int i = 0; i < list1.size(); i++) {
			str = list1.get(i);
			tmp="";
			for (int j = 0; j < str.length() - 1; j++) {
				tmp += cols.get(str.charAt(j) - 'a') + ",";
			}
			tmp += cols.get(str.charAt(str.length() - 1) - 'a');
			list.add(tmp);
		}
		
		return list;
	}

	
}
