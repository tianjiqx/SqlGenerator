package com.tianjiqx.table;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import com.tianjiqx.util.RandRow;
import com.tianjiqx.util.SqlWriter;

public class Table {

	public String tableName;
	public String aliasName;
	public Column[] cols;
	public Column[] joinCols;
	public Column[] whereCols;
	public Column[] groupbyCols;
	public Column[] orderbyCols;
	
	public Column [] requiredCols; // 必须project的列

	public Table(String tableName, String aliasName, Column[] cols,
			Column[] joinCols, Column[] whereCols) {
		super();
		this.tableName = tableName;
		this.aliasName = aliasName;
		this.cols = cols;
		this.joinCols = joinCols;
		this.whereCols = whereCols;
		this.groupbyCols = whereCols;
		this.orderbyCols = whereCols;
		this.requiredCols = new Column[0];
	}

	public Table(String tableName, String aliasName, Column[] cols,
			Column[] joinCols, Column[] whereCols, Column[] groupbyCols,
			Column[] orderbyCols) {
		super();
		this.tableName = tableName;
		this.aliasName = aliasName;
		this.cols = cols;
		this.joinCols = joinCols;
		this.whereCols = whereCols;
		this.groupbyCols = groupbyCols;
		this.orderbyCols = orderbyCols;
		this.requiredCols = new Column[0];
	}
	
	public void setJoinColKey(String [] joinColKeys)
	{
		for (int i=0;i<joinCols.length && i < joinColKeys.length;i++)
		{
			this.joinCols[i].joinColKey=joinColKeys[i];
		}
	}
	
	public void setRequiredCols(Column[] requiredCols)
	{
		this.requiredCols = requiredCols;
	}
	

	public void printCreate() {

		System.out.println("create table " + tableName + "(");
		System.out.println(cols[0].colName + " " + Column.TYPES[cols[0].type]
				+ " primary key");
		for (int i = 1; i < cols.length; i++) {
			System.out.println("," + cols[i].colName + " "
					+ Column.TYPES[cols[i].type]);
		}
		System.out.println(");");

	}

	public ArrayList<String> insertRow(int rows) {

		ArrayList<String> rowlist = new ArrayList<String>();
		
		rowlist.addAll(insertConstRow());
		
		String rowString = "insert into " + tableName + " values ( ";
		String row = "";

		HashSet<Integer> set = new HashSet();

		int const_row=rowlist.size();
		for (int i = 0; i < rows-const_row; i++) {
			row = rowString;
			for (int j = 0; j < cols.length; j++) {
				// System.out.println(i+" "+j);
				RandRow.delt = i;
				switch (cols[j].type) {
				case Column.CINT:

					int id;
					do {
						id = RandRow.randInt(100, 10000);
					} while (!set.add(id));
					row += id + ",";
					break;
				case Column.CBOOL:
					row += RandRow.randBool() + ",";
					break;
				case Column.CDOUBLE:
					row += RandRow.randDouble(0, 1000) + ",";
					break;
				case Column.CVARCHAR:
					row += RandRow.randVarChar(10) + ",";
					break;
				case Column.CDATE:
					row += RandRow.randDate("2000-01-01", "2020-12-29") + ",";
					break;
				case Column.CTIME:
					row += RandRow.randTime("00:00:00", "23:59:59") + ",";
					break;
				case Column.CDECIMAL:
					row += RandRow.randDecimal(0, 9999999) + ",";
					break;
				default:
					break;
				}
			}
			row = row.substring(0, row.length() - 1) + ");";
			rowlist.add(row);
		}

		SqlWriter.writeFile("insert_" + tableName + ".sql", rowlist);
		
		return rowlist;
	}

	public ArrayList<String> insertConstRow() {

		ArrayList<String> list = new ArrayList<String>();
		String[] rowStrings = {
				"insert into "+tableName+" values ( 1,'1',836.43,'abc','2014-06-15','08:22:09','6161473');",
				"insert into "+tableName+" values ( 2,'2',210.53,'uzcepbpqzs','2016-08-09','15:52:44','77777');",
				"insert into "+tableName+" values ( 3,'1',836.58,'def','2017-09-10','12:03:24','88888');",
				"insert into "+tableName+" values ( 4,'1',836.58,'tfmsorczsl','2017-09-10','20:09:10','77777');",
				"insert into "+tableName+" values ( 5,'2',1233.14,'abc','2003-02-21','12:03:24','88888');",
				"insert into "+tableName+" values ( 6,'1',210.53,'tfmsorczsl','2003-02-21','20:09:10','6546222');",
				"insert into "+tableName+" values ( 7,'2',836.58,'abc','2003-02-21','08:22:09','6546222');",
				"insert into "+tableName+" values ( 8,'2',1233.14,'tfmsorczsl','2016-08-09','12:03:24','77777');",
				"insert into "+tableName+" values ( 9,'1',836.58,'def','2017-09-10','08:22:09','6546222');",

		};

		for (int i = 0; i < rowStrings.length; i++) {
//			System.out.println(rowStrings[i]);
			list.add(rowStrings[i]);
		}
		return list;
	}

}
