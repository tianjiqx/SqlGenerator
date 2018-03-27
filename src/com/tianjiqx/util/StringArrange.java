package com.tianjiqx.util;

import java.util.ArrayList;
import java.util.Collections;

//#################################
//# ����˵����
//# ���ܣ��ַ���ȫ����
//# ���ߣ�quxing
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
		// ���Ϊ��  
		if (str == null){
			return;  
		}
		// ���iָ�������һ���ַ�  
		if (i == str.length - 1)  
		{  
			if (list.contains(String.valueOf(str)))
				return;
			list.add(String.valueOf(str));
		} else{  
			// iָ��ǰ���������в������ַ����ĵ�һ���ַ�  
			for (int j = i; j < str.length; j++)  
			{  
				// �������в������ַ����ĵ�һ���ַ��ͺ���������ַ�����  
				char temp = str[j];  
				str[j] = str[i];  
				str[i] = temp;  
				// �������i������ַ����ݹ������в���  
				Permutation(str, i + 1, list);  
				// ÿһ�ֽ����󻻻���������һ�����в���  
				temp = str[j];  
				str[j] = str[i];  
				str[i] = temp;  
			}  
		}  

	}  

}
