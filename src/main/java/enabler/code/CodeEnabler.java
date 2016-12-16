package enabler.code;

import java.io.UnsupportedEncodingException;

//相关编码转换
public class CodeEnabler {
	public  String urlEncode(String src,String charset) throws UnsupportedEncodingException{
		return java.net.URLEncoder.encode(src,charset);   
	}
	public  String urlDecode(String src,String charset) throws UnsupportedEncodingException{
		return java.net.URLDecoder.decode(src,charset);   
	}
	public static void main(String[] args) throws UnsupportedEncodingException {
		
//		String charset="gb2312";
//		String uu=urlEncode("人",charset);
//		System.out.println(uu);
//		System.out.println(urlDecode(uu,charset));
	}
}
