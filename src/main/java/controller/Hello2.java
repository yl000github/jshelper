package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import utils.LogUtil;
import utils.StringUtil;

public class Hello2 extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public Hello2() {
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		LogUtil.p("req.getQueryString()", req.getQueryString());
		LogUtil.p("req.getParameterMap()", req.getParameterMap());
		String is=StringUtil.is2string(req.getInputStream());
		LogUtil.p("post",is);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(req, response);
	}

}
