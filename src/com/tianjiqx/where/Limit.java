package com.tianjiqx.where;

import java.util.ArrayList;

public class Limit {
	
	public static ArrayList<String> generateLimit() {
		ArrayList<String> list =new ArrayList<String>();
		
		 int [] limit ={1,100,1000}; 
		for (int i =0; i< limit.length;i++)
		{
			list.add("Limit "+limit[i]);
		}
		
		return list;
	}

}
