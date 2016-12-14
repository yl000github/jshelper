package enabler.txt;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import utils.LogUtil;

public class ConfigReader {
	static String  properties="config.properties";
	//所有的配置约定为键值对
//	static Map<String,String> map;
	static Properties p=new Properties();
	static{
//		map=new HashMap<>();
	}
	public static void setMap(String dir){
		try {
			FileInputStream fis = new FileInputStream(dir+properties);
			p.load(fis);
		} catch (Exception e) {
			e.printStackTrace();
			LogUtil.p("读取配置文件失败",dir+properties);
		}
	}
	public static String getValue(String key){
		return p.getProperty(key, "");
	}
	public static void main(String[] args) {
		setMap("D:/git/jscontroller/");
		System.out.println(getValue("url"));
	}
}
