package enabler.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import enabler.txt.ConfigReader;

public abstract class AJdbc {
	Connection conn = null;
	Statement stmt = null;
	ResultSet rs = null;
	String url = null;
	String user = null;
	String password = null;
	String driver = null;
	String sql = null;

	public AJdbc() {
		try {
			prepare();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void prepare() throws Exception {
		driver=ConfigReader.getValue("driver");
		Class.forName(driver); // 加载mysq驱动
//		url = "jdbc:mysql://localhost:3306/myself?useUnicode=true&&characterEncoding=utf-8&autoReconnect=true";
//		user = "root";
//		password ="yl123";
		url=ConfigReader.getValue("url");
		user=ConfigReader.getValue("user");
		password=ConfigReader.getValue("password");
		conn = DriverManager.getConnection(url, user, password);
	}

	public void shut() {
		try {
			if (rs != null) {
				rs.close();
				rs = null;
			}
			if (stmt != null) {
				stmt.close();
				stmt = null;
			}
			if (conn != null) {
				conn.close();
				conn = null;
			}
		} catch (Exception e) {
			System.out.println("数据库关闭错误");
		}
	}
	public void printResultSet(ResultSet rst) throws SQLException{
		int len=rst.getMetaData().getColumnCount();
		while(rst.next()){
			for(int i=1;i <=len;i++) 
			{ 
				System.out.print(rs.getString(i)+ "\t ");//一列一列的读 
			} 
			System.out.println();
		}
	}
}
