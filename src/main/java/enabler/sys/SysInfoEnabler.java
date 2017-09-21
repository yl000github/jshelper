package enabler.sys;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class SysInfoEnabler {
	public static String getEnv(String key){
		return System.getenv(key);
	}
	public static Map<String,String> getAllEnv(){
		return System.getenv();
	}
	public static void main(String[] args) {
		System.out.println(getEnv("APPDATA"));
	}
}
