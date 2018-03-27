package com.tianjiqx.where;

import java.nio.DoubleBuffer;

public interface Expr {

	//存在的常量
	static int [] const_int = {1,2};
	static String [] const_bool={"true","false"};
	static double [] const_double={1.0,3.14};
	static String [] const_string = {"abc","def"};
	static String [] const_date={"2016-08-09","2017-09-10"};
	static String [] const_time={"08:22:09","20:09:10"};
	static String [] const_decimal={"77777","88888"};
	//不存在的常量
	static int  not_const_int = 99999;
	static double  not_const_double=44.4;
	static String  not_const_string = "def";
	static String  not_const_date="1994-10-10";
	static String  not_const_time="09:09:09";
	static String  not_const_decimal="99999";
	
	
}
