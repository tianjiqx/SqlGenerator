package com.tianjiqx.util;

import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;

public class SqlWriter {

	
	public static int batchCount=300; // 一批写入大小
	
	//清空文件
	public static void clear(String filename) {
		try {
			FileOutputStream fos = new FileOutputStream(filename);
			BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(
					fos);
			bufferedOutputStream.write("".getBytes());
			bufferedOutputStream.flush();
			bufferedOutputStream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	
	public static void writeFile(String filename, ArrayList<String> list) {

		long st = System.currentTimeMillis();
		try {
			FileOutputStream fos = new FileOutputStream(filename);
			BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(
					fos);
			String tmp = "";
			for (int i = 0; i < list.size(); i++) {
				tmp = list.get(i) + "\n";
				bufferedOutputStream.write(tmp.getBytes());

			}
			bufferedOutputStream.flush();
			bufferedOutputStream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {

		}
		long et = System.currentTimeMillis();

		System.out.println("write file time :" + (et - st) / 1000);

	}

	public static void appendWriteFile(String filename, ArrayList<String> list) {

		long st = System.currentTimeMillis();
		try {
			FileOutputStream fos = new FileOutputStream(filename, true);
			BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(
					fos);
			String tmp = "";
			for (int i = 0; i < list.size(); i++) {
				tmp = list.get(i) + "\n";
				bufferedOutputStream.write(tmp.getBytes());
			}
			
			bufferedOutputStream.flush();
			bufferedOutputStream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {

		}
		long et = System.currentTimeMillis();

		System.out.println("write file time :" + (et - st) / 1000);

	}
}
