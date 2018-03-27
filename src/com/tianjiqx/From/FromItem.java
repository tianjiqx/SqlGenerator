package com.tianjiqx.From;

import java.awt.List;
import java.awt.print.Printable;
import java.time.chrono.MinguoChronology;
import java.util.ArrayList;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane.IconifyAction;

import com.tianjiqx.table.Table;
import com.tianjiqx.util.StringArrange;

//#################################
//# 模块说明：
//# 功能：生成fromitem 元素
//# 作者：quxing
//# email: tianjiqx@126.com
//#################################



public class FromItem {


	//生成2表jion 表达式
	public static ArrayList<String>  generateTwoTabJoinExpr(Table ta,Table tb) {

		//String [] joinExpr = new String[Math.min(ta.joinCols.length,tb.joinCols.length)];
		ArrayList<String> joinExpr = new ArrayList<String>();

		Table a=null,b=null;

		if (ta.joinCols.length > tb.joinCols.length )
		{
			a=tb;
			b=ta;
		}
		else{
			a=ta;
			b=tb;
		}

		for (int i= 0 ; i < a.joinCols.length;i++ )
		{
			for(int j=0;j< b.joinCols.length;j++)
			{
				if (a.joinCols[i].type == b.joinCols[j].type)
				{
					joinExpr.add(a.aliasName+"."+a.joinCols[i].colName+"="+b.aliasName+"."+b.joinCols[j].colName );
					break;
				}
			}

		}

		return joinExpr;
	}

	//生成inner join and left join
	public static ArrayList<String> generateJoin(Table [] tables){

		ArrayList<String> list = new ArrayList<String>();
		if (tables.length  == 0)
		{

		}
		else if (tables.length  == 1)
		{
			list.add(tables[0].tableName+" "+tables[0].aliasName);
		}
		else
		{
			//枚举table顺序
			String tab="";
			for (int i = 0; i < tables.length; i++)
			{
				tab+=(char)('a'+i);
			}
			ArrayList<String> list1 = StringArrange.Permutation(tab);
			//System.out.println("string arrage list:");
			//System.out.println(list1);
			for (int i =0;i< list1.size();i++)
			{
				String fi ="";
				tab=list1.get(i);
				ArrayList<String> tmplist1= new ArrayList<String>();
				tmplist1.add("");
				//System.out.println("tab.length="+tab.length());
				for (int j=0;j<tab.length()-1;j++)
				{
					//System.out.println("j="+j);
					ArrayList<String> tmplist2= new ArrayList<String>();
					String [] jointypes={" inner join "," left join "};
					for (int m =0;m< jointypes.length;m++)
					{
						for (int k =0 ; k < tmplist1.size();k++)
						{
							//System.out.println("k="+k);
							fi=tmplist1.get(k);
							//left table
							fi+=" "+tables[tab.charAt(j)-'a'].tableName +" "+tables[tab.charAt(j)-'a'].aliasName;
							// join 
							fi+=jointypes[m];
							// right table
							fi +=tables[tab.charAt(j+1)-'a'].tableName +" "+tables[tab.charAt(j+1)-'a'].aliasName;
							// on
							fi+=" on ";
							// join expr now is single 
							ArrayList<String> list2 = generateTwoTabJoinExpr(tables[tab.charAt(j)-'a'],tables[tab.charAt(j+1)-'a']);
							for (int l=0;l<list2.size();l++)
							{
								tmplist2.add(fi+list2.get(l)+" ");
							}
						}
					}
					tmplist1=tmplist2;
				}
				list.addAll(tmplist1);
			}
		}

		return list;
	}


}
