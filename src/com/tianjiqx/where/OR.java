package com.tianjiqx.where;

import java.util.ArrayList;

import javax.naming.InitialContext;

import org.omg.PortableServer.ID_ASSIGNMENT_POLICY_ID;

import com.tianjiqx.util.RandSelect;
import com.tianjiqx.util.StringCombine;

//#################################
//# ģ��˵����
//# ���ܣ�����or���ʽ
//# ���ߣ�quxing
//# email: tianjiqx@126.com
//#################################
public class OR {

	public static ArrayList<String> generateOr(ArrayList<String> expr) {

		ArrayList<String> list = new ArrayList<String>();

		list.addAll(generateTwoOr(expr));
		list.addAll(generateThreeOr(expr));
		
		//�����ȡ
		int max=500;
		if (list.size() > max)
		{
			list=RandSelect.rangSelect(list, max);
		}
		
		return list;
	}

	
	//��n����ȡ3��
	public static ArrayList<String> generateThreeOr(ArrayList<String> expr) {
		ArrayList<String> list = new ArrayList<String>();
		//System.out.println("or3 expr size="+expr.size());
		if (expr.size() <3)
		{
			return list;
		}
		//System.out.println(expr);

		char[] chs = new char[expr.size()];

		for (int i = 0; i < expr.size(); i++) {
			chs[i] = (char) ('a' + i);
		}
		ArrayList<String> list1 = StringCombine.threeConbine(chs);
		String str = "", tmp = "";
		for (int i = 0; i < list1.size(); i++) {
			str = list1.get(i);
			tmp="( ";
			for (int j = 0; j < str.length() - 1; j++) {
				tmp += expr.get(str.charAt(j) - 'a') + " or ";
			}
			tmp += expr.get(str.charAt(str.length() - 1) - 'a')+" )";
			list.add(tmp);
		}
		//System.out.println("or three size = "+list.size());
		
		//�����ȡ
		int max=500;
		if (list.size() > max)
		{
			list=RandSelect.rangSelect(list, max);
		}
		
		return list;

	}
	
	
	// ��n����ȡ2��
	public static ArrayList<String> generateTwoOr(ArrayList<String> expr) {

		ArrayList<String> list = new ArrayList<String>();
		
		if (expr.size() <2)
		{
			return list;
		}
		
		char[] chs = new char[expr.size()];

		for (int i = 0; i < expr.size(); i++) {
			chs[i] = (char) ('a' + i);
		}
		ArrayList<String> list1 = StringCombine.twoConbine(chs);
		String str = "", tmp = "( ";
		for (int i = 0; i < list1.size(); i++) {
			tmp="( ";
			str = list1.get(i);
			for (int j = 0; j < str.length() - 1; j++) {
				tmp += expr.get(str.charAt(j) - 'a') + " or ";
			}
			tmp += expr.get(str.charAt(str.length() - 1) - 'a')+" )";
			list.add(tmp);
		}

		//System.out.println("or two size = "+list.size());
		
		//�����ȡ
		int max=500;
		if (list.size() > max)
		{
			list=RandSelect.rangSelect(list, max);
		}
		
		
		return list;
	}

}
