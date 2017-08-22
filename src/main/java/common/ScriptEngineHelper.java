package common;

import java.io.FileNotFoundException;
import java.io.FileReader;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class ScriptEngineHelper {
	private static ScriptEngine instance;
	private ScriptEngineHelper(){
		ScriptEngineManager manager;
		manager=new ScriptEngineManager();
		instance=manager.getEngineByName("nashorn");
		//注入一些默认的函数
		try {
			instance.eval("var $_jsDir_$='"+Constants.jsDir+"'");
			instance.eval(new FileReader(Constants.jsDir+"lib.js"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (ScriptException e) {
			e.printStackTrace();
		}
	}
	public static ScriptEngine getInstance(){
		if(instance==null){
			new ScriptEngineHelper();
			return instance;
		}else{
			return instance;
		}
	}
}
