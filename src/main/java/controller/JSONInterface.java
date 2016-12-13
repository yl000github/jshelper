package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import utils.JSONUtil;
import utils.LogUtil;
import utils.StringUtil;

public abstract class JSONInterface extends HttpServlet{
	private static final long serialVersionUID = 1L;
	
	public  abstract  JsonObject handler(JsonObject request);
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.getWriter().append("json请求暂时不支持get方法");
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		super.doPost(req, resp);
//		LogUtil.p("req.getQueryString()", req.getQueryString());
//		LogUtil.p("req.getParameterMap()", req.getParameterMap());
		String post=StringUtil.is2string(req.getInputStream());
		LogUtil.p("请求的内容为", post);
		JsonElement je=JSONUtil.parse(post);
		JsonObject jo=je.getAsJsonObject();
		JsonObject rs=handler(jo);
		String output=JSONUtil.stringify(rs);
		LogUtil.p("输出的结果为", output);
		resp.getWriter().append(output);
	}
}
