package com.tianjiqx.where;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

import com.tianjiqx.table.Column;
import com.tianjiqx.table.Table;
import com.tianjiqx.util.SelectGroupOrderRel;
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
		// 3表
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
		ArrayList<String> tmp = new ArrayList<String>();
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

	
	
	
	//根据group　by 生成order by
	public static ArrayList<SelectGroupOrderRel> generateOrderby2(ArrayList<SelectGroupOrderRel> groupbyList,Table [] tables) {
		
		ArrayList<SelectGroupOrderRel> tmpArrayList = new ArrayList<SelectGroupOrderRel>();
		
		for (int i=0;i<groupbyList.size();i++)
		{
			tmpArrayList.add(groupbyList.get(i));
		}
		
		//System.out.println("groupbyList size="+tmpArrayList.size());
		groupbyList.clear();
		
		for (int i =0 ;i < tmpArrayList.size();i++)
		{
			ArrayList<String> order_stmt= new ArrayList<String>();
			// 处理 group by xxx,xx,
			String tmp= tmpArrayList.get(i).grouby_stmt.split(" ")[2];
			//1. 
			order_stmt.add(tmp);
			
			//切分出所有的列
			String [] tmpCol= tmp.split(","); 
			
			order_stmt.add(tmp);
			HashSet<String> notGroupCol = new HashSet<String>();

			for (int j=0; j < tables.length;j++)
			{
				for (int k=0;k<tmpCol.length;k++)
				{
					
					if ( tmpCol[k].startsWith(tables[j].aliasName))
					{
						
						String colname= tmpCol[k].split("[.]")[1];
						boolean find=false;
						for (int m=0;m<tables[j].cols.length;m++)
						{
							if (colname == tables[j].cols[m].colName)
							{
								find=true;
								break;
							}
						}
						if (!find){
							notGroupCol.add(tmpCol[k]);
						}
					}
				}				
			} //end for 
			//2.
			String str="";
			for (int j=0;j < tmpCol.length;j++)
			{
				if (j%2==0)
				{
					str+=tmpCol[j]+",";
				}
			}
			if (str!="")
			{
				order_stmt.add(str.substring(0,str.length()-1));
			}
			
			//3
			str="";
			for (int j=1;j < tmpCol.length;j++)
			{
				if (j%2==0)
				{
					str+=tmpCol[j]+",";
				}
			}
			if (str!="")
			{
				order_stmt.add(str.substring(0,str.length()-1));
			}
			for (int k=0;k<order_stmt.size();k++)
			{
				SelectGroupOrderRel sgo= new SelectGroupOrderRel();
				sgo.grouby_stmt=tmpArrayList.get(i).grouby_stmt;
				sgo.orderby_stmt=order_stmt.get(k);
				sgo.selct_stmt=tmpArrayList.get(i).selct_stmt;
				groupbyList.add(sgo);
			}
		}
	

		return groupbyList;
	}
	
	
	
	
	
	
	
}
