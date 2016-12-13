package utils;

import java.io.IOException;
import java.io.InputStream;

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
	
}
