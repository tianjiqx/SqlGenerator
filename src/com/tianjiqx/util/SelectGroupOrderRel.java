package com.tianjiqx.util;


//#################################
//# 模块说明：
//# 功能：group 与 order by 、select 子句的关联 
//# 由于db2 不支持  order by  非group by 列（需聚集列的别名），以及 select 非group by 列
//# 作者：quxing
//# email: tianjiqx@126.com
//#################################

public class SelectGroupOrderRel {

	public String grouby_stmt;
	public String selct_stmt;
	public String orderby_stmt;
	
}
