package com.tianjiqx.where;

import java.util.ArrayList;

import com.tianjiqx.table.Column;
import com.tianjiqx.table.Table;

//#################################
//# 模块说明：
//# 功能：生成小于表达式
//# 作者：quxing
//# email: tianjiqx@126.com
//#################################
public class LT implements  Expr {

	public static ArrayList<String> generateLt(Table table)
	{
		ArrayList<String> list= new ArrayList<String>();
		String str;
		for (int i =0; i< table.whereCols.length; i++ )
		{
			switch (table.whereCols[i].type) {
			case Column.CINT:
				for (int j=0;j<const_int.length;j++)
				{
					str=table.aliasName+"."+table.whereCols[i].colName+"<"+const_int[j];
					list.add(str);
				}
				str=table.aliasName+"."+table.whereCols[i].colName+"<"+not_const_int;
				list.add(str);
				break;
//			case Column.CBOOL:
//				for (int j=0;j<const_bool.length;j++)
//				{
//					str=table.aliasName+"."+table.whereCols[i].colName+"<"+const_bool[j];
//					list.add(str);
//				}
//				
//				break;
			case Column.CDOUBLE:
				for (int j=0;j<const_double.length;j++)
				{
					str=table.aliasName+"."+table.whereCols[i].colName+"<"+const_double[j];
					list.add(str);
				}
				str=table.aliasName+"."+table.whereCols[i].colName+"<"+not_const_double;
				list.add(str);
				break;
//			case Column.CVARCHAR:
//				for (int j=0;j<const_string.length;j++)
//				{
//					str=table.aliasName+"."+table.whereCols[i].colName+"<'"+const_string[j]+"'";
//					list.add(str);
//				}
//				str=table.aliasName+"."+table.whereCols[i].colName+"<'"+not_const_string+"'";
//				list.add(str);
//				break;
			case Column.CDATE:
				for (int j=0;j<const_date.length;j++)
				{
					str=table.aliasName+"."+table.whereCols[i].colName+"<'"+const_date[j]+"'";
					list.add(str);
				}
				str=table.aliasName+"."+table.whereCols[i].colName+"<'"+not_const_date+"'";
				list.add(str);
				break;
			case Column.CTIME:
				for (int j=0;j<const_time.length;j++)
				{
					str=table.aliasName+"."+table.whereCols[i].colName+"<'"+const_time[j]+"'";
					list.add(str);
				}
				str=table.aliasName+"."+table.whereCols[i].colName+"<'"+not_const_time+"'";
				list.add(str);
				break;
			case Column.CDECIMAL:
				for (int j=0;j<const_decimal.length;j++)
				{
					str=table.aliasName+"."+table.whereCols[i].colName+"<'"+const_decimal[j]+"'";;
					list.add(str);
				}
				str=table.aliasName+"."+table.whereCols[i].colName+"<'"+not_const_decimal+"'";;
				list.add(str);
				break;
			default:
				break;
			}
			
		}
		System.out.println("generateLt "+table.tableName +" sql num="+list.size());
		return list;
	}
	
}
