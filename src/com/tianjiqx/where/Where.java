package com.tianjiqx.where;

import java.util.ArrayList;

import javax.naming.InitialContext;

import com.tianjiqx.table.Table;

//#################################
//# ģ��˵����
//# ���ܣ�����where���ʽ
//# ���ߣ�quxing
//# email: tianjiqx@126.com
//#################################

public class Where {

	public static ArrayList<String> generateWhere(Table [] tables) {
		
		ArrayList<String> list= new ArrayList<String>();
		
		
		for (int i= 0; i < tables.length;i++)
		{
			ArrayList<String> eqList=new ArrayList<String>();
			ArrayList<String> ltList = new ArrayList<String>();
			ArrayList<String> inList= new ArrayList<String>();
			
			
			ArrayList<String> andeqList=new ArrayList<String>();
			ArrayList<String> andltList = new ArrayList<String>();
			ArrayList<String> andinList= new ArrayList<String>();
			
			//eq
			eqList.addAll(Eq.generateEq(tables[i]));
			//System.out.println("eq count="+eqList.size());
			//lt
			ltList.addAll(LT.generateLt(tables[i]));
			//System.out.println("lt count="+ltList.size());
			//in
			inList.addAll(In.generateIn(tables[i]));
			//System.out.println("in count="+inList.size());
			//like
			
			//btw
			
			//and 
			andeqList.addAll(eqList);
			andltList.addAll(ltList);
			andinList.addAll(inList);
			
			andeqList.addAll(And.generateTwoAnd(eqList));
			//System.out.println("andeq count="+andeqList.size());
			andltList.addAll(And.generateTwoAnd(ltList));
			//System.out.println("andlt count="+andltList.size());
			andinList.addAll(And.generateTwoAnd(inList));
			//System.out.println("andin count="+andinList.size());
			
			
			//or
			eqList.addAll(OR.generateOr(andeqList));
			//System.out.println("eq count="+eqList.size());
			ltList.addAll(OR.generateOr(andltList));
			//System.out.println("lt count="+ltList.size());
			inList.addAll(OR.generateOr(andinList));
			//System.out.println("in count="+inList.size());
			
			list.addAll(eqList);
			list.addAll(ltList);
			list.addAll(inList);
			
		}
		
		
		
		return list;
	}
	
	
}
