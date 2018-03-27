package com.tianjiqx.table;



public class Column {

	/**
	 * type:
	 * 1: int
	 * 2: bool
	 * 3: double
	 * 4: varchar
	 * 5: date
	 * 6: time
	 * 7: decimal
	 */
	public final static int CINT=1;
	public final static int CBOOL=2;
	public final static int CDOUBLE=3;
	public final static int CVARCHAR=4;
	public final static int CDATE=5;
	public final static int CTIME=6;
	public final static int CDECIMAL=7;
	
	public int type;
	public String colName;
	public Column(int type, String colName) {
		super();
		this.type = type;
		this.colName = colName;
	}
	
}
