package com.tianjiqx.From;

import java.util.ArrayList;
import java.util.LinkedList;

import javax.naming.InitialContext;

import com.tianjiqx.table.Table;
import com.tianjiqx.util.RandSelect;
import com.tianjiqx.util.StringArrange;
import com.tianjiqx.util.StringCombine;

//#################################
//# 模块说明：
//# 功能：生成from子句
//# 作者：quxing
//# email: tianjiqx@126.com
//#################################

public class From {

	static int alias_idx = 0; // 别名唯一id

	// 表的别名生成器
	public static void generateAlias(Table[] ts) {

		String[] aliastabs = new String[ts.length];
		for (int i = 0; i < ts.length; i++) {
			alias_idx++;
			aliastabs[i] = ts[i].tableName + "_" + alias_idx;
			ts[i].aliasName = aliastabs[i];
		}
	}



	// 生成全隐式join的From子句和join条件
	public static ArrayList<String> generateImplicit(Table[] tables) {
		ArrayList<String> list = new ArrayList<String>();

		ArrayList<String> tmplist1 = new ArrayList<String>();
		tmplist1.add("");

		// 全隐式声明
		for (int i = 0; i < tables.length; i++) {
			ArrayList<String> tmplist2 = new ArrayList<String>();
			for (int k = 0; k < tmplist1.size(); k++) {
				String str = tmplist1.get(k);
				Table[] t = new Table[1];
				t[0] = tables[i];
				ArrayList<String> l = FromItem.generateJoin(t);
				for (int j = 0; j < l.size(); j++) {
					tmplist2.add(str + l.get(j) + ",");
				}
			}
			tmplist1 = tmplist2;
		}

		for (int i = 0; i < tmplist1.size(); i++) {
			list.add("From "
					+ tmplist1.get(i)
							.substring(0, tmplist1.get(i).length() - 1)
					+ " Where ");
		}

		if (tables.length < 2)
		{

		}
		else 
		{
			// 生成 join 表达式
			ArrayList<String> joinExprArrayList = FromItem.generateAllJoinExpr(tables);

			ArrayList<String> list3 = new ArrayList<String>();
			for (int i = 0; i < list.size(); i++) {
				for (int j = 0; j < joinExprArrayList.size(); j++) {
					list3.add(list.get(i) + joinExprArrayList.get(j));
				}
			}

			list = list3;
		}

		System.out.println("generateImplicit join sql:" + list.size());
		return list;
	}
	
	


	// 生成带有显示join的From子句和join条件
	public static ArrayList<String> generateWithExplicit(Table[] tables) {

		ArrayList<String> list = new ArrayList<String>();

		// 带显示声明
		for (int i = 1; i < tables.length; i++) {  // i 可以理解为 显示join个个数
			
			Table[] tab = new Table[i + 1];
			for (int j = 0; j <= i; j++) {
				tab[j] = tables[j];
			}
			
			// 生成joinitem
			ArrayList<String> tlist1 = FromItem.generateJoin(tab);
			for (int j = 0; j < tlist1.size(); j++) {
				tlist1.set(j, tlist1.get(j) + ",");
			}
			
			// 隐式声明
			// System.out.println("tab.lenth="+tab.length+" tables.length="+tables.length);
			for (int m = tab.length; m < tables.length; m++) {
				ArrayList<String> tmplist2 = new ArrayList<String>();
				for (int k = 0; k < tlist1.size(); k++) {
					String str = tlist1.get(k);
					Table[] t = new Table[1];
					t[0] = tables[m];
					ArrayList<String> l = FromItem.generateJoin(t);
					for (int j = 0; j < l.size(); j++) {
						tmplist2.add(str + l.get(j) + ",");
					}
				}
				tlist1 = tmplist2;
			}

			// 隐式join表达式
			// tlist1 = new ArrayList<>();
			// tlist1.add("");
			// 无隐式fromitem
			if (tab.length == tables.length) {
				for (int j = 0; j < tlist1.size(); j++) {
					list.add("From "
							+ tlist1.get(j).substring(0,
									tlist1.get(j).length() - 1) + "Where ");
				}
				continue;
			}
			
			
			tab = new Table[tables.length - tab.length + 1];
			for (int j = 0; j < tab.length; j++) {
				tab[j] = tables[i + j];
			}

			// order
			String strtab = "";
			for (int j = 0; j < tab.length; j++) {
				strtab += (char) ('a' + j);
			}
			ArrayList<String> list1 = StringArrange.Permutation(strtab);

			for (int j = 0; j < list1.size(); j++) {
				ArrayList<String> tlist2 = new ArrayList<String>();
				for (int a = 0; a < tlist1.size(); a++) {
					tlist2.add("From "
							+ tlist1.get(a).substring(0,
									tlist1.get(a).length() - 1) + " Where ");
				}
				strtab = list1.get(j);
				for (int m = 0; m < strtab.length() - 1; m++) {
					ArrayList<String> tmplist2 = new ArrayList<String>();
					for (int n = 0; n < tlist2.size(); n++) {
						String fi = tlist2.get(n);

						// join expr now is single
//						ArrayList<String> list2 = FromItem
//								.generateTwoTabJoinExpr(
//										tab[strtab.charAt(m) - 'a'],
//										tab[strtab.charAt(m + 1) - 'a']);
						//当前只生成相邻的表的join表达式
						Table [] tables2 =new Table[2];
						//no +i will cause Cartesian product
						//tables2[0]=tables[strtab.charAt(m)-'a'];
						//tables2[1]=tables[strtab.charAt(m+1)-'a'];
						tables2[0]=tables[i+strtab.charAt(m)-'a'];
						tables2[1]=tables[i+strtab.charAt(m+1)-'a'];
						ArrayList<String> list2 = FromItem.generateSingleLink(tables2);
						
						// 缩减规模，剪枝,由于全隐式join已经枚举了各种可能,所以处理带有显示join的隐式部分可以不用完全排列组合
						list2=RandSelect.rangSelect(list2, 2);
						
						for (int l = 0; l < list2.size(); l++) {
							tmplist2.add(fi + list2.get(l));
						
						}
					}
					tlist2 = tmplist2;
				}
				list.addAll(tlist2);
			}

		}

		return list;
	}

	
	//生成from字句
	public static ArrayList<String> generateForm(Table[] tables) {

		ArrayList<String> list = new ArrayList<String>();

		list.addAll(generateImplicit(tables));
		list.addAll(generateWithExplicit(tables));

		return list;
	}

}
