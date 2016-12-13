package controller;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.script.Bindings;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import utils.JSONUtil;
import utils.LogUtil;
import utils.StringUtil;

public class MainController extends HttpServlet{
	private static final long serialVersionUID = 1L;
	
	ScriptEngineManager manager;
	ScriptEngine nashorn;
	String jsDir;
	public MainController() {
		manager=new ScriptEngineManager();
		nashorn=manager.getEngineByName("nashorn");
		
	}
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		jsDir=config.getInitParameter("rootPath");
		if(StringUtil.isBlank(jsDir)){
//			jsDir=getServletContext().getRealPath("/");
			jsDir=this.getClass().getClassLoader().getResource("/").getPath();
		}
		LogUtil.p("jsPath", jsDir);
		//注入一些默认的函数
		try {
			nashorn.eval("var $_jsDir_$='"+jsDir+"'");
			nashorn.eval(new FileReader(jsDir+"lib.js"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (ScriptException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.getWriter().append("not support for get request");
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		//获取请求url，内容
		String url=req.getRequestURI().substring(req.getContextPath().length())+".js";
		String reqBody=StringUtil.is2string(req.getInputStream());
		LogUtil.p("url", url);
		LogUtil.p("reqBody", reqBody);
		reqBody=reqBody.replace("\n", "");
		//注入$_request_param_$对象
		if(!JSONUtil.isJSON(reqBody)){
			failResponse(resp, "请求json格式不合法");
			return;
		}
		try {
			nashorn.eval("var $_request_param_$=JSON.parse('"+reqBody+"');\n");
		} catch (ScriptException e) {
			e.printStackTrace();
			failResponse(resp, "请求json格式不合法");
			return;
		}
//		nashorn.eval(reader, n);
		//找到对应的js文件，执行
		String jsPath=jsDir+url;
		LogUtil.p("要执行的文件路径为", jsPath);
		try {
			Reader r=new FileReader(jsPath);
			nashorn.eval(r);
			String res=(String) nashorn.eval("JSON.stringify($_response_$)").toString();
			LogUtil.p("res",res);
			if(res==null){
				failResponse(resp, "$_response_$为空");
			}
			if(!JSONUtil.isJSON(res)){
				failResponse(resp, "$_response_$格式不合法");
				return;
			}
			successResponse(resp, res);
		} catch (FileNotFoundException  e) {
			LogUtil.p("无法找到该js文件");
			failResponse(resp, "无法找到该js文件");
		}catch (ScriptException e) {
			failResponse(resp, "js文件异常");
			e.printStackTrace();
		}
	}
	private void output(HttpServletResponse resp,String msg) throws IOException{
		LogUtil.p("要输出的内容",msg);
		resp.addHeader("Content-Type", "text/json;charset=utf-8");
		resp.setCharacterEncoding("utf-8");
		resp.getWriter().append(msg);
	}
	public void failResponse(HttpServletResponse resp,String msg) throws IOException{
		Map<String,String> map=new HashMap<>();
		map.put("errorCode", "-100");
		map.put("errorMsg", msg);
		String outputStr=JSONUtil.stringify(map);
		output(resp, outputStr);
	}
	public void successResponse(HttpServletResponse resp,String msg) throws IOException{
		output(resp, msg);
	}
}
