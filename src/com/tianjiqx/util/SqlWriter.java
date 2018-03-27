package com.tianjiqx.util;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class SqlWriter {

	public static void writeFile (String filename,ArrayList<String> list) {
		
		try {
			FileOutputStream fos = new FileOutputStream(filename);
			String tmp="";
			for (int i = 0; i < list.size(); i++) {
				tmp=list.get(i)+"\n";
				fos.write(tmp.getBytes());
				
			}
			fos.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			
		}
		
		
	}
}
