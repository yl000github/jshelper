package enabler.db;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


import utils.JSONUtil;
import utils.LogUtil;

public class DBEnabler extends AJdbc{
	public DBEnabler(boolean autoCommit) {
		try {
			conn.setAutoCommit(autoCommit);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public static DBEnabler open(boolean autoCommit){
		return new DBEnabler(autoCommit);
	}
	private Req trans(String src){
		return (Req) JSONUtil.parse(src, Req.class);
	}
	private String getSql(String src){
		Req req=trans(src);
//		LogUtil.p("req",req);
//		LogUtil.p("req c",req.param.get("c"));
		String sql=req.sql;
		Map<String,String> map=req.param;
		Set<String> keySet=map.keySet();
		for (String key : keySet) {
			String value=map.get(key);
			sql=sql.replace("@"+key, "'"+value+"'");
		}
		return sql;
	}
	public void commitAndClose() throws SQLException{
		 conn.commit();
		 conn=null;
	}
	public void rollbackAndClose() throws SQLException{
		 conn.rollback();
		 conn=null;
	}
	private String rs2str(ResultSet rs) throws SQLException{
		List<Map<String,String>> list=new ArrayList<>();
		ResultSetMetaData rsmd=rs.getMetaData();
		int colCount=rs.getMetaData().getColumnCount();
		while (rs.next()) {
			Map<String,String> map=new HashMap<>();
			for (int i = 1; i <= colCount; i++) {
				String columnName=rsmd.getColumnName(i);
				String value=rs.getString(i);
				map.put(columnName, value);
			}
			list.add(map);
		}
		return JSONUtil.stringify(list);
	}
	public String query(String src) throws SQLException{
		LogUtil.p("处理前的sql语句为",sql);
		String sql=getSql(src);
		LogUtil.p("要执行的sql语句为",sql);
		stmt=conn.createStatement();
		ResultSet rs=stmt.executeQuery(sql);
		String str=rs2str(rs);
		LogUtil.p("查询出的结果为",str);
		return str;
	}
	public String execute(String src) throws SQLException{
		LogUtil.p("处理前的sql语句为",sql);
		String sql=getSql(src);
		LogUtil.p("要执行的sql语句为",sql);
		stmt=conn.createStatement();
		//批处理？
		boolean rs=stmt.execute(sql);
		String str=String.valueOf(rs);
		LogUtil.p("查询出的结果为",str);
		return str;
	}
	public static void main(String[] args) {
		System.out.println("==");
	}
}
