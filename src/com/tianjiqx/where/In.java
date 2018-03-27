package com.tianjiqx.where;

import java.util.ArrayList;

import com.tianjiqx.table.Column;
import com.tianjiqx.table.Table;

//#################################
//# 模块说明：
//# 功能：生成in表达式
//# 作者：quxing
//# email: tianjiqx@126.com
//#################################
public class In  implements  Expr {

	public static ArrayList<String> generateIn(Table table)
	{
		ArrayList<String> list= new ArrayList<String>();
		
		for (int i =0; i< table.whereCols.length; i++ )
		{
			String str="";
			switch (table.whereCols[i].type) {
			case Column.CINT:
				for (int j=0;j<const_int.length;j++)
				{
					str +=  const_int[j]+',';
				}
				str=table.aliasName+"."+table.whereCols[i].colName+" in (" +str.substring(0, str.length()-1)+")";
				list.add(str);
				str=table.aliasName+"."+table.whereCols[i].colName+" in ("+not_const_int+")";
				list.add(str);
				break;
			case Column.CBOOL:
				for (int j=0;j<const_bool.length;j++)
				{
					str +=  const_bool[j]+',';
				}
				str=table.aliasName+"."+table.whereCols[i].colName+" in (" +str.substring(0, str.length()-1)+")";
				list.add(str);
				break;
			case Column.CDOUBLE:
				for (int j=0;j<const_double.length;j++)
				{
					str +=  const_double[j]+',';
				}
				str=table.aliasName+"."+table.whereCols[i].colName+" in (" +str.substring(0, str.length()-1)+")";
				list.add(str);
				str=table.aliasName+"."+table.whereCols[i].colName+" in ("+not_const_double+")";
				list.add(str);
				break;
			case Column.CVARCHAR:
				for (int j=0;j<const_string.length;j++)
				{
					str += "'"+ const_string[j]+"',";
				}
				str=table.aliasName+"."+table.whereCols[i].colName+" in (" +str.substring(0, str.length()-1)+")";
				list.add(str);
				str=table.aliasName+"."+table.whereCols[i].colName+" in ('"+not_const_string+"')";
				list.add(str);
				break;
			case Column.CDATE:
				for (int j=0;j<const_date.length;j++)
				{
					str += "'"+ const_date[j]+"',";
				}
				str=table.aliasName+"."+table.whereCols[i].colName+" in (" +str.substring(0, str.length()-1)+")";
				list.add(str);
				str=table.aliasName+"."+table.whereCols[i].colName+" in ('"+not_const_date+"')";
				list.add(str);
				break;
			case Column.CTIME:
				for (int j=0;j<const_time.length;j++)
				{
					str += "'"+ const_time[j]+"',";
				}
				str=table.aliasName+"."+table.whereCols[i].colName+" in (" +str.substring(0, str.length()-1)+")";
				list.add(str);
				str=table.aliasName+"."+table.whereCols[i].colName+" in ('"+not_const_time+"')";;
				list.add(str);
				break;
			case Column.CDECIMAL:
				for (int j=0;j<const_decimal.length;j++)
				{
					str += "'"+ const_decimal[j]+"',";
				}
				str=table.aliasName+"."+table.whereCols[i].colName+" in (" +str.substring(0, str.length()-1)+")";
				list.add(str);
				str=table.aliasName+"."+table.whereCols[i].colName+" in ('"+not_const_decimal+"')";
				list.add(str);
				break;
			default:
				break;
			}
			
		}
		
		return list;
	}
	
}
