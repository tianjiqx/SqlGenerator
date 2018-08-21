package com.tianjiqx.where;

import java.util.ArrayList;

import javax.naming.InitialContext;

import org.omg.PortableServer.ID_ASSIGNMENT_POLICY_ID;

import com.tianjiqx.util.RandSelect;
import com.tianjiqx.util.StringCombine;

//#################################
//# 模块说明：
//# 功能：生成or表达式
//# 作者：quxing
//# email: tianjiqx@126.com
//#################################
public class OR {

	public static ArrayList<String> generateOr(ArrayList<String> expr) {

		ArrayList<String> list = new ArrayList<String>();

		list.addAll(generateTwoOr(expr));
		list.addAll(generateThreeOr(expr));
		
		//随机抽取
		int max=500;
		if (list.size() > max)
		{
			list=RandSelect.rangSelect(list, max);
		}
		
		System.out.println("generateOr sql num="+list.size());
		return list;
	}

	
	//从n个中取3个
	public static ArrayList<String> generateThreeOr(ArrayList<String> expr) {
		ArrayList<String> list = new ArrayList<String>();
		System.out.println("or3 expr size="+expr.size());
		if (expr.size() <3)
		{
			return list;
		}
		//System.out.println(expr);

//		char[] chs = new char[expr.size()];
//
//		for (int i = 0; i < expr.size(); i++) {
//			chs[i] = (char) ('a' + i);
//		}
//		ArrayList<String> list1 = StringCombine.threeConbine(chs);
//		String str = "", tmp = "";
//		for (int i = 0; i < list1.size(); i++) {
//			str = list1.get(i);
//			tmp="( ";
//			for (int j = 0; j < str.length() - 1; j++) {
//				tmp += expr.get(str.charAt(j) - 'a') + " or ";
//			}
//			tmp += expr.get(str.charAt(str.length() - 1) - 'a')+" )";
//			list.add(tmp);
//		}
		//System.out.println("or three size = "+list.size());
		
		
		
		ArrayList<String> tmp = StringCombine.ThreeStringConbine(expr, " or ");
				
		//随机抽取
		int max=500;
		if (tmp.size() > max)
		{
			tmp=RandSelect.rangSelect(tmp, max);
		}
		
		for (int i=0;i<tmp.size();i++)
		{
			list.add("( "+tmp.get(i)+" )");
		}
		
		System.out.println("generateThreeOr sql num="+list.size());
		return list;
	}
	
	
	// 从n个中取2个
	public static ArrayList<String> generateTwoOr(ArrayList<String> expr) {

		ArrayList<String> list = new ArrayList<String>();
		
		if (expr.size() <2)
		{
			return list;
		}
		
//		char[] chs = new char[expr.size()];
//
//		for (int i = 0; i < expr.size(); i++) {
//			chs[i] = (char) ('a' + i);
//		}
//		ArrayList<String> list1 = StringCombine.twoConbine(chs);
//		String str = "", tmp = "( ";
//		for (int i = 0; i < list1.size(); i++) {
//			tmp="( ";
//			str = list1.get(i);
//			for (int j = 0; j < str.length() - 1; j++) {
//				tmp += expr.get(str.charAt(j) - 'a') + " or ";
//			}
//			tmp += expr.get(str.charAt(str.length() - 1) - 'a')+" )";
//			list.add(tmp);
//		}
		
		
		ArrayList<String> tmp = StringCombine.ThreeStringConbine(expr, " or ");
		

		ArrayList<String> tmp2 = tmp;		
		//随机抽取
		int max=500;
		if (tmp.size() > max)
		{
			tmp=RandSelect.rangSelect(tmp, max);
		}

		for (int i=0;i<tmp.size();i++)
		{
			list.add("( "+tmp.get(i)+" )");
		}
		
		System.out.println("generateTwoOr sql num="+list.size());
		return list;
	}

}
