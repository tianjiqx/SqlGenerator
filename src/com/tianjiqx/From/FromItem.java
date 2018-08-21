package com.tianjiqx.From;


import java.util.ArrayList;
import java.util.LinkedList;

import com.tianjiqx.table.Table;
import com.tianjiqx.util.RandSelect;
import com.tianjiqx.util.StringArrange;
import com.tianjiqx.util.StringCombine;

//#################################
//# 模块说明：
//# 功能：生成from 元素
//# 作者：quxing
//# email: tianjiqx@126.com
//#################################



public class FromItem {


	//生产2表连接表达式
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
				if (a.joinCols[i].joinColKey.endsWith(b.joinCols[j].joinColKey))
				{
					joinExpr.add(a.aliasName+"."+a.joinCols[i].colName+"="+b.aliasName+"."+b.joinCols[j].colName );
					break;
				}
			}

		}

		return joinExpr;
	}
	
	
	
	public static ArrayList<String> allEdgeRelJoin(Table[] tables) {
		
		ArrayList<String> joinExprArrayList = new ArrayList<String>();
		String strtab = "";

		if (tables.length < 4)
		{
			return joinExprArrayList;
		}
		
		for (int j = 0; j < tables.length; j++) {
			strtab += (char) ('a' + j);
		}
		
		ArrayList<String> list1 = StringCombine
				.twoConbine(strtab.toCharArray());


		LinkedList<String[]> list2 = new LinkedList<String[]>();

		for (int i = 0; i < list1.size(); i++) {
			ArrayList<String> tmp = FromItem.generateTwoTabJoinExpr(
					tables[list1.get(i).charAt(0) - 'a'], tables[list1.get(i)
							.charAt(1) - 'a']);

			String[] strarry = new String[tmp.size()];
			for (int j = 0; j < tmp.size(); j++) {
				strarry[j] = tmp.get(j);
			}

			list2.add(strarry);

		}


		ArrayList<String> list3 = new ArrayList<String>();
		list3.add("");
		for (int i = 0; i < list2.size(); i++) {
			ArrayList<String> tmp = new ArrayList<String>();
			for (int j = 0; j < list3.size(); j++) {
				for (int k = 0; k < list2.get(i).length; k++) {
					tmp.add(list3.get(j) + list2.get(i)[k] + " and ");
				}
			}
			list3 = tmp;
		}
		joinExprArrayList.addAll(list3);

		System.out.println("allEdgeRelJoin size=" + joinExprArrayList.size());
		return joinExprArrayList;
	}

	//生成单环 join 
	public static ArrayList<String> generateSingleRing(Table[] tables) {

		ArrayList<String> joinExprArrayList = new ArrayList<String>();

		if (tables.length < 3) {
			return joinExprArrayList;
		}

		String strtab = "";

		for (int j = 0; j < tables.length; j++) {
			strtab += (char) ('a' + j);
		}

		ArrayList<String> list3 = new ArrayList<String>();
		list3.add("");
		ArrayList<String> allTableArrange = StringArrange.Permutation(strtab);
		// join A-B-C-A
		for (int i = 0; i < allTableArrange.size(); i++) {

			int len = allTableArrange.get(i).length();
			for (int j = 0; j < len; j++) {
				
				ArrayList<String> tmp = FromItem
						.generateTwoTabJoinExpr(tables[allTableArrange.get(i)
								.charAt(j) - 'a'], tables[allTableArrange
								.get(i).charAt((j + 1) % len) - 'a']);

				ArrayList<String> tt = new ArrayList<String>();
				for (int m = 0; m < list3.size(); m++) {
					for (int n = 0; n < tmp.size(); n++) {
						tt.add(list3.get(m) + tmp.get(n) + " and ");
					}
				}

				list3 = tt;
			}
			joinExprArrayList.addAll(list3);
			list3.clear();
			list3.add("");
		}

		System.out.println("generateSingleRing join expr sql:"
				+ joinExprArrayList.size());

		return joinExprArrayList;
	}

	// 单链 join A-B-C
	public static ArrayList<String> generateSingleLink(Table[] tables) {

		ArrayList<String> joinExprArrayList = new ArrayList<String>();

		if (tables.length < 2) {
			return joinExprArrayList;
		}

		String strtab = "";

		for (int j = 0; j < tables.length; j++) {
			strtab += (char) ('a' + j);
		}

		ArrayList<String> list3 = new ArrayList<String>();
		list3.add("");

		ArrayList<String> allTableArrange = StringArrange.Permutation(strtab);
		//  join A-B-C
		for (int i = 0; i < allTableArrange.size(); i++) {

			int len = allTableArrange.get(i).length();
			for (int j = 0; j < len - 1; j++) {
				ArrayList<String> tmp = FromItem.generateTwoTabJoinExpr(
						tables[allTableArrange.get(i).charAt(j) - 'a'],
						tables[allTableArrange.get(i).charAt((j + 1)) - 'a']);

				ArrayList<String> tt = new ArrayList<String>();

				for (int m = 0; m < list3.size(); m++) {
					for (int n = 0; n < tmp.size(); n++) {
						tt.add(list3.get(m) + tmp.get(n) + " and ");
					}
				}
				list3 = tt;
			}
			joinExprArrayList.addAll(list3);
			list3.clear();
			list3.add("");
		}

		System.out.println("generateSingleLink join expr sql:"
				+ joinExprArrayList.size());

		return joinExprArrayList;
	}

	//子环 join A-B-C-D A-C B-D
	public static ArrayList<String> generateSubRing1(Table[] tables) {

		ArrayList<String> joinExprArrayList = new ArrayList<String>();

		if (tables.length < 4) {
			return joinExprArrayList;
		}

		String strtab = "";

		for (int j = 0; j < tables.length; j++) {
			strtab += (char) ('a' + j);
		}

		ArrayList<String> list3 = new ArrayList<String>();
		list3.add("");
		ArrayList<String> allTableArrange = StringArrange.Permutation(strtab);
		// join A-B-C
		for (int i = 0; i < allTableArrange.size(); i++) {

			int len = allTableArrange.get(i).length();
			for (int j = 0; j < len - 1; j++) {
				ArrayList<String> tmp1 = FromItem.generateTwoTabJoinExpr(
						tables[allTableArrange.get(i).charAt(j) - 'a'],
						tables[allTableArrange.get(i).charAt((j + 1)) - 'a']);

				ArrayList<String> tmp = new ArrayList<String>();

				if (j - 1 >= 0) {
					ArrayList<String> tmp2 = FromItem
							.generateTwoTabJoinExpr(
									tables[allTableArrange.get(i).charAt(j - 1) - 'a'],
									tables[allTableArrange.get(i).charAt(
											(j + 1)) - 'a']);

					for (int m = 0; m < tmp1.size(); m++) {
						for (int n = 0; n < tmp2.size(); n++) {
							tmp.add(tmp1.get(m) + " and " + tmp2.get(n));
						}
					}
				} else {
					tmp = tmp1;
				}

				ArrayList<String> tt = new ArrayList<String>();

				for (int m = 0; m < list3.size(); m++) {
					for (int n = 0; n < tmp.size(); n++) {
						tt.add(list3.get(m) + tmp.get(n) + " and ");
					}
				}
				list3 = tt;
			}

			joinExprArrayList.addAll(list3);
			list3.clear();
			list3.add("");
		}

		System.out.println("generateSubRing1 join expr sql:"
				+ joinExprArrayList.size());

		return joinExprArrayList;
	}

	// 子环2 join A-B-C-D-E A-C A-D
	public static ArrayList<String> generateSubRing2(Table[] tables) {

		ArrayList<String> joinExprArrayList = new ArrayList<String>();

		if (tables.length < 4) {
			return joinExprArrayList;
		}

		String strtab = "";

		for (int j = 0; j < tables.length; j++) {
			strtab += (char) ('a' + j);
		}

		ArrayList<String> list3 = new ArrayList<String>();
		list3.add("");
		ArrayList<String> allTableArrange = StringArrange.Permutation(strtab);
		//  join A-B-C
		for (int i = 0; i < allTableArrange.size(); i++) {

			int len = allTableArrange.get(i).length();
			for (int j = 0; j < len - 1; j++) {
				ArrayList<String> tmp1 = FromItem.generateTwoTabJoinExpr(
						tables[allTableArrange.get(i).charAt(j) - 'a'],
						tables[allTableArrange.get(i).charAt((j + 1)) - 'a']);

				ArrayList<String> tmp = new ArrayList<String>();

				if (j - 1 >= 0 && j != len - 2) {
					ArrayList<String> tmp2 = FromItem
							.generateTwoTabJoinExpr(
									tables[allTableArrange.get(i).charAt(0) - 'a'],
									tables[allTableArrange.get(i).charAt(
											(j + 1)) - 'a']);

					for (int m = 0; m < tmp1.size(); m++) {
						for (int n = 0; n < tmp2.size(); n++) {
							tmp.add(tmp1.get(m) + " and " + tmp2.get(n));
						}
					}
				} else {
					tmp = tmp1;
				}

				ArrayList<String> tt = new ArrayList<String>();

				for (int m = 0; m < list3.size(); m++) {
					for (int n = 0; n < tmp.size(); n++) {
						tt.add(list3.get(m) + tmp.get(n) + " and ");
					}
				}
				list3 = tt;
			}

			joinExprArrayList.addAll(list3);
			list3.clear();
			list3.add("");
		}

		System.out.println("generateSubRing2 join expr sql:"
				+ joinExprArrayList.size());

		return joinExprArrayList;
	}

	// 子环3 join A-B-C-D-E A-C A-D A-E
	public static ArrayList<String> generateSubRing3(Table[] tables) {

		ArrayList<String> joinExprArrayList = new ArrayList<String>();

		if (tables.length < 4) {
			return joinExprArrayList;
		}

		String strtab = "";

		for (int j = 0; j < tables.length; j++) {
			strtab += (char) ('a' + j);
		}

		ArrayList<String> list3 = new ArrayList<String>();
		list3.add("");

		ArrayList<String> allTableArrange = StringArrange.Permutation(strtab);
		// join A-B-C
		for (int i = 0; i < allTableArrange.size(); i++) {

			int len = allTableArrange.get(i).length();
			for (int j = 0; j < len - 1; j++) {
				ArrayList<String> tmp1 = FromItem.generateTwoTabJoinExpr(
						tables[allTableArrange.get(i).charAt(j) - 'a'],
						tables[allTableArrange.get(i).charAt((j + 1)) - 'a']);

				ArrayList<String> tmp = new ArrayList<String>();

				if (j - 1 >= 0) {
					ArrayList<String> tmp2 = FromItem
							.generateTwoTabJoinExpr(
									tables[allTableArrange.get(i).charAt(0) - 'a'],
									tables[allTableArrange.get(i).charAt(
											(j + 1)) - 'a']);

					for (int m = 0; m < tmp1.size(); m++) {
						for (int n = 0; n < tmp2.size(); n++) {
							tmp.add(tmp1.get(m) + " and " + tmp2.get(n));
						}
					}
				} else {
					tmp = tmp1;
				}

				ArrayList<String> tt = new ArrayList<String>();

				for (int m = 0; m < list3.size(); m++) {
					for (int n = 0; n < tmp.size(); n++) {
						tt.add(list3.get(m) + tmp.get(n) + " and ");
					}
				}
				list3 = tt;
			}

			joinExprArrayList.addAll(list3);
			list3.clear();
			list3.add("");
		}

		System.out.println("generateSubRing3 join expr sql:"
				+ joinExprArrayList.size());

		return joinExprArrayList;
	}

	// 爪型1 
	public static ArrayList<String> generateClaw1(Table[] tables) {

		ArrayList<String> joinExprArrayList = new ArrayList<String>();

		if (tables.length < 2) {
			return joinExprArrayList;
		}

		String strtab = "";

		for (int j = 0; j < tables.length; j++) {
			strtab += (char) ('a' + j);
		}

		ArrayList<String> list3 = new ArrayList<String>();
		list3.add("");

		ArrayList<String> allTableArrange = StringArrange.Permutation(strtab);

		for (int i = 0; i < allTableArrange.size(); i++) {

			int len = allTableArrange.get(i).length();
			for (int j = 0; j < len - 1; j++) {
				ArrayList<String> tmp = FromItem.generateTwoTabJoinExpr(
						tables[allTableArrange.get(i).charAt(0) - 'a'],
						tables[allTableArrange.get(i).charAt((j + 1)) - 'a']);

				ArrayList<String> tt = new ArrayList<String>();

				for (int m = 0; m < list3.size(); m++) {
					for (int n = 0; n < tmp.size(); n++) {
						tt.add(list3.get(m) + tmp.get(n) + " and ");
					}
				}
				list3 = tt;
			}

			joinExprArrayList.addAll(list3);
			list3.clear();
			list3.add("");
		}

		System.out.println("generateClaw1 join expr sql:"
				+ joinExprArrayList.size());

		return joinExprArrayList;
	}

	

	// 2重单环  join A=B=C=A
	public static ArrayList<String> generateDoubleRing(Table[] tables) {

		ArrayList<String> joinExprArrayList = new ArrayList<String>();

		if (tables.length < 2) {
			return joinExprArrayList;
		}

		String strtab = "";

		for (int j = 0; j < tables.length; j++) {
			strtab += (char) ('a' + j);
		}

		ArrayList<String> list3 = new ArrayList<String>();
		list3.add("");
		ArrayList<String> allTableArrange = StringArrange.Permutation(strtab);

		for (int i = 0; i < allTableArrange.size(); i++) {

			int len = allTableArrange.get(i).length();
			for (int j = 0; j < len; j++) {
				ArrayList<String> tmp = FromItem
						.generateTwoTabJoinExpr(tables[allTableArrange.get(i)
								.charAt(j) - 'a'], tables[allTableArrange
								.get(i).charAt((j + 1) % len) - 'a']);

				
				tmp = StringCombine.twoStringConbine(tmp, " and ");

				ArrayList<String> tt = new ArrayList<String>();

				for (int m = 0; m < list3.size(); m++) {
					for (int n = 0; n < tmp.size(); n++) {

						tt.add(list3.get(m) + tmp.get(n) + " and ");
					}
				}

				list3 = tt;
			}
			joinExprArrayList.addAll(list3);
			list3.clear();
			list3.add("");
		}

		System.out.println("generateSingleRing join expr sql:"
				+ joinExprArrayList.size());

		return joinExprArrayList;
	}

	// 2种单链 join A=B=C
	public static ArrayList<String> generateDoubleLink(Table[] tables) {

		ArrayList<String> joinExprArrayList = new ArrayList<String>();

		if (tables.length < 2) {
			return joinExprArrayList;
		}

		String strtab = "";

		for (int j = 0; j < tables.length; j++) {
			strtab += (char) ('a' + j);
		}

		ArrayList<String> list3 = new ArrayList<String>();
		list3.add("");
		ArrayList<String> allTableArrange = StringArrange.Permutation(strtab);
		for (int i = 0; i < allTableArrange.size(); i++) {

			int len = allTableArrange.get(i).length();
			for (int j = 0; j < len - 1; j++) {
				ArrayList<String> tmp = FromItem.generateTwoTabJoinExpr(
						tables[allTableArrange.get(i).charAt(j) - 'a'],
						tables[allTableArrange.get(i).charAt((j + 1)) - 'a']);

				tmp = StringCombine.twoStringConbine(tmp, " and ");

				ArrayList<String> tt = new ArrayList<String>();

				for (int m = 0; m < list3.size(); m++) {
					for (int n = 0; n < tmp.size(); n++) {

						tt.add(list3.get(m) + tmp.get(n) + " and ");
					}
				}
				list3 = tt;
			}
			joinExprArrayList.addAll(list3);
			list3.clear();
			list3.add("");
		}

		System.out.println("generateDoubleLink join expr sql:"
				+ joinExprArrayList.size());

		return joinExprArrayList;
	}

	
	//生成所有的join 表达式
	public static ArrayList<String> generateAllJoinExpr(Table[] tables) {
		
		ArrayList<String> joinExprArrayList=new ArrayList<String>();
		
		if ( tables.length <=3)
		{
			joinExprArrayList.addAll(FromItem.allEdgeRelJoin(tables));
			joinExprArrayList.addAll(FromItem.generateSingleLink(tables));
			joinExprArrayList.addAll(FromItem.generateSingleRing(tables));
			joinExprArrayList.addAll(FromItem.generateDoubleLink(tables));
			joinExprArrayList.addAll(FromItem.generateDoubleRing(tables));
		}
		else if ( tables.length == 4){
			joinExprArrayList.addAll(FromItem.generateSubRing1(tables));
			joinExprArrayList.addAll(FromItem.generateSubRing2(tables));
			joinExprArrayList.addAll(FromItem.generateSubRing3(tables));
		}
		
		System.out.println("generateAllJoinExpr sql num="+joinExprArrayList.size());
		
		return joinExprArrayList;
	}
	
	public static ArrayList<String> generatePartJoinExpr(Table[] tables) {
		
		ArrayList<String> joinExprArrayList=new ArrayList<String>();
		
		joinExprArrayList.addAll(FromItem.generateSingleLink(tables));
		joinExprArrayList.addAll(FromItem.generateDoubleLink(tables));
		
		
		joinExprArrayList=RandSelect.rangSelect(joinExprArrayList, 4);
		
		System.out.println("generatePartJoinExpr sql num="+joinExprArrayList.size());
		
		return joinExprArrayList;
	}
	
	//生成显式 inner join and left join
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
							if (j==0)
							{
								//left table
								fi+=" "+tables[tab.charAt(j)-'a'].tableName +" "+tables[tab.charAt(j)-'a'].aliasName;
							}
							// join 
							fi+=jointypes[m];
							// right table
							fi +=tables[tab.charAt(j+1)-'a'].tableName +" "+tables[tab.charAt(j+1)-'a'].aliasName;
							// on
							fi+=" on ";
							Table [] tables2 =new Table[2];
							tables2[0]=tables[tab.charAt(j)-'a'];
							tables2[1]=tables[tab.charAt(j+1)-'a'];
							//ArrayList<String> list2 = generateAllJoinExpr(tables2);
							//此时的显示声明，只做
							ArrayList<String> list2 = generatePartJoinExpr(tables2);
							for (int l=0;l<list2.size();l++)
							{
								tmplist2.add(fi+list2.get(l).substring(0,list2.get(l).length()-4)+" ");
							}
						}
					}
					tmplist1=tmplist2;
				}
				list.addAll(tmplist1);
			}
		}
		System.out.println("generateJoin size="+list.size());
		return list;
	}


}
