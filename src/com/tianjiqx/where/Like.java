package com.tianjiqx.where;

import java.util.ArrayList;

import com.tianjiqx.table.Column;
import com.tianjiqx.table.Table;

//#################################
//# 模块说明：
//# 功能：生成like表达式
//# 作者：quxing
//# email: tianjiqx@126.com
//#################################
public class Like implements Expr {

	public static ArrayList<String> generateLike(Table table) {

		ArrayList<String> list = new ArrayList<String>();

		for (int i = 0; i < table.whereCols.length; i++) {
			String str = "";
			switch (table.whereCols[i].type) {

			case Column.CVARCHAR:
				for (int j = 0; j < const_string.length; j++) {
					str = table.aliasName + "." + table.whereCols[i].colName + " like '"
							+ const_string[j].substring(0, const_string[j].length() - 1) + "%'";
					list.add(str);
				}
				str = table.aliasName + "." + table.whereCols[i].colName + " like '"
						+ not_const_string.substring(0, not_const_string.length() - 1) + "'";
				list.add(str);
				break;

			default:
				break;
			}

		}
		System.out.println("generateLike "+table.tableName +" sql num="+list.size());
		return list;
	}
}
