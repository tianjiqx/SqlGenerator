package com.tianjiqx.table;


public class Table {
	
	public String tableName;
	public String aliasName;
	public Column [] cols;
	public Column [] joinCols;
	public Column [] whereCols;
	public Column [] groupbyCols;
	public Column [] orderbyCols;
	

	public Table(String tableName, String aliasName, Column[] cols, Column[] joinCols, Column[] whereCols) {
		super();
		this.tableName = tableName;
		this.aliasName = aliasName;
		this.cols = cols;
		this.joinCols = joinCols;
		this.whereCols = whereCols;
		this.groupbyCols = whereCols;
		this.orderbyCols = whereCols;
	}


	public Table(String tableName, String aliasName, Column[] cols, Column[] joinCols, Column[] whereCols,
			Column[] groupbyCols, Column[] orderbyCols) {
		super();
		this.tableName = tableName;
		this.aliasName = aliasName;
		this.cols = cols;
		this.joinCols = joinCols;
		this.whereCols = whereCols;
		this.groupbyCols = groupbyCols;
		this.orderbyCols = orderbyCols;
	}
	
	


	

}
