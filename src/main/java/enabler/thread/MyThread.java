package enabler.thread;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import common.Constants;
import common.ScriptEngineHelper;
import utils.JSONUtil;
import utils.LogUtil;

public class MyThread extends Thread{
	ScriptEngine nashorn;
	String jsFilePath;
	String param;
	public MyThread(String jsFilePath,String param) {
		nashorn=ScriptEngineHelper.getInstance();
		this.jsFilePath=jsFilePath;
		this.param=param;
	}
	@Override
	public void run() {
		
		//注入$_request_param_$对象
		if(!JSONUtil.isJSON(param)){
			LogUtil.p("请求json格式不合法");
			return;
		}
		try {
			nashorn.eval("var $_inject_$=JSON.parse('"+param+"');\n");
		} catch (ScriptException e) {
			e.printStackTrace();
			LogUtil.p("请求json格式不合法");
			return;
		}
		//找到对应的js文件，执行
		String jsPath=Constants.jsDir+jsFilePath;
		LogUtil.p("线程要执行的文件路径为", jsPath);
		try {
			Reader r=new FileReader(jsPath);
			nashorn.eval(r);
		} catch (FileNotFoundException  e) {
			LogUtil.p("无法找到该js文件");
		}catch (ScriptException e) {
			LogUtil.p("js文件异常");
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		Map<String,String> m=new HashMap<>();
		m.put("hh", "1");
		m.put("hh1", "122");
		String param=JSONUtil.stringify(m);
		Constants.jsDir="d:/git/jscontroller/";
		MyThread thread=new MyThread("/crawler/_thread.js", param);
		thread.start();
	}
	
}
