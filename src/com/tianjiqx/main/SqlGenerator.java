package com.tianjiqx.main;

import java.util.ArrayList;

import com.tianjiqx.From.From;
import com.tianjiqx.select.Select;
import com.tianjiqx.table.Column;
import com.tianjiqx.table.Table;
import com.tianjiqx.util.RandSelect;
import com.tianjiqx.util.SelectGroupOrderRel;
import com.tianjiqx.util.SqlWriter;
import com.tianjiqx.util.StringCombine;
import com.tianjiqx.where.Groupby;
import com.tianjiqx.where.Limit;
import com.tianjiqx.where.Orderby;
import com.tianjiqx.where.Where;

//#################################
//# 程序说明：
//# 功能：该程序用于自动化生成一系列的sql测试集，用于自动化测试
//# 作者：quxing
//# email: tianjiqx@126.com
//#################################

public class SqlGenerator {

	public static Column[] createColums(int[] col_inexs, int num) {

		Column[] cols = new Column[7];
		cols[0] = new Column(1, "id");
		cols[1] = new Column(2, "bool_col");
		cols[2] = new Column(3, "double_col");
		cols[3] = new Column(4, "varchar_col");
		cols[4] = new Column(5, "date_col");
		cols[5] = new Column(6, "time_col");
		cols[6] = new Column(7, "decimal_col");

		Column[] result = new Column[num];
		for (int i = 0; i < num; i++) {
			result[i] = cols[col_inexs[i]];
		}
		return result;
	}

	public static Table createTable(String tableName, int[] col_inexs,
			int[] join_col_indexs, int[] where_col_indexs,
			int[] groupby_col_indexs, int[] orderby_col_indexs) {
		Table tab = null;
		Column[] columns = createColums(col_inexs, col_inexs.length);
		Column[] joinColumns = createColums(join_col_indexs,
				join_col_indexs.length);
		Column[] whereColumns = createColums(where_col_indexs,
				where_col_indexs.length);
		Column[] groupbyColumns = createColums(groupby_col_indexs,
				groupby_col_indexs.length);
		Column[] orderbyColumns = createColums(orderby_col_indexs,
				orderby_col_indexs.length);

		tab = new Table(tableName, tableName, columns, joinColumns,
				whereColumns, groupbyColumns, orderbyColumns);

		return tab;
	}

	public static Table[] init(int n) {
		Table[] tables = new Table[n];

		int[] cols = { 0, 1, 2, 3, 4, 5, 6 };
		int[] joinCols = { 0, 3, 6 };
		int[] whereCols = { 0, 1, 3, 4, 6 };
		int[] groupbyCols = { 0, 3, 4, 6 };
		int[] orderbyCols = { 0, 3, 4, 6 };
		for (int i = 0; i < n; i++) {
			tables[i] = createTable("t" + (i + 1), cols, joinCols, whereCols,
					groupbyCols, orderbyCols);
		}
		// tables[0] = createTable("t1", cols, joinCols, whereCols, groupbyCols,
		// orderbyCols);
		// tables[1] = createTable("t2", cols, joinCols, whereCols, groupbyCols,
		// orderbyCols);
		// tables[2]= createTable("t3",cols,joinCols,whereCols, groupbyCols,
		// orderbyCols);
		// tables[3]= createTable("t4",cols,joinCols,whereCols);

		return tables;

	}
	

	public static void test() {
		Table[] tables = init(3);

		// 生成select 子句
		ArrayList<String> selectList = Select.generateSelect(tables);

		for (int i = 0; i < selectList.size(); i++) {
			System.out.println(selectList.get(i));
		}
		System.out.println("generate select sql num=" + selectList.size());

		// 生成from子句
		ArrayList<String> fromList = From.generateForm(tables);

		for (int i = 0; i < fromList.size(); i++) {
			System.out.println(fromList.get(i));
		}
		System.out.println("generate from sql num=" + fromList.size());

		// 生成 where子句

		ArrayList<String> whereList = Where.generateWhere(tables);

		for (int i = 0; i < whereList.size(); i++) {
			System.out.println(whereList.get(i));
		}
		System.out.println("generate where expr sql num=" + whereList.size());

		System.out.println("sql count="
				+ (selectList.size() * fromList.size() * whereList.size()));

		// 生成group by子句
		ArrayList<String> groupbyList = Groupby.generateGroupby(tables);
		for (int i = 0; i < groupbyList.size(); i++) {
			System.out.println(groupbyList.get(i));
		}
		System.out.println("generate group by expr sql num="
				+ groupbyList.size());

		// order by
		ArrayList<String> orderbyList = Orderby.generateOrderby(tables);
		for (int i = 0; i < orderbyList.size(); i++) {
			System.out.println(orderbyList.get(i));
		}
		System.out.println("generate order by expr sql num="
				+ orderbyList.size());

		// limit

		ArrayList<String> limitList = Limit.generateLimit();
		for (int i = 0; i < limitList.size(); i++) {
			System.out.println(limitList.get(i));
		}
		System.out.println("generate limit expr sql num=" + limitList.size());
	}

	public static void printTable() {
		Table[] tables = init(1);

		for (int i = 0; i < tables.length; i++) {
			tables[i].printCreate();
		}

	}

	public static void insertTable() {
		Table[] tables = init(5);

		for (int i = 0; i < tables.length; i++) {

			tables[i].insertRow((i+1)*100);
		}

	}

	// simple sql
	public static void produce1() {

		Table[] tables = init(1);

		// 生成别名
		From.generateAlias(tables);

		// 生成select 子句
		ArrayList<String> selectList = Select.generateSelect(tables);

		// for (int i = 0; i < selectList.size(); i++) {
		// System.out.println(selectList.get(i));
		// }
		System.out.println("generate select sql num=" + selectList.size());

		// 生成from子句
		ArrayList<String> fromList = From.generateForm(tables);

		// for (int i = 0; i < fromList.size(); i++) {
		// System.out.println(fromList.get(i));
		// }
		System.out.println("generate from sql num=" + fromList.size());

		// 生成 where子句
		int max = 1000;
		ArrayList<String> whereList = Where.generateWhere(tables);
		whereList = RandSelect.rangSelect(whereList, max);

		// for (int i = 0; i < whereList.size(); i++) {
		// //System.out.println(whereList.get(i));
		// }
		System.out.println("generate where expr sql num=" + whereList.size());

		String sql = "";
		ArrayList<String> sqlList = new ArrayList<String>();
		for (int i = 0; i < selectList.size(); i++) {
			for (int j = 0; j < fromList.size(); j++) {
				for (int k = 0; k < whereList.size(); k++) {
					sql = selectList.get(i) + fromList.get(j)
							+ whereList.get(k);
					sqlList.add(sql);
				}
			}
		}

		// for (int i=0;i<sqlList.size();i++)
		// {
		// System.out.println(sqlList.get(i));
		// }

		System.out.println("sql count=" + sqlList.size());
		String filename = "sample.sql";
		SqlWriter.writeFile(filename, sqlList);

	}

	// group by order by
	public static void produce2() {
		int max = 5;

		Table[] tables = init(1);

		// 生成别名
		From.generateAlias(tables);

		
		// for (int i = 0; i < selectList.size(); i++) {

		// System.out.println(selectList.get(i));
		// }
		//System.out.println("generate select sql num=" + selectList.size());

		// 生成from子句
		ArrayList<String> fromList = From.generateForm(tables);
		max = 5;
		fromList = RandSelect.rangSelect(fromList, max);

		// for (int i = 0; i < fromList.size(); i++) {
		// System.out.println(fromList.get(i));
		// }
		System.out.println("generate from sql num=" + fromList.size());

		// 生成 where子句

		// 生成 where子句
		// int max=30;
		ArrayList<String> whereList = Where.generateWhere(tables);
		// whereList=RandSelect.rangSelect(whereList, max);

		// for (int i = 0; i < whereList.size(); i++) {
		// //System.out.println(whereList.get(i));
		// }

		max = 5;
		whereList = RandSelect.rangSelect(whereList, max);
		System.out.println("generate where expr sql num=" + whereList.size());

		// System.out.println("sql count=" + (selectList.size() *
		// fromList.size() * whereList.size()));

		// 生成group by子句
		ArrayList<String> groupbyList = Groupby.generateGroupby(tables);
		// for (int i = 0; i < groupbyList.size(); i++) {
		// System.out.println(groupbyList.get(i));
		// }
		System.out.println("generate group by expr sql num="
				+ groupbyList.size());
		
		
		ArrayList<SelectGroupOrderRel> sgoList=new ArrayList<SelectGroupOrderRel>();
		for (int i=0;i<groupbyList.size();i++)
		{
			SelectGroupOrderRel sgo = new SelectGroupOrderRel();
			sgo.grouby_stmt=groupbyList.get(i);
			sgoList.add(sgo);
		}
		System.out.println("group by sql num="+sgoList.size());
		
		// order by
		ArrayList<SelectGroupOrderRel> orderbyList = Orderby.generateOrderby2(sgoList,tables);
		
		System.out.println("order by sql num="+orderbyList.size());
		
		// 生成select 子句
		ArrayList<SelectGroupOrderRel> selectList = Select.generateSelect2(orderbyList,tables);
		//max = 10;
		//selectList = RandSelect.rangSelect(selectList, max);
		
		System.out.println("selectList count="+selectList.size());
		
		// // for (int i = 0; i < orderbyList.size(); i++) {
		// // System.out.println(orderbyList.get(i));
		// // }
//		System.out.println("generate order by expr sql num="
//				+ orderbyList.size());

		String sql = "";
		ArrayList<String> sqlList = new ArrayList<String>();
		for (int i = 0; i < selectList.size(); i++) {
			for (int j = 0; j < fromList.size(); j++) {
				for (int k = 0; k < whereList.size(); k++) {

					sql = "select "+selectList.get(i).selct_stmt +" "+ fromList.get(j)
							+ whereList.get(k) +" "+selectList.get(i).grouby_stmt+" ORDER BY "+selectList.get(i).orderby_stmt;
					sqlList.add(sql);
				}


			}
		}

		max = 15;
		//groupbyList = RandSelect.rangSelect(groupbyList, max);
		//orderbyList = RandSelect.rangSelect(orderbyList, max);

//		String sql = "";
//		ArrayList<String> sqlList = new ArrayList<String>();
//		for (int i = 0; i < selectList.size(); i++) {
//			for (int j = 0; j < fromList.size(); j++) {
//				for (int k = 0; k < whereList.size(); k++) {
//					for (int l = 0; l < groupbyList.size(); l++) {
//						for (int m = 0; m < orderbyList.size(); m++) {
//							sql = selectList.get(i) + fromList.get(j)
//									+ whereList.get(k) + groupbyList.get(l)
//									+ orderbyList.get(m);
//							sqlList.add(sql);
//						}
//
//					}
//
//				}
//			}
//		}

		// for (int i=0;i<sqlList.size();i++)
		// {
		// System.out.println(sqlList.get(i));
		// }

		System.out.println("sql count=" + sqlList.size());
		String filename = "group_orderby.sql";
		SqlWriter.writeFile(filename, sqlList);

	}
	
		
	// join sql
	public static void produce3() {

		Table[] tables = init(3);
		int max = 100;
		
		StringCombine.maxOneceCobine=20;
		StringCombine.maxOneceCobine2=15;
		
		// 生成别名
		From.generateAlias(tables);

		// 生成select 子句
		ArrayList<String> selectList = Select.generateSelect(tables);
		max = 20;
		selectList=RandSelect.rangSelect(selectList, max);

		// for (int i = 0; i < selectList.size(); i++) {
		// System.out.println(selectList.get(i));
		// }
		System.out.println("generate select sql num=" + selectList.size());

		// 生成from子句
		ArrayList<String> fromList = From.generateForm(tables);

		// for (int i = 0; i < fromList.size(); i++) {
		// System.out.println(fromList.get(i));
		// }
		System.out.println("generate from sql num=" + fromList.size());
		max = fromList.size()/tables.length;
		//fromList=RandSelect.rangSelect(fromList, max);
		fromList=RandSelect.deltSelect(fromList, max);
		// 生成 where子句
		max=50;
		ArrayList<String> whereList = Where.generateWhere(tables);
		whereList = RandSelect.rangSelect(whereList, max);

//		 for (int i = 0; i < whereList.size(); i++) {
//		 //System.out.println(whereList.get(i));
//		 }
		System.out.println("generate where expr sql num=" + whereList.size());

		String sql = "";
		String filename = "3join.sql";
		//清空文件内容
		SqlWriter.clear(filename); 
		ArrayList<String> sqlList = new ArrayList<String>();
		long count=0;
		for (int i = 0; i < selectList.size(); i++) {
			for (int j = 0; j < fromList.size(); j++) {
				for (int k = 0; k < whereList.size(); k++) {
					sql = selectList.get(i) + fromList.get(j)
							+ whereList.get(k);
					count++;
					sqlList.add(sql);
					
					if (sqlList.size()==100000)
					{
						SqlWriter.appendWriteFile(filename, sqlList);
						sqlList.clear();
					}
					
					
				}
			}
		}

		// for (int i=0;i<sqlList.size();i++)
		// {
		// System.out.println(sqlList.get(i));
		// }

		System.out.println("sql count=" + count);
		SqlWriter.writeFile(filename, sqlList);

	}

	// join sql
	public static void produce4() {

		Table[] tables = init(4);
		int max = 100;
		
		
		StringCombine.maxOneceCobine=15;
		StringCombine.maxOneceCobine2=10;
		// 生成别名
		From.generateAlias(tables);

		// 生成select 子句
		ArrayList<String> selectList = Select.generateSelect(tables);
		max = 10;
		selectList=RandSelect.rangSelect(selectList, max);

		// for (int i = 0; i < selectList.size(); i++) {
		// System.out.println(selectList.get(i));
		// }
		System.out.println("generate select sql num=" + selectList.size());

		// 生成from子句
		ArrayList<String> fromList = From.generateForm(tables);

		// for (int i = 0; i < fromList.size(); i++) {
		// System.out.println(fromList.get(i));
		// }
		System.out.println("generate from sql num=" + fromList.size());
		max = fromList.size()/tables.length;
		//fromList=RandSelect.rangSelect(fromList, max);
		fromList=RandSelect.deltSelect(fromList, max);
		// 生成 where子句
		max=20;
		ArrayList<String> whereList = Where.generateWhere(tables);
		whereList = RandSelect.rangSelect(whereList, max);

//		 for (int i = 0; i < whereList.size(); i++) {
//		 //System.out.println(whereList.get(i));
//		 }
		System.out.println("generate where expr sql num=" + whereList.size());

		String sql = "";
		String filename = "4join.sql";
		//清空文件内容
		SqlWriter.clear(filename); 
		ArrayList<String> sqlList = new ArrayList<String>();
		long count=0;
		for (int i = 0; i < selectList.size(); i++) {
			for (int j = 0; j < fromList.size(); j++) {
				for (int k = 0; k < whereList.size(); k++) {
					sql = selectList.get(i) + fromList.get(j)
							+ whereList.get(k);
					count++;
					sqlList.add(sql);
					
					if (sqlList.size()==100000)
					{
						SqlWriter.appendWriteFile(filename, sqlList);
						sqlList.clear();
					}
					
					
				}
			}
		}

		// for (int i=0;i<sqlList.size();i++)
		// {
		// System.out.println(sqlList.get(i));
		// }

		System.out.println("sql count=" + count);
		
		SqlWriter.appendWriteFile(filename, sqlList);

	}
	
	
	// 3表 group order by
	public static void produce5() {
		int max = 5;

		Table[] tables = init(3);

		// 生成别名
		From.generateAlias(tables);

		// for (int i = 0; i < selectList.size(); i++) {

		// System.out.println(selectList.get(i));
		// }
		//System.out.println("generate select sql num=" + selectList.size());

		// 生成from子句
		ArrayList<String> fromList = From.generateForm(tables);
		max = 5;
		fromList = RandSelect.rangSelect(fromList, max);

		// for (int i = 0; i < fromList.size(); i++) {
		// System.out.println(fromList.get(i));
		// }
		System.out.println("generate from sql num=" + fromList.size());

		// 生成 where子句

		// 生成 where子句
		// int max=30;
		ArrayList<String> whereList = Where.generateWhere(tables);
		// whereList=RandSelect.rangSelect(whereList, max);

		// for (int i = 0; i < whereList.size(); i++) {
		// //System.out.println(whereList.get(i));
		// }

		max = 5;
		whereList = RandSelect.rangSelect(whereList, max);
		System.out.println("generate where expr sql num=" + whereList.size());

		// System.out.println("sql count=" + (selectList.size() *
		// fromList.size() * whereList.size()));

		// 生成group by子句
		ArrayList<String> groupbyList = Groupby.generateGroupby(tables);
		// for (int i = 0; i < groupbyList.size(); i++) {
		// System.out.println(groupbyList.get(i));
		// }
		System.out.println("generate group by expr sql num="
				+ groupbyList.size());
		
		max=10000;
		groupbyList=RandSelect.deltSelect(groupbyList,max);
		
		ArrayList<SelectGroupOrderRel> sgoList=new ArrayList<SelectGroupOrderRel>();
		for (int i=0;i<groupbyList.size();i++)
		{
			SelectGroupOrderRel sgo = new SelectGroupOrderRel();
			sgo.grouby_stmt=groupbyList.get(i);
			sgoList.add(sgo);
		}
		System.out.println("group by sql num="+sgoList.size());
		
		// order by
		ArrayList<SelectGroupOrderRel> orderbyList = Orderby.generateOrderby2(sgoList,tables);
		
		System.out.println("order by sql num="+orderbyList.size());
		
		max = 10000;
		orderbyList = RandSelect.rangSelect2(orderbyList, max);
		
		// 生成select 子句
		ArrayList<SelectGroupOrderRel> selectList = Select.generateSelect2(orderbyList,tables);
		//max = 10;
		//selectList = RandSelect.rangSelect(selectList, max);
		//orderbyList.clear();
		groupbyList.clear();
		
		System.out.println("selectList count="+selectList.size());
		
		// // for (int i = 0; i < orderbyList.size(); i++) {
		// // System.out.println(orderbyList.get(i));
		// // }
//		System.out.println("generate order by expr sql num="
//				+ orderbyList.size());

		
		String filename = "group_orderby.sql";
		//清空文件内容
		SqlWriter.clear(filename); 
		
		String sql = "";
		int count=0;
		ArrayList<String> sqlList = new ArrayList<String>();
		for (int i = 0; i < selectList.size(); i++) {
			for (int j = 0; j < fromList.size(); j++) {
				for (int k = 0; k < whereList.size(); k++) {

					count++;
					sql = "select "+selectList.get(i).selct_stmt +" "+ fromList.get(j)
							+ whereList.get(k) +" "+selectList.get(i).grouby_stmt+" ORDER BY "+selectList.get(i).orderby_stmt;
					sqlList.add(sql);
					if (count%1000==0)
					{
						SqlWriter.appendWriteFile(filename, sqlList);
						sqlList.clear();
					}
				}
			}
		}

		selectList.clear();
		fromList.clear();
		whereList.clear();
		
		System.out.println("sql count=" + sqlList.size());

		SqlWriter.appendWriteFile(filename, sqlList);
				
		
		SqlWriter.appendWriteFile(filename, sqlList);

	}
	
	//order by
	public static void produce6() {
		int max = 5;

		Table[] tables = init(1);

		// 生成别名
		From.generateAlias(tables);

		
		// for (int i = 0; i < selectList.size(); i++) {

		// System.out.println(selectList.get(i));
		// }
		//System.out.println("generate select sql num=" + selectList.size());

		// 生成from子句
		ArrayList<String> fromList = From.generateForm(tables);
		max = 5;
		fromList = RandSelect.rangSelect(fromList, max);

		// for (int i = 0; i < fromList.size(); i++) {
		// System.out.println(fromList.get(i));
		// }
		System.out.println("generate from sql num=" + fromList.size());

		// 生成 where子句

		// 生成 where子句
		// int max=30;
		ArrayList<String> whereList = Where.generateWhere(tables);
		// whereList=RandSelect.rangSelect(whereList, max);

		// for (int i = 0; i < whereList.size(); i++) {
		// //System.out.println(whereList.get(i));
		// }

		max = 5;
		whereList = RandSelect.rangSelect(whereList, max);
		System.out.println("generate where expr sql num=" + whereList.size());

		// System.out.println("sql count=" + (selectList.size() *
		// fromList.size() * whereList.size()));

		// 生成group by子句
		ArrayList<String> groupbyList = Groupby.generateGroupby(tables);
		// for (int i = 0; i < groupbyList.size(); i++) {
		// System.out.println(groupbyList.get(i));
		// }
		System.out.println("generate group by expr sql num="
				+ groupbyList.size());
		
		
		ArrayList<SelectGroupOrderRel> sgoList=new ArrayList<SelectGroupOrderRel>();
		for (int i=0;i<groupbyList.size();i++)
		{
			SelectGroupOrderRel sgo = new SelectGroupOrderRel();
			sgo.grouby_stmt=groupbyList.get(i);
			sgoList.add(sgo);
		}
		System.out.println("group by sql num="+sgoList.size());
		
		// order by
		ArrayList<SelectGroupOrderRel> orderbyList = Orderby.generateOrderby2(sgoList,tables);
		
		System.out.println("order by sql num="+orderbyList.size());
		
		// 生成select 子句
		ArrayList<SelectGroupOrderRel> selectList = Select.generateSelect2(orderbyList,tables);
		//max = 10;
		//selectList = RandSelect.rangSelect(selectList, max);
		
		System.out.println("selectList count="+selectList.size());
		
		// // for (int i = 0; i < orderbyList.size(); i++) {
		// // System.out.println(orderbyList.get(i));
		// // }
//		System.out.println("generate order by expr sql num="
//				+ orderbyList.size());

		String sql = "";
		ArrayList<String> sqlList = new ArrayList<String>();
		for (int i = 0; i < selectList.size(); i++) {
			for (int j = 0; j < fromList.size(); j++) {
				for (int k = 0; k < whereList.size(); k++) {

					sql = "select "+selectList.get(i).selct_stmt +" "+ fromList.get(j)
							+ whereList.get(k) +" "+selectList.get(i).grouby_stmt+" ORDER BY "+selectList.get(i).orderby_stmt;
					sqlList.add(sql);
				}


			}
		}

		max = 15;
		//groupbyList = RandSelect.rangSelect(groupbyList, max);
		//orderbyList = RandSelect.rangSelect(orderbyList, max);

//		String sql = "";
//		ArrayList<String> sqlList = new ArrayList<String>();
//		for (int i = 0; i < selectList.size(); i++) {
//			for (int j = 0; j < fromList.size(); j++) {
//				for (int k = 0; k < whereList.size(); k++) {
//					for (int l = 0; l < groupbyList.size(); l++) {
//						for (int m = 0; m < orderbyList.size(); m++) {
//							sql = selectList.get(i) + fromList.get(j)
//									+ whereList.get(k) + groupbyList.get(l)
//									+ orderbyList.get(m);
//							sqlList.add(sql);
//						}
//
//					}
//
//				}
//			}
//		}

		// for (int i=0;i<sqlList.size();i++)
		// {
		// System.out.println(sqlList.get(i));
		// }

		System.out.println("sql count=" + sqlList.size());
		String filename = "group_orderby.sql";
		SqlWriter.writeFile(filename, sqlList);

	}
	
	
	
	//测试join col key生成join连接
	public static void produce7() {

		Table[] tables = init(3);
		int max = 100;
		
		StringCombine.maxOneceCobine=20;
		StringCombine.maxOneceCobine2=15;
		
		String [] joinColKeys0={"key1a","key3a","key6b"};
		tables[0].setJoinColKey(joinColKeys0);
		String [] joinColKeys1={"key1a","key3a","key6a"};
		tables[1].setJoinColKey(joinColKeys1);
		String [] joinColKeys2={"key1a","key3b","key6b"};
		tables[2].setJoinColKey(joinColKeys2);
		
		// 生成别名
		From.generateAlias(tables);

		// 生成select 子句
		ArrayList<String> selectList = Select.generateSelect(tables);
		max = 20;
		selectList=RandSelect.rangSelect(selectList, max);

		// for (int i = 0; i < selectList.size(); i++) {
		// System.out.println(selectList.get(i));
		// }
		System.out.println("generate select sql num=" + selectList.size());

		// 生成from子句
		ArrayList<String> fromList = From.generateForm(tables);

		// for (int i = 0; i < fromList.size(); i++) {
		// System.out.println(fromList.get(i));
		// }
		System.out.println("generate from sql num=" + fromList.size());
		max = fromList.size()/tables.length;
		//fromList=RandSelect.rangSelect(fromList, max);
		fromList=RandSelect.deltSelect(fromList, max);
		// 生成 where子句
		max=50;
		ArrayList<String> whereList = Where.generateWhere(tables);
		whereList = RandSelect.rangSelect(whereList, max);

//		 for (int i = 0; i < whereList.size(); i++) {
//		 //System.out.println(whereList.get(i));
//		 }
		System.out.println("generate where expr sql num=" + whereList.size());

		String sql = "";
		String filename = "3join.sql";
		//清空文件内容
		SqlWriter.clear(filename); 
		ArrayList<String> sqlList = new ArrayList<String>();
		long count=0;
		for (int i = 0; i < selectList.size(); i++) {
			for (int j = 0; j < fromList.size(); j++) {
				for (int k = 0; k < whereList.size(); k++) {
					sql = selectList.get(i) + fromList.get(j)
							+ whereList.get(k);
					count++;
					sqlList.add(sql);
					
					if (sqlList.size()==100000)
					{
						SqlWriter.appendWriteFile(filename, sqlList);
						sqlList.clear();
					}
					
					
				}
			}
		}

		// for (int i=0;i<sqlList.size();i++)
		// {
		// System.out.println(sqlList.get(i));
		// }

		System.out.println("sql count=" + count);
		SqlWriter.writeFile(filename, sqlList);

	}

	
	// 生成sql表达式
	public static ArrayList<String> generateSql()
	{
		Table[] tables = init(1);
		int max = 100;
		
		StringCombine.maxOneceCobine=20;
		StringCombine.maxOneceCobine2=15;
		
		// 生成别名
		From.generateAlias(tables);

		// 生成select 子句
		ArrayList<String> selectList = Select.generateSelect(tables);
		max = 20;
		selectList=RandSelect.rangSelect(selectList, max);

		System.out.println("generate select sql num=" + selectList.size());

		// 生成from子句
		ArrayList<String> fromList = From.generateForm(tables);

		System.out.println("generate from sql num=" + fromList.size());
		max = fromList.size()/tables.length;
		//fromList=RandSelect.rangSelect(fromList, max);
		fromList=RandSelect.deltSelect(fromList, max);
		// 生成 where子句
		max=50;
		ArrayList<String> whereList = Where.generateWhere(tables);
		whereList = RandSelect.rangSelect(whereList, max);


		System.out.println("generate where expr sql num=" + whereList.size());

		String sql = "";
		ArrayList<String> sqlList = new ArrayList<String>();
		long count=0;
		for (int i = 0; i < selectList.size(); i++) {
			for (int j = 0; j < fromList.size(); j++) {
				for (int k = 0; k < whereList.size(); k++) {
					sql = selectList.get(i) + fromList.get(j)
							+ whereList.get(k);
					count++;
					sqlList.add(sql);
				}
			}
		}
		System.out.println("generateSql sql num = "+count);
		return sqlList;
	}
	
	
    // 二层子查询 出现在from 子句
	public static void produce8()
	{
		
		Table[] tables = init(3);
		int max = 100;
		
		StringCombine.maxOneceCobine=20;
		StringCombine.maxOneceCobine2=15;
		
		// 生成别名
		From.generateAlias(tables);

		// 生成select 子句
		ArrayList<String> selectList = Select.generateSelect(tables);
		max = 20;
		selectList=RandSelect.rangSelect(selectList, max);

		// for (int i = 0; i < selectList.size(); i++) {
		// System.out.println(selectList.get(i));
		// }
		System.out.println("generate select sql num=" + selectList.size());

		// 生成from子句
		ArrayList<String> fromList = From.generateForm(tables);

		// for (int i = 0; i < fromList.size(); i++) {
		// System.out.println(fromList.get(i));
		// }
		System.out.println("generate from sql num=" + fromList.size());
		max = fromList.size()/tables.length;
		//fromList=RandSelect.rangSelect(fromList, max);
		fromList=RandSelect.deltSelect(fromList, max);
		// 生成 where子句
		max=50;
		ArrayList<String> whereList = Where.generateWhere(tables);
		whereList = RandSelect.rangSelect(whereList, max);

//		 for (int i = 0; i < whereList.size(); i++) {
//		 //System.out.println(whereList.get(i));
//		 }
		System.out.println("generate where expr sql num=" + whereList.size());

		String sql = "";
		String filename = "subqueryfrom.sql";
		//清空文件内容
		SqlWriter.clear(filename); 
		ArrayList<String> sqlList = new ArrayList<String>();
		long count=0;
		for (int i = 0; i < selectList.size(); i++) {
			for (int j = 0; j < fromList.size(); j++) {
				for (int k = 0; k < whereList.size(); k++) {
					sql = selectList.get(i) + fromList.get(j)
							+ whereList.get(k);
					count++;
					sqlList.add(sql);
					
					if (sqlList.size()==100000)
					{
						SqlWriter.appendWriteFile(filename, sqlList);
						sqlList.clear();
					}
					
					
				}
			}
		}

		// for (int i=0;i<sqlList.size();i++)
		// {
		// System.out.println(sqlList.get(i));
		// }

		System.out.println("sql count=" + count);
		SqlWriter.writeFile(filename, sqlList);
		
	}
	
	
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		// test();

		//produce1();
		 //produce2();

		//produce5();
		
		//produce3();
		//produce4();
		
		
		produce7();
		
		
		// printTable();
		// insertTable();

	}

}
