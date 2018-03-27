package com.tianjiqx.select;

import java.awt.List;
import java.util.ArrayList;

import com.tianjiqx.table.Table;

//#################################
//# 模块说明：
//# 功能：生成select子句
//# 作者：quxing
//# email: tianjiqx@126.com
//#################################

public class Select {
	
	public static ArrayList<String> generateSelectItem(Table tab)
	{
		ArrayList<String> list = new ArrayList<String>();
		
		//*
		list.add(tab.aliasName+".*,");
		//half 1
		String str="";
		for (int i =0;i < tab.cols.length;i++)
		{
			if (i%2==0)
			{
				str+=tab.aliasName+"."+tab.cols[i].colName+",";
			}
		}
		list.add(str);
		//half 2
		str="";
		for (int i =0;i < tab.cols.length/2;i++)
		{
			str+=tab.aliasName+"."+tab.cols[i].colName+",";
		}
		list.add(str);
		// join cols
		str="";
		for (int i =0;i < tab.joinCols.length;i++)
		{
			str+=tab.aliasName+"."+tab.joinCols[i].colName+",";
		}
		list.add(str);
		for (int i =0 ;i < tab.cols.length;i++)
		{
			if (i%2==1)
			{
				str=tab.aliasName+"."+tab.cols[i].colName+",";
				list.add(str);
			}
		}
		// empty
		list.add("");
		
		return list;
	}
	
	
	public static ArrayList<String> generateSelect(Table [] tables) {
		ArrayList<String> list = new ArrayList<String>();
		ArrayList<String> tmplist1= new ArrayList<String>();
		tmplist1.add("");
		String str="";
		for (int j=0;j< tables.length;j++)
		{
			ArrayList<String> tmplist2= new ArrayList<String>();
			for (int k =0 ; k < tmplist1.size();k++)
			{
				str=tmplist1.get(k);
				ArrayList<String> l = generateSelectItem(tables[j]);
				for (int i=0;i< l.size();i++)
				{
					tmplist2.add(str+l.get(i));
				}
			}
//			tmplist1.clear();
//			for(int k=0;k< tmplist2.size();k++)
//			{
//				tmplist1.add(tmplist2.get(k))
//			}
			tmplist1=tmplist2;
		}
		//tmplist1.remove(tmplist1.size()-1);
		for (int i=0;i< tmplist1.size()-1;i++)
		{
			list.add("select "+tmplist1.get(i).substring(0,tmplist1.get(i).length()-1)+" ");
		}
		
		return list;
		
	}

}
