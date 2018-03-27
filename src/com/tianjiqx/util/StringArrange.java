package com.tianjiqx.util;

import java.util.ArrayList;
import java.util.Collections;

//#################################
//# 程序说明：
//# 功能：字符串全排列
//# 作者：quxing
//# email: tianjiqx@126.com
//#################################


public class StringArrange {

	public static ArrayList<String> Permutation(String str)  
	{  
		if (str == null)  
			return null;  
		ArrayList<String> list = new ArrayList<String>();  
		char[] pStr = str.toCharArray();  

		Permutation(pStr, 0, list);  
		Collections.sort(list);  
		return list;  
	}  

	static void Permutation(char[] str, int i, ArrayList<String> list)  
	{  
		// 如果为空  
		if (str == null){
			return;  
		}
		// 如果i指向了最后一个字符  
		if (i == str.length - 1)  
		{  
			if (list.contains(String.valueOf(str)))
				return;
			list.add(String.valueOf(str));
		} else{  
			// i指向当前我们做排列操作的字符串的第一个字符  
			for (int j = i; j < str.length; j++)  
			{  
				// 把做排列操作的字符串的第一个字符和后面的所有字符交换  
				char temp = str[j];  
				str[j] = str[i];  
				str[i] = temp;  
				// 交换后对i后面的字符串递归做排列操作  
				Permutation(str, i + 1, list);  
				// 每一轮结束后换回来进行下一轮排列操作  
				temp = str[j];  
				str[j] = str[i];  
				str[i] = temp;  
			}  
		}  

	}  

}
