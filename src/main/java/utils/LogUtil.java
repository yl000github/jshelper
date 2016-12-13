package utils;

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.Map;

public class LogUtil {
	public static void p(String flag){
		p(flag,null);
	}
	public static void p(String flag,Object ob){
//		System.out.println(ob.getClass().getName());
		String c="";
		if(ob!=null&&!ob.equals("")){
			try {
				c=JSONUtil.stringify(ob);
			} catch (Exception e) {
				c=(String) ob;
			}
		}
		System.out.println("===="+flag+"===="+c);
	}
	public static void main(String[] args) {
		String [] arr={"1","2"};
		Integer [] arrInt={1,2};
		Map<String,String> m=new HashMap<>();
		m.put("1", "ff");
		m.put("2", "ff");
		p("String","ssssssssss");
		p("String []",arr);
		p("int []",arrInt);
		p("Map<String,String>",m);
	}
}
