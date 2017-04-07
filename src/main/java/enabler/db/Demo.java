package enabler.db;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
public class Demo {
	private static Connection getConn() {
	    String driver = "com.mysql.jdbc.Driver";
	    String url = "jdbc:mysql://192.168.1.20:3306/dlb";
	    String username = "ymt";
	    String password = "yimiaotong2015";
	    Connection conn = null;
	    try {
	        Class.forName(driver); //classLoader,加载对应驱动
	        conn = (Connection) DriverManager.getConnection(url, username, password);
	    } catch (ClassNotFoundException e) {
	        e.printStackTrace();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return conn;
	}
	public static void main(String[] args) {
		   Connection conn = getConn();
		    String sql = "insert into agreement(agreementNO,userID) values('Aasdf3','9999');";
		    PreparedStatement pstmt;
		    try {
		        pstmt = (PreparedStatement) conn.prepareStatement(sql);
		        int id= pstmt.executeUpdate();
		        ResultSet results=pstmt.getGeneratedKeys();
		        int num = -1;
	            if(results.next())
	            {
	                num = results.getInt(1);
	            }
		        System.out.println("id="+num);
		        pstmt = (PreparedStatement)conn.prepareStatement("select * from agreement where id="+num);
		        ResultSet rs = pstmt.executeQuery();
		        int col = rs.getMetaData().getColumnCount();
		        System.out.println("============================");
		        while (rs.next()) {
		            for (int i = 1; i <= col; i++) {
		                System.out.print(rs.getString(i) + "\t");
		                if ((i == 2) && (rs.getString(i).length() < 8)) {
		                    System.out.print("\t");
		                }
		             }
		            System.out.println("");
		        }
		        
		        
		        
		        pstmt.close();
		        conn.close();
		    } catch (SQLException e) {
		        e.printStackTrace();
		    }
	}
}
