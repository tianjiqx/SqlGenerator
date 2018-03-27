package com.tianjiqx.where;

import java.util.ArrayList;

import com.tianjiqx.table.Column;
import com.tianjiqx.table.Table;

//#################################
//# 模块说明：
//# 功能：生成between and表达式
//# 作者：quxing
//# email: tianjiqx@126.com
//#################################
public class BTW implements Expr {

	public static ArrayList<String> generateLt(Table table) {
		ArrayList<String> list = new ArrayList<String>();
		String str;
		for (int i = 0; i < table.whereCols.length; i++) {
			switch (table.whereCols[i].type) {

			case Column.CDATE:
				str = table.aliasName + "." + table.whereCols[i].colName + "between '" + const_date[0] + "' and '"
						+ const_date[1] + "'";
				list.add(str);
				list.add(str);
				break;

			default:
				break;
			}
		}

		return list;
	}

}
