package com.mkl.mkltest.utility;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class UtilitiesMethod {
	public static String unAccent(String s) {
        String temp = Normalizer.normalize(s, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        
        return pattern.matcher(temp).replaceAll("").replaceAll("Đ", "D").replaceAll("đ", "d");
    }
	
	public static <T> List<T> nestedListToArr(List<List<T>> list, Class<T> classOfT) {
		List<T> ts = new ArrayList<T>();
		
		for (List<T> lst: list) {
			for (T ele: lst) {
				ts.add(ele);
			}
		}
		
		return ts;
	}
}
