package utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

public class StringUtil {
	public static String is2string(InputStream in) throws IOException {
		StringBuffer out = new StringBuffer();
		byte[] b = new byte[4096];
		for (int n; (n = in.read(b)) != -1;) {
			out.append(new String(b, 0, n));
		}
		return out.toString();
	}
	public static boolean isBlank(String s)  {
		if(s==null||s.trim().equals(""))return true;
		else return false;
	}
	public static String getFixLenthString(int strLength) {  
	    Random rm = new Random();  
	    // 获得随机数  
	    double pross = (1 + rm.nextDouble()) * Math.pow(10, strLength);  
	    // 将获得的获得随机数转化为字符串  
	    String fixLenthString = String.valueOf(pross);  
	    // 返回固定的长度的随机数  
	    return fixLenthString.substring(1, strLength + 1);  
	}
	public static void main(String[] args) {
		for (int i = 0; i < 5; i++) {
			System.out.println(getFixLenthString(6));
		}
	}
}
