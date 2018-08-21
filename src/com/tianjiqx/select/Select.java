package com.tianjiqx.select;

import java.awt.List;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

import com.tianjiqx.table.Column;
import com.tianjiqx.table.Table;
import com.tianjiqx.util.SelectGroupOrderRel;

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
		
		
		//添加必要列
		if (tab.requiredCols.length!=0)
		{
			for (int i=0;i<list.size();i++)
			{
				for (int j=0;j<tab.requiredCols.length;j++)
				{
					if (!list.get(i).contains(tab.requiredCols[j].colName))
					{
						list.set(i, list.get(i)+tab.aliasName+"."+tab.requiredCols[j].colName+",");
					}
				}
				
			}
		}
		
		
		return list;
	}
	
	// sql 带有group by
	public static ArrayList<String> generateSelectItem2(Table tab)
	{
		ArrayList<String> list = new ArrayList<String>();
		
		ArrayList<Column> notGroubyColList = new ArrayList<Column>(); 
		
		String str="";
		if (tab.groupbyCols.length==0)
		{
			list.addAll(generateSelectItem(tab));
		}
		else {

			//group by column
			str="";
			for (int i =0 ;i < tab.groupbyCols.length;i++)
			{
				str+="max("+tab.aliasName+"."+tab.cols[i].colName+"),";
			}
			list.add(str);
			
			for (int i =0 ;i < tab.cols.length;i++)
			{
				boolean isGroubyCol=false;
				for( int j =0; j < tab.groupbyCols.length;j++ )
				{
					if (tab.cols[i].colName == tab.groupbyCols[j].colName)
					{
						isGroubyCol=true;
						break;
					}
				}
				if (!isGroubyCol)
				{
					notGroubyColList.add(tab.cols[i]);
				}
			}
		}
		
		//half 1
		str="";
		for (int i =0;i < notGroubyColList.size();i++)
		{
			if (i%2==0)
			{
				str+="count("+tab.aliasName+"."+notGroubyColList.get(i).colName+"),";
			}
		}
		list.add(str);
		//str="";
		for (int i =0;i < notGroubyColList.size();i++)
		{
			if (i%2==0)
			{
				str+="max("+tab.aliasName+"."+notGroubyColList.get(i).colName+"),";
			}
		}
		list.add(str);
		//half 2
		str="";
		for (int i =0;i < notGroubyColList.size()/2;i++)
		{
			str+="count("+tab.aliasName+"."+notGroubyColList.get(i).colName+"),";
		}
		list.add(str);
		str="";
		for (int i =notGroubyColList.size()/2;i < notGroubyColList.size();i++)
		{
			str+="min("+tab.aliasName+"."+notGroubyColList.get(i).colName+"),";
		}
		list.add(str);
		
		
		// empty
		list.add("");
		
		
		//添加必要列
		if (tab.requiredCols.length!=0)
		{
			for (int i=0;i<list.size();i++)
			{
				for (int j=0;j<tab.requiredCols.length;j++)
				{
					if (!list.get(i).contains(tab.requiredCols[j].colName))
					{
						list.set(i, list.get(i)+"max("+tab.aliasName+"."+tab.requiredCols[j].colName+"),");
					}
				}
			}
		}

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
	
	
	// 根据grouby 语句生成 select子句
	public static ArrayList<SelectGroupOrderRel> generateSelect2(ArrayList<SelectGroupOrderRel> groupbyList,Table [] tables) {
		
		ArrayList<SelectGroupOrderRel> tmpArrayList = new ArrayList<SelectGroupOrderRel>();
		
		for (int i=0;i<groupbyList.size();i++)
		{
			tmpArrayList.add(groupbyList.get(i));
		}
		
		//System.out.println("groupbyList size="+tmpArrayList.size());
		groupbyList.clear();
		
		for (int i =0 ;i < tmpArrayList.size();i++)
		{
			ArrayList<String> select_stmt= new ArrayList<String>();
			// 处理 group by xxx,xx,
			String tmp= tmpArrayList.get(i).grouby_stmt.split(" ")[2];
			
			//1. 
			select_stmt.add(tmp);
			
			//切分出所有的列
			String [] tmpCol= tmp.split(","); 
			
			// 提取表
//			HashSet<String> tn= new HashSet<String>();
//			for (int j=0;j < tmpCol.length;j++)
//			{
//				tn.add(tmpCol[j].split(".")[1]);
//			}
			select_stmt.add(tmp);
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
			}
			String pre="";
			for (int j=0; j < tmpCol.length;j++ )
			{
				if (j%2==0)
				{
					pre+=tmpCol[j]+",";
				}
			}
			//2.
			String str="";
			str=pre;
			Iterator<String> iterator= notGroupCol.iterator();
			int j=0;
			while(iterator.hasNext())
			{
				if (j++%2==0)
				{
				  str+="count("+iterator.next()+"),";
				}
			}
			str=str.substring(0,str.length()-1);
			select_stmt.add(str);
			
			//3.
			str=pre;
			j=0;
			iterator= notGroupCol.iterator();
			while(iterator.hasNext())
			{
				if (j++%3==0)
				{
					str+="max("+iterator.next()+"),";
				}
			}
			str=str.substring(0,str.length()-1);
			select_stmt.add(str);

			for (int k=0;k<select_stmt.size();k++)
			{
				SelectGroupOrderRel sgo= new SelectGroupOrderRel();
				sgo.grouby_stmt=tmpArrayList.get(i).grouby_stmt;
				sgo.orderby_stmt=tmpArrayList.get(i).orderby_stmt;
				sgo.selct_stmt=select_stmt.get(k);
				
				groupbyList.add(sgo);
			}
		}
		
		return groupbyList;
		
	}
	
	
	
	
	
	

}
